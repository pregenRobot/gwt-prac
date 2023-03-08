package com.mycompany.mywebapp.shared;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.HashSet;

public class ActiveClients implements IsSerializable{

    public HashSet<Integer> clients;
}
