package HelloWorld;

public class IndexOf {
    public static void main(String[] args) {
        IndexOf io = new IndexOf();
        String haystack = "sadbutsad";
        String needle = "sad";
        System.out.println("Index Of: " + io.strStr(haystack, needle));
    }
    

    public int strStr(String haystack, String needle) {
        System.out.println("Haystack: " + haystack + ", Needle: " + needle);
        int[] lps = lps(needle);
        for(int j=0, i=0;i<haystack.length();i++){
            while(j>0 && haystack.charAt(i)!=needle.charAt(j)){
                j = lps[j-1];
            }
            if(haystack.charAt(i)==needle.charAt(j)){
                ++j;
            }
            if(j==needle.length()) return i-j;
        }
        return -1;
    }

    int[] lps(String n){
        int[] lps = new int[n.length()];
        for(int j=0,i=1;i<n.length();i++){
            while(j>0 && n.charAt(i)!=n.charAt(j)){
                j = lps[j-1];
            }
            if(n.charAt(i)==n.charAt(j)) lps[i]=++j;
        }
        return lps;
    }
}
