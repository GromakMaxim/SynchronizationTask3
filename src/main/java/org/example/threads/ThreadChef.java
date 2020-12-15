package org.example.threads;

import org.example.Controller;
import org.example.Order;

public class ThreadChef extends Thread {
    private final Controller controller;
    private final int COOKING_TIME = 1000;

    public ThreadChef(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        System.out.println("Повар приступил к работе.");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = this.controller.getNextOrder();
                System.out.println("Повар: готовлю " + order.getDish() + " для " + order.getClient());
                Thread.sleep(COOKING_TIME);
                this.controller.addMakeOrder(order);
            }
        } catch (InterruptedException e) {
            //empty block
        }
        System.out.println("Повар завершил работу.");
    }
}