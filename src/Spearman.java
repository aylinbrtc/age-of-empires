import java.util.ArrayList;

public class Spearman extends Soldier{
    private int powerCavalry;
    private int powerOthers;

    public int getPowerCavalry() {
        return powerCavalry;
    }

    public void setPowerCavalry(int powerCavalry) {
        this.powerCavalry = powerCavalry;
    }

    public int getPowerOthers() {
        return powerOthers;
    }

    public void setPowerOthers(int powerOthers) {
        this.powerOthers = powerOthers;
    }

    public Spearman() {
    }
    public void determine(Player player) {
        setPlayer(player);
        if(this.player.getUniversity()!=null) {
            setLifePoints(5 + (player.getUniversity().getInfCounter()));
            powerCavalry = 10 + (player.getUniversity().getInfCounter());
            powerOthers = 2 + (player.getUniversity().getInfCounter());
        }else{
            setLifePoints(5);
            powerCavalry = 10 ;
            powerOthers = 2;
        }
    }

    @Override
    public String getSymbol() {
        return "S";
    }

    @Override
    public void move(int x, int y) throws AgeOfEmpiresException {
        if (moveController(2,y-1,x-1)) {
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
        ArrayList<int[]> possibilities = mapDesigner(7,this.getRow(),this.getCol());
        for(int i=0; i<possibilities.size(); i++) {
            if (row == possibilities.get(i)[0] && col == possibilities.get(i)[1]) {
                if (player.takeMap().getMap()[row][col].size() != 0)
                    return true;
            }
        }return false;

    }

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {
        int row=y-1;
        int col=x-1;

        if (strikeController(row, col)) {
            for (Item item : player.takeMap().getMap()[row][col]) {
                if (item.player != this.player && player.takeMap().getMap()[row][col].size() == 1){
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - powerOthers);
                    else {
                        if (item instanceof Archer && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerOthers);
                            if(!isAnyoneDead(item,row,col)){
                             ((Archer) item).defense(getRow(), getCol());
                             isAnyoneDead(item,getRow(),getCol());}
                        } else if (item instanceof Catapult && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerOthers);
                            if(!isAnyoneDead(item,row,col)){
                                ((Catapult) item).defense(getRow(), getCol());
                                isAnyoneDead(item,getRow(),getCol());}
                        }else if (item instanceof Cavalry && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerCavalry);
                            if(!isAnyoneDead(item,row,col)){
                                ((Cavalry) item).defense(getRow(), getCol());
                                isAnyoneDead(item,getRow(),getCol());}
                        } else if (item instanceof Spearman && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerOthers);
                            ((Spearman) item).defense(getRow(), getCol());
                            isAnyoneDead(item,row,col);
                            isAnyoneDead(item,getRow(),getCol());
                        } else if (item instanceof Swordman && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerOthers);
                            if(!isAnyoneDead(item,row,col)){
                                ((Swordman) item).defense(getRow(), getCol());
                                isAnyoneDead(item,getRow(),getCol());}
                        } else if (item instanceof Tower && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerOthers);
                            if(!isAnyoneDead(item,row,col)){
                                ((Tower) item).defense(getRow(), getCol());
                                isAnyoneDead(item,getRow(),getCol());}
                        } else if (item instanceof Worker && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerOthers);
                            if(!isAnyoneDead(item,row,col)){
                                ((Catapult) item).defense(getRow(), getCol());
                                isAnyoneDead(item,getRow(),getCol());}
                            }
                        }
                    }else if(item.player != this.player){
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - powerOthers);
                    else if (item instanceof Tower && item.strikeController(row, col)) {
                        item.setLifePoints(item.getLifePoints() - powerOthers);
                        if(!isAnyoneDead(item,row,col)){
                            ((Tower) item).defense(getRow(), getCol());
                            isAnyoneDead(item,getRow(),getCol());}
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
                    if (!(item instanceof Catapult)) {
                        if (item instanceof Cavalry)
                            item.setLifePoints(item.getLifePoints() - powerCavalry);
                        else item.setLifePoints(item.getLifePoints() - powerOthers);
                    }
                }
            }
        }

    }
}
