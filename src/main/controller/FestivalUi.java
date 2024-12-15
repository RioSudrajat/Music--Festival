package main.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import main.comparator.NameComparator;
import main.comparator.StartDateCityComparator;
import main.list.BST;
import main.model.Employee;
import main.model.Festival;
import main.model.User;

/**
 * Menyediakan fungsi antarmuka pengguna untuk mengelola festival dalam Aplikasi Festival Musik.
 * Termasuk fitur untuk mencari, menampilkan, menambah, memperbarui, dan menghapus festival.
 */
public class FestivalUi {

    private static final NameComparator NAME_COMPARATOR = new NameComparator();
    private static final StartDateCityComparator START_DATE_CITY_COMPARATOR = new StartDateCityComparator();

    public static void searchFestival(Scanner scanner, BST<Festival> byName, BST<Festival> byStartDateCity) {

        int choice = -1;
        while (choice != 3) {
            System.out.println("|-----------------------------------------------|");
            System.out.println("| Apakah Anda ingin mencari festival?        |");
            System.out.println("|-----------------------------------------------|");
            System.out.println("| 1. Cari berdasarkan nama                      |");
            System.out.println("| 2. Cari berdasarkan tanggal mulai dan kota    |");
            System.out.println("| 3. Kembali ke menu sebelumnya                 |");
            System.out.println("|-----------------------------------------------|");
            choice = getValidInput(1, 3, scanner);

            System.out.println();
            switch (choice) {
                case 1:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| Cari berdasarkan nama.                   |");
                    System.out.println("|-----------------------------------------------|");
                    System.out.print("Masukkan nama festival: ");
                    String festivalName = scanner.nextLine();
                    Festival festival = new Festival(festivalName);
                    Festival foundByName = byName.search(festival, NAME_COMPARATOR);
                    if (foundByName != null) {
                        System.out.println("|-----------------------------------------------|");
                        System.out.println("| Berikut adalah informasi festival:            |");
                        System.out.println("|-----------------------------------------------|");
                        System.out.println(foundByName);
                    } else {
                        System.out.println("|-----------------------------------------------|");
                        System.out.println("| Festival tidak ditemukan dalam sistem kami.   |");
                        System.out.println("|-----------------------------------------------|");
                    }
                    break;
                case 2:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| Cari berdasarkan tanggal mulai dan kota. |");
                    System.out.println("|-----------------------------------------------|");
                    System.out.print("Masukkan tanggal mulai festival (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    System.out.print("Masukkan kota: ");
                    String city = scanner.nextLine();
                    Festival festivalToSearch = new Festival(startDate, city);
                    Festival foundByStartDateCity = byStartDateCity.search(festivalToSearch, START_DATE_CITY_COMPARATOR);
                    if (foundByStartDateCity != null) {
                        System.out.println("|-----------------------------------------------|");
                        System.out.println("| Berikut adalah informasi festival:            |");
                        System.out.println("|-----------------------------------------------|");
                        System.out.println(foundByStartDateCity);
                    } else {
                        System.out.println("|-----------------------------------------------|");
                        System.out.println("| Festival tidak ditemukan dalam sistem kami.   |");
                        System.out.println("|-----------------------------------------------|");
                    }
                    break;
                case 3:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| Kembali ke menu sebelumnya.          |");
                    System.out.println("|-----------------------------------------------|");
                    break;
                default:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| Pilihan tidak valid. Masukkan 1, 2, atau 3.   |");
                    System.out.println("|-----------------------------------------------|");
            }
        }
    }

    /**
     * Displays festivals sorted either by name or by start date and city based on user choice.
     */
    public static void displayFestival(Scanner scanner, BST<Festival> byName, BST<Festival> byStartDateCity) {
        int choice = -1;
        while (choice != 3) {
            System.out.println("|-----------------------------------------------|");
            System.out.println("| Apakah Anda ingin melihat daftar festival? |");
            System.out.println("|-----------------------------------------------|");
            System.out.println("| 1. Daftar festival berdasarkan nama           |");
            System.out.println("| 2. Daftar festival berdasarkan tanggal mulai  |");
            System.out.println("|    dan kota                                   |");
            System.out.println("| 3. Kembali ke menu sebelumnya                 |");
            System.out.println("|-----------------------------------------------|");

            choice = getValidInput(1, 3, scanner);

            System.out.println();
            switch (choice) {
                case 1:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| Berikut adalah daftar festival berdasarkan    |");
                    System.out.println("| nama.                                         |");
                    System.out.println("|-----------------------------------------------|");
                    System.out.println(byName.inOrderString());
                    break;
                case 2:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| Berikut adalah daftar festival berdasarkan    |");
                    System.out.println("| tanggal mulai dan kota.                       |");
                    System.out.println("|-----------------------------------------------|");
                    System.out.println(byStartDateCity.inOrderString());
                    break;
                case 3:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| Baiklah. Kembali ke menu sebelumnya.          |");
                    System.out.println("|-----------------------------------------------|");
                    break;
                default:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| Pilihan tidak valid. Masukkan 1, 2, atau 3.   |");
                    System.out.println("|-----------------------------------------------|");
            }
        }
    }

     /**
     * Menambahkan festival baru ke dalam sistem setelah memvalidasi kredensial pengguna.
     */
    public static void addFestival(Scanner scanner, BST<Festival> byName, BST<Festival> byStartDateCity,
                                    ArrayList<Festival> festivalArrayList, User user) {
        if (isManager(user)) {
            System.out.println("|-----------------------------------------------|");
            System.out.println("| PENGGUNA DIAUTENTIKASI. Anda berwenang untuk |");
            System.out.println("| menambahkan festival baru.                   |");
            System.out.println("|-----------------------------------------------|");
            System.out.println("Tekan enter untuk melanjutkan: ");
            scanner.nextLine();
            System.out.print("Masukkan nama festival: ");
            String name = scanner.nextLine();

            System.out.print("Masukkan tanggal mulai festival (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();

            System.out.print("Masukkan harga tiket per orang: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Masukkan kota tempat festival: ");
            String city = scanner.nextLine();

            System.out.print("Masukkan inisial negara bagian: ");
            String state = scanner.nextLine();

            System.out.print("Masukkan jumlah tiket yang tersisa: ");
            int tickets = Integer.parseInt(scanner.nextLine());

            System.out.print("Masukkan artis yang tampil (pisahkan dengan koma): ");
            String artistInput = scanner.nextLine();
            String[] artists = artistInput.split(",");
            ArrayList<String> featuredArtists = new ArrayList<>();
            for (String artist : artists) {
                featuredArtists.add(artist.trim());
            }

            System.out.print("Masukkan genre festival (pisahkan dengan koma): ");
            String genreInput = scanner.nextLine();
            String[] genreArray = genreInput.split(",");
            ArrayList<String> genres = new ArrayList<>();
            for (String genre : genreArray) {
                genres.add(genre.trim());
            }

            Festival newFestival = new Festival(name, startDate, price, city, state, tickets, genres, featuredArtists);

            byName.insert(newFestival, NAME_COMPARATOR);
            byStartDateCity.insert(newFestival, START_DATE_CITY_COMPARATOR);
            festivalArrayList.add(newFestival);
            writeToFile(festivalArrayList);

            System.out.println("|-----------------------------------------------|");
            System.out.println("| Festival berhasil ditambahkan!                |");
            System.out.println("| Berikut informasi festival yang ditambahkan: |");
            System.out.println("|-----------------------------------------------|");
            System.out.println(newFestival);
        } else {
            System.out.println("|-----------------------------------------------|");
            System.out.println("| AUTENTIKASI GAGAL. Anda tidak berwenang       |");
            System.out.println("| untuk menambahkan festival.                  |");
            System.out.println("|-----------------------------------------------|");
        }
    }

    /**
     * Memperbarui festival yang ada di sistem setelah memvalidasi kredensial pengguna.
     */
    public static void updateFestival(Scanner scanner, BST<Festival> byName, BST<Festival> byStartDateCity,
                                       ArrayList<Festival> festivalArrayList, User user) {
        if (isManager(user)) {
            System.out.println("|-----------------------------------------------|");
            System.out.println("| PENGGUNA DIAUTENTIKASI. Anda berwenang untuk |");
            System.out.println("| memperbarui festival.                        |");
            System.out.println("|-----------------------------------------------|");
            System.out.println("Tekan enter untuk melanjutkan: ");
            scanner.nextLine();
            System.out.println("Berikut daftar festival saat ini:");
            System.out.println(byName.inOrderString());

            int input = -1;
            while (input != 3) {
                System.out.print("Masukkan nama festival yang ingin diperbarui: ");
                String name = scanner.nextLine();

                Festival removed = findAndRemove(name, byName, byStartDateCity);
                if (removed == null) {
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| Festival tidak ditemukan. Silakan coba lagi. |");
                    System.out.println("|-----------------------------------------------|");
                } else {
                    festivalArrayList.remove(removed);
                    System.out.println("Apa yang ingin Anda perbarui?");
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| 1. Perbarui harga festival                   |");
                    System.out.println("| 2. Perbarui jumlah tiket yang tersisa        |");
                    System.out.println("| 3. Kembali ke menu sebelumnya                |");
                    System.out.println("|-----------------------------------------------|");
                    input = getValidInput(1, 3, scanner);
                    switch (input) {
                        case 1:
                            System.out.print("Harga saat ini: ");
                            System.out.println(removed.getPrice());
                            System.out.print("Masukkan harga baru: ");
                            double price = Double.parseDouble(scanner.nextLine());
                            Festival updatedPriceFestival = new Festival(removed, price);
                            byName.insert(updatedPriceFestival, NAME_COMPARATOR);
                            byStartDateCity.insert(updatedPriceFestival, START_DATE_CITY_COMPARATOR);
                            festivalArrayList.add(updatedPriceFestival);
                            System.out.println("|-----------------------------------------------|");
                            System.out.println("| Harga berhasil diperbarui!                   |");
                            System.out.println("|-----------------------------------------------|");
                            break;
                        case 2:
                            System.out.print("Jumlah tiket saat ini: ");
                            System.out.println(removed.getTicketsRemaining());
                            System.out.print("Masukkan jumlah tiket baru: ");
                            int tickets = Integer.parseInt(scanner.nextLine());
                            Festival updatedTicketsFestival = new Festival(removed, tickets);
                            byName.insert(updatedTicketsFestival, NAME_COMPARATOR);
                            byStartDateCity.insert(updatedTicketsFestival, START_DATE_CITY_COMPARATOR);
                            festivalArrayList.add(updatedTicketsFestival);
                            System.out.println("|-----------------------------------------------|");
                            System.out.println("| Jumlah tiket berhasil diperbarui!            |");
                            System.out.println("|-----------------------------------------------|");
                            break;
                        case 3:
                            System.out.println("|-----------------------------------------------|");
                            System.out.println("| Kembali ke menu sebelumnya.                  |");
                            System.out.println("|-----------------------------------------------|");
                            break;
                        default:
                            System.out.println("Pilihan tidak valid. Masukkan angka 1-3.");
                    }
                    writeToFile(festivalArrayList);
                }
            }
        } else {
            System.out.println("|-----------------------------------------------|");
            System.out.println("| AUTENTIKASI GAGAL. Anda tidak berwenang       |");
            System.out.println("| untuk memperbarui festival.                  |");
            System.out.println("|-----------------------------------------------|");
        }
    }

    /**
     * Menghapus festival dari sistem setelah memvalidasi kredensial pengguna.
     */
    public static void removeFestival(Scanner scanner, BST<Festival> byName, BST<Festival> byStartDateCity,
                                       ArrayList<Festival> festivalArrayList, User user) {
        if (isManager(user)) {
            System.out.println("|-----------------------------------------------|");
            System.out.println("| PENGGUNA DIAUTENTIKASI. Anda berwenang untuk |");
            System.out.println("| menghapus festival.                          |");
            System.out.println("|-----------------------------------------------|");
            boolean found = false;
            while (!found) {
                System.out.println("Tekan enter untuk melanjutkan: ");
                scanner.nextLine();
                System.out.println("Berikut daftar festival saat ini:");
                System.out.println(byName.inOrderString());
                System.out.print("Masukkan nama festival yang ingin dihapus: ");
                String name = scanner.nextLine();

                Festival removed = findAndRemove(name, byName, byStartDateCity);
                if (removed != null) {
                    festivalArrayList.remove(removed);
                    writeToFile(festivalArrayList);
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| Festival berhasil dihapus.                   |");
                    System.out.println("|-----------------------------------------------|");
                    found = true;
                } else {
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("| ERROR. Festival tidak ditemukan.             |");
                    System.out.println("|-----------------------------------------------|");
                    System.out.print("Masukkan '1' untuk mencoba lagi atau lainnya untuk keluar: ");
                    String choice = scanner.nextLine();
                    if (!choice.equals("1")) {
                        break;
                    }
                }
            }
        } else {
            System.out.println("|-----------------------------------------------|");
            System.out.println("| AUTENTIKASI GAGAL. Anda tidak berwenang       |");
            System.out.println("| untuk menghapus festival.                    |");
            System.out.println("|-----------------------------------------------|");
        }
    }

    private static Festival findAndRemove(String festivalName, BST<Festival> byName, BST<Festival> byStartDateCity) {
        Festival found = byName.search(new Festival(festivalName), NAME_COMPARATOR);
        if (found != null) {
            byName.remove(found, NAME_COMPARATOR);
            byStartDateCity.remove(found, START_DATE_CITY_COMPARATOR);
        }
        return found;
    }

    private static boolean isManager(User user) {
        return (user instanceof Employee) && ((Employee) user).getIsManager();
    }

    private static int getValidInput(int validStart, int validEnd, Scanner scanner) {
        while (true) {
            System.out.print("Masukkan pilihan Anda: ");
            String input = scanner.nextLine();
            if (input == null || input.trim().isEmpty() || !Character.isDigit(input.charAt(0))) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                continue;
            }
            int choice = Integer.parseInt(input.trim());
            if (choice < validStart || choice > validEnd) {
                System.out.println("Input tidak valid. Harap masukkan angka dalam rentang " + validStart + "-" + validEnd + ".");
                continue;
            }
            return choice;
        }
    }

    private static void writeToFile(ArrayList<Festival> festivals) {
        //Write to festivals.txt
        try (FileWriter writer = new FileWriter("festivals.txt", false)) {
            for(int i = 0; i < festivals.size(); i++) {
                Festival festival = festivals.get(i);
                writer.write(festival.getName() + "\n");
                writer.write(festival.getDate() + "\n");
                writer.write(festival.getPrice() + "\n");
                writer.write(festival.getLocation() + "\n");
                writer.write(festival.getState() + "\n");
                writer.write(festival.getTicketsRemaining() + "\n");
                writer.write(festival.getGenre().size() + "\n");
                writer.write(String.join("\n", festival.getGenre()) + "\n");
                writer.write(festival.getArtistLineup().size() + "\n");
                writer.write(String.join("\n", festival.getArtistLineup()) + "\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
