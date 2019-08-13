package com.accenture.flowershop.fe.ws;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface FlowersStockWebService {

    @WebResult(name = "result")
    int add(@WebParam(name = "a") int a);

}
