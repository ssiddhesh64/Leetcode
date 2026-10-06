package org.leetcode;

// https://leetcode.com/problems/scramble-string/
class ScrambleStrings {

    public boolean isScramble(int i1, int i2, int len, String s1, String s2, int n, int[][][] dp) {

        if(len == 1) {
            return s1.charAt(i1) == s2.charAt(i2);
        }

        if(dp[i1][i2][len] != -1) {
            return dp[i1][i2][len] == 1;
        }

        for(int k = 1; k < len; k++) {

            // check no swap
            if(isScramble(i1, i2, k, s1, s2, n, dp) && isScramble(i1 + k, i2 + k, len - k, s1, s2, n, dp)) {
                dp[i1][i2][len] = 1;
                return true;
            }

            // check swap
            if(isScramble(i1, i2 + len - k, k, s1, s2, n, dp) && isScramble(i1 + k, i2, len - k, s1, s2, n, dp)) {
                dp[i1][i2][len] = 1;
                return true;
            }
        }

        dp[i1][i2][len] = 0;
        return false;

    }
    public boolean isScramble(String s1, String s2) {

        int n = s1.length();

        int[] freq1 = new int[26];
        int[] freq2 = new int[26];

        for(int i = 0; i < n; i++) {
            freq1[s1.charAt(i) - 'a']++;
            freq2[s2.charAt(i) - 'a']++;
        }

        // check anagrams
        for(int i = 0; i < 26; i++) {
            if(freq1[i] != freq2[i]) {
                return false;
            }
        }

        int[][][] dp = new int[n][n][n + 1];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < n + 1; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }

        return isScramble(0, 0, n, s1, s2, n, dp);
    }
}