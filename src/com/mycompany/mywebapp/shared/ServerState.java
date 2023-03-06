package com.mycompany.mywebapp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

public class ServerState implements IsSerializable{

    public ArrayList<Integer> availableClients;
}
