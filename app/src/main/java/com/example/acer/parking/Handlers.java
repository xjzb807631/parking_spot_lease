package com.example.acer.parking;
import client.*;
/**
 * Created by bao on 2017/6/13.
 */

public class Handlers {
    public static ClientAccountHandler clientAccountHandler;
    public static ClientMainHandler clientMainHandler;
    public static ClientSpotHandler clientSpotHandler;
    public static void start()
    {
        clientAccountHandler=new ClientAccountHandler();
        clientMainHandler=new ClientMainHandler();
        clientSpotHandler=new ClientSpotHandler();
    }
}
