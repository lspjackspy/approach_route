import java.util.ArrayList;

public class navpoint {
    public int id;
    public boolean is_obstacle;
    public boolean is_final;
    public int proximity_cost;
    public int[] coordinate;
    public boolean is_visited;
    public int[] prev;
    public ArrayList<int[]> adj_list;
    public navpoint(int id, int[] coordinate , boolean is_final ){
        this.is_obstacle = false;
        this.id = id;
        this.is_final = is_final;
        this.coordinate = coordinate.clone();
        this.proximity_cost = 0;
        is_visited = false;
    }
    public void assign_prev(int[] prev){
        this.prev = prev;
    }
    public void add_proxi_navpoint(int[] np){
        this.adj_list.add(np);
    }

}
