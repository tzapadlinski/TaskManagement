package com.structure;

public class MyException extends IndexOutOfBoundsException{
    MyException() {};

    MyException(String message) {
        super(message);
    }
}
