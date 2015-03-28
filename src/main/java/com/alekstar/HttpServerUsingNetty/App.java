package com.alekstar.HttpServerUsingNetty;

public class App {

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        try {
            server.start();
        } catch (InterruptedException e) {
            System.err.println("Server's work was interupted.");
            e.printStackTrace();
        }
    }

}
