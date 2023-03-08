package com.mycompany.mywebapp.server;

import java.util.Deque;
import java.util.HashSet;
import javax.print.attribute.standard.MediaSize.JIS;
import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.mywebapp.client.PaxosService;
import com.mycompany.mywebapp.client.PaxosServiceAsync;
import com.mycompany.mywebapp.shared.ActiveClients;
import com.mycompany.mywebapp.shared.GodViewLogs;
import com.mycompany.mywebapp.shared.PaxosAcceptReq;
import com.mycompany.mywebapp.shared.PaxosAckReq;
import com.mycompany.mywebapp.shared.PaxosEnquireReq;
import com.mycompany.mywebapp.shared.PaxosPrepareReq;
import com.mycompany.mywebapp.shared.PaxosReq;
import com.shapesecurity.salvation2.Values.Hash;
import java.sql.Time;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class PaxosServiceImpl extends RemoteServiceServlet implements PaxosService{

    HashMap<Integer, Integer> pidToDelayMap = new HashMap<>();

    HashSet<Integer> registeredClients = new HashSet<>();
    HashMap<Integer, Deque<PaxosPrepareReq>> prepareMessagePoolMap = new HashMap<>();
    HashMap<Integer, Deque<PaxosAckReq>> ackMessagePoolmap = new HashMap<>();
    HashMap<Integer, Deque<PaxosAcceptReq>> acceptMessagePoolMap = new HashMap<>();
    HashMap<Integer, Deque<PaxosEnquireReq>> enquireMessagePoolMap = new HashMap<>();

    ArrayList<String> godViewLogs = new ArrayList<String>();

    @Override
    public String addDelay(Integer source, Integer delayValue) throws IllegalArgumentException {
        inwardsPreprocessing(source, false);

        pidToDelayMap.put(source, delayValue);
        
        godViewLogs.add(String.format("<DELAY source: %d delay: %d>",source,delayValue));
        
        return "OK";
    }

    int registerTracker = 1;

    @Override
    public Integer register() {
        Integer pidToRegister = Integer.valueOf(registerTracker);
        pidToDelayMap.put(pidToRegister, 0);
        registeredClients.add(pidToRegister);

        prepareMessagePoolMap.put(pidToRegister, new ArrayDeque<>());
        ackMessagePoolmap.put(pidToRegister, new ArrayDeque<>());
        acceptMessagePoolMap.put(pidToRegister, new ArrayDeque<>());
        enquireMessagePoolMap.put(pidToRegister, new ArrayDeque<>());
        
        godViewLogs.add(String.format("<REGISTER pid: %d>", pidToRegister));
        
        return pidToRegister;
    }

    @Override
    public ActiveClients pollActiveClients() {
        
        ActiveClients result = new ActiveClients();
        result.clients = registeredClients;
        return result;
    }

    @Override
    public GodViewLogs pollGodViewLogs() {
        GodViewLogs result = new GodViewLogs();
        result.logLines = godViewLogs;
        return result;
    }

    private void outwardsPreprocess(PaxosReq req) throws IllegalArgumentException{
        
        if (req instanceof PaxosPrepareReq) {
            if(!prepareMessagePoolMap.containsKey(req.target)){
                throw new IllegalArgumentException("Target has not registered yet...");
            }
            if(!pidToDelayMap.containsKey(req.source)){
                throw new IllegalArgumentException("Source has not registered yet...");
            }
        }
        if (req instanceof PaxosAckReq){
            if(!ackMessagePoolmap.containsKey(req.target)){
                throw new IllegalArgumentException("Target has not registered yet...");
            }
            if(!pidToDelayMap.containsKey(req.source)){
                throw new IllegalArgumentException("Source has not registered yet...");
            }
        }
        if(req instanceof PaxosAcceptReq){
            if(!acceptMessagePoolMap.containsKey(req.target)){
                throw new IllegalArgumentException("Target has not registered yet...");
            }
            if(!pidToDelayMap.containsKey(req.source)){
                throw new IllegalArgumentException("Source has not registered yet...");
            }
        }
        if(req instanceof PaxosEnquireReq){
            if(!enquireMessagePoolMap.containsKey(req.target)){
                throw new IllegalArgumentException("Target has not registered yet...");
            }
            if(!pidToDelayMap.containsKey(req.source)){
                throw new IllegalArgumentException("Source has not registered yet...");
            }
        }
        try{
            Thread.sleep(pidToDelayMap.get(req.source));
        }catch(InterruptedException e){
            throw new IllegalArgumentException("ERROR WITH SIMULATING SLEEPING");
        }
    }


    @Override
    public String prepare(PaxosPrepareReq req) throws IllegalArgumentException {
        outwardsPreprocess(req);
        prepareMessagePoolMap.get(req.target).push(req);
        godViewLogs.add(String.format("<PREPARE source: %d target:%d ballotId: %d>", req.source, req.target, req.ballotId));
        return "OK";
    }

    @Override
    public String acknowledge(PaxosAckReq req) throws IllegalArgumentException {
        outwardsPreprocess(req);
        ackMessagePoolmap.get(req.target).push(req);
        if(req.previouslyAccepted){
            godViewLogs.add(String.format("<ACK source: %d target: %d previouslyAccepted: %s ballotId: %d decree: %s>",req.source,req.target, "YES", req.ballotId, req.decree));
        }else{
            godViewLogs.add(String.format("<ACK source: %d target: %d prviouslyAccepted: %s>", req.source, req.target, "NO"));
        }
        return "OK";
    }

    @Override
    public String accept(PaxosAcceptReq req) throws IllegalArgumentException {
        outwardsPreprocess(req);
        godViewLogs.add(String.format("<ACCEPT source: %d target: %d ballotID: %d>", req.source, req.target, req.ballotId));
        return "OK";
    }

    @Override
    public String enquire(PaxosEnquireReq req) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        outwardsPreprocess(req);
        godViewLogs.add(String.format("<ENQUIRE source: %d target: %d decree: %s>",req.source,req.target,req.decree));
        return "OK";
    }

    private void inwardsPreprocessing(Integer source, boolean sleep){

        if(!pidToDelayMap.containsKey(source)){
            throw new IllegalArgumentException("Source has not registered yet...");
        }

        if(sleep){
            try{
                Thread.sleep(pidToDelayMap.get(source));
            }catch(InterruptedException e){
                throw new IllegalArgumentException("ERROR WITH SIMULATING SLEEPING");
            }

        }
    }


    @Override
    public PaxosPrepareReq pollPrepare(Integer source) throws IllegalArgumentException {
        inwardsPreprocessing(source,true);
        return prepareMessagePoolMap.get(source).pollFirst();
    }

    @Override
    public PaxosAckReq pollAck(Integer source) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        inwardsPreprocessing(source,true);
        return ackMessagePoolmap.get(source).pollFirst();
    }

    @Override
    public PaxosAcceptReq pollAccept(Integer source) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        inwardsPreprocessing(source,true);
        return acceptMessagePoolMap.get(source).pollFirst();
    }

    @Override
    public PaxosEnquireReq pollEnquire(Integer source) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        inwardsPreprocessing(source,true);
        return enquireMessagePoolMap.get(source).pollFirst();
    }

    @Override
    public String deRegister(Integer source) {
        // TODO Auto-generated method stub
        inwardsPreprocessing(source,false);
        pidToDelayMap.remove(source);

        registeredClients.remove(source);
        prepareMessagePoolMap.remove(source);
        ackMessagePoolmap.remove(source);
        acceptMessagePoolMap.remove(source);
        enquireMessagePoolMap.remove(source);
        
        return "OK";
    }

    
}
