package com.mycompany.mywebapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.mywebapp.shared.TACSMessage;

public interface TimeAndClockServiceAsync {

    void sendMessage(TACSMessage message, AsyncCallback<String> callback) throws IllegalArgumentException;

    void fetchMyMessage(int source,AsyncCallback<String> callback) throws IllegalArgumentException;
    
}
