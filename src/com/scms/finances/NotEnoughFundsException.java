/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scms.finances;

/**
 *
 * @author Admin
 */
public class NotEnoughFundsException extends RuntimeException {

    /**
     * Creates a new instance of <code>NotEnoughFundsException</code> without
     * detail message.
     */
    public NotEnoughFundsException() {
        //It must be mandatory to make it checked because when funds are found to be low you can perform some operation
    }

    /**
     * Constructs an instance of <code>NotEnoughFundsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotEnoughFundsException(String msg) {
        super(msg);
    }
}
