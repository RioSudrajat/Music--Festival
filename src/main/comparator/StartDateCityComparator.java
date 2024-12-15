package main.comparator;

import java.util.Comparator;

import main.model.Festival;

public class StartDateCityComparator implements Comparator<Festival> {
    @Override
    public int compare(Festival festival1, Festival festival2) {
        return festival1.getStartDateCity().compareTo(festival2.getStartDateCity());
    }
}
