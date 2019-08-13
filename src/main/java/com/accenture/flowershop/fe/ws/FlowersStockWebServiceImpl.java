package com.accenture.flowershop.fe.ws;

import javax.jws.WebService;

@WebService(endpointInterface = "com.accenture.flowershop.fe.ws.FlowersStockWebService")
public class FlowersStockWebServiceImpl implements FlowersStockWebService {

    @Override
    public int add(int a) {
        return a + a;
    }

}
