import java.util.ArrayList;

public class Cavalry extends Soldier{
    private int powerCavalry;
    private int powerOtherHuman;
    private int powerBuild;

    public int getPowerCavalry() {
        return powerCavalry;
    }

    public void setPowerCavalry(int powerCavalry) {
        this.powerCavalry = powerCavalry;
    }

    public int getPowerOtherHuman() {
        return powerOtherHuman;
    }

    public void setPowerOtherHuman(int powerOtherHuman) {
        this.powerOtherHuman = powerOtherHuman;
    }

    public int getPowerBuild() {
        return powerBuild;
    }

    public void setPowerBuild(int powerBuild) {
        this.powerBuild = powerBuild;
    }

    public Cavalry() {
    }
    public void determine(Player player) {
        setPlayer(player);
        if(this.player.getUniversity()!=null) {
            setLifePoints(20 + this.player.getUniversity().getCavalryCounter());
            powerCavalry = 5 + (player.getUniversity().getCavalryCounter());
            powerOtherHuman = 10 + (player.getUniversity().getCavalryCounter());
            powerBuild = 5 + (player.getUniversity().getCavalryCounter());
        }else {
            setLifePoints(20);
            powerCavalry = 5 ;
            powerOtherHuman = 10 ;
            powerBuild = 5 ;
        }

    }

    @Override
    public String getSymbol() {
        return "A";
    }

    @Override
    public void move(int x, int y) throws AgeOfEmpiresException {
        if (player.takeMap().checkIndex(y-1,x-1) && moveController(9,y-1,x-1)) {
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
                if (player.takeMap().getMap()[row][col].size() != 0){
                    return true;
                }
            }
        }
        return  false;
    }

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {
        int row=y-1;
        int col=x-1;
        if(strikeController(row,col)) {
            ArrayList<Item> items = new ArrayList<>(player.takeMap().getMap()[row][col]);
            for (Item item : items) {
                if (!(item.player == this.player) && player.takeMap().getMap()[row][col].size() == 1) {//parantez hatasi yapmisim duzelttim
                   // !(item.player == this.player && player.takeMap().getMap()[row][col].size())== 1   SEKLİNDEYDİ
                    if ((item instanceof University || item instanceof MainBuilding))
                        item.setLifePoints(item.getLifePoints() - powerBuild);
                    else {
                        if (item instanceof Archer) {

                            item.setLifePoints(item.getLifePoints() - powerOtherHuman);
                            ((Archer) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Catapult ) {

                            item.setLifePoints(item.getLifePoints() - powerOtherHuman);
                            ((Catapult) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Cavalry) {
                            item.setLifePoints(item.getLifePoints() - powerCavalry);
                            ((Cavalry) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Spearman) {
                            ((Spearman) item).defense(this,getRow(), getCol());
                            if (!isAnyoneDead(item, getRow(), getCol()))
                                item.setLifePoints(item.getLifePoints() - powerOtherHuman);
                            isAnyoneDead(item, row, col);
                        } else if (item instanceof Swordman) {
                            item.setLifePoints(item.getLifePoints() - powerOtherHuman);
                            ((Swordman) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Tower) {
                            item.setLifePoints(item.getLifePoints() - powerBuild);
                            ((Tower) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(this, getRow(), getCol());
                        } else if (item instanceof Worker) {
                            item.setLifePoints(item.getLifePoints() - powerOtherHuman);
                            ((Worker) item).defense(this,getRow(), getCol());
                            isAnyoneDead(item, row, col);
                            isAnyoneDead(this, getRow(), getCol());
                        }
                    }
                } else if (item.player != this.player && player.takeMap().getMap()[row][col].size() > 1) {
                    if (item instanceof University || item instanceof MainBuilding)
                        item.setLifePoints(item.getLifePoints() - powerBuild);
                    else if (item instanceof Tower) {
                        item.setLifePoints(item.getLifePoints() - powerBuild);
                        ((Tower) item).defense(this,getRow(), getCol());
                        isAnyoneDead(item, row, col);
                        isAnyoneDead(this, getRow(), getCol());
                    }
                }
            }
        }else throw new AgeOfEmpiresException("xxxxxxxxxxxxxxxxxxxxxxxx");
         player.changeTurn();
    }

    @Override
    public void defense(Item item,int row, int col) {
        if(strikeController(row,col)) {
            if (!(item.player == this.player)) {
                if (item instanceof University || item instanceof MainBuilding || item instanceof Tower)
                    item.setLifePoints(item.getLifePoints() - powerBuild);
                else if (item instanceof Cavalry) {
                    item.setLifePoints(item.getLifePoints() - powerCavalry);
                } else {
                    item.setLifePoints(item.getLifePoints() - powerOtherHuman);

                }
            }
        }
    }
}
