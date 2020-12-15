package org.example;

class Dish {
    private final int id;
    private final String name;

    Dish(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Заказ N" + this.id + " - " + this.name;
    }
}
