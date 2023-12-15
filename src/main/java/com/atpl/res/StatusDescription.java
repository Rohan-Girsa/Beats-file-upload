package com.atpl.res;

import lombok.Data;

@Data
public class StatusDescription {

    private int statusCode;
    private String statusMessage;
    private int insertCount;
    private int duplicateCount;
}
