package com.framework.file.pojo;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OperationType implements Serializable {

    public  enum OPERATIONTYPE{
        GET("get"), SAVE("save"), UPDATE("update"), DEL("del");

        private String type;


        OPERATIONTYPE(String type) {
            this.type=type;
        }
    }

    private String operationType;

}
