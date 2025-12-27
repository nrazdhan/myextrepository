package HelloWorld;

public class HappyPrefix {

    public static void main(String[] args) {
        HappyPrefix hp = new HappyPrefix();
        String s = "abcdabcc";
        System.out.println("Longest Happy Prefix: " + hp.longestPrefix(s));
    }

    public String longestPrefix(String s) {
        int[] dp = new int[s.length()];
        int len = 0, i = 1;
        while(i < s.length()){
            if(s.charAt(i) == s.charAt(len)){
                dp[i] = ++len;
                i++;
            }
            else{
                if(len>0){
                    len = dp[len-1];
                }
                else{
                    dp[i] = 0;
                    i++;
                }
            }
        }
        return s.substring(0,dp[dp.length-1]);
    }

    // public String longestPrefix(String s) {
    //     for(int i=1;i<s.length();i++){
    //         String s1=s.substring(i, s.length());
    //         String s2=s.substring(0,s.length()-i);
    //         if(s1.equals(s2))
    //             return s1;
    //     }
    //     return "";
    // }
}
