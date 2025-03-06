package com.example.school.excption;

import lombok.Data;

/**
 * This class defines the schema of the response. It is used to encapsulate data prepared by
 * the server side, this object will be serialized to JSON before sent back to the client end.
*/

@Data
public class Result {
  
    private boolean flag; // two values: true means success, false means not success

    private Integer code; // Status Code, e.g. 404, 200, 401

    private String message; // Response message

    private Object data; // the Response payload

    public Result(){}

    public Result(boolean flag, Integer code, String message){
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    
    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
}
