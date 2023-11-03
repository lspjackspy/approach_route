import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        //airspace size to be 50km x 50km x 3km
        //with space between each horizontal navpoint to be 1km
        //vertical distance is 300m
        int height_init = 0;
        int x_coord = -25;
        int y_coord = -25;
        int id = 1;
        HashMap<String,Integer> matrix = new HashMap<>();
        HashMap<String,navpoint> coordinate_to_navpoint = new HashMap<>();
        HashMap<Integer,String> id_to_coordinate = new HashMap<>();
        int[] dest = {100,100,100};

        //String s = coordinate_to_string(dest);
        //int[] result = string_to_coordinate(s);
        //System.out.println(result[2]);

        for(int k=0;k<11;k++) {
            for (int i = -25; i <=25; i++) {
                for (int j = -25; j <=25; j++) {

                    int[] tmpc = {i,j,k};
                    if(k!=10) {
                        navpoint np = new navpoint(id,tmpc, false);
                        coordinate_to_navpoint.put(array_to_string(tmpc,0),np);
                        id_to_coordinate.put(id,array_to_string(tmpc,0));
                    }
                    else {
                        navpoint np = new navpoint(id,tmpc, true);
                        coordinate_to_navpoint.put(array_to_string(tmpc,0),np);
                        int[] edge = {id,0};
                        matrix.put(array_to_string(edge,1),1);
                        id_to_coordinate.put(id,array_to_string(tmpc,0));
                    }
                    id++;
                }
            }
        }
        System.out.println(id);
        //int[][] matrix = new int[id+2][id+2];
        int[] start_point = {0,0,0};
        int[] must = {1,0,1};
        int[][] obstacles = {{}};//create terrains
        //set these points to be obstacle, and set all approximate point cost to be 20
        //second in approximate set cost to 10
        coordinate_to_navpoint.get(array_to_string(must,0)).assign_prev(start_point);
        ArrayList<int[]> need_visit = new ArrayList<>();
        //matrix[coordinate_to_navpoint.get(start_point).id][coordinate_to_navpoint.get(must).id] = 1;
        int[] cds = {coordinate_to_navpoint.get(array_to_string(start_point,0)).id,coordinate_to_navpoint.get(array_to_string(must,0)).id};
        matrix.put(array_to_string(cds,1),1);
        need_visit.add(must);
        int cyc = 0;
        while(!need_visit.isEmpty()){
            System.out.println(cyc);
            cyc++;
            int[] current = need_visit.get(0);
            System.out.println(Arrays.toString(current));
            if(coordinate_to_navpoint.get(array_to_string(current,0)).is_visited){
                need_visit.remove(0);
                continue;
            }
            int[] prev = coordinate_to_navpoint.get(array_to_string(current,0)).prev;
            coordinate_to_navpoint.get(array_to_string(current,0)).is_visited  = true;
            int tid = coordinate_to_navpoint.get(array_to_string(current,0)).id;
            int[] p1 = current.clone();
            int[] p2 = current.clone();
            int[] p3 = current.clone();
            int[] p4 = current.clone();
            int[] p5 = current.clone();
            int[] p6 = current.clone();
            p6[2]++;
            p4[2]++;
            p2[2]++;
            if(prev[0] == current[0]){
                if(prev[1] == current[1] + 1){
                    p1[1]--;
                    p2[1]--;
                }
                else if(prev[1] == current[1] -1){
                    p1[1]++;
                    p2[1]++;
                }
                else{
                    System.out.println(Arrays.toString(current));
                    System.out.println(Arrays.toString(prev));
                    throw new RouteConstructionError("No such point possible");
                }
                p3[0]--;
                p4[0]--;
                p5[0]++;
                p6[0]++;
            }
            else if(prev[1]==current[1]){
                if(prev[0]==current[0]+1){
                    p1[0]--;
                    p2[0]--;
                }
                else if(prev[0]==current[0]-1){
                    p1[0]++;
                    p2[0]++;
                }
                p3[1]--;
                p4[1]--;
                p5[1]++;
                p6[1]++;
            }
            else{
                throw new RouteConstructionError("Construction Phase Point Coordinate Incorrect");
            }
            if(p1[0]<26 && p1[0]>-26 && p1[1]<26 &&p1[1]>-26 &&p1[2]<11){
                if(!coordinate_to_navpoint.get(array_to_string(p1,0)).is_obstacle && !coordinate_to_navpoint.get(array_to_string(p1,0)).is_visited){
                    int[] p1t = {tid,coordinate_to_navpoint.get(array_to_string(p1,0)).id};
                    matrix.put(array_to_string(p1t,1),5+coordinate_to_navpoint.get(array_to_string(p1,0)).proximity_cost);
                    coordinate_to_navpoint.get(array_to_string(p1,0)).assign_prev(current);
                    need_visit.add(p1);
                    //System.out.printf("Linking",current.toString()," ",p1);
                    System.out.println("p1");
                    System.out.println(Arrays.toString(p1));
                }
            }
            if(p2[0]<26 && p2[0]>-26 && p2[1]<26 &&p2[1]>-26 &&p2[2]<11){
                if(!coordinate_to_navpoint.get(array_to_string(p2,0)).is_obstacle && !coordinate_to_navpoint.get(array_to_string(p2,0)).is_visited){
                    int[] p2t = {tid,coordinate_to_navpoint.get(array_to_string(p2,0)).id};
                    matrix.put(array_to_string(p2t,1),5+coordinate_to_navpoint.get(array_to_string(p2,0)).proximity_cost);
                    coordinate_to_navpoint.get(array_to_string(p2,0)).assign_prev(current);
                    need_visit.add(p2);
                    System.out.println("p2");
                    System.out.println(Arrays.toString(p2));
                }
            }
            if(p3[0]<26 && p3[0]>-26 && p3[1]<26 &&p3[1]>-26 &&p3[2]<11){
                if(!coordinate_to_navpoint.get(array_to_string(p3,0)).is_obstacle && !coordinate_to_navpoint.get(array_to_string(p3,0)).is_visited){
                    int[] p3t = {tid,coordinate_to_navpoint.get(array_to_string(p3,0)).id};
                    matrix.put(array_to_string(p3t,1),10+coordinate_to_navpoint.get(array_to_string(p3,0)).proximity_cost);
                    coordinate_to_navpoint.get(array_to_string(p3,0)).assign_prev(current);
                    need_visit.add(p3);
                    System.out.println("p3");
                    System.out.println(Arrays.toString(p3));
                }
            }
            if(p4[0]<26 && p4[0]>-26 && p4[1]<26 &&p4[1]>-26 &&p4[2]<11){
                if(!coordinate_to_navpoint.get(array_to_string(p4,0)).is_obstacle && !coordinate_to_navpoint.get(array_to_string(p4,0)).is_visited){
                    int[] p4t = {tid,coordinate_to_navpoint.get(array_to_string(p4,0)).id};
                    matrix.put(array_to_string(p4t,1),10+coordinate_to_navpoint.get(array_to_string(p4,0)).proximity_cost);
                    coordinate_to_navpoint.get(array_to_string(p4,0)).assign_prev(current);
                    need_visit.add(p4);
                    System.out.println("p4");
                    System.out.println(Arrays.toString(p4));
                }
            }
            if(p5[0]<26 && p5[0]>-26 && p5[1]<26 &&p5[1]>-26 &&p5[2]<11){
                if(!coordinate_to_navpoint.get(array_to_string(p5,0)).is_obstacle && !coordinate_to_navpoint.get(array_to_string(p5,0)).is_visited){
                    int[] p5t = {tid,coordinate_to_navpoint.get(array_to_string(p5,0)).id};
                    matrix.put(array_to_string(p5t,1),10+coordinate_to_navpoint.get(array_to_string(p5,0)).proximity_cost);
                    coordinate_to_navpoint.get(array_to_string(p5,0)).assign_prev(current);
                    need_visit.add(p5);
                    System.out.println("p5");
                    System.out.println(Arrays.toString(p5));
                }
            }
            if(p6[0]<26 && p6[0]>-26 && p6[1]<26 &&p6[1]>-26 &&p6[2]<11){
                if(!coordinate_to_navpoint.get(array_to_string(p6,0)).is_obstacle && !coordinate_to_navpoint.get(array_to_string(p6,0)).is_visited){
                    int[] p6t = {tid,coordinate_to_navpoint.get(array_to_string(p6,0)).id};
                    matrix.put(array_to_string(p6,1),10+coordinate_to_navpoint.get(array_to_string(p6,0)).proximity_cost);
                    coordinate_to_navpoint.get(array_to_string(p6,0)).assign_prev(current);
                    need_visit.add(p6);
                    System.out.println("p6");
                    System.out.println(Arrays.toString(p6));
                }
            }
            need_visit.remove(0);

        }

        boolean[] flag = new boolean[id+1];
        int[] U = new int[id+1];
        int[] prev_v = new int[id+1];
        int start_id = coordinate_to_navpoint.get(array_to_string(start_point,0)).id;
        int[] shortest_vertex = new int[id+2];
        for(int i=0;i<id+1;i++){
            flag[i] = false;
            int[] sideid = {start_id,i};
            U[i] = matrix.getOrDefault(array_to_string(sideid, 1), Integer.MAX_VALUE);
            prev_v[i] = 0;
        }
        U[start_id] = 0;
        flag[start_id] = true;
        shortest_vertex[0] = start_id;

        int k=0;
        for(int i=1;i<id+1;i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < id + 1; j++) {
                if (!flag[j] && U[j] < min) {
                    min = U[j];
                    k = j;
                }
            }
            shortest_vertex[i] = k;
            flag[k]=true;
            for(int j=0;j<id+1;j++){
                int[] e = {k,j};
                int tmp = (matrix.getOrDefault(array_to_string(e,1),Integer.MAX_VALUE));
                if(tmp != Integer.MAX_VALUE){
                    tmp = tmp + min;
                }
                if(!flag[j] && tmp<U[j]){
                    U[j] = tmp;
                    prev_v[j] = k;
                }
            }
        }

        ArrayList<String> path = new ArrayList<>();
        int j = 0;
        while(true){
            path.add(id_to_coordinate.get(j));
            if(j == start_id){
                break;
            }
            j = prev_v[j];
        }
        for(int x = path.size()-1;x>=0;x--){
            if(x==0){
                System.out.println(path.get(x));
            }
            else{
                System.out.print(path.get(x) + "->");
            }
        }
        return;
    }

    public static String array_to_string(int[] coordinate,int mode){
        String s = "";
        if(mode==0) {
            s = ((Integer) coordinate[0]).toString() + " ";
            s = s + ((Integer) coordinate[1]).toString() + " ";
            s = s + ((Integer) coordinate[2]).toString();
        }
        else{
            s = ((Integer) coordinate[0]).toString() + " ";
            s = s + ((Integer) coordinate[1]).toString();
        }
        return s;
    }
    public static int[] string_to_array(String s,int mode){
        int len = 0;
        if(mode == 0) len = 3;
        else len = 2;
        int[] results = new int[len];
        int c = 0;
        for(String r: s.split(" ")){
            System.out.println(r);
            results[c] = Integer.parseInt(r);
            c++;
        }
        return results;
    }
}