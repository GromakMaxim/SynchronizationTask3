package org.example;

import org.example.threads.ThreadClient;
import org.example.threads.ThreadChef;
import org.example.threads.ThreadWaiter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final int amountClients;
    private final String[] dishes;

    private final List<ThreadClient> poolClients;
    private final ThreadChef chef;
    private final ThreadWaiter waiter1;
    private final ThreadWaiter waiter2;

    private final ArrayDeque<Order> orders;//очередь заказов
    private final ArrayDeque<Order> makeOrders;//очередь готовых заказов

    Controller(int amountClients, String[] dishes) {
        this.amountClients = amountClients;
        this.dishes = dishes;

        this.orders = new ArrayDeque<>();
        this.makeOrders = new ArrayDeque<>();

        this.poolClients = new ArrayList<>();
        this.chef = new ThreadChef(this);
        this.waiter1 = new ThreadWaiter(this, "Официант1");
        this.waiter2 = new ThreadWaiter(this, "Официант2");
    }

    private ArrayDeque<Dish> getListOrders(String[] dishes) {
        ArrayDeque<Dish> resultList = new ArrayDeque<>();
        if (dishes != null) {
            for (int index = 0; index < dishes.length; index++) {
                resultList.add(new Dish(index + 1, dishes[index]));
            }
        }
        return resultList;
    }

    //старт потоков
    void init() {
        System.out.println("Start program...");
        this.chef.start();
        this.waiter1.start();
        this.waiter2.start();
        this.startClients();
        while (true) {//продолжаем программу пока жив хоть один клиент=)
            if (!this.clientsIs()) {
                this.stop();
                break;
            }
        }
        System.out.println("End program...");
    }

    private void startClients() {
        for (int count = 0; count < this.amountClients; count++) {
            ThreadClient client = new ThreadClient(new Client(count + 1, "Гость", this, this.getListOrders(this.dishes)));
            this.poolClients.add(client);
            client.start();
        }
    }

    private void stop() {
        this.chef.interrupt();
        this.waiter1.interrupt();
        this.waiter2.interrupt();
        while (this.chef.isAlive() || this.waiter1.isAlive() || this.waiter2.isAlive()) {
            //empty cycle
        }
    }

    private boolean clientsIs() {
        boolean result = false;
        for (ThreadClient client : this.poolClients) {
            if (result = client.isAlive()) {
                break;
            }
        }
        return result;
    }

    public Order getNextMakeOrder() throws InterruptedException {
        Order order;
        synchronized (this.makeOrders) {
            while (this.makeOrders.isEmpty()) {
                this.makeOrders.wait();
            }
            order = this.makeOrders.removeFirst();
        }
        return order;
    }

    public Order getNextOrder() throws InterruptedException {
        Order order;
        synchronized (this.orders) {
            while (this.orders.isEmpty()) {
                this.orders.wait();
            }
            order = this.orders.removeFirst();
        }
        return order;
    }

    public void addOrder(Order order) {
        if (order != null) {
            synchronized (this.orders) {
                this.orders.add(order);
                System.out.println(order);
                this.orders.notify();
            }
        }
    }

    public void addMakeOrder(Order order) {
        if (order != null) {
            synchronized (this.makeOrders) {
                this.makeOrders.add(order);
                this.makeOrders.notify();
            }
        }
    }
}
