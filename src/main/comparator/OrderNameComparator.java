package main.comparator;

import java.util.Comparator;

import main.model.Order;

class OrderNameComparator implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        String order1CustomerName = order1.getFirstName() + " " + order1.getLastName();
        String order2CustomerName = order2.getFirstName() + " " + order2.getLastName();
        return order1CustomerName.compareTo(order2CustomerName);
    }
}
