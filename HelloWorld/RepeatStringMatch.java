package HelloWorld;

public class RepeatStringMatch {

    public static void main(String[] args) {
        RepeatStringMatch rsm = new RepeatStringMatch();
        String a = "abab";
        String b = "aba";
        System.out.println("Minimum Repeats: " + rsm.repeatedStringMatch(a, b));
    }

    public int repeatedStringMatch(String a, String b) {
        String source=a;
        int i=1;
        while(source.length()<b.length()){
            source += a;
            i++;
        }

        if(source.equals(b)) return i;

        if(rabinkarp(source, b) != -1) return i;
        if(rabinkarp(source+a, b) != -1) return i+1;

        return -1;
    }

    int rabinkarp(String s, String t){
        int m=t.length();
        int power=1;
        int M = 1000000;
        for(int i=0;i<m;i++){
            power = (power*31)%M;
        }

        int tcode=0;
        for(int i=0;i<m;i++){
            tcode = ((tcode*31)%M+t.charAt(i))%M;
        }

        int scode=0;
        for(int i=0;i<s.length();i++){
            scode = ((scode*31)%M+s.charAt(i))%M;
            if(i<m-1) continue;

            if(i>=m){
                scode = (scode - (s.charAt(i-m)*power)%M)%M;
            }

            if(scode<0) scode+=M;

            if(tcode==scode){
                if(s.substring(i-m+1, i+1).equals(t)) return i-m+1;
            }
        }
        return -1;
    }
}
