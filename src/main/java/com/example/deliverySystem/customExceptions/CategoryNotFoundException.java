package com.example.deliverySystem.customExceptions;

public class CategoryNotFoundException extends Exception{

    public CategoryNotFoundException(String message){
        super(message);
    }
}
