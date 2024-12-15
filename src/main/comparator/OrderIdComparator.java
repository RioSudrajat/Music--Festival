package main.comparator;

import java.util.Comparator;

import main.model.Order;

public class OrderIdComparator implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        return order1.getOrderID().compareTo(order2.getOrderID());
    }
}
