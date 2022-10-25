package dev.scaraz.common.utils;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

public class PaginationUtil {


    public static <T> HttpHeaders getPaginationHeader(Page<T> page) {
        HttpHeaders header = new HttpHeaders();
        header.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        header.add("X-Total-Page", String.valueOf(page.getTotalPages()));
        header.add("X-Sort", page.getSort().toString());
        return header;
    }
}
