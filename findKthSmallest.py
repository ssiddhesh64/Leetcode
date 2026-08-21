class Solution:
    def findKthSmallest(self, coins: List[int], k: int) -> int:
        
        def gcd(a, b):
            if b == 0:
                return a
            return gcd(b, a % b)

        def removeDivisible():
            new_coins = []
            n = len(coins)

            for i in range(n):
                is_divisible = False
                for j in range(len(new_coins)):
                    if coins[i] % new_coins[j] == 0:
                        is_divisible = True
                        break
                if not is_divisible:
                    new_coins.append(coins[i])

            return new_coins

        def count(x, lcms):
            total = 0
            for cnt in range(1, len(coins) + 1):
                sign = 1 if cnt % 2 == 1 else -1
                for _, lcm in lcms[cnt]:
                    total += sign * (x // lcm)
            return total


        coins.sort()
        coins = removeDivisible()
        n = len(coins)
        coins = coins[::-1]
        # print(coins)

        lcms = {i : [] for i in range(1, n + 1)}
        for i in range(n):
            lcms[1].append((1 << i, coins[n - i - 1]))

        # print(coins)
        for cnt in range(2, n + 1):
            for prev in lcms[cnt - 1]:
                num, prev_lcm = prev
                st = n - 1
                while (1 << st) & num == 0:
                    st -= 1
                # print(num, prev_lcm, st)
                for to_set in range(st + 1, n):
                    if num & (1 << to_set) == 0:
                        new_num = num | (1 << to_set)
                        coin_num = coins[n - to_set - 1]
                        new_lcm = (prev_lcm // gcd(prev_lcm, coin_num)) * coin_num
                        if new_lcm > k * coins[-1]:
                            break
                        lcms[cnt].append((new_num, new_lcm))
    
        # print(lcms)
        lo, hi = 1, k * coins[-1]
        while lo < hi:
            mid = (lo + hi) // 2
            if count(mid, lcms) < k:
                lo = mid + 1
            else:
                hi = mid
        
        # print(lo)
        return lo