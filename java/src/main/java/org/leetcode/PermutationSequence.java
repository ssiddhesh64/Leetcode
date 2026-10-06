package org.leetcode;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/permutation-sequence/
class PermutationSequence {

    int[] fact = new int[10];
    public String getPermutation(int n, int k) {

        // int[] nums = new int[n + 1];
        int[] fact = new int[10];
        fact[0] = 1;

        for(int i = 1; i < 10; i++) {
            fact[i] = i * fact[i - 1];
        }

        List<Integer> nums = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            nums.add(i);
        }

        // convert to 0 based indexing
        k--;

        StringBuilder res = new StringBuilder();

        for(int rem = n; rem >= 1; rem--) {
            int blockSize = fact[rem - 1];

            int idx = k / blockSize;
            res.append(nums.get(idx));
            nums.remove(idx);

            k %= blockSize;
        }

        return res.toString();
        // return recur(n, k, "", nums, n);
    }

    public String getKthSmallest(int n, int k, int[] nums) {

        int off = 0;
        int i = 1;
        while(i <= n) {
            if(nums[i] == 1) {
                i++;
                continue;
            }
            if(off == k) {
                nums[i] = 1;
                return String.valueOf(i);
            }
            off++;
            i++;
        }

        return "";
    }

    public String recur(int n, int k, String cur, int[] nums, int rem) {

        // System.out.println("called with n = " + n + " k = " + k);
        if(k == 1 || rem == 1) {
            // System.out.println("in k == 1");
            for(int i = 1; i <= n; i++) {
                if(nums[i] == 0) {
                    cur += String.valueOf(i);
                }
            }
            return cur;
        }

        int blockSize = fact[rem - 1];

        int off = (k - 1) / blockSize;

        String ch = getKthSmallest(n, off, nums);
        cur += ch;

        String res = recur(n, k - off * blockSize, cur, nums, rem - 1);
        return res;
    }
}