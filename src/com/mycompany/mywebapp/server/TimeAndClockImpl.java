package com.mycompany.mywebapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.mywebapp.client.TimeAndClockService;
import com.mycompany.mywebapp.shared.ServerState;
import com.mycompany.mywebapp.shared.TACSMessage;

import java.util.HashMap;
import java.util.ArrayDeque;
import javax.servlet.ServletException;
import org.eclipse.jetty.io.ssl.ALPNProcessor.Server;
import java.util.Deque;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashSet;

@SuppressWarnings("serial")
public class TimeAndClockImpl extends RemoteServiceServlet implements TimeAndClockService {

	HashMap<Integer,Deque<TACSMessage>> pidToPoolmapping;

	Logger logger;

	@Override
	public String sendMessage(TACSMessage message) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		logger.info("Received Message Back: " + message);
		Integer pid = Integer.valueOf(message.targetPid);
		if (!pidToPoolmapping.containsKey(pid)){
			throw new IllegalArgumentException("Provided Pid Process does not exist");
		}

		// Introduce sleep

		long currServerTime = System.currentTimeMillis() / 1000L;
		message.receiveTime = Math.max(currServerTime,message.sendTime) + 1;

		pidToPoolmapping.get(pid).addLast(message);
		return "Pooled!";
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		pidToPoolmapping = new HashMap<>();
		logger = Logger.getLogger(getServletName());
		logger.info("Initialized Server");
	}

	int pidMax = 1;
	@Override
	public String registration(){
		int pidToReturn = pidMax;
		pidMax+=1;
		pidToPoolmapping.put(pidToReturn, new ArrayDeque<>());
		logger.info("Registered: PID " + pidToReturn);
		return Integer.toString(pidToReturn);
	}

	@Override
	public TACSMessage fetchMyMessage(Integer source) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		if(!pidToPoolmapping.containsKey(source)){
			throw new IllegalArgumentException("You have not registered yet.");
		}
		TACSMessage message = pidToPoolmapping.get(source).pollFirst();
		logger.info("Sending message back: " + message);
		return message;
	}

	@Override
	public String deRegistration(Integer pid) throws IllegalArgumentException{
		if(!pidToPoolmapping.containsKey(pid)){
			throw new IllegalArgumentException("Already deregistered");
		}
		pidToPoolmapping.remove(pid);
		logger.info("Removed Pid from active clients: " + pid);
		return "OK";
	}

	@Override
	public ServerState fetchActiveClients() {
		// return ",".join(pidToPoolmapping.keySet())
		ServerState serverState = new ServerState();
		serverState.availableClients = new ArrayList<Integer>(pidToPoolmapping.keySet());
		return serverState;
	}

    
}

