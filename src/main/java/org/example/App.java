package org.example;

public class App {
    private static final int AMOUNT_CLIENTS = 5;//кол-во новых посетителей
    private static final String[] DISHES = {"Суп", "Салат", "Кофе"};

    public static void main(String[] args) {
        new Controller(AMOUNT_CLIENTS, DISHES).init();
    }
}











