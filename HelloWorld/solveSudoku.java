package HelloWorld;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class solveSudoku {
    public static void main(String[] args) {
        solveSudoku ss = new solveSudoku();
         char[][] board = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        ss.solveSudoku(board);
        System.out.println("Solved Sudoku:");
    }

    public void solveSudoku(char[][] board) {
        rs = new HashSet[board.length];
        cs = new HashSet[board[0].length];
        gs = new HashSet[9];

        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                int gn = j/3+(i/3)*3;
                rs[i]=rs[i]==null?new HashSet<>():rs[i];
                cs[j]=cs[j]==null?new HashSet<>():cs[j];
                gs[gn]=gs[gn]==null?new HashSet<>():gs[gn];
                Set<Integer> r = rs[i];
                Set<Integer> c = cs[j];
                Set<Integer> g = gs[gn];

                if(board[i][j]!='.'){
                    r.add((int)(board[i][j]-'0'));
                    c.add((int)(board[i][j]-'0'));
                    g.add((int)(board[i][j]-'0'));
                }
            }
        }
        solve(board, 0, 0);
    }

    Set<Integer>[] rs;
    Set<Integer>[] cs;
    Set<Integer>[] gs;
    boolean solve(char[][] board, int x, int y){

        if(x==board.length) {
            return true;
        }

        int nx=x;
        int ny=y+1;
        if(ny==board[0].length) {
            nx=x+1;
            ny=0;
        }

        if(board[x][y]!='.') {
            return solve(board, nx, ny);
        }

        List<Integer> lst = new ArrayList<>();
        int gn = y/3 + 3*(x/3);
        for(int i=1;i<=9;i++){
            if(!rs[x].contains(i) && 
            !cs[y].contains(i) &&
            !gs[gn].contains(i)
            ){
                lst.add(i);
            }
        }

        for(int val : lst) {
            board[x][y]=(char)('0'+val);
            rs[x].add(val);
            cs[y].add(val);
            boolean flag = solve(board, nx, ny);
            if(flag) return true;
            board[x][y]='.';
            rs[x].remove(val);
            cs[y].remove(val);
        }
        
        return false;
    }
}
