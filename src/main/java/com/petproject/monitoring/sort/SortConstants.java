package com.petproject.monitoring.sort;

import java.util.Arrays;
import java.util.List;

public interface SortConstants {
    String DATE_FIELD = "created_at_t";
    String DESC = "desc";
    String ASC = "asc";
    List<String> SORT_CRITERIA = Arrays.asList(DESC, ASC);
}
