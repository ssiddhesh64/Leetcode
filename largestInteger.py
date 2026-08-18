# Find the Largest Almost Missing Integer
# https://leetcode.com/problems/find-the-largest-almost-missing-integer/description/?envType=daily-question&envId=2026-08-18
# Level - Easy
# Tags - Array, Hash Table

from collections import Counter
from typing import List

class Solution:
    def largestInteger(self, nums: List[int], k: int) -> int:

        n = len(nums)
        freq = Counter(nums)
            
        if k == 1:
            return max(
                (num for num, count in freq.items() if count == 1),
                default=-1
            )

        if k == n:
            return max(nums)
        
        # For 1 < k < n, only boundary elements can appear in exactly one
        # subarray of length k.
        first, last = nums[0], nums[-1]
        if first < last:
            first, last = last, first

        if freq[first] == 1:
            return first
        if freq[last] == 1:
            return last
        
        return -1