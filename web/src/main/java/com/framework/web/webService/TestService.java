package com.framework.web.webService;

import com.framework.web.pojo.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.io.File;

@WebService(name = "test")
public interface TestService {

    @WebResult
    @WebMethod
    User test(@WebParam(name = "data") String data);

    @WebResult
    @WebMethod
    User t2(@WebParam(name = "file") File file);
}
