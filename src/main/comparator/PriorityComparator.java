package main.comparator;

import java.util.Comparator;
import java.time.LocalDate;

import main.model.Order;

public class PriorityComparator implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        LocalDate date1 = order1.getDatePurchased() != null ? LocalDate.parse(order1.getDatePurchased()) : LocalDate.MAX;
        LocalDate date2 = order2.getDatePurchased() != null ? LocalDate.parse(order2.getDatePurchased()) : LocalDate.MAX;
        int dateComparison = date1.compareTo(date2);
        if (dateComparison != 0) {
            return dateComparison;
        } else {
            return Integer.compare(order2.getShippingSpeed().getShippingCode(), 
                                   order1.getShippingSpeed().getShippingCode());
        }
    }
 }

