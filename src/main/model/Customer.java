package main.model;

import main.list.LinkedList;

/**
 * Represents a customer in the Music Festival App.
 * A customer is a user with additional information such as address and order history.
 * Customers can have both shipped and unshipped orders.
 */
public class Customer extends User {
    private String address;
    private String city;
    private String state;
    private String zip;
    private LinkedList<Order> shippedOrders = new LinkedList<>();
    private LinkedList<Order> unshippedOrders = new LinkedList<>();

    public Customer(String email) {
        super(email);
    }
    public Customer(String email, String password) {
        super(email, password);
    }

    public Customer(String firstName, String lastName, String email, String password,
                    boolean isEmployee, String address, String city, String state, String zip) {
        super(firstName, lastName, email, password, isEmployee);
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    // Getters
    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public LinkedList<Order> getShippedOrders() {
        return this.shippedOrders;
    }

    public LinkedList<Order> getUnshippedOrders() {
        return this.unshippedOrders;
    }

    // Setters
    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    public void addShippedOrder(Order order) {
        shippedOrders.addLast(order);
    }

    public void addUnshippedOrder(Order order) {
        unshippedOrders.addLast(order);
    }

    public void shipOrder(Order order) {
        int index = unshippedOrders.findIndex(order);
        if (index == -1) {
            throw new IllegalArgumentException("shipOrder(): Order tidak bisa ditrmukan didalam unshipped orders untuk customer " + getFullName() + ": " + order);
        }
        unshippedOrders.advanceIteratorToIndex(index);
        unshippedOrders.removeIterator();
        addShippedOrder(order);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Customer)) return false;
        Customer that = (Customer) other;
        return this.email.equals(that.email);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nama: ").append(firstName).append(" ").append(lastName).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Kota: ").append(city).append("\n");
        sb.append("Zip: ").append(zip).append("\n");
        sb.append("Order terkirim: ").append("\n").append(shippedOrders.toString()).append("\n");
        sb.append("Order belum terikirim: ").append("\n").append(unshippedOrders.toString()).append("\n");
        return sb.toString();
    }
}