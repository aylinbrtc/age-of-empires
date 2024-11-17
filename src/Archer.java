import java.util.ArrayList;

public class Archer extends Soldier{


    private int powerArrowHuman;
    private int powerArrowBuild;
    private int powerSword;

    public int getPowerArrowHuman() {
        return powerArrowHuman;
    }

    public void setPowerArrowHuman(int powerArrowHuman) {
        this.powerArrowHuman = powerArrowHuman;
    }

    public int getPowerArrowBuild() {
        return powerArrowBuild;
    }

    public void setPowerArrowBuild(int powerArrowBuild) {
        this.powerArrowBuild = powerArrowBuild;
    }

    public int getPowerSword() {
        return powerSword;
    }

    public void setPowerSword(int powerSword) {
        this.powerSword = powerSword;
    }


    public Archer() {

    }

    public void determine(Player player){
        setPlayer(player);
        if(this.player.getUniversity()!=null) {
            setLifePoints(5 + (player.getUniversity().getInfCounter()));
            powerArrowHuman = 2 + (player.getUniversity().getInfCounter());
            powerArrowBuild = 1 + (player.getUniversity().getInfCounter());
            powerSword = 2 + (player.getUniversity().getInfCounter());
        }else{
            setLifePoints(5);
            powerArrowHuman = 2 ;
            powerArrowBuild = 1 ;
            powerSword = 2 ;
        }
    }

    @Override
    public String getSymbol() {
        return "O";
    }

    @Override
    public void move(int x, int y) throws AgeOfEmpiresException {
        if (moveController(2,x,y)) {
            player.takeMap().remove(this);
            player.takeMap().add(this,y-1,x-1);
            player.changeTurn();
        }
        else
            throw new AgeOfEmpiresException("");
    }

    //uzağı kontrol eder
    @Override
    public boolean strikeController(int row, int col) {
        ArrayList<int[]> possibilities = mapDesigner(5,this.getRow(),this.getCol());
        ArrayList<int[]> not = mapStraight(this.getRow(),this.getCol());
        for(int i=0; i<possibilities.size(); i++) {
            if (row == possibilities.get(i)[0] && col == possibilities.get(i)[1] && (row != not.get(i)[0] && col != not.get(i)[1])) {
                if (player.takeMap().getMap()[row][col].size() != 0)
                    return true;
            }
        }
        return  false;
    }

    public boolean strikeControllerSword(int row, int col) {
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
        int row = y - 1;
        int col = x - 1;

        if (strikeControllerSword(row, col)) {
            for (Item item : player.takeMap().getMap()[row][col]) {
                if (item.player != this.player && player.takeMap().getMap()[row][col].size() == 1) {
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - powerSword);
                    else {
                        if (item instanceof Archer && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerSword);
                            ((Archer) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Catapult && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerSword);
                            ((Catapult) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                        } else if (item instanceof Cavalry && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerSword);
                            ((Cavalry) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Spearman && item.strikeController(row, col)) {
                            ((Spearman) item).defense(getRow(), getCol());
                            if (!isAnyoneDead(item, getRow(), getCol()))
                                item.setLifePoints(item.getLifePoints() - powerSword);
                            isAnyoneDead(item,row,col);
                        } else if (item instanceof Swordman && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerSword);
                            ((Swordman) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Tower && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerSword);
                            ((Tower) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Worker && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerSword);
                            ((Worker) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(item, getRow(), getCol());
                        }
                    }

                } else if (item.player != this.player) {
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - powerSword);
                    else if (item instanceof Tower && item.strikeController(row, col)) {
                        item.setLifePoints(item.getLifePoints() - powerSword);
                        ((Tower) item).defense(getRow(), getCol());
                        isAnyoneDead(item, row, col);
                        isAnyoneDead(item, getRow(), getCol());
                    }
                }
            }
        } else if (strikeController(row, col)) {
            for (Item item : player.takeMap().getMap()[row][col]) {
                if (item.player != this.player && player.takeMap().getMap()[row][col].size() == 1) {
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - powerArrowBuild);
                    else {
                        if (item instanceof Archer && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerArrowHuman);
                            ((Archer) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Catapult && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerArrowHuman);
                            ((Catapult) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                        }  else if (item instanceof Cavalry && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerArrowHuman);
                            ((Cavalry) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Spearman && item.strikeController(row, col)) {
                            ((Spearman) item).defense(getRow(), getCol());
                            if (!isAnyoneDead(item, getRow(), getCol()))
                                item.setLifePoints(item.getLifePoints() - powerArrowHuman);
                            isAnyoneDead(item,row,col);
                        } else if (item instanceof Swordman && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerArrowHuman);
                            ((Swordman) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Tower && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerArrowBuild);
                            ((Tower) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(item, getRow(), getCol());
                        } else if (item instanceof Worker && item.strikeController(row, col)) {
                            item.setLifePoints(item.getLifePoints() - powerArrowHuman);
                            ((Worker) item).defense(getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(item, getRow(), getCol());
                        }

                    }
                } else if (item.player != this.player) {
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - powerArrowBuild);
                    else if (item instanceof Tower && item.strikeController(row, col)) {
                        item.setLifePoints(item.getLifePoints() - powerArrowBuild);
                        ((Tower) item).defense(row, col);
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
        if (strikeControllerSword(row, col)) {
            for (Item item : player.takeMap().getMap()[row][col]) {
                if (!(item.player == this.player)) {
                        item.setLifePoints(item.getLifePoints() - powerSword);
                }
            }
        }else if (strikeController(row, col)) {
            for (Item item : player.takeMap().getMap()[row][col]) {
                if (!(item.player == this.player)) {
                    if (item instanceof University || item instanceof MainBuilding || item instanceof Tower)
                        item.setLifePoints(item.getLifePoints() - powerArrowBuild);
                    else item.setLifePoints(item.getLifePoints() - powerArrowHuman);
                }
            }
        }

    }
}