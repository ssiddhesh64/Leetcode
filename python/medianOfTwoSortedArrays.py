# https://leetcode.com/problems/median-of-two-sorted-arrays/
class Solution:

    def findMedianSortedArrays(self, nums1: list[int], nums2: list[int]) -> float:

        def find(off, a1, a2, s1, e1, s2, e2, m, n):

            # print("called with", off, a1[s1: e1 + 1], a2[s2:e2 + 1], s1, e1, s2, e2)
            if s1 > e1:
                return a2[s2 + off]

            if s2 > e2:
                return a1[s1 + off]

            # Consider a1 always less than or equal to a2, if not swap them to fix keep this invariant
            if a1[s1] > a2[s2]:
                a1, a2 = a2, a1
                s1, s2 = s2, s1
                e1, e2 = e2, e1

            # print("called with", off, a1[s1: e1 + 1], a2[s2:e2 + 1], s1, e1, s2, e2)
            l1 = e1 - s1 + 1
            l2 = e2 - s2 + 1

            # Disjoint
            if a2[s2] > a1[e1]:
                if off < l1:
                    return a1[s1 + off]
                return a2[s2 + off - l1]

            low1 = bisect_right(a1, a2[s2], s1, e1 + 1) - s1
            if(off < low1):
                return a1[s1 + off]

            # ---------------
            #    -------
            # Complete overlap
            if a1[e1] >= a2[e2]:
                upp1 = bisect_right(a1, a2[e2], s1, e1 + 1)
                first = upp1 - s1
                second = l2
                if off >= first + second:
                    return a1[upp1 + (off - (first + second))]
                return find(off - low1, a1, a2, s1 + low1, upp1 - 1, s2, e2, m, n)

            # ---------------
            #    --------------------
            # partial overlap
            low2 = bisect_left(a2, a1[e1], s2, e2 + 1)
            first = l1
            second = low2 - s2
            if off >= first + second:
                return a2[low2 + (off - (first + second))]
            return find(off - low1, a1, a2, s1 + low1, e1, s2, low2 - 1, m, n)

        m, n = len(nums1), len(nums2)

        m1, m2 = 0, 0
        offset = (m + n) // 2
        m1 = find(offset, nums1, nums2, 0, m - 1, 0, n - 1, m, n)
        if ((m + n) % 2 == 1):
            m2 = m1
        else:
            m2 = find(offset - 1, nums1, nums2, 0, m - 1, 0, n - 1, m, n)

        return (m1 + m2) / 2

