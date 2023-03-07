package com.mycompany.mywebapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.mywebapp.shared.ServerState;
import com.mycompany.mywebapp.shared.TACSMessage;

public interface TimeAndClockServiceAsync {

    void sendMessage(TACSMessage message, AsyncCallback<String> callback)
            throws IllegalArgumentException;

    void registration(AsyncCallback<String> callback);

    void fetchMyMessage(Integer source, AsyncCallback<TACSMessage> callback) throws IllegalArgumentException;

    void deRegistration(Integer pid, AsyncCallback<String> callback) throws IllegalArgumentException;

    void fetchActiveClients(AsyncCallback<ServerState> callback);

}
