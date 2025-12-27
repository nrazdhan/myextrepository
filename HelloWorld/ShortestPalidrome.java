package HelloWorld;

public class ShortestPalidrome {
    public static void main(String[] args) {
        ShortestPalidrome sp = new ShortestPalidrome();
        String s = "aacecaaa";
        System.out.println("Shortest Palindrome: " + sp.shortestPalindrome(s));
    }

    public String shortestPalindrome(String s) {
        StringBuilder sb = new StringBuilder(s);
        String sr =  sb.reverse().toString();
        
        int i=0;
        for(i=0;i<s.length();i++){
           int j=s.length()-i;
           if(sr.substring(i).equals(s.substring(0, j))) {
                break;
           }
        }
        sb= new StringBuilder();
        for(int j=0;j<i;j++){
            sb.append(sr.charAt(j));
        }
        return sb.toString()+s;
    }

    // public String shortestPalindrome(String s) {
    //     int i=0;
    //     for(i=s.length()-1;i>=0;i--){
    //         if(isPalindrome(s, i))
    //             break;
    //     }

    //     StringBuilder sb = new StringBuilder(s);
    //     sb.reverse();
    //     for(int j=i+1;j<s.length();j++){
    //         sb.append(s.charAt(j)); 
    //     }
    //     sb.reverse();
    //     return sb.toString();
    // }

    // boolean isPalindrome(String s, int p){
    //     int i=0;
    //     int j=p;
    //     while(i<j){
    //         if(s.charAt(i)!=s.charAt(j)) return false;
    //         i++;
    //         j--;
    //     }
    //     return true;
    // }
}
