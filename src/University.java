import java.util.ArrayList;

public class University extends Building implements UniversityInterface{

    private int infCounter=0;
    private int cavalryCounter=0;
    private int catapultCounter=0;

    public int getInfCounter() {
        return infCounter;
    }

    public void setInfCounter(int infCounter) {
        this.infCounter = infCounter;
    }

    public int getCavalryCounter() {
        return cavalryCounter;
    }

    public void setCavalryCounter(int cavalryCounter) {
        this.cavalryCounter = cavalryCounter;
    }

    public int getCatapultCounter() {
        return catapultCounter;
    }

    public void setCatapultCounter(int catapultCounter) {
        this.catapultCounter = catapultCounter;
    }

    public University() {
        setLifePoints(30);
    }

    @Override
    public String getSymbol() {
        return "U";
    }

    @Override
    public void trainInfantry() throws AgeOfEmpiresException {

        if (player.getGold() >= 50) {
            for (int i = 0; i < player.spearmans.size(); i++) {
                Spearman spearman = player.spearmans.get(i);
                spearman.setLifePoints(spearman.getLifePoints() + 1);
                spearman.setPowerCavalry(spearman.getPowerCavalry() + 1);
                spearman.setPowerOthers(spearman.getPowerCavalry() + 1);
            }

            for (int i = 0; i < player.archers.size(); i++) {
                Archer archer = player.archers.get(i);
                archer.setLifePoints(archer.getLifePoints() + 1);
                archer.setPowerArrowBuild(archer.getPowerArrowHuman()+1);
                archer.setPowerArrowHuman(archer.getPowerArrowBuild()+1);
                archer.setPowerSword(archer.getPowerSword()+1);
            }

            for (int i = 0; i < player.swordmans.size(); i++) {
                Swordman swordman = player.swordmans.get(i);
                swordman.setLifePoints(swordman.getLifePoints() + 1);
                swordman.setPower(swordman.getPower()+1);
            }



            player.setGold(player.getGold() - 50);
            infCounter++;
            player.changeTurn();


        }else throw new AgeOfEmpiresException("");
    }

    @Override
    public void trainCavalry() throws AgeOfEmpiresException {
        if (player.getGold() >= 50) {
            for (int i = 0; i < player.cavalries.size(); i++) {
                Cavalry cavalry = player.cavalries.get(i);
                cavalry.setLifePoints(cavalry.getLifePoints() + 1);
                cavalry.setPowerCavalry(cavalry.getPowerCavalry() + 1);
                cavalry.setPowerBuild(cavalry.getPowerBuild() + 1);
                cavalry.setPowerOtherHuman(cavalry.getPowerOtherHuman() + 1);
            }
            player.setGold(player.getGold() - 50);
            cavalryCounter++;
            player.changeTurn();


        }else throw new AgeOfEmpiresException("");

    }

    @Override
    public void trainCatapult() throws AgeOfEmpiresException {
        if (player.getGold() >= 50) {

            for (int i = 0; i < player.catapults.size(); i++) {
                Catapult catapult = player.catapults.get(i);
                catapult.setLifePoints(catapult.getLifePoints() + 1);
                catapult.setPowerBuild(catapult.getPowerBuild() + 1);
            }

            player.setGold(player.getGold() - 50);
            catapultCounter++;
            player.changeTurn();


        }else throw new AgeOfEmpiresException("");
    }

    public void isUniversityDead(int row,int col){
        if(getLifePoints()<= 0){
            player.takeMap().remove(this);
            for(Cavalry cavalry : player.cavalries){
                cavalry.setLifePoints(cavalry.getLifePoints() -cavalryCounter);
                cavalry.setPowerCavalry(cavalry.getPowerCavalry() -cavalryCounter);
                cavalry.setPowerBuild(cavalry.getPowerBuild() -cavalryCounter);
                cavalry.setPowerOtherHuman(cavalry.getPowerOtherHuman() -cavalryCounter);
                isAnyoneDead(cavalry,cavalry.getRow(),cavalry.getCol());
            }
            for(Spearman spearman: player.spearmans){
                spearman.setLifePoints(spearman.getLifePoints() - infCounter);
                spearman.setPowerCavalry(spearman.getPowerCavalry() - infCounter);
                spearman.setPowerOthers(spearman.getPowerCavalry() - infCounter);
                isAnyoneDead(spearman,spearman.getRow(),spearman.getCol());
            }
            for (Archer archer: player.archers){
                archer.setLifePoints(archer.getLifePoints() - infCounter);
                archer.setPowerArrowBuild(archer.getPowerArrowHuman()- infCounter);
                archer.setPowerArrowHuman(archer.getPowerArrowBuild()- infCounter);
                archer.setPowerSword(archer.getPowerSword()- infCounter);
                isAnyoneDead(archer,archer.getRow(),archer.getCol());

            }
            for (Swordman swordman: player.swordmans){
                swordman.setLifePoints(swordman.getLifePoints() - infCounter);
                swordman.setPower(swordman.getPower()- infCounter);
                isAnyoneDead(swordman,swordman.getRow(),swordman.getCol());
            }
            for (Catapult catapult:player.catapults){
                catapult.setLifePoints(catapult.getLifePoints() - catapultCounter);
                catapult.setPowerBuild(catapult.getPowerBuild() - catapultCounter);
                isAnyoneDead(catapult,catapult.getRow(),catapult.getCol());
            }
            infCounter=0;
            cavalryCounter=0;
            catapultCounter=0;

            }

    }

    }

