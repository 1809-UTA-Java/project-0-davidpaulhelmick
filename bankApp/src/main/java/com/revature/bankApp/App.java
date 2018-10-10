package com.revature.bankApp;

/**
 * BankingApp by davidpaulhelmick
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        State.state = new OpeningState();
        State.run();
    }
}
