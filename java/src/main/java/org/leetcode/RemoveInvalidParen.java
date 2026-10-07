package org.leetcode;

import java.util.*;

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

    public void generateStrings(int pos, int bal, int removals, StringBuilder cur, Set<String> res, int n, String s) {

        // System.out.println(pos + " " + bal + " " + removals + " " + cur);
        if(pos == n) {
            if(bal == 0 && removals == 0) {
                res.add(cur.toString());
            }
            return;
        }

        // Invalid cases pruning, if bal is neg or if removals are exhausted or removals are more than remaining chars
        if(bal < 0 || removals < 0 || removals > n - pos) return;

        char ch = s.charAt(pos);
        int balanceChange = Character.isLetter(ch) ? 0 : ch == '(' ? 1 : -1;

        // include cur char, update bal by +1/-1 and removals unchanged
        cur.append(ch);
        generateStrings(pos + 1, bal + balanceChange, removals, cur, res, n, s);
        cur.deleteCharAt(cur.length() - 1);

        // remove cur char if it is bracket, decrease removals by 1 and bal unchanged
        if(!Character.isLetter(ch)) {
            generateStrings(pos + 1, bal, removals - 1, cur, res, n, s);
        }
    }

    public List<String> removeInvalidParentheses(String s) {

        int removals = getMinRemovals(s);

        Set<String> res = new HashSet<>();
        generateStrings(0, 0, removals, new StringBuilder(), res, s.length(), s);

        return new ArrayList<>(res);
    }
}
