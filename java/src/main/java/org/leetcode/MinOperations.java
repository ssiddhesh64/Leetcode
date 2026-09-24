package org.leetcode;
//https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/description/?envType=daily-question&envId=2026-09-23

public class MinOperations {

    public int minOperations(int[] nums, int x) {

        int n = nums.length;
        int j = n - 1;
        long suff = 0;
        while(j >= 0) {
            suff += nums[j];
            if(suff >= x) break;
            j--;
        }

        if(j < 0) return -1;
        long pref = 0;
        int res = Integer.MAX_VALUE;
        for(int i = 0; i < n; i++) {
            long sum_ = pref + suff;
            while(j < n && (pref + suff > x)) {
                suff -= nums[j++];
            }

            if(pref + suff == x) res = Math.min(res, i + n - j);
            pref += nums[i];

            if(suff == 0 && pref > x) break;
        }

        if(res == Integer.MAX_VALUE) return -1;

        return res;
    }

    public static void main(String[] args) {

        MinOperations mn = new MinOperations();

        int[] arr1 = {1,1,4,2,3};
        int x1 = 5;

        int[] arr2 =  {5,6,7,8,9};
        int x2 = 10;

        System.out.println(mn.minOperations(arr1, x1));
        System.out.println(mn.minOperations(arr2, x2));
    }
}
