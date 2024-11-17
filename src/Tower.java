import java.util.ArrayList;

public class Tower extends Building implements TowerInterface{
    private int power;
    public Tower() {
        setLifePoints(50);
        power =2;
    }

    @Override
    public String getSymbol() {
        return "T";
    }

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {
        int row=y-1;
        int col=x-1;

        if (strikeController(row, col)) {
            for (Item item : player.takeMap().getMap()[row][col]) {
                if (item.player != this.player && player.takeMap().getMap()[row][col].size() == 1){
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - power);
                    else {
                        if (item instanceof Archer && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Archer) item).defense(getRow(), getCol());
                            isAnyoneDead(item,row,col);
                            isAnyoneDead(item,getRow(),getCol());
                        } else if (item instanceof Catapult && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Catapult) item).defense(getRow(), getCol());
                            isAnyoneDead(item,row,col);
                        }else if (item instanceof Cavalry && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Cavalry) item).defense(getRow(), getCol());
                            isAnyoneDead(item,row,col);
                            isAnyoneDead(item,getRow(),getCol());
                        } else if (item instanceof Spearman && item.strikeController(row, col)) {
                            ((Spearman) item).defense(getRow(), getCol());
                            if(!isAnyoneDead(item,getRow(),getCol()))
                                item.setLifePoints(item.getLifePoints() - power);
                            isAnyoneDead(item,row,col);
                        } else if (item instanceof Swordman && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Swordman) item).defense(getRow(), getCol());
                            isAnyoneDead(item,row,col);
                            isAnyoneDead(item,getRow(),getCol());
                        } else if (item instanceof Tower && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Tower) item).defense(getRow(), getCol());
                            isAnyoneDead(item,row,col);
                            isAnyoneDead(item,getRow(),getCol());
                        } else if (item instanceof Worker && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - power);
                            ((Worker) item).defense(getRow(), getCol());
                            isAnyoneDead(item,row,col);
                            isAnyoneDead(item,getRow(),getCol());
                        }
                    }
                }else if(item.player != this.player){
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - power);
                    else if (item instanceof Tower && item.strikeController(row, col)) {
                        item.setLifePoints(item.getLifePoints() - power);
                        ((Tower) item).defense(getRow(), getCol());
                        isAnyoneDead(item,row,col);
                        isAnyoneDead(item,getRow(),getCol());
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
                if (!(item.player == this.player)) {
                    item.setLifePoints(item.getLifePoints() - power);
                }
            }
        }
    }

    @Override
    public boolean strikeController(int row, int col) {
        ArrayList<int[]> possibilities = mapDesigner(7,this.getRow(),this.getCol());
        for(int i=0; i<possibilities.size(); i++) {
            if (row == possibilities.get(i)[0] && col == possibilities.get(i)[1]) {
                if (player.takeMap().getMap()[row][col].size() != 0)
                    return true;
            }
        }
        return false;
    }
}
