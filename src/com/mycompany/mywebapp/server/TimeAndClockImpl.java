package com.mycompany.mywebapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.mywebapp.client.TimeAndClockService;
import com.mycompany.mywebapp.shared.TACSMessage;

public class TimeAndClockImpl extends RemoteServiceServlet implements TimeAndClockService {

	@Override
	public String sendMessage(TACSMessage message) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
	}

	@Override
	public TACSMessage fetchMyMessage(int source) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'fetchMyMessage'");
	}

    
}

