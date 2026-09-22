package org.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

//https://leetcode.com/problems/tag-validator/

public class TagValidator {

    public int isValidTagName(String code, int st) {

        int n = code.length();
        if(st < 0 || st >= n) return -1;

        int i = st;
        while(i < n && code.charAt(i) != '>') {
            if(code.charAt(i) < 'A' || code.charAt(i) > 'Z') return -1;
            if(i - st + 1 > 9) return -1;
            i++;
        }
        if(i == st || i >= n) return -1;
        return i;
    }

    public boolean isValid(String code) {

        if (code == null || code.isEmpty()) {
            return false;
        }

        int n = code.length();
        Deque<String> tags = new ArrayDeque<>();

        boolean rootClosed = false;

        int i = 0;
        while (i < n) {

            if (rootClosed) {
                return false;
            }

            char ch = code.charAt(i);
            if(ch == '<') {
                i++;

                if (i >= n) {
                    return false;
                }

                char next = code.charAt(i);
                int end;
                if(next == '/') {
                    i++;
                    end = isValidCloseTag(code, i, tags);
                    if (end < 0) {
                        return false;
                    }

                    if (tags.isEmpty()) {
                        rootClosed = true;
                    }
                } else if (next == '!'){

                    if (tags.isEmpty()) {
                        return false;
                    }
                    end = isValidCdata(code, i);

                    if (end < 0) {
                        return false;
                    }
                } else {

                    end = isValidTagName(code, i);

                    if (end < 0) {
                        return false;
                    }

                    tags.push(code.substring(i, end));
                }

                i = end + 1;

            } else {

                if (tags.isEmpty()) {
                    return false;
                }

                i++;
            }
        }

        return rootClosed && tags.isEmpty();
    }

    private int isValidCdata(String code, int st) {

        if (!code.startsWith("![CDATA[", st)) {
            return -1;
        }

        int end = code.indexOf("]]>", st + 8);

        if (end == -1) {
            return -1;
        }

        return end + 2;
    }

    private int isValidCloseTag(String code, int st, Deque<String> tags) {

        int n = code.length();
        if(tags.isEmpty() || st < 0 || st >= n) return -1;

        String tagName = tags.peek();
        int i = st;
        int j = 0;
        while(i < n && code.charAt(i) != '>') {
            if(j >= tagName.length() || code.charAt(i++) != tagName.charAt(j++)) return -1;
        }

        if (j != tagName.length()) {
            return -1;
        }

        if(i >= n) return -1;
        tags.pop();
        return i;
    }

    public static void main(String[] args) {

        TagValidator tagValidator = new TagValidator();
        String code1 = "<DIV>This is the first line <![CDATA[<div>]]></DIV>";
        String code2 = "<DIV>This is the first line <![CDATA[<div>]]></DIV>";
        String code3 = "<DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>";
        String code4 = "<A>  <B> </A>   </B>";
        String code5 = "<![CDATA[wahaha]]]><![CDATA[]> wahaha]]>";
        String code6 = "<A><![CDATA[</A>]]kkk></A>";

//        String code5 = "<![CDATA[wahaha]]]><![CDATA[]> wahaha]]>";
        System.out.println("hello");
//        System.out.println(tagValidator.isValid(code1));
//        System.out.println(tagValidator.isValid(code2));
//        System.out.println(tagValidator.isValid(code3));
//        System.out.println(tagValidator.isValid(code4));
//        System.out.println(tagValidator.isValid(code5));
        System.out.println(tagValidator.isValid(code6));
    }
}
