package com.desafio.serasa.experian.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CustomException extends Exception {
    private final int errorCode;
    private final String message;
}
