package HelloWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodStrings {

    public static void main(String[] args) {
        GoodStrings gs = new GoodStrings();
        int n = 8;
        String s1 = "leetcode";
        String s2 = "leetgoes";
        String evil = "leet";
        System.out.println("Good Strings Count: " + gs.findGoodStrings(n, s1, s2, evil));
    }


    static final int MOD = 1_000_000_000 + 7;
    Map<String, Integer> index = new HashMap<>();
    HashMap[][][] memo;
    String A;
    String Z;
    public int findGoodStrings(int n, String s1, String s2, String evil) {
        A = generate(n, 'a');
        Z = generate(n, 'z');

        memo = new HashMap[4][4][n + 1];
        index.put(s1, 0);
        index.put(s2, 1);
        index.put(A, 2);
        index.put(Z, 3);
        return f(n, s1, s2, evil, Collections.emptyList());
    }
    
    private int f(int n, String s1, String s2, String evil, List<Integer> evilCandidates) {
	    // prefixed with evil
        if (!evilCandidates.isEmpty() && evilCandidates.get(0) == evil.length()) {
            return 0;
        }
		// empty string
        if (n == 0) {
            return 1;
        }
        
        HashMap map = memo[index.get(s1)][index.get(s2)][n];
        
        String key = evilCandidates.toString();
        if (map != null && map.containsKey(key)) {
            return (int) map.get(key);
        }
        int k = s1.length() - n;
        long total = 0;
        char c1 = s1.charAt(k), c2 = s2.charAt(k);
        for (char s = c1; s <= c2; s++) {
		    // if we have abc to xyz, we split it into abc-azz, baa-bzz, caa-czz, ..., waa-wzz, xaa-xyz
            String left = s == c1 ? s1 : A;
            String right = s == c2 ? s2 : Z;
            
            List<Integer> nextEvil = new ArrayList<>();
			// if current symbol is potentially the next symbol of any candidate - add it to the new candidates list
            for (int c : evilCandidates) {
                if (s == evil.charAt(c)) {
                    nextEvil.add(c + 1);
                }
            }
			// check if current character is a start of evil word
            if (s == evil.charAt(0)) {
                nextEvil.add(1);
            }
            total += f(n - 1, left, right, evil, nextEvil);
        }
        
        int res = (int) (total % MOD);
        
        if (map == null) {
            map = new HashMap<>();
            memo[index.get(s1)][index.get(s2)][n] = map;
        }
        map.put(key, res);
        return res;
    }
    
    static String generate(int count, char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
