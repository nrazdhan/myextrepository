package HelloWorld;

public class DungeonGame {
    public static void main(String[] args) {
        DungeonGame dg = new DungeonGame();
        int[][] dungeon = {
            {-2,-3,3},
            {-5,-10,1},
            {10,30,-5}
        };
        System.out.println("Minimum Health Required: " + dg.calculateMinimumHP(dungeon));
    }

    public int calculateMinimumHP(int[][] dungeon) {
        int minLost = dfs(dungeon, new int[]{0,0}, 0, 0);
        return 1-minLost;
    }

    int dfs(int[][] d, int[] p, int energyInput, int lowestPathEnergy){

        int energy=energyInput+d[p[0]][p[1]];
        lowestPathEnergy = Math.min(lowestPathEnergy, energy);
        int lowestPathEneryResult=lowestPathEnergy;

        int result1=Integer.MIN_VALUE, result2=Integer.MIN_VALUE;

        if(p[1]+1<d[0].length){
            result1 = dfs(d, new int[]{p[0], p[1]+1}, energy, lowestPathEnergy);
        }
        
        if(p[0]+1<d.length){
            result2 = dfs(d, new int[]{p[0]+1, p[1]}, energy, lowestPathEnergy);
        }
        
        if(result1==Integer.MIN_VALUE && result2==Integer.MIN_VALUE){
            return lowestPathEneryResult;
        }

        return Math.max(result1, result2);
    }
}
