package com.mycompany.mywebapp.client;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.mywebapp.shared.ActiveClients;
import com.mycompany.mywebapp.shared.GodViewLogs;
import com.mycompany.mywebapp.shared.PaxosAcceptReq;
import com.mycompany.mywebapp.shared.PaxosAckReq;
import com.mycompany.mywebapp.shared.PaxosEnquireReq;
import com.mycompany.mywebapp.shared.PaxosPrepareReq;

public interface PaxosServiceAsync {
  void addDelay(Integer source, Integer delayValue, AsyncCallback<String> callback) throws IllegalArgumentException;

  void register(AsyncCallback<Integer> callback);
  void deRegister(AsyncCallback<String> callback);

  void pollActiveClients(AsyncCallback<ActiveClients> callback);

  void pollGodViewLogs(AsyncCallback<GodViewLogs> callback);




  void prepare(PaxosPrepareReq req, AsyncCallback<String> callback);
  void acknowledge(PaxosAckReq req, AsyncCallback<String> callback);
  void accpet(PaxosAcceptReq req, AsyncCallback<String> callback);
  void enquire(PaxosEnquireReq req, AsyncCallback<String> callback);

  void pollPrepare(Integer source, AsyncCallback<PaxosPrepareReq> callback);
  void pollAck(Integer source, AsyncCallback<PaxosAckReq> callback);
  void pollAccept(Integer source, AsyncCallback<PaxosAcceptReq> callback);
  void pollEnquire(Integer source, AsyncCallback<PaxosEnquireReq> callback);

    
}
