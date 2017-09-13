package com.honeacademy.petfinder.util;

import java.util.List;

/**
 * Created by jmaina on 8/31/17.
 */

public class Utils {
    public static String listToString(List<String> list, String separator) {
        StringBuilder sb = new StringBuilder();
        int counter=0;
        for (String s : list) {
            counter++;
            sb.append(s);
            sb.append(counter+1>list.size()?"":separator);
        }
        return sb.toString();
    }
}
