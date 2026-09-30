package org.leetcode;

import java.util.*;

//https://leetcode.com/problems/brace-expansion-ii/?envType=daily-question&envId=2026-09-30

class BraceExpansionII {

    static class ParserResult {
        Set<String> res;
        int idx;

        public ParserResult(Set<String> res, int idx) {
            this.res = res;
            this.idx = idx;
        }
    }

    public List<String> braceExpansionII(String expression) {

        ParserResult pr = parseExpression(0, expression);;
        return new ArrayList<>(pr.res);

    }

    // expression := term (',' term)*
    public ParserResult parseExpression(int pos, String expression) {

        Set<String> result = new TreeSet<>();

        ParserResult termResult = parseTerm(pos, expression);

        result.addAll(termResult.res);
        pos = termResult.idx;

        // Handle union: term,term,term
        while (pos < expression.length()
                && expression.charAt(pos) == ',') {

            // consume ','
            pos++;

            termResult = parseTerm(pos, expression);

            result.addAll(termResult.res);
            pos = termResult.idx;
        }

        return new ParserResult(result, pos);
    }

    // term := factor*
    public ParserResult parseTerm(int pos, String expression) {

        // Identity element for concatenation
        Set<String> result = new TreeSet<>();
        result.add("");

        while (pos < expression.length()) {

            char ch = expression.charAt(pos);

            // Term ends at ',' or '}'
            if (ch == ',' || ch == '}') {
                break;
            }

            ParserResult factorResult =
                    parseFactor(pos, expression);

            result = concatenate(result, factorResult.res);

            pos = factorResult.idx;
        }

        return new ParserResult(result, pos);
    }


    // factor := letter | '{' expression '}'
    public ParserResult parseFactor(int pos, String expression) {

        char ch = expression.charAt(pos);

        // Single letter
        if (ch >= 'a' && ch <= 'z') {

            Set<String> result = new TreeSet<>();
            result.add(String.valueOf(ch));

            return new ParserResult(result, pos + 1);
        }

        // Nested expression
        if (ch == '{') {
            // Parse expression after '{'
            ParserResult inner =
                    parseExpression(pos + 1, expression);

            // inner.idx points to '}'
            return new ParserResult(
                    inner.res,
                    inner.idx + 1
            );
        }

        throw new IllegalArgumentException(
                "Unexpected character at position: " + pos
        );
    }

    public Set<String> concatenate(Set<String> left, Set<String> right) {

        Set<String> res = new TreeSet<>();
        for(String l : left) {
            for(String r : right) {
                res.add(l + r);
            }
        }
        return res;
    }

    public static void main(String[] args) {

    }
}

