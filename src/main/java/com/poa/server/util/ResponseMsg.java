package com.poa.server.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Basic Response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMsg {

    private Object data;

    private String message;

    private boolean success;



    public static ResponseMsg ok() {
        ResponseMsg msg = new ResponseMsg();
        msg.setSuccess(true);
        msg.setMessage("Successful operation!");
        return msg;
    }

    public static ResponseMsg ok(Object data) {
        ResponseMsg ok = ok();
        ok.setData(data);
        return ok;
    }

    public static ResponseMsg msg(String message) {
        ResponseMsg ok = ok();
        ok.setMessage(message);
        return ok;
    }

    public static ResponseMsg error(String message) {
        ResponseMsg msg = new ResponseMsg();
        msg.setSuccess(true);
        msg.setMessage(message);
        return msg;
    }



}
