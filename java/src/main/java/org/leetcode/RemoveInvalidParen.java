package org.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

// https://leetcode.com/problems/remove-invalid-parentheses/?envType=daily-question&envId=2026-10-07
class RemoveInvalidParen {

    public int getMinRemovals(String s) {

        int n = s.length();
        int rem = 0;
        int bal = 0;

        int i = 0;
        while(i < n) {
            char ch = s.charAt(i);

            // If char is letter bal is unchanged, else +1/-1 depending upon bracket
            bal = Character.isLetter(ch) ? bal : ch == '(' ? bal + 1 : bal - 1;

            // If bal goes negative, we should remove cur char and reset bal to 0
            if(bal < 0) {
                bal = 0;
                rem++;
            }
            i++;
        }

        return rem + bal;
    }

    public void generateStrings(int pos, int bal, int removals, String cur, Set<String> res, int n, String s) {

        // System.out.println(pos + " " + bal + " " + removals + " " + cur);
        if(pos == n) {
            if(bal == 0 && removals == 0) {
                res.add(cur);
            }
            return;
        }

        if(bal < 0 || removals < 0) return;

        char ch = s.charAt(pos);
        int removed = 0;
        if(Character.isLetter(ch)) {
            generateStrings(pos + 1, bal, removals, cur + ch, res, n, s);
            return;
        }

        // remove cur char, decrease removals by 1 and bal unchanged
        generateStrings(pos + 1, bal, removals - 1, cur, res, n, s);

        // include cur char, update bal by +1/-1 and removals unchanged
        int balanceChange = ch == '(' ? 1 : -1;
        generateStrings(pos + 1, bal + balanceChange, removals, cur + ch, res, n, s);

    }

    public List<String> removeInvalidParentheses(String s) {

        int removals = getMinRemovals(s);

        Set<String> res = new TreeSet<>();
        generateStrings(0, 0, removals, "", res, s.length(), s);

        return new ArrayList<>(res);
    }
}
