package main;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import main.comparator.NameComparator;
import main.comparator.OrderIdComparator;
import main.comparator.PriorityComparator;
import main.controller.DataLoader;
import main.controller.FestivalUi;
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

public class MainMusicFestival {
    public static BST<Festival> festivalsByName = new BST<>();
    public static BST<Festival> festivalsByStartDateCity = new BST<>();
    public static ArrayList<Festival> festivalArrayList = new ArrayList<>();
    public static HashTable<User> users = new HashTable<>(100);
    public static HashTable<User> employees = new HashTable<>(100);
    public static LinkedList<Customer> customers = new LinkedList<>();
    public static Heap<Order> shippedOrders = new Heap<>(new ArrayList<>(), new PriorityComparator());
    public static Heap<Order> unshippedOrders = new Heap<>(new ArrayList<>(), new PriorityComparator());
    public static final NameComparator NAME_COMPARATOR = new NameComparator();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        DataLoader.populateFestivals(festivalsByName, festivalsByStartDateCity, festivalArrayList);
        DataLoader.populateUsers(users, employees, customers);
        DataLoader.populateOrders(shippedOrders, unshippedOrders, festivalsByName, customers);

        System.out.println("Selamat datang di MainMusicFestivalApp\n");
        User user = login(scanner);

        if (!user.getIsEmployee()) {
            CustomerMenu customerMenu = new CustomerMenu(scanner, (Customer) user);
            customerMenu.displayMenu();
        } else {
            EmployeeMenu employeeMenu = new EmployeeMenu(scanner, (Employee) user);
            employeeMenu.displayMenu();
        }

        System.out.println("Terima kasih tekah menggunakan MainMusicFestivalApp\n");
    }

    public static User login(Scanner scanner) {
        boolean loggedin = false;
        User user = null;

        do {
            System.out.println("===== Music Festival ====");
            System.out.println("=  1. customer          =");
            System.out.println("=  2. register          =");
            System.out.println("=  3. pengunjung biasa  =");
            System.out.println("=  4. login karyawan    =");
            System.out.println("=  5. login manager     =");
            System.out.println("==========================");
            System.out.println(" Masukkan pilihan anda (1-5): ");
            int loginChoice = scanner.nextInt();
            scanner.nextLine();

            switch (loginChoice) {
                case 1 -> user = LoginService.customerLogin(scanner, users);
                case 2 -> user = LoginService.createNewCustomer(scanner, users, customers);
                case 3 -> user = LoginService.guestLogin();
                case 4 -> user = LoginService.employeeLogin(scanner, employees);
                case 5 -> user = LoginService.managerLogin(scanner, employees);
                default -> System.out.println("Invalid input");
            }

            if (user != null) {
                loggedin = true;
            }
        } while (!loggedin);

        return user;
    }
    public static void writeOrdersToFile(ArrayList<Order> orders) {
        orders.sort(new OrderIdComparator());
        try (FileWriter writer = new FileWriter("orders.txt", false)) {
            for (int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                writer.write(order.getFirstName() + " " + order.getLastName() + "\n");
                writer.write(order.getEmail() + "\n");
                writer.write(order.getDatePurchased() + "\n");

                LinkedList<Festival> contents = order.getOrderContents();
                writer.write(contents.getLength() + "\n");
                contents.positionIterator();
                while (!contents.offEnd()) {
                    writer.write(contents.getIterator().getName() + "\n");
                    contents.advanceIterator();
                }

                writer.write(order.getIsShipped() + "\n");
                writer.write(order.getShippingSpeed().getShippingCode() + "\n");
                if (i != orders.size() - 1) {
                    writer.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class CustomerMenu {
    public Scanner scanner;
    public Customer customer;

    public CustomerMenu(Scanner scanner, Customer customer) {
        this.scanner = scanner;
        this.customer = customer;
    }

    public void displayMenu() {
        boolean quit = false;
        do {
            System.out.println("=================================");
            System.out.println("=       Dashboard Pelanggan     =");
            System.out.println("=================================");
            System.out.println("= 1. Cari festival              =");
            System.out.println("= 2. Tampilkan daftar festival  =");
            System.out.println("= 3. Pesan tiket                =");
            System.out.println("= 4. Lihat pembelian            =");
            System.out.println("= 5. Keluar                     =");
            System.out.println("=================================");
            System.out.print("Masukkan pilihan Anda: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> FestivalUi.searchFestival(scanner, MainMusicFestival.festivalsByName, MainMusicFestival.festivalsByStartDateCity);
                case 2 -> FestivalUi.displayFestival(scanner, MainMusicFestival.festivalsByName, MainMusicFestival.festivalsByStartDateCity);
                case 3 -> OrderService.placeOrder(scanner, customer);
                case 4 -> OrderService.viewPurchases(customer);
                case 5 -> quit = true;
                default -> System.out.println("Invalid input");
            }
        } while (!quit);
    }
}

class EmployeeMenu {
    public Scanner scanner;
    public Employee employee;

    public EmployeeMenu(Scanner scanner, Employee employee) {
        this.scanner = scanner;
        this.employee = employee;
    }

    public void displayMenu() {
        boolean quit = false;
        do {
            System.out.println("=====================================================");
            System.out.println("=            Dashboard karyawan                     =");
            System.out.println("=====================================================");
            System.out.println("| No | Menu                                         |");
            System.out.println("|----|----------------------------------------------|");
            System.out.println("| 1  | Cari Pesanan berdasarkan ID                  |");
            System.out.println("| 2  | Cari berdasarkan Nama Pelanggan              |");
            System.out.println("| 3  | Lihat Pesanan dengan Prioritas Tertinggi     |");
            System.out.println("| 4  | Lihat Semua Pesanan yang Diurutkan           |");
            System.out.println("|    | berdasarkan Prioritas                        |");
            System.out.println("| 5  | Kirimkan Pesanan                             |");
            if (employee.getIsManager()) {
                System.out.println("| 6  | Tambahkan Festival (Hanya untuk Manajer)     |");
                System.out.println("| 7  | Perbarui Festival (Hanya untuk Manajer)      |");
                System.out.println("| 8  | Hapus Festival (Hanya untuk Manajer)         |");
            }
            System.out.println("| 9  | Keluar dan Tulis ke File                     |");
            System.out.println("=====================================================");
            System.out.print("Pilihan Anda: ");

            int menuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (menuChoice) {
                case 1 -> OrderService.searchOrderByID(scanner);
                case 2 -> OrderService.searchByCustomerName(scanner, employee);
                case 3 -> OrderService.viewOrderWithHighestPriority();
                case 4 -> OrderService.viewAllOrdersSortedByPriority();
                case 5 -> OrderService.shipOrder(scanner);
                case 6 -> {
                    if (employee.getIsManager()) {
                        FestivalUi.addFestival(scanner, MainMusicFestival.festivalsByName, MainMusicFestival.festivalsByStartDateCity, MainMusicFestival.festivalArrayList, employee);
                    }
                }
                case 7 -> {
                    if (employee.getIsManager()) {
                        FestivalUi.updateFestival(scanner, MainMusicFestival.festivalsByName, MainMusicFestival.festivalsByStartDateCity, MainMusicFestival.festivalArrayList, employee);
                    }
                }
                case 8 -> {
                    if (employee.getIsManager()) {
                        FestivalUi.removeFestival(scanner, MainMusicFestival.festivalsByName, MainMusicFestival.festivalsByStartDateCity, MainMusicFestival.festivalArrayList, employee);
                    }
                }
                case 9 -> quit = true;
                default -> System.out.println("input tidak valid");
            }
        } while (!quit);
    }
}

class LoginService {
    public static boolean login = false;
        public static User customerLogin(Scanner scanner, HashTable<User> users) {
            System.out.println("====  login sebagai customer  ====");
            System.out.print("masukkan email anda: ");
            String email = scanner.nextLine();
            System.out.print("masukkan passord anda: ");
            String password = scanner.nextLine();
            User user = users.get(new Customer(email));
            if (user != null && user.passwordMatch(password)) {
                System.out.println("selamat datang "+ user.getFirstName() + user.getLastName());
                login = true;
            return user;
        } else {
            System.out.println("Kombinasi email atau password salah !");
            return null;
        }
    }

    public static User createNewCustomer(Scanner scanner, HashTable<User> users, LinkedList<Customer> customers) {
        System.out.println("=====  Membuat akun baru  =====");
        System.out.print("Masukkan nama depan: ");
        String firstName = scanner.nextLine();
        System.out.print("Masukkan nama belakang: ");
        String lastName = scanner.nextLine();
        System.out.print("masukkan email anda: ");
        String email = scanner.nextLine();
        System.out.print("Masukkan password anda: ");
        String password = scanner.nextLine();
        System.out.print("Masukkan alamat anda: ");
        String address = scanner.nextLine();
        System.out.print("Masikkan kota anda tinggal: ");
        String city = scanner.nextLine();
        System.out.print("Maukkan negara anda tinggal: ");
        String state = scanner.nextLine();
        System.out.print("Maukkan kode pos: ");
        String zip = scanner.nextLine();
        User user = new Customer(firstName, lastName, email, password, false, address, city, state, zip);
        users.add(user);
        customers.addLast((Customer) user);
        try (FileWriter writer = new FileWriter("users.txt", true)) {
            writer.write("\n");
            writer.write(user.getFirstName() + " " + user.getLastName() + "\n");
            writer.write(user.getEmail() + "\n");
            writer.write(user.password + "\n");
            writer.write(user.getIsEmployee() + "\n");
            writer.write(((Customer) user).getAddress() + "\n");
            writer.write(((Customer) user).getCity() + "\n");
            writer.write(((Customer) user).getState() + "\n");
            writer.write(((Customer) user).getZip() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Selamat datang "+ user.getFirstName() + user.getLastName());
        login = true;
        return user;

    }

    public static User guestLogin() {
        System.out.println("Login sebagai guest");
        User user = new Customer("guest@email.com");
        System.out.println("Selamat datang Guest");
        return user;
    }

    public static User employeeLogin(Scanner scanner, HashTable<User> employees) {
        System.out.println("====  login sebagai karyawan  ====");
        System.out.print("masukkan email anda: ");
        String email = scanner.nextLine();
        System.out.print("masukkan password anda: ");
        String password = scanner.nextLine();
        User user = employees.get(new Employee(email, password));
        if (user != null) {
            System.out.printf("Selamat datang karyawan "+ user.getFirstName() + user.getLastName());
            login = true;
            return user;
        } else {
            System.out.println("Kombinasi email/password salah");
            return null;
        }
    }

    public static User managerLogin(Scanner scanner, HashTable<User> employees) {
        System.out.println("====  login sebagai manager  ====");
        System.out.println("masukkkan email anda: ");
        String email = scanner.nextLine();
        System.out.println("masukkan password anda: ");
        String password = scanner.nextLine();
        User user = employees.get(new Manager(email, password));
        if (user != null && user instanceof Manager) {
            ((Manager) user).setIsManager(true); 
            System.out.println("Selamat datang Manager "+ user.getFirstName() + user.getLastName());
            login = true;
                return user;
        } else {
            System.out.println("kombinasi email/password tidak sesuai");
            return null;
        }
    }
}

class OrderService {
    public static void placeOrder(Scanner scanner, Customer customer) {
        if (customer.getEmail().equals("guest@email.com")) {
            System.out.println("Pemesanan tidak tersedia untuk guests.");
            return;
        }

        FestivalUi.displayFestival(scanner, MainMusicFestival.festivalsByName, MainMusicFestival.festivalsByStartDateCity);

        LinkedList<Festival> orders = new LinkedList<>();
        String line;

        while (true) {
            System.out.print("Masukkan nama festival untuk memesan atau ketik NEXT: ");
            line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("NEXT")) {
                System.out.println();
                break; 
            }

            Festival searchFestival = new Festival(line);
            Festival toOrder = MainMusicFestival.festivalsByName.search(searchFestival, MainMusicFestival.NAME_COMPARATOR);

            if (toOrder == null) {
                System.out.println("Nama festival tidak ditemukan. Silakan coba lagi.");
                continue; 
            }

            if (toOrder.getTicketsRemaining() == 0) {
                System.out.println("\n" + toOrder.getName() + " telah habis terjual. Silakan coba lagi.\n");
                continue;
            }

            System.out.println("Detail Festival:");
            System.out.println(toOrder);
            orders.addLast(toOrder);
        }

        if (orders.isEmpty()) {
            System.out.println("Tidak ada festival yang dipesan.");
            return;
        }

        System.out.println("====   OPSI PENGIRIMAN ====");
        System.out.println("1. Pengiriman Standar");
        System.out.println("2. Pengiriman Cepat");
        System.out.println("3. Pengiriman Semalam");
        System.out.println("4. Pengiriman Digital");
        System.out.println("===========================");
        System.out.print("Pilih jenis pengiriman: ");

        int shippingChoice;
        try {
            shippingChoice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Input tidak valid. Dipilih pengiriman standar.");
            shippingChoice = 1;
        }
        scanner.nextLine(); 

        Order order;
        switch (shippingChoice) {
            case 1 -> {
                System.out.println("Dipilih pengiriman standar.");
                order = new Order(customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                        LocalDate.now().toString(), orders, Order.ShippingSpeed.STANDARD, false);
            }
            case 2 -> {
                System.out.println("Dipilih pengiriman cepat.");
                order = new Order(customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                        LocalDate.now().toString(), orders, Order.ShippingSpeed.RUSH, false);
            }
            case 3 -> {
                System.out.println("Dipilih pengiriman semalam.");
                order = new Order(customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                        LocalDate.now().toString(), orders, Order.ShippingSpeed.OVERNIGHT, false);
            }
            case 4 -> {
                System.out.println("Dipilih pengiriman digital.");
                order = new Order(customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                        LocalDate.now().toString(), orders, Order.ShippingSpeed.DIGITAL, false);
            }
            default -> {
                System.out.println("Input tidak valid. Dipilih pengiriman standar.");
                order = new Order(customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                        LocalDate.now().toString(), orders, Order.ShippingSpeed.STANDARD, false);
            }
        }

        System.out.println("Pesanan Anda telah diproses. Terima kasih!");

        order.setOrderID("" + Order.generateOrderID());
        customer.addUnshippedOrder(order);
        MainMusicFestival.unshippedOrders.insert(order);

        System.out.println("Order ditempatkan untuk:");
        System.out.println(orders);

        ArrayList<Order> ordersArrayList = new ArrayList<>();
        for (int i = 1; i <= MainMusicFestival.shippedOrders.getHeapSize(); i++) {
            ordersArrayList.add(MainMusicFestival.shippedOrders.getElement(i));
        }
        for (int i = 1; i <= MainMusicFestival.unshippedOrders.getHeapSize(); i++) {
            ordersArrayList.add(MainMusicFestival.unshippedOrders.getElement(i));
        }
        MainMusicFestival.writeOrdersToFile(ordersArrayList);
    }

    public static void viewPurchases(Customer customer) {
        if (customer.getEmail().equals("guest@email.com")) {
            System.out.println("order tidak tersedia untuk guest");
        }
        System.out.println("Order yang sudah dikirim ");
        System.out.println(customer.getShippedOrders());
        System.out.println("Order yang belum dikirim");
        System.out.println(customer.getUnshippedOrders());
    }

    public static void searchOrderByID(Scanner scanner) {
        System.out.print("Masukkan ID order yang ingin dicari: ");
        String orderID = scanner.nextLine();
        Order foundOrder = null;
        for (int i = 1; i <= MainMusicFestival.shippedOrders.getHeapSize(); i++) {
            if (MainMusicFestival.shippedOrders.getElement(i).getOrderID().equals(orderID)) {
                foundOrder = MainMusicFestival.shippedOrders.getElement(i);
                break;
            }
        }
        if (foundOrder == null) {
            for (int i = 1; i <= MainMusicFestival.unshippedOrders.getHeapSize(); i++) {
                if (MainMusicFestival.unshippedOrders.getElement(i).getOrderID().equals(orderID)) {
                    foundOrder = MainMusicFestival.unshippedOrders.getElement(i);
                    break;
                }
            }
        }
        if (foundOrder != null) {
            System.out.println("Order ditemukan: " + foundOrder);
        } else {
            System.out.println("Order tidak ditemukan.");
        }
    }

    public static void searchByCustomerName(Scanner scanner, Employee user) {
        System.out.println("====  mencari customet berdasarkan id  ====");
        System.out.print("Masukkan nama depan Customer: ");
        String firstName = scanner.nextLine();
        System.out.print("Masukkan nama belakang: ");
        String lastName = scanner.nextLine();
        LinkedList<Order> foundOrders = new LinkedList<>();
        LinkedList<Order> ordersTrueFlag = user.getOrdersByName(firstName, lastName, MainMusicFestival.customers, true);
        LinkedList<Order> ordersFalseFlag = null;
        if (ordersTrueFlag != null) {
            foundOrders = ordersTrueFlag;
            System.out.print(foundOrders);
        } else {
            ordersFalseFlag = user.getOrdersByName(firstName, lastName, MainMusicFestival.customers, false);
            if (ordersFalseFlag != null) {
                foundOrders = ordersFalseFlag;
                System.out.print(foundOrders);
            } else {
                System.out.println("Tidak ada order yang ditemukan pada customer ini.");
            }
        }

    }

    public static void viewOrderWithHighestPriority() {
        Order highestPriorityOrder = null;
        for (int i = 1; i <= MainMusicFestival.shippedOrders.getHeapSize(); i++) {
            Order order = MainMusicFestival.shippedOrders.getElement(i);
            if (highestPriorityOrder == null
                    || new PriorityComparator().compare(order, highestPriorityOrder) < 0) {
                highestPriorityOrder = order;
            }
        }
        for (int i = 1; i <= MainMusicFestival.unshippedOrders.getHeapSize(); i++) {
            Order order = MainMusicFestival.unshippedOrders.getElement(i);
            if (highestPriorityOrder == null
                    || new PriorityComparator().compare(order, highestPriorityOrder) < 0) {
                highestPriorityOrder = order;
            }
        }
        if (highestPriorityOrder != null) {
            System.out.println("Order dengan proritas tinggi: " + highestPriorityOrder);
        } else {
            System.out.println("Order tidak ditemukan.");
        }
    }

    public static void viewAllOrdersSortedByPriority() {
        ArrayList<Order> allOrders = new ArrayList<>();
        for (int i = 1; i <= MainMusicFestival.shippedOrders.getHeapSize(); i++) {
            allOrders.add(MainMusicFestival.shippedOrders.getElement(i));
        }
        for (int i = 1; i <= MainMusicFestival.unshippedOrders.getHeapSize(); i++) {
            allOrders.add(MainMusicFestival.unshippedOrders.getElement(i));
        }
        Collections.sort(allOrders, new PriorityComparator());
        System.out.println("semua Order urut Berdasarkan Prioritas:");
        for (Order order : allOrders) {
            System.out.println(order);
        }
    }

    public static void shipOrder(Scanner scanner) {
        System.out.println("Masukkan ID Order yang akan dikirim ");
        String orderID1 = scanner.nextLine();
        boolean orderShipped = false;
        for (int i = 1; i <= MainMusicFestival.shippedOrders.getHeapSize(); i++) {
            Order order = MainMusicFestival.shippedOrders.getElement(i);
            if (order.getOrderID().equalsIgnoreCase(orderID1)) {
                order.setIsShipped(true);
                orderShipped = true;
                System.out.println("Order sudah pernah dikirim");
            }
        }
        for (int i = 1; i <= MainMusicFestival.unshippedOrders.getHeapSize(); i++) {
            Order order = MainMusicFestival.unshippedOrders.getElement(i);
            if (order.getOrderID().equalsIgnoreCase(orderID1)) {
                order.setIsShipped(true);
                orderShipped = true;
                MainMusicFestival.unshippedOrders.remove(i);
                MainMusicFestival.shippedOrders.insert(order);
                String email = order.getEmail();
                User placeholderCustomer = new Customer(email);
                Customer customer = (Customer) MainMusicFestival.users.get(placeholderCustomer);
                if (customer != null) {
                    customer.shipOrder(order);
                }
                System.out.println("Order berhasil dikirim");
            }
        }
        if (!orderShipped) {
            System.out.println("Order tidak ditemukan");
        }
    }
}
