import java.io.Serializable;
import java.util.ArrayList;

public class Player implements PlayerInterface, Serializable {
    private boolean turn=false;

    public void setMap(Map map) {
        this.map = map;
    }

    private Map map;

    public Map takeMap() {
        return map;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    Game g;
    private int order;
    private MainBuilding mainBuilding;
    private University university=null;
    private Worker worker;
    private int wood=100;
    private int gold=100;
    private int stone=100;



    public ArrayList<Worker> workers = new ArrayList<>();
    public ArrayList<Soldier> soldiers = new ArrayList<>();
    public ArrayList<Swordman> swordmans = new ArrayList<>();
    public ArrayList<Archer> archers = new ArrayList<>();
    public ArrayList<Spearman> spearmans = new ArrayList<>();
    public ArrayList<Cavalry> cavalries = new ArrayList<>();
    public ArrayList<Catapult> catapults = new ArrayList<>();

    public MainBuilding getMainBuilding() {
        return mainBuilding;
    }


    public ArrayList<Tower> towers=new ArrayList<>();

    public Player(){

    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Player(int order, Map map, Game g) {
        this.g=g;
        this.map=map;
        this.order = order;
        this.mainBuilding = new MainBuilding();
        mainBuilding.setPlayer(this);
        this.worker=new Worker();
        worker.determine(this);
        if(order==0){
            mainBuilding.setRow(0);
            mainBuilding.setCol(0);
            worker.setRow(0);
            worker.setCol(0);
            addMap(mainBuilding,0,0);
            addMap(worker,0,0);
        }
        else if(order==1){
            mainBuilding.setRow(49);
            mainBuilding.setCol(99);
            worker.setRow(49);
            worker.setCol(99);
            addMap(mainBuilding,49,99);
            addMap(worker,49,99);
        }
        else if(order==2){
            mainBuilding.setRow(49);
            mainBuilding.setCol(0);
            worker.setRow(49);
            worker.setCol(0);
            addMap(mainBuilding,49,0);
            addMap(worker,49,0);
        }
        else if(order==3){
            mainBuilding.setRow(0);
            mainBuilding.setCol(99);
            worker.setRow(0);
            worker.setCol(99);
            addMap(mainBuilding,0,99);
            addMap(worker,0,99);
        }

    }

    public void setTurn(boolean turn){
        this.turn=turn;
    }

    public boolean getTurn() {
        return turn;
    }

    @Override
    public int getWood() {
        return wood;
    }

    @Override
    public int getGold() {
        return gold;
    }

    @Override
    public int getStone() {
        return stone;
    }

    @Override
    public int getTowerCount() {
        return towers.size();
    }

    @Override
    public int getWorkerCount() {
        return workers.size();
    }

    @Override
    public int getSoldierCount() {
        return soldiers.size();
    }

    @Override
    public void pass() throws AgeOfEmpiresException {
        changeTurn();
    }

    @Override
    public Soldier getSoldier(int index) throws AgeOfEmpiresException {
        if (index<getSoldierCount())
            return soldiers.get(index);
        else
            throw new AgeOfEmpiresException("");
    }

    @Override
    public Worker getWorker(int index) throws AgeOfEmpiresException {
        if(index<workers.size())
            return workers.get(index);
        else throw new AgeOfEmpiresException("");
    }

    @Override
    public void purchase(Item item) throws AgeOfEmpiresException {
        item.setRow(mainBuilding.getRow());
        item.setCol(mainBuilding.getCol());
        if (item instanceof Worker){
            Worker worker = (Worker) item;
            int total = getTotal();
            if(total<20){
                if(gold>=1) {
                    workers.add(worker);
                    item.determine(this);
                    gold--;
                    takeMap().add(worker,worker.getRow(),worker.getCol());
                    changeTurn();
                }
            }
        }
        else if (item instanceof Swordman){
            Swordman swordman = (Swordman) item;
            int total=getTotal();
            if(total<20){
                if(gold>=2 && wood>=2) {
                    soldiers.add(swordman);
                    swordmans.add(swordman);
                    item.determine(this);
                    gold = gold -2;
                    wood = wood-2;
                    takeMap().add(swordman,swordman.getRow(),swordman.getCol());
                    changeTurn();
                }
            }
        }
        else if (item instanceof Archer){
            Archer archer = (Archer) item;
            int total=getTotal();
            if(total<20){
                if(gold>=2 && wood>=5) {
                    soldiers.add(archer);
                    archers.add(archer);
                    item.determine(this);
                    gold = gold-2;
                    wood = wood-5;
                    takeMap().add(archer,archer.getRow(),archer.getCol());
                    changeTurn();
                }
            }
        }
        else if (item instanceof Spearman){
            Spearman spearman = (Spearman) item;
            int total=getTotal();
            if(total<20){
                if(gold>=3 && wood>=2) {
                    soldiers.add(spearman);
                    spearmans.add(spearman);
                    item.determine(this);
                    gold = gold-3;
                    wood = wood-2;
                    takeMap().add(spearman,spearman.getRow(),spearman.getCol());
                    changeTurn();
                }
            }
        }
        else if (item instanceof Cavalry){
            Cavalry cavalry = (Cavalry) item;
            int total=getTotal();
            if(total<20){
                if(gold>=10 && wood>=2) {
                    soldiers.add(cavalry);
                    cavalries.add(cavalry);
                    item.determine(this);
                    gold = gold-10;
                    wood = wood-2;
                    takeMap().add(cavalry,cavalry.getRow(),cavalry.getCol());
                    changeTurn();
                }
            }
        }
        else if (item instanceof Catapult){
            Catapult catapult = (Catapult) item;
            int total=getTotal();
            if(total<20){
                if(gold>=20 && stone>=5 && wood>=30) {
                    soldiers.add(catapult);
                    catapults.add(catapult);
                    item.determine(this);
                    gold = gold-20;
                    stone = stone-5;
                    wood = wood-30;
                    takeMap().add(catapult,catapult.getRow(),catapult.getCol());
                    changeTurn();
                }
            }
        }
        else
            throw new AgeOfEmpiresException("");
    }

    private int getTotal() {
        return workers.size() + soldiers.size() ;

    }
    public void addMap(Item item,int row, int col){
        map.add(item,row,col);
        if (item instanceof Worker){
            Worker worker=(Worker) item;
            workers.add(worker);
        }
        else if (item instanceof Swordman) {
            Swordman swordman = (Swordman) item;
            soldiers.add(swordman);
            swordmans.add(swordman);
        }
        else if (item instanceof Archer){
            Archer archer = (Archer) item;
            soldiers.add(archer);
            archers.add(archer);

        }

        else if (item instanceof Spearman){
            Spearman spearman = (Spearman) item;
            soldiers.add(spearman);
            spearmans.add(spearman);

        }
        else if (item instanceof Cavalry){
            Cavalry cavalry = (Cavalry) item;
            soldiers.add(cavalry);
            cavalries.add(cavalry);

        }
        else if (item instanceof Catapult){
            Catapult catapult = (Catapult) item;
            soldiers.add(catapult);
            catapults.add(catapult);

        }
        else if (item instanceof University){
            university = (University) item;
        }
        else if (item instanceof Tower){
            Tower tower = (Tower) item;
            towers.add(tower);
        }

    }


    public void changeTurn() throws AgeOfEmpiresException {


        boolean x=mainBuilding.isDeadMain(mainBuilding.getRow(),mainBuilding.getCol());
        if (university!=null)
            university.isUniversityDead(university.getRow(),university.getCol());
        if (g.getPlayers().size()>=2) {
            int i = g.getPlayers().indexOf(this);
            if (i == g.getPlayers().size() - 1) {
                g.getPlayers().get(0).setTurn(true);
            } else {
                g.getPlayers().get(i + 1).setTurn(true);
            }
            g.getPlayers().get(i).setTurn(false);
            gold = gold + 2;
            wood = wood + 10;
            stone = stone + 5;
        }
        else
            System.out.println("Oyun bitti");
    }


    @Override
    public University getUniversity() {
        return university;
    }
    public void setWood(int wood) {
        this.wood = wood;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setStone(int stone) {
        this.stone = stone;
    }
    @Override
    public Tower getTower(int index) throws AgeOfEmpiresException {
        if (index<getTowerCount())
            return towers.get(index);
        else
            throw new AgeOfEmpiresException("");
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public void setMainBuilding(MainBuilding mainBuilding) {
        this.mainBuilding = mainBuilding;
    }


    public Map getMap() {
        return map;
    }

    public Game getG() {
        return g;
    }

    public void setG(Game g) {
        this.g = g;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<Worker> workers) {
        this.workers = workers;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(ArrayList<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    public ArrayList<Swordman> getSwordmans() {
        return swordmans;
    }

    public void setSwordmans(ArrayList<Swordman> swordmans) {
        this.swordmans = swordmans;
    }

    public ArrayList<Archer> getArchers() {
        return archers;
    }

    public void setArchers(ArrayList<Archer> archers) {
        this.archers = archers;
    }

    public ArrayList<Spearman> getSpearmans() {
        return spearmans;
    }

    public void setSpearmans(ArrayList<Spearman> spearmans) {
        this.spearmans = spearmans;
    }

    public ArrayList<Cavalry> getCavalries() {
        return cavalries;
    }

    public void setCavalries(ArrayList<Cavalry> cavalries) {
        this.cavalries = cavalries;
    }

    public ArrayList<Catapult> getCatapults() {
        return catapults;
    }

    public void setCatapults(ArrayList<Catapult> catapults) {
        this.catapults = catapults;
    }

    public void setTowers(ArrayList<Tower> towers) {
        this.towers = towers;
    }
}
