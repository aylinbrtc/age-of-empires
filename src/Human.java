import java.util.ArrayList;

public class Human extends Item implements HumanInterface{


    public Human() {
    }

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {

    }

    @Override
    public void move(int x, int y) throws AgeOfEmpiresException {
    }


    @Override
    public int getLifePoints() {
        return super.getLifePoints();
    }

    @Override
    public void defense(Item item,int x, int y) {

    }

    public boolean moveController(int n,int row,int col){
        ArrayList<int[]> possibilities = mapDesigner(n,getRow(),getCol());

        if(player.takeMap().getMap()[row][col].size() == 0){
            for(int i=0; i<possibilities.size(); i++){
                if(row == possibilities.get(i)[0] && col == possibilities.get(i)[1] )
                    return true;
            }
        }
        return false;
    }
}
