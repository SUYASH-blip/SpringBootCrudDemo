package com.example.demo.exceptions;

public class DuplicateResource extends RuntimeException{
    public DuplicateResource(String message){
        super(message);
    }
}
