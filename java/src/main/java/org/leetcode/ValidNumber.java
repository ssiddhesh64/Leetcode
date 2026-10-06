package org.leetcode;

import java.util.ArrayList;
import java.util.List;

class ValidNumber {

    public List<String> split(String s) {

        List<String> res = new ArrayList<>();
        int prev = 0;
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(ch == 'e' || ch == 'E') {
                res.add(s.substring(prev, i));
                prev = i + 1;
            }
        }
        res.add(s.substring(prev));
        return res;
    }

    public boolean isInteger(String s) {

        int n = s.length();
        if(n == 0) {
            return false;
        }

        char first = s.charAt(0);
        int i = 0;
        if(first == '-' || first == '+') i++;

        if(i == n) return false;

        while(i < n) {
            char num = s.charAt(i);
            if(num < '0' || num > '9') {
                return false;
            }
            i++;
        }

        return true;
    }

    public boolean isDecimal(String s) {

        if(s.equals(".")) return false;

        int n = s.length();
        if(n == 0) {
            return false;
        }

        char first = s.charAt(0);
        int i = 0;
        if(first == '-' || first == '+') i++;


        for(; i < n; i++) {
            char num = s.charAt(i);
            if(s.charAt(i) == '.') break;

            if(num < '0' || num > '9') {
                return false;
            }
        }

        if(i == n) return false;

        i++;
        for(; i < n; i++) {
            char num = s.charAt(i);
            if(num < '0' || num > '9') {
                return false;
            }
        }

        return true;
    }

    public boolean isNumber(String s) {

        int n = s.length();

        // Should not be empty
        if(n == 0) {
            return false;
        }

        char first = s.charAt(0);
        int i = 0;
        if(first == '-' || first == '+') i++;

        int prev = i;
        for(; i < n; i++) {
            char ch = s.charAt(i);
            if(ch == '.') continue;
            if(ch == 'e' || ch == 'E') break;

            if(ch < '0' || ch > '9') {
                return false;
            }
        }

        boolean firstPart = isInteger(s.substring(prev, i)) || isDecimal(s.substring(prev, i));
        if(i == n || !firstPart) {
            return firstPart;
        }

        // If contains exponent, then righ half should be integer
        return isInteger(s.substring(i + 1));
    }

    public void testIsDecimal(String s) {
        boolean isDec = isDecimal(s);
        System.out.println("Is decimal " + s +  " " + isDec);
    }

    public void testIsInteger(String s) {
        boolean isInt = isInteger(s);
        System.out.println("Is Int " + s +  " " + isInt);
    }
}
