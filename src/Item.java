import java.io.Serializable;
import java.util.ArrayList;

public class Item implements ItemInterface, Serializable {

    private int lifePoints;

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    Player player;
    private int row;
    private int col;
    public void setCol(int x) {
        col = x;
    }

    public void setRow(int y) {
        row = y;
    }


    public void determine(Player player){
        setPlayer(player);
    }
    //private Map map;

    public Item() {}


    @Override
    public int getX() {
        return col+1;
    }

    @Override
    public int getY() {
        return row+1;
    }

    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }

    @Override
    public int getLifePoints() {
        return lifePoints;
    }

    @Override
    public String getSymbol() {
        return null;
    }

    @Override
    public String print_message() {
        return ItemInterface.super.print_message();
    }

    protected void setPlayer(Player player){
        this.player=player;
    }

    //uygun xy mi kontrol etmiyor   x ve y matrixin içinde mi yami sınırları komtrol ediyor
    public ArrayList<int[]> mapDesigner(int n, int row, int col) {

        ArrayList<int[]> list = new ArrayList<>();
        for (int i = row - n; i < row + n; i++) {
            for (int j = col - n; j < col + n; j++) {
                if (Math.abs(row - i) + Math.abs(col - j) <= n && Math.abs(row - i) + Math.abs(col - j) > 0) {
                    if (i < 50 && j < 100 && i >= 0 && j >= 0) {
                        int[] entry = {i, j};
                        list.add(entry);
                    }
                }
            }
        }

        return list;
    }
    //thisleyip get row colla ver
    public ArrayList<int[]> mapStraight(int row, int col) {
        ArrayList<int[]> possibilities = new ArrayList<>();
        int[] a1={row,col-1};
        int[] a2={row,col+1};
        int[] a3={row-1,col-1};
        int[] a4={row-1,col+1};
        int[] a5={row+1,col-1};
        int[] a6={row+1,col+1};
        int[] a7={row-1,col};
        int[] a8={row+1,col};
        possibilities.add(a1);
        possibilities.add(a2);
        possibilities.add(a3);
        possibilities.add(a4);
        possibilities.add(a5);
        possibilities.add(a6);
        possibilities.add(a7);
        possibilities.add(a8);
        return possibilities;
    }


    public boolean strikeController(int row, int col){
        return false;
    }

    public boolean isAnyoneDead(Item item,int row,int col){
        if(item.getLifePoints() <= 0){
            if(player.takeMap().getMap()[row][col].size()==1) {
                player.takeMap().remove(item);
                if (item instanceof Tower) {
                    player.towers.remove(item);
                } else if (item instanceof Soldier) {
                    player.soldiers.remove(item);
                } else if (item instanceof Worker) {
                    player.workers.remove(item);
                }
                return true;
            }
        }return false;
    }
}



