package org.leetcode;

// https://leetcode.com/problems/find-x-value-of-array-ii/?envType=daily-question&envId=2026-09-22

import java.util.Arrays;

public class SegTree {

    static class Node {

        int pdt;
        int[] prefixCnt;

        Node(int k) {
            pdt = 1;
            prefixCnt = new int[k];
        }
    }

    private final int k;
    private final Node[] tree;
    private final int[] nums;

    public SegTree(int[] nums, int k) {
        this.k = k;
        this.nums = nums;

        this.tree = new Node[4 * nums.length];

        buildTree(1, 0, nums.length - 1);
    }

    private void buildTree(int node, int left, int right) {
        if(left > right) return;
        if(left == right) {
            tree[node] = createLeaf(nums[left]);
            return;
        }

        int mid = left +  (right - left) / 2;
        buildTree(2 * node, left, mid);
        buildTree(2 * node + 1, mid + 1, right);

        tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
    }

    public void update(int index, int value) {

        nums[index] = value;

        update(1, 0, nums.length - 1, index, value);
    }

    private void update(
            int node,
            int left,
            int right,
            int index,
            int value
    ) {

        if (left == right) {
            tree[node] = createLeaf(value);
            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {

            update(
                    node * 2,
                    left,
                    mid,
                    index,
                    value
            );

        } else {

            update(
                    node * 2 + 1,
                    mid + 1,
                    right,
                    index,
                    value
            );
        }

        tree[node] = merge(
                tree[node * 2],
                tree[node * 2 + 1]
        );
    }

    public Node query(int queryLeft, int queryRight) {

        return query(
                1,
                0,
                nums.length - 1,
                queryLeft,
                queryRight
        );
    }

    private Node query(
            int node,
            int left,
            int right,
            int queryLeft,
            int queryRight
    ) {

        // No overlap
        if (right < queryLeft || left > queryRight) {
            return null;
        }

        // Complete overlap
        if (queryLeft <= left && right <= queryRight) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        Node leftResult = query(
                node * 2,
                left,
                mid,
                queryLeft,
                queryRight
        );

        Node rightResult = query(
                node * 2 + 1,
                mid + 1,
                right,
                queryLeft,
                queryRight
        );

        return merge(leftResult, rightResult);
    }

    public int getAnswer(int start, int x) {

        Node result = query(start, nums.length - 1);

        return result.prefixCnt[x];
    }

    public Node createLeaf(int val) {
        Node node = new Node(k);
        node.pdt = val % k;
        node.prefixCnt[val % k] = 1;
        return node;
    }

    public Node merge(Node left, Node right) {
        if(left == null) return right;
        if(right == null) return left;

        Node merged = new Node(k);
        merged.pdt = (int) (((long) left.pdt * right.pdt) % k);

        for(int r = 0; r < k; r++) {
            merged.prefixCnt[r] += left.prefixCnt[r];
        }

        for(int r = 0; r < k; r++) {

            if(right.prefixCnt[r] == 0) {
                continue;
            }

            int newRem = (int) (((long) left.pdt * r) % k);
            merged.prefixCnt[newRem] += right.prefixCnt[r];
        }
        return merged;
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        SegTree ra = new SegTree(nums, k);
        int[] res = new int[queries.length];

        int i = 0;
        for(int[] query : queries) {
            int idx = query[0];
            int val = query[1];
            int start = query[2];
            int x = query[3];

            ra.update(idx, val);
            res[i++] = ra.getAnswer(start, x);
        }

        return res;
    }

    public static void main(String[] args) {

        int[] nums1 = {1, 2, 3, 4, 5};

        int k1 = 3;

        int[][] queries1 = {
                {2, 2, 0, 2},
                {3, 3, 3, 0},
                {0, 1, 0, 1}
        };

        SegTree solution1 = new SegTree(nums1, k1);

        int[] result1 =
                solution1.resultArray(nums1, k1, queries1);

        System.out.println("Example 1:");
        System.out.println(Arrays.toString(result1));

        // Expected:
        // [2, 2, 2]


        // =====================================================
        // Example 2
        // =====================================================

        int[] nums2 = {1, 2, 4, 8, 16, 32};

        int k2 = 4;

        int[][] queries2 = {
                {0, 2, 0, 2},
                {0, 2, 0, 1}
        };

        SegTree solution2 = new SegTree(nums2, k2);

        int[] result2 =
                solution2.resultArray(nums2, k2, queries2);

        System.out.println("Example 2:");
        System.out.println(Arrays.toString(result2));

        // Expected:
        // [1, 0]


        // =====================================================
        // Example 3
        // =====================================================

        int[] nums3 = {1, 1, 2, 1, 1};

        int k3 = 2;

        int[][] queries3 = {
                {2, 1, 0, 1}
        };

        SegTree solution3 = new SegTree(nums3, k3);

        int[] result3 =
                solution3.resultArray(nums3, k3, queries3);

        System.out.println("Example 3:");
        System.out.println(Arrays.toString(result3));
    }
}
