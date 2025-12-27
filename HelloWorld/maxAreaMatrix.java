package HelloWorld;

import java.util.Stack;

public class maxAreaMatrix {
    public static void main(String[] args) {
        maxAreaMatrix mam = new maxAreaMatrix();
        char[][] matrix = {
            {'1','0'}
        };
        System.out.println("Max Area of 1's in Matrix: " + mam.maximalRectangle(matrix));
    }

    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return 0;

        int[] barHeights = new int[matrix[0].length];
        int maxA=0;
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                barHeights[j] = (matrix[i][j]=='1')?barHeights[j]+1:0;
            }
            maxA = Math.max(maxA, maxArea(barHeights));
        }

        return maxA;
    }

    int maxArea(int[] barHeights){
        int n=barHeights.length;
        Stack<Integer> st = new Stack<>();
        int[] smallLeft = new int[n];
        int[] smallRight = new int[n];

        for(int i=0;i<n;i++){
            while(!st.isEmpty() && barHeights[st.peek()] >= barHeights[i]){
                st.pop();
            }
            smallLeft[i] = (st.isEmpty())?-1:st.peek();
            st.push(i);
        }

        st.clear();

        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty() && barHeights[st.peek()] >= barHeights[i]) {
                st.pop();
            }
            smallRight[i] = (st.isEmpty())?n:st.peek();
            st.push(i);
        }

        int a=0;
        for(int i=0;i<n;i++){
            a = Math.max(a, barHeights[i]*(smallRight[i]-smallLeft[i]-1));
        }
        return a;
    }
}
