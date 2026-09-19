# https://leetcode.com/problems/distinct-subsequences/?envType=daily-question&envId=2026-09-07
# Given two strings s and t, return the number of distinct subsequences of s which equals t.

class Solution:
    def numDistinct(self, s: str, t: str) -> int:
    
        n, m = len(s), len(t)
        prevdp = [0] * (m + 1)
        prevdp[0] = 1
        for i in range(n):
            newdp = [0] * (m + 1)
            newdp[0] = 1
            for j in range(m):
                if s[i] == t[j]:
                    newdp[j + 1] = prevdp[j]
                newdp[j + 1] += prevdp[j + 1]
            prevdp = newdp
        
        return prevdp[m]

        
        