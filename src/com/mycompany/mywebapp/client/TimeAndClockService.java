package com.mycompany.mywebapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mycompany.mywebapp.shared.TACSMessage;


@RemoteServiceRelativePath("timeandclock")
public interface TimeAndClockService extends RemoteService{
    String sendMessage(TACSMessage message) throws IllegalArgumentException;

    TACSMessage fetchMyMessage(int source) throws IllegalArgumentException;
}
