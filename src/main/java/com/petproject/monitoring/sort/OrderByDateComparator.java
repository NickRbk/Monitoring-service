package com.petproject.monitoring.sort;

import com.petproject.monitoring.domain.model.Tweet;
import lombok.AllArgsConstructor;

import java.util.Comparator;

@AllArgsConstructor
public class OrderByDateComparator implements Comparator<Tweet>, SortConstants {
    private String key;

    @Override
    public int compare(Tweet t1, Tweet t2) {
        if(DESC.equals(key)) {
            return t2.getCreatedAtTwitter().compareTo(t1.getCreatedAtTwitter());
        }
        if(ASC.equals(key)) {
            return t1.getCreatedAtTwitter().compareTo(t2.getCreatedAtTwitter());
        }
        return 0;
    }
}
