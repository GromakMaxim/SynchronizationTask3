package org.example.threads;

import org.example.Client;

public class ThreadClient extends Thread {
    private final Client client;
    private final int DECISION_TIME = 1000;

    public ThreadClient(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        System.out.println(this.client + " вошел в ресторан ");
        try {
            while (!this.client.dishesIsEmpty()) {
                Thread.sleep(DECISION_TIME);
                this.client.makeOrder();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.client + " покинул ресторан ");
    }
}
