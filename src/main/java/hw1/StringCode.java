package ir.soroushtabesh.ap2020.hw1;

public class StringCode {

    /**
     * Given a string, returns the length of the largest run.
     * A a run is a series of adajcent chars that are the same.
     *
     * @param str
     * @return The largest length of consecutive equal characters
     */
    public static int maxCons(String str) {
        int counter = 1;
        int mxCounter = str.isEmpty() ? 0 : 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                ++counter;
                mxCounter = Math.max(mxCounter, counter);
            } else {
                counter = 1;
            }
        }
        return mxCounter;
    }

    /**
     * Given a string, for each digit in the original string,
     * replaces the digit with that many occurrences of the character
     * following. So the string "a3tx2z" yields "attttxzzz".
     *
     * @param str
     * @return blown up string
     */
    public static String blowup(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                for (int j = 0; j < str.charAt(i) - '0'; j++)
                    stringBuilder.append(str.charAt(i + 1));
                i += 1;
            } else {
                stringBuilder.append(str.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Given 2 strings, consider all the substrings within them
     * of length len. Returns true if there are any such substrings
     * which appear in both strings.
     */
    public static boolean isIntersect(String a, String b, int len) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 1 : 0));
            }
        }
        return dp[a.length()][b.length()] >= len;
    }
}
