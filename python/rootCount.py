# https://leetcode.com/problems/count-number-of-possible-root-nodes/
class Solution:
    def rootCount(self, edges: list[list[int]], guesses: list[list[int]], k: int) -> int:

        guessSet = set()
        for guess in guesses:
            guessSet.add(tuple(guess))

        # print(guessSet)
        rootGuess = defaultdict(int)
        graph = defaultdict(list)

        for u, v in edges:
            graph[u].append(v)
            graph[v].append(u)

        correctGuesses = 0
        def dfs(par, cur):
            nonlocal correctGuesses
            if (par, cur) in guessSet:
                correctGuesses += 1

            for nbh in graph[cur]:
                if nbh != par:
                    dfs(cur, nbh)

        def dfs2(par, cur, guesses):
            if(par, cur) in guessSet:
                guesses -= 1

            if(cur, par) in guessSet:
                guesses += 1

            rootGuess[cur] = guesses
            for nbh in graph[cur]:
                if nbh != par:
                    dfs2(cur, nbh, guesses)

        dfs(-1, 0)
        dfs2(-1, 0, correctGuesses)
        return sum([1 for v in rootGuess.values() if v >= k])

