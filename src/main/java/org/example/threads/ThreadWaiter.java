package org.example.threads;

import org.example.Controller;
import org.example.Order;

public class ThreadWaiter extends Thread {
    private final Controller controller;
    private String name;
    private final int DELIVERY_TIME = 1000;

    public ThreadWaiter(Controller controller, String name) {
        this.controller = controller;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(this.name + " приступил к работа");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = this.controller.getNextMakeOrder();
                order.getClient().putOrder(order.getDish());
                Thread.sleep(DELIVERY_TIME);
                System.out.println(this.name + " отношу " + order.getDish() + " для " + order.getClient());
            }
        } catch (InterruptedException e) {
            //empty block
        }
        System.out.println(this.name + " завершил работу.");
    }
}
