package main.controller;

import java.io.*;
import java.util.*;

import main.comparator.NameComparator;
import main.comparator.StartDateCityComparator;
import main.list.BST;
import main.list.HashTable;
import main.list.Heap;
import main.list.LinkedList;
import main.model.Customer;
import main.model.Employee;
import main.model.Festival;
import main.model.Manager;
import main.model.Order;
import main.model.User;


public class DataLoader {

    public static void populateFestivals(BST<Festival> festivalsByName, BST<Festival> festivalsByStartDateCity,
                                  ArrayList<Festival> festivalArrayList) {
        try {
            Scanner scanner = new Scanner(new File("festivals.txt"));
            NameComparator nameComparator = new NameComparator();
            StartDateCityComparator startDateCityComparator = new StartDateCityComparator();
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                String startDate = scanner.nextLine();
                double price = Double.parseDouble(scanner.nextLine());
                String city = scanner.nextLine();
                String state = scanner.nextLine();
                int ticketsRemaining = Integer.parseInt(scanner.nextLine());
                int numGenre = Integer.parseInt(scanner.nextLine());
                // Genre
                ArrayList<String> genre = new ArrayList<>();
                for (int i = 0; i < numGenre; i++) {
                    genre.add(scanner.nextLine());
                }
                // Artists
                int numArtists = Integer.parseInt(scanner.nextLine());
                ArrayList<String> artists = new ArrayList<>();
                for (int i = 0; i < numArtists; i++) {
                    artists.add(scanner.nextLine());
                }
                Festival festival = new Festival(name, startDate, price, city, state, ticketsRemaining, genre, artists);
                festivalsByName.insert(festival, nameComparator);
                festivalsByStartDateCity.insert(festival, startDateCityComparator);
                festivalArrayList.add(festival);
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found: ");
        }
    }
    
    public static void populateUsers(HashTable<User> users, HashTable<User> employees, LinkedList<Customer> customers) {
        try {
            Scanner scanner = new Scanner(new File("users.txt"));
            while (scanner.hasNextLine()) {
                String[] name = scanner.nextLine().split(" ");
                String email = scanner.nextLine();
                String password = scanner.nextLine();
                boolean isEmployee = Boolean.parseBoolean(scanner.nextLine());
                if (!isEmployee) {
                    String address = scanner.nextLine();
                    String city = scanner.nextLine();
                    String state = scanner.nextLine();
                    String zip = scanner.nextLine();
                    Customer customer = new Customer(name[0], name[1], email, password,
                                        isEmployee, address, city, state, zip);
                    users.add(customer);
                    customers.addLast(customer);
                } else {
                    boolean isManager = Boolean.parseBoolean(scanner.nextLine());
                    if (isManager) {
                        Manager manager = new Manager(name[0], name[1], email, password, isEmployee, isManager);
                        users.add(manager);
                        employees.add(manager);
                    } else {
                        Employee employee = new Employee(name[0], name[1], email, password, isEmployee, isManager);
                        users.add(employee);
                        employees.add(employee);
                    }
                }

                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found: ");
        }
    }

    /**
     * Reads order data from a file and populates two heaps, one for shipped orders
     * and another for unshipped orders, as well as updating customer records with their orders.
     */
    public static void populateOrders(Heap<Order> shippedOrders, Heap<Order> unShippedOrders,
                BST<Festival> festivals, LinkedList<Customer> customers) {
        try {
            Scanner scanner = new Scanner(new File("orders.txt"));
            while (scanner.hasNextLine()) {
                String[] names = scanner.nextLine().split(" ");
                String email = scanner.nextLine();
                String dateOfPurchase = scanner.nextLine();
                int numFestivals = Integer.parseInt(scanner.nextLine());
                LinkedList<Festival> festivalList = new LinkedList<>();

                for (int i = 0; i < numFestivals; i++) {
                    NameComparator comparator = new NameComparator();
                    String festivalName = scanner.nextLine();
                    Festival placeholderFestival = new Festival(festivalName);
                    Festival festival = festivals.search(placeholderFestival, comparator);
                    if (festival != null) {
                        festivalList.addLast(festival);
                    }
                }

                boolean isShipped = Boolean.parseBoolean(scanner.nextLine());
                int shippingCode = Integer.parseInt(scanner.nextLine());
                Order.ShippingSpeed shippingSpeed = Order.ShippingSpeed.fromCode(shippingCode);

                /**
                 * Set up customer in order to populate their
                 * unshippedOrders or shippedOrders variables
                 */
                Customer customerPlaceholder = new Customer(email);
                int index = customers.findIndex(customerPlaceholder);
                if (index >= 0) {
                    customers.advanceIteratorToIndex(index);
                    Customer customer = customers.getIterator();
                    Order order = new Order(names[0], names[1], email, dateOfPurchase, festivalList,
                                  shippingSpeed, isShipped
                    );
                    order.setOrderID("" + Order.generateOrderID());
                    if (isShipped) {
                        customer.addShippedOrder(order);
                        shippedOrders.insert(order);
                    } else {
                        customer.addUnshippedOrder(order);
                        unShippedOrders.insert(order);
                    }
                }

                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found: ");
        }
    }
}