import java.util.ArrayList;

public class Swordman extends Soldier{
    private int power;

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Swordman() {
    }
    public void determine(Player player){
        setPlayer(player);
        if(this.player.getUniversity()!=null) {
            setLifePoints(5 + (player.getUniversity().getInfCounter()));

            power = 3 + (player.getUniversity().getInfCounter());
        }else{
            setLifePoints(5);
            power = 3;
        }
    }

    @Override
    public String getSymbol() {
        return "K";
    }


    @Override
    public void move(int x, int y) throws AgeOfEmpiresException {
        if (player.takeMap().checkIndex(y-1,x-1) && moveController(2,y-1,x-1)) {
            player.takeMap().removeInMap(this);
            player.takeMap().add(this,y-1,x-1);
            setRow(y-1);
            setCol(x-1);
            player.changeTurn();
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
            }
        }
        return  false;
    }

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {
        int row=y-1;
        int col=x-1;

        if (strikeController(row, col)) {
            ArrayList<Item> items = new ArrayList<>(player.takeMap().getMap()[row][col]);
            for (Item item : items) {
                if (item.player != this.player && player.takeMap().getMap()[row][col].size() == 1){
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - power);
                    else {
                        if (item instanceof Archer ) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Archer) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item,row,col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Catapult ) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Catapult) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item,row,col);
                        }else if (item instanceof Cavalry ) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Cavalry) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item,row,col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Spearman) {
                            ((Spearman) item).defense(this,getRow(), getCol());
                            if(!isAnyoneDead(this, getRow(), getCol()))
                                item.setLifePoints(item.getLifePoints() - power);
                            isAnyoneDead(item,row,col);
                        } else if (item instanceof Swordman) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Swordman) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item,row,col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Tower ) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Tower) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item,row,col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Worker) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Worker) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item,row,col);
                            isAnyoneDead(this, getRow(), getCol());
                        }
                    }
                }else if(item.player != this.player){
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - power);
                    else if (item instanceof Tower) {
                        item.setLifePoints(item.getLifePoints() - power);
                        ((Tower) item).defense(this,getRow(), getCol());
                        isAnyoneDead(item,row,col);
                        isAnyoneDead(this, getRow(), getCol());
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
