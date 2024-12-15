package main.model;

import java.util.ArrayList;

/**
 * Mewakili festival musik di Aplikasi Festival Musik.
 * Setiap festival memiliki nama, tanggal mulai, harga, lokasi, dan daftar genre serta artis unggulan.
 * Tiket yang tersisa untuk festival juga dilacak.
 */
public class Festival {
    private String name;
    private String startDate;
    private double price;
    private String city;
    private String state;
    private int ticketsRemaining;
    private String startDateCity;
    private ArrayList<String> genre;
    private ArrayList<String> featuredArtistLineup;

    public Festival(String name) {
        this.name = name;
        this.startDate = "tanggal tifak diketahui";
        this.price = 0.0;
        this.city = "Kota tidak fiketahui";
        this.state = "negara tidak diketahui";
        this.ticketsRemaining = 0;
        this.startDateCity = "Kota dan tanggal tidak diketahui";
    }

    public Festival(String startDate, String city) {
        this.name = "nama tidak diketahui";
        this.startDate = startDate;
        this.price = 0.0;
        this.city = city;
        this.state = "negara tidak diketahui";
        this.ticketsRemaining = 0;
        this.startDateCity = startDate + ", " + city;
    }


    public Festival(Festival festival, int ticketsRemaining) {
        this.ticketsRemaining = ticketsRemaining;

        this.name = festival.name;
        this.startDate = festival.startDate;
        this.price = festival.price;
        this.city = festival.city;
        this.state = festival.state;
        this.startDateCity = festival.startDateCity;
        this.genre = festival.genre;
        this.featuredArtistLineup = festival.featuredArtistLineup;
    }

    public Festival(Festival festival, double price) {
        this.price = price;

        this.name = festival.name;
        this.startDate = festival.startDate;
        this.city = festival.city;
        this.state = festival.state;
        this.ticketsRemaining = festival.ticketsRemaining;
        this.startDateCity = festival.startDateCity;
        this.genre = festival.genre;
        this.featuredArtistLineup = festival.featuredArtistLineup;        
    }    

    public Festival(String name, String startDate, double price, String city, String state,
                    int ticketsRemaining, ArrayList<String> genre, ArrayList<String> featuredArtistLineup) {
        this.name = name;
        this.startDate = startDate;
        this.price = price;
        this.city = city;
        this.state = state;
        this.ticketsRemaining = ticketsRemaining;
        this.startDateCity = startDate + ", " + city;
        this.genre = genre;
        this.featuredArtistLineup = featuredArtistLineup;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getDate() {
        return startDate;
    }

    public double getPrice() {
        return price;
    }

    public String getLocation() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getTicketsRemaining() {
        return ticketsRemaining;
    }

    public String getStartDateCity() {
        return startDateCity;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public ArrayList<String> getArtistLineup() {
        return featuredArtistLineup;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.startDate = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLocation(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTicketsRemaining(int ticketsRemaining) {
        this.ticketsRemaining = ticketsRemaining;
    }
    public void addGenre(String genre) {
        this.genre.add(genre);
    }
    public void removeGenre(String genre) {
        this.genre.remove(genre);
    }
    public void addArtist(String artist) {
        featuredArtistLineup.add(artist);
    }
    public void removeArtist(String artist) {
        featuredArtistLineup.remove(artist);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nama: ").append(name).append("\n");
        sb.append("tanggal dimulai: ").append(startDate).append("\n");
        sb.append("Harga: RP.").append(String.format("%.2f", price)).append("\n");
        sb.append("Lokasi: ").append(city).append(", ").append(state).append("\n");
        sb.append("Tiket tersisa: ").append(ticketsRemaining).append("\n");
        sb.append("Genre: ").append("\n").append(genre.toString()).append("\n"); // More readable for ArrayList of Strings
        sb.append("Artists Unggulan: ").append("\n").append(featuredArtistLineup.toString()).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Festival)) return false;
        Festival that = (Festival) other;
        return this.name.equals(that.name);
    }
}

