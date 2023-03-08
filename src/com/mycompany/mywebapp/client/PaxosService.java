package com.mycompany.mywebapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mycompany.mywebapp.shared.ActiveClients;
import com.mycompany.mywebapp.shared.GodViewLogs;
import com.mycompany.mywebapp.shared.PaxosAcceptReq;
import com.mycompany.mywebapp.shared.PaxosAckReq;
import com.mycompany.mywebapp.shared.PaxosEnquireReq;
import com.mycompany.mywebapp.shared.PaxosPrepareReq;

@RemoteServiceRelativePath("paxos")
public interface PaxosService extends RemoteService{

    String addDelay(Integer source, Integer delayValue) throws IllegalArgumentException;

    Integer register();

    String deRegister(Integer source);

    ActiveClients pollActiveClients();

    GodViewLogs pollGodViewLogs();
    

    String prepare(PaxosPrepareReq req) throws IllegalArgumentException;

    String acknowledge(PaxosAckReq req) throws IllegalArgumentException;

    String accept(PaxosAcceptReq req) throws IllegalArgumentException;

    String enquire(PaxosEnquireReq req) throws IllegalArgumentException;
    

    PaxosPrepareReq pollPrepare(Integer source) throws IllegalArgumentException;

    PaxosAckReq pollAck(Integer source) throws IllegalArgumentException;

    PaxosAcceptReq pollAccept(Integer source) throws IllegalArgumentException;

    PaxosEnquireReq pollEnquire(Integer source) throws IllegalArgumentException;
    
}
