# https://leetcode.com/problems/distinct-subsequences-ii/?envType=daily-question&envId=2026-09-07
# Given a string s, return the number of distinct non-empty subsequences of s. Since the answer may be very large, return it modulo 109 + 7.
class Solution:
    def distinctSubseqII(self, s: str) -> int:
        
        n = len(s)
        MOD = 10**9 + 7

        dp = [0] * (n + 1)
        dp[0] = 1

        last = [0] * 26

        for i in range(n):
            ch = ord(s[i]) - ord('a')
            dp[i + 1] = (2 * dp[i] - last[ch]) % MOD
            last[ch] = dp[i] % MOD
        
        return (dp[n] - 1) % MOD