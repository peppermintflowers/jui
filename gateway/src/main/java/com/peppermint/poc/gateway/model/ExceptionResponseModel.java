package com.peppermint.poc.gateway.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponseModel{
    private String errorCode;
    private String errorMessage;
    private String errorDetails;
    private Date timestamp;

}
