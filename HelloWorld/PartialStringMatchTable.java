package HelloWorld;

public class PartialStringMatchTable {
    public static void main(String[] args) {
        PartialStringMatchTable pmt = new PartialStringMatchTable();
        String pattern = "ABABAC";
        int[] table = pmt.buildPartialMatchTable(pattern);
        System.out.print("Partial Match Table: ");
        for(int i : table){
            System.out.print(i + " ");
        }
    }

    public int[] buildPartialMatchTable(String pattern){
        int n = pattern.length();
        int[] table = new int[n];
        int j = 0;
        int i = 1;

        while(i < n){
            if(pattern.charAt(i) == pattern.charAt(j)){
                j++;
                table[i] = j;
                i++;
            }
            else{
                if(j != 0){
                    j = table[j - 1];
                }
                else{
                    table[i] = 0;
                    i++;
                }
            }
        }
        return table;
    }
}
