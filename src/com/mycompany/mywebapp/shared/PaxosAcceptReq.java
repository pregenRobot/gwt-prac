package com.mycompany.mywebapp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PaxosAcceptReq extends PaxosReq{

    public Integer ballotId;
    public Integer decree;
}
