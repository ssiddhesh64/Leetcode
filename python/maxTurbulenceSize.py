#https://leetcode.com/problems/longest-turbulent-subarray/

class Solution:
    def maxTurbulenceSize(self, arr: list[int]) -> int:

        n = len(arr)
        if n == 1:
            return 1

        prev = 1
        prev = 2 if (arr[0] != arr[1]) else 1

        res = prev
        for i in range(2, n):
            if(arr[i] == arr[i - 1]):
                prev = 1
                continue
            if(arr[i - 1] > arr[i]):
                if(arr[i - 2] < arr[i - 1]):
                    prev += 1
                else:
                    prev = 2
            else:
                if(arr[i - 2] > arr[i - 1]):
                    prev += 1
                else:
                    prev = 2
            res = max(res, prev)
        return res