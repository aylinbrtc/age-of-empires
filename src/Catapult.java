import java.util.ArrayList;

public class Catapult extends Soldier{

    private int powerBuild;


    public int getPowerBuild() {
        return powerBuild;
    }

    public void setPowerBuild(int powerBuild) {
        this.powerBuild = powerBuild;
    }

    public Catapult() {
        }
    public void determine(Player player) {
        setPlayer(player);
        if(this.player.getUniversity()!=null) {
        setLifePoints(20 + this.player.getUniversity().getCavalryCounter());}
    }


    @Override
    public String getSymbol() {
        return "C";
    }

    @Override
    public void move(int x, int y) throws AgeOfEmpiresException {
        if (moveController(1,y,x)) {
            player.takeMap().remove(this);
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
            if (row == possibilities.get(i)[0] && col == possibilities.get(i)[1] && !(row == not.get(i)[0]) && !(col == not.get(i)[1])) {
                if (player.takeMap().getMap()[row][col].size() != 0)
                    return true;
            }
        }
        return  false;
    }

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {
        int row = y - 1;
        int col = x - 1;

        if (strikeController(row, col)) {
            for (Item item : player.takeMap().getMap()[row][col]) {
                if (item.player != this.player && player.takeMap().getMap()[row][col].size() == 1) {
                    if (item instanceof University || item instanceof MainBuilding) {
                        item.setLifePoints(item.getLifePoints() - powerBuild);
                    } else {
                        if (item instanceof Archer && item.strikeController(row, col)) {
                            ((Archer) item).defense(getRow(), getCol());
                            ArrayList<Item> empty = null;
                            player.takeMap().getMap()[getRow()][getCol()] = empty;
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Catapult && item.strikeController(row, col)) {
                            ((Catapult) item).defense(getRow(), getCol());
                            ArrayList<Item> empty = null;
                            player.takeMap().getMap()[getRow()][getCol()] = empty;
                        } else if (item instanceof Cavalry && item.strikeController(row, col)) {
                            ((Cavalry) item).defense(getRow(), getCol());
                            ArrayList<Item> empty = null;
                            player.takeMap().getMap()[getRow()][getCol()] = empty;
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Spearman && item.strikeController(row, col)) {
                            ((Spearman) item).defense(getRow(), getCol());
                            if (!isAnyoneDead(item, getRow(), getCol())) {
                                ArrayList<Item> empty = null;
                                player.takeMap().getMap()[getRow()][getCol()] = empty;
                            }
                        } else if (item instanceof Swordman && item.strikeController(row, col)) {
                            ((Swordman) item).defense(getRow(), getCol());
                            ArrayList<Item> empty = null;
                            player.takeMap().getMap()[getRow()][getCol()] = empty;
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Tower && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerBuild);
                            ((Tower) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Worker && item.strikeController(row, col)) {
                            if (player.takeMap().getMap()[row][col].size() == 1) {
                                ((Worker) item).defense(getRow(), getCol());
                                ArrayList<Item> empty = null;
                                player.takeMap().getMap()[getRow()][getCol()] = empty;
                                isAnyoneDead(item, getRow(), getCol());
                            }
                        }
                    }
                }else if(item.player != this.player) {
                    if (item instanceof University || item instanceof MainBuilding) {
                        item.setLifePoints(item.getLifePoints() - powerBuild);
                    } else if (item instanceof Tower && item.strikeController(row, col)) {
                        item.setLifePoints(item.getLifePoints() - powerBuild);
                        ((Tower) item).defense(getRow(), getCol());
                        isAnyoneDead(item, row, col);
                        isAnyoneDead(item, getRow(), getCol());
                    }
                }

            }
        }else throw new AgeOfEmpiresException("");
         player.changeTurn();
    }

    @Override
    public void defense(int row, int col) {
        if(strikeController(row,col)) {
            for (Item item : player.takeMap().getMap()[row][col]) {
                if (!(item.player == this.player) && item instanceof Human) {
                    ArrayList<Item> empty =null;
                    player.takeMap().getMap()[getRow()][getCol()] = empty;
                }else if (!(item.player == this.player) && item instanceof Building) {
                    item.setLifePoints(item.getLifePoints() - powerBuild) ;
                }
            }
        }
    }

}
