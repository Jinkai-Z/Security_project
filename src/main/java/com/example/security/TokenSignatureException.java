/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.security;

/**
 * Exception class for invalid token signatures on validation
 * @author Justin Heinrichs
 */
public class TokenSignatureException extends Exception {
    public TokenSignatureException(String errorMessage) {
        super(errorMessage);
    }
}
