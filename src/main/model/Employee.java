package main.model;

import main.list.LinkedList;

/**
* Mewakili karyawan di Aplikasi Festival Musik.
 * Karyawan memiliki keistimewaan tambahan dibandingkan pengguna biasa, seperti mengelola pesanan.
 * Seorang karyawan dapat dipromosikan menjadi manajer, yang mungkin memberi mereka hak istimewa lebih lanjut.
 */
public class Employee extends User {
    private boolean isManager;

    public Employee(String email) {
        super(email);
    }

    public Employee(String email, String password) {
        super(email, password);
    }

    public Employee(String firstName, String lastName, String login, String password,
                    boolean isEmployee, boolean isManager) {
        super(firstName, lastName, login, password, isEmployee);
        this.isManager = isManager;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public void updateOrderStatus() {
    }

    public Order getByOrderID(String orderID, LinkedList<Customer> customers, boolean searchShipped) {
        customers.positionIterator();
        while (!customers.offEnd()) {
            Customer currentCustomer = customers.getIterator();
            LinkedList<Order> orders = searchShipped ? currentCustomer.getShippedOrders() : currentCustomer.getUnshippedOrders();

            orders.positionIterator();
            while (!orders.offEnd()) {
                Order currentOrder = orders.getIterator();
                if (currentOrder.getOrderID().equals(orderID)) {
                    return currentOrder;
                }
                orders.advanceIterator();
            }
            customers.advanceIterator();
        }
        return null;
    }

    /**
     * Mengambil semua order yang terkait dengan nama pelanggan, baik terkirim atau belum terkirim.
     */
    public LinkedList<Order> getOrdersByName(String firstName, String lastName, LinkedList<Customer> customers, boolean searchShipped) {
        customers.positionIterator();
        while (!customers.offEnd()) {
            Customer currentCustomer = customers.getIterator();
            if (currentCustomer.getFirstName().equals(firstName) && currentCustomer.getLastName().equals(lastName)) {
                System.out.print(currentCustomer.getShippedOrders());
                System.out.print(currentCustomer.getUnshippedOrders());
                return searchShipped ? currentCustomer.getShippedOrders() : currentCustomer.getUnshippedOrders();
            }
            customers.advanceIterator();
        }
        return new LinkedList<Order>();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Employee)) return false;
        Employee that = (Employee) other;
        return this.email.equals(that.email);
    }
}