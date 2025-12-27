package HelloWorld;

public class MinTicketCost {
    public static void main(String[] args) {
        MinTicketCost mtc = new MinTicketCost();
        int[] days = {1,2,3,4,6,8,9,10,13,14,16,17,19,21,24,26,27,28,29};
        int[] costs = {3,14,50};
        System.out.println("Minimum Ticket Cost: " + mtc.mincostTickets(days, costs));
    }
    public int mincostTickets(int[] days, int[] costs) {
        return min(days, costs, 0);
    }

    int min(int[] days, int[] costs, int p){
        int cost = Integer.MAX_VALUE;
        if(p+1==days.length){
            cost=Math.min(Math.min(costs[0], costs[1]), costs[2]);
        }
        
        for(int i=p+1;i<=days.length;i++){
            int currentcost=Integer.MAX_VALUE;

            int daysdiff = 0;
            if(i==days.length){
                daysdiff = days[i-1]-days[p]+1;
            }else{
                 daysdiff = days[i-1]-days[p]+1;
                
            }

            if(i==p+1) currentcost=Math.min(Math.min(Math.min(currentcost, costs[0]), costs[1]), costs[2]);
            if(daysdiff<=7) currentcost=Math.min(currentcost, costs[1]);
            if(daysdiff<=30) currentcost=Math.min(currentcost, costs[2]);
            
            if(i<days.length){
                int subsetcost = min(days, costs, i);
                if(subsetcost==Integer.MAX_VALUE || currentcost==Integer.MAX_VALUE)
                {
                    currentcost = Integer.MAX_VALUE;
                }else{
                    currentcost += subsetcost;
                }
                cost = Math.min(cost, currentcost);
            }
        }
        
        return cost;
    }
}
