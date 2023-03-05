package com.mycompany.mywebapp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TACSMessage implements IsSerializable{
    public int sourcePid;
    public int targetPid;
    public String message;
}
