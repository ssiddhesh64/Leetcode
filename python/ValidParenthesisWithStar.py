# https://leetcode.com/problems/valid-parenthesis-string/?envType=daily-question&envId=2026-10-04

class Solution:
    def checkValidString(self, s: str) -> bool:

        n = len(s)
        bracketStack = []
        starStack = []

        for i in range(n):
            ch = s[i]
            if ch == '(':
                bracketStack.append(i)
            elif ch == '*':
                starStack.append(i)
            else:
                if bracketStack:
                    bracketStack.pop()
                elif starStack:
                    starStack.pop()
                else:
                    return False

        # check for unbalanced open brackets '('
        while bracketStack:
            if not starStack:
                return False

            # if last open bracket is after star, not possible
            if bracketStack[-1] > starStack[-1]:
                return False

            # match one open bracket with one star
            bracketStack.pop()
            starStack.pop()

        return True