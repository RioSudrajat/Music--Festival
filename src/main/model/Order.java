package main.model;

import main.list.LinkedList;

/**
 * Mewakili order dalam Aplikasi Festival Musik.
 * order berisi informasi tentang pelanggan, festival yang termasuk dalam pesanan,
 * total harga, kecepatan order, dan status order.
 */
public class Order {

    public enum ShippingSpeed {
        STANDARD(1),
        RUSH(2),
        OVERNIGHT(3),
        DIGITAL(4);

        private final int shippingCode;

        ShippingSpeed(int shippingCode) {
            this.shippingCode = shippingCode;
        }

        public int getShippingCode() {
            return this.shippingCode;
        }

        public static ShippingSpeed fromCode(int code) {
            for (ShippingSpeed speed : ShippingSpeed.values()) {
                if (speed.getShippingCode() == code) {
                    return speed;
                }
            }
            throw new IllegalArgumentException("Shipping Speed code tidak valid: " + code);
        }
    }

    private String orderID;
    private String firstName;
    private String lastName;
    private String email;
    private String datePurchased;
    private LinkedList<Festival> orderContents;
    private double totalPrice;
    private ShippingSpeed shippingSpeed;
    private boolean isShipped;
    private static int orderIDSeed = 100000000;

    // Constructor
    public Order(String orderID) {
        this.orderID = orderID;
        this.firstName = "Nama depan tidak diketahui";
        this.lastName = "nama belakang tidak diketahui";
        this.email = "email tidak diketahui";
        this.datePurchased = "tanggal tidak diketahui";
        this.orderContents = null;
        this.totalPrice = 0.0;
        this.shippingSpeed = null;
        this.isShipped = false;
    }

    public Order(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = "email tidak diketahui";
        this.datePurchased = "tanggal tidak diketahui";
        this.orderContents = null;
        this.totalPrice = 0.0;
        this.shippingSpeed = null;
        this.isShipped = false;
    }

    public Order(String firstName, String lastName, String email, String datePurchased, LinkedList<Festival> orderContents,
                 ShippingSpeed shippingSpeed, boolean isShipped) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.datePurchased = datePurchased;
        this.orderContents = orderContents;
        this.totalPrice = calculateTotalPrice();
        this.shippingSpeed = shippingSpeed;
        this.isShipped = isShipped;
    }

    // Getters
    public static int generateOrderID() {
        orderIDSeed++;
        return orderIDSeed;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDatePurchased() {
        return datePurchased;
    }

    public LinkedList<Festival> getOrderContents() {
        return orderContents;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public ShippingSpeed getShippingSpeed() {
        return shippingSpeed;
    }

    public boolean getIsShipped() {
        return isShipped;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }

    public void setShippingSpeed(ShippingSpeed shippingSpeed) {
        this.shippingSpeed = shippingSpeed;
    }

    public void setIsShipped(boolean isShipped) {
        this.isShipped = isShipped;
    }

    public void addFestival(Festival festival) {
        orderContents.addLast(festival);
    }
    public double calculateTotalPrice() {
        if (this.orderContents == null || this.orderContents.isEmpty()) {
            return 0.0;
        }
        double totalPrice = 0.0;
        this.orderContents.positionIterator();
        while (!this.orderContents.offEnd()) {
            Festival currentFestival = this.orderContents.getIterator();
            totalPrice += currentFestival.getPrice();
            orderContents.advanceIterator();
        }
        this.totalPrice = totalPrice;
        return totalPrice;
    }

    public void removeFestival(Festival festival) {
        int index = orderContents.findIndex(festival);
        orderContents.advanceIteratorToIndex(index);
        orderContents.removeIterator();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Order)) return false;
        Order that = (Order) other;
        return this.orderID.equals(that.orderID);
    }
    @Override
    public int hashCode() {
        return (orderID == null) ? 0 : orderID.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderID).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Nama: ").append(firstName).append(" ").append(lastName).append("\n");
        sb.append("tanggal dibayar: ").append(datePurchased).append("\n");
        sb.append("Music Festivals: ").append("\n").append(orderContents.toString()).append("\n");
        sb.append("Total harga: RP.").append(String.format("%.2f", totalPrice)).append("\n");
        sb.append("kecepeatan pengiriman: ").append(shippingSpeed).append("\n");
        sb.append("Status telah terkirim: ").append(isShipped).append("\n");
        return sb.toString();
    }
}