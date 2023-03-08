package com.mycompany.mywebapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mycompany.mywebapp.shared.ServerState;
import com.mycompany.mywebapp.shared.TACSMessage;
import java.util.ArrayList;


@RemoteServiceRelativePath("timeandclock")
public interface TimeAndClockService extends RemoteService{
    String sendMessage(TACSMessage message) throws IllegalArgumentException;

    String registration();

    String deRegistration(Integer pid) throws IllegalArgumentException;

    ServerState fetchActiveClients();

    TACSMessage fetchMyMessage(Integer source) throws IllegalArgumentException;
}
