package com.alpsbte.essentials.utils;

import com.alpsbte.essentials.AlpsEssentials;

public class Server {
    private final String name;
    private final String address;
    private final int port;
    private int onlinePlayers = 0;

    Server(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @SuppressWarnings("unused")
    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(int amount) {
        onlinePlayers = amount;
    }
}
