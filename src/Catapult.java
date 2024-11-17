import java.util.ArrayList;

public class Catapult extends Soldier{

    private int powerBuild;

    public void determine(Player player){
        setPlayer(player);
        if(this.player.getUniversity()!=null) {
            setLifePoints(10 + (player.getUniversity().getCatapultCounter()));
            powerBuild = 30 + (player.getUniversity().getCatapultCounter());

        }else{
            setLifePoints(10);
            powerBuild = 30;
        }
    }


    public int getPowerBuild() {
        return powerBuild;
    }

    public void setPowerBuild(int powerBuild) {
        this.powerBuild = powerBuild;
    }

    public Catapult() {
        }



    @Override
    public String getSymbol() {
        return "C";
    }

    @Override
    public void move(int x, int y) throws AgeOfEmpiresException {
        if (player.takeMap().checkIndex(y-1,x-1) && moveController(1,y-1,x-1)) {
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
        ArrayList<int[]> possibilities = mapDesigner(10,this.getRow(),this.getCol());
        ArrayList<int[]> not = mapDesigner(5,this.getRow(),this.getCol());

        for(int i=0; i<possibilities.size(); i++) {
            for (int j=0; j<not.size();j++) {  // **********************************************************************YOKTU
                if (row == possibilities.get(i)[0] && col == possibilities.get(i)[1] && !(row == not.get(j)[0]) && !(col == not.get(j)[1])) {
                    if (player.takeMap().getMap()[row][col].size() != 0)
                        return true;
                }
            }
        }
        return  false;
    }

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {
        int row = y - 1;
        int col = x - 1;
        if (strikeController(row, col)) {
            ArrayList<Item> items = new ArrayList<>(player.takeMap().getMap()[row][col]);
            for (Item item : items) {
                if (item.player != this.player && player.takeMap().getMap()[row][col].size() == 1) {
                    if (item instanceof University || item instanceof MainBuilding) {
                        item.setLifePoints(item.getLifePoints() - powerBuild);
                    } else {
                        if (item instanceof Archer) {
                            ((Archer) item).defense(this,getRow(), getCol());
                            player.takeMap().getMap()[getRow()][getCol()].remove(item);
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Catapult ) {
                            ((Catapult) item).defense(this,getRow(), getCol());
                            player.takeMap().getMap()[getRow()][getCol()].remove(item);
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Cavalry ) {
                            ((Cavalry) item).defense(this,getRow(), getCol());
                            player.takeMap().getMap()[getRow()][getCol()].remove(item);
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Spearman ) {
                            ((Spearman) item).defense(this,getRow(), getCol());
                            if (!isAnyoneDead(this, getRow(), getCol())) {
                                player.takeMap().getMap()[getRow()][getCol()].remove(item);
                            }
                        } else if (item instanceof Swordman) {
                            ((Swordman) item).defense(this,getRow(), getCol());
                            player.takeMap().getMap()[getRow()][getCol()].remove(item);
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Tower) {
                            item.setLifePoints(item.getLifePoints() - powerBuild);
                            ((Tower) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Worker) {
                            if (player.takeMap().getMap()[row][col].size() == 1) {
                                ((Worker) item).defense(this,getRow(), getCol());
                                player.takeMap().getMap()[getRow()][getCol()].remove(item);
                                isAnyoneDead(item, row, col);
                                isAnyoneDead(this, getRow(), getCol());
                            }
                        }
                    }
                }else if(item.player != this.player) {
                    if (item instanceof University || item instanceof MainBuilding) {
                        item.setLifePoints(item.getLifePoints() - powerBuild);
                    } else if (item instanceof Tower ) {
                        item.setLifePoints(item.getLifePoints() - powerBuild);
                        ((Tower) item).defense(this,getRow(), getCol());
                        isAnyoneDead(item, row, col);
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
            if (!(item.player == this.player) && item instanceof Human) {
                player.takeMap().getMap()[getRow()][getCol()].remove(item);
            }else if (!(item.player == this.player) && item instanceof Building) {
                item.setLifePoints(item.getLifePoints() - powerBuild) ;
            }
        }
    }

}
