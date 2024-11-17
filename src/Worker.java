import java.util.ArrayList;

public class Worker extends Human implements WorkerInterface{

    private Player player;

    private int power;

    @Override
    public void move(int x, int y) throws AgeOfEmpiresException {
        if (player.takeMap().checkIndex(y-1,x-1) && moveController(3,y-1,x-1)) {
            player.takeMap().removeInMap(this);
            player.takeMap().add(this,y-1,x-1);
            setRow(y-1);
            setCol(x-1);
            player.changeTurn();
        }
        else
            throw new AgeOfEmpiresException("");
    }

    public Worker(Player player) {
        this.player=player;

    }

    public void determine(Player player){
        setPlayer(player);
        setLifePoints(2);
        power=1;
    }
    @Override
    public String getSymbol() {
        return "W";
    }

    @Override
    public void build(Building b) throws AgeOfEmpiresException {
        if(b instanceof University && player.getUniversity()==null){
            if (player.getStone()>=5 && player.getWood()>=30 && player.getGold()>=50){
                b.setRow(this.getRow());
                b.setCol(this.getCol());
                b.determine(player);   //*************************************************************************************************YOKTU
                player.addMap(b,getRow(),getCol());
                player.changeTurn();
            }
        }
        else if (b instanceof Tower){
            if (player.getStone()>=40 && player.getWood()>=10 && player.getGold()>=5){
                b.setRow(this.getRow());
                b.setCol(this.getCol());
                b.determine(player);//***************************************************************************************************YOKTU
                player.addMap(b,getRow(),getCol());
                player.changeTurn();
            }
        }
        else
            throw new AgeOfEmpiresException("");

    }

    @Override
    public boolean strikeController(int row, int col) {
        ArrayList<int[]> possibilities = mapStraight(this.getRow(),this.getCol());
        for(int i=0; i<possibilities.size(); i++) {
            if (row == possibilities.get(i)[0] && col == possibilities.get(i)[1]) {
                if (player.takeMap().getMap()[row][col].size() != 0)
                    return true;
                else new AgeOfEmpiresException(" ");
            }
        }
        return false;
    }

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {
        int row = y - 1;
        int col = x - 1;

        if (strikeController(row, col)) {
            for (Item item : player.takeMap().getMap()[row][col]) {
                if (item.player != this.player && player.takeMap().getMap()[row][col].size() == 1){
                    if (item instanceof University || item instanceof MainBuilding) {
                        item.setLifePoints(item.getLifePoints() - power);
                    }else {
                            if (item instanceof Archer ) {
                                item.setLifePoints(item.getLifePoints() - power);
                                ((Archer) item).defense(this,getRow(), getCol());
                                isAnyoneDead(item,row,col);
                                isAnyoneDead(item,getRow(),getCol());
                            } else if (item instanceof Catapult ) {
                                item.setLifePoints(item.getLifePoints() - power);
                                ((Catapult) item).defense(this,getRow(), getCol());
                                isAnyoneDead(item,row,col);
                            }else if (item instanceof Cavalry ) {
                                item.setLifePoints(item.getLifePoints() - power);
                                ((Cavalry) item).defense(this,getRow(), getCol());
                                isAnyoneDead(item,row,col);
                                isAnyoneDead(item,getRow(),getCol());
                            } else if (item instanceof Spearman) {
                                ((Spearman) item).defense(this,getRow(), getCol());
                                if(!isAnyoneDead(item,getRow(),getCol()))
                                   item.setLifePoints(item.getLifePoints() - power);
                                isAnyoneDead(item,row,col);
                            } else if (item instanceof Swordman ){
                                item.setLifePoints(item.getLifePoints() - power);
                                ((Swordman) item).defense(this,getRow(), getCol());
                                isAnyoneDead(item,row,col);
                                isAnyoneDead(item,getRow(),getCol());
                            } else if (item instanceof Tower ) {
                                item.setLifePoints(item.getLifePoints() - power);
                                ((Tower) item).defense(this,getRow(), getCol());
                                isAnyoneDead(item,row,col);
                                isAnyoneDead(item,getRow(),getCol());
                            } else if (item instanceof Worker) {
                                    item.setLifePoints(item.getLifePoints() - power);
                                    ((Worker) item).defense(this,getRow(), getCol());
                                    isAnyoneDead(item,row,col);
                                    isAnyoneDead(item,getRow(),getCol());
                                }
                            }
                        }
                else if(item.player != this.player){
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - power);
                    else if (item instanceof Tower) {
                        item.setLifePoints(item.getLifePoints() - power);
                        ((Tower) item).defense(this,getRow(), getCol());
                        isAnyoneDead(item,row,col);
                        isAnyoneDead(item,getRow(),getCol());
                    }
                }

            }
        }else throw new AgeOfEmpiresException("");
        player.changeTurn();
    }


    @Override
    public void defense(Item item,int row, int col) {
        if(strikeController(row,col)) {
                if (!(item.player == this.player)) {
                    item.setLifePoints(item.getLifePoints() - power);
                }

        }


    }

}









