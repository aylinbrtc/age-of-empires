import java.io.Serializable;
import java.util.ArrayList;

public class Map implements MapInterface, Serializable {
    ArrayList<Item>[][] map;

    public ArrayList<Item>[][] getMap() {
        return map;
    }


    @Override
    public String status() {
        String s="";
        for(int i=0; i< map.length; i++){
            for(int j=0; j< map[i].length; ++j){
                if(map[i][j].size()!=0) {
                    int more = 0;
                    for (int k = 0; k < map[i][j].size(); k++) {
                        if (map[i][j].get(k) instanceof University || map[i][j].get(k) instanceof Tower || map[i][j].get(k) instanceof MainBuilding) {
                            System.out.print(map[i][j].get(k).getSymbol());
                            more++;
                        }
                    }
                    if (more==0){
                        System.out.print(map[i][j].get(0).getSymbol());
                    }
                }
                else System.out.print("_");
            }
            System.out.println();
        }
        return s;
    }

    public Map() {
        map= new ArrayList[50][100];
        for(int i=0; i<map.length; i++){
            for (int j=0; j<map[i].length; j++){
                map[i][j]=new ArrayList<>();
            }
        }
    }

    public void add(Item item, int row , int col){
        if(checkIndex(row,col)){
            map[row][col].add(item);
        }
    }


    public boolean checkIndex(int row, int col){
        return row<50 && row>=0 && col >=0 && col <100;
    }

    public Item remove(Item item) {
        int row=item.getRow();
        int col=item.getCol();
        map[row][col].remove(item);
        return item;
    }



}
