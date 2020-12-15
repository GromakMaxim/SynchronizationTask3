package org.example;

public class Order {
    private final Client client;
    private final Dish dish;

    public Client getClient() {
        return client;
    }

    public Dish getDish() {
        return dish;
    }

    Order(Client client, Dish dish) {
        this.client = client;
        this.dish = dish;
    }

    @Override
    public String toString() {
        return String.format(this.client + " определился с выбором! - " + this.dish);
    }
}
