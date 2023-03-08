package com.mycompany.mywebapp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class PaxosReq implements IsSerializable{
    public Integer target;
    public Integer source;
}
