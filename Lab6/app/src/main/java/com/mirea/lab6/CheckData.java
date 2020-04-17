package com.mirea.lab6;

public class CheckData {

    static boolean checkPrice(String price) {
        if (price.contains(".")) {
            return price.matches("^[0-9]+(\\.[0-9]{1,2})?$");
        } else {
            return checkCount(price);
        }
    }

    static boolean checkCount(String count) {
        return count.matches("^[0-9]+$");
    }
}
