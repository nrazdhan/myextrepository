package HelloWorld;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class CutOffTrees {
    
    public static void main(String[] args) {
        CutOffTrees cot = new CutOffTrees();
        List<List<Integer>> forest = List.of(
            List.of(54581641,64080174,24346381,69107959),
            List.of(86374198,61363882,68783324,79706116),
            List.of(668150,92178815,89819108,94701471),
            List.of( 83920491,22724204,46281641,47531096),
            List.of(89078499,18904913,25462145,60813308)
        );
        System.out.println("Steps to Cut Off Trees: " + cot.cutOffTree(forest));
    }

    public int cutOffTree(List<List<Integer>> forest1) {

        List<List<Integer>> forest = new ArrayList<>();
        for (List<Integer> row : forest1) {
            forest.add(new ArrayList<>(row));
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[2]-b[2]);

        for(int i=0;i<forest.size();i++){
            for(int j=0;j<forest.get(0).size();j++){
                if(i==0 && j==0) continue;
                if(forest.get(i).get(j)>1) pq.add(new int[]{i,j,forest.get(i).get(j)});
            }
        }

        int stepCount=1;
        if(pq.isEmpty()) return 0;
        int[] a= new int[]{0,0,forest.get(0).get(0)};
        while(!pq.isEmpty()){
            int[] b=pq.poll();
            int step=bfs(a, b, forest);
            if(step==-1) return -1;
            stepCount+=step;
            a[0]=b[0];
            a[1]=b[1];
            a[2]=b[2];
        }

        return stepCount;
    }

    int bfs(int[] start, int b[], List<List<Integer>> f){
        
        int[][] dist=new int[f.size()][f.get(0).size()];
        for(int i=0;i<f.size();i++){
            for(int j=0;j<f.get(0).size();j++){
                dist[i][j]=Integer.MAX_VALUE;
            }
        }
        Queue<int[]> q = new LinkedList<>();
        q.add(start);
        dist[start[0]][start[1]]=0;
        int[][] direction = new int[][]{{-1,0},{0,-1},{1,0},{0,1}};
        while(!q.isEmpty()){
            int[] a=q.poll();
            
            for(int[] d : direction){
                int x=a[0]+d[0];
                int y=a[1]+d[1];
                if(x>=0 && y>=0&& x<f.size() && y<f.get(0).size() && ((f.get(x).get(y)==1) || (x==b[0] && y==b[1])) && dist[x][y]>dist[a[0]][a[1]]+1){
                    f.get(x).set(y, 1);
                    dist[x][y]=dist[a[0]][a[1]]+1;
                    if(x==b[0] && y==b[1]){
                        return dist[x][y];
                    }
                    q.add(new int[]{x,y});
                }
            }
        }
        return -1;
    }
}
