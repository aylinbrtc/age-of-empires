import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Game implements GameInterface,Serializable{


    Map map;
    private int numPlayers;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    private ArrayList<Player> players= new ArrayList<>();;

    public Game(int numPlayers) throws AgeOfEmpiresException {
        map=new Map();
        this.numPlayers=numPlayers;
        if (numPlayers < 2 || numPlayers > 4) {
            throw new AgeOfEmpiresException("");
        }


        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player(i,map,this);
            players.add(player);
        }
        players.get(0).setTurn(true);
    }

    public Game(String binaryFile, boolean b) {
        if (b){
            load_binary(binaryFile);
        }
        else
            load_text(binaryFile);


    }

    private void load_text(String binaryFile) {
        try {
            Scanner s=new Scanner(new FileInputStream(binaryFile));
            Map m = new Map();
            map=m;
            int numPlayers=s.nextInt();
            this.numPlayers=numPlayers;
            for (int i=0; i<numPlayers; i++){
                Player player=new Player();
                players.add(player);
                player.setMap(m);
                player.setG(this);
                player.setOrder(s.nextInt());
                player.setTurn(s.nextBoolean());
                player.setGold(s.nextInt());
                player.setWood(s.nextInt());
                player.setStone(s.nextInt());
                int numOfItems =s.nextInt();
                for (int j=0; j<numOfItems; j++){
                    String str=s.next();
                    if (str.equals("U")) {
                        University u = new University();
                        u.setRow(s.nextInt());
                        u.setCol(s.nextInt());
                        u.setLifePoints(s.nextInt());
                        u.setPlayer(player);
                        m.add(u,u.getRow(),u.getCol());
                        player.setUniversity(u);
                        u.setCatapultCounter(s.nextInt());
                        u.setCavalryCounter(s.nextInt());
                        u.setInfCounter(s.nextInt());
                    }
                    else if (str.equals("T")){
                        Tower t =new Tower();
                        t.setRow(s.nextInt());
                        t.setCol(s.nextInt());
                        t.setLifePoints(s.nextInt());
                        t.setPlayer(player);
                        m.add(t,t.getRow(),t.getCol());
                        player.getTowers().add(t);
                    }
                    if (str.equals("M")){
                        MainBuilding m1 = new MainBuilding();
                        m1.setRow(s.nextInt());
                        m1.setCol(s.nextInt());
                        m1.setLifePoints(s.nextInt());
                        m1.setPlayer(player);
                        m.add(m1,m1.getRow(),m1.getCol());
                        player.setMainBuilding(m1);
                    }
                    if (str.equals("K")){
                        Swordman k=new Swordman();
                        k.setRow(s.nextInt());
                        k.setCol(s.nextInt());
                        k.setLifePoints(s.nextInt());
                        k.setPlayer(player);
                        m.add(k,k.getRow(),k.getCol());
                        player.getSwordmans().add(k);
                        player.getSoldiers().add(k);
                    }
                    if (str.equals("O")){
                        Archer o= new Archer();
                        o.setRow(s.nextInt());
                        o.setCol(s.nextInt());
                        o.setLifePoints(s.nextInt());
                        o.setPlayer(player);
                        m.add(o,o.getRow(),o.getCol());
                        player.getArchers().add(o);
                        player.getSoldiers().add(o);
                    }
                    if (str.equals("S")){
                        Spearman o= new Spearman();
                        o.setRow(s.nextInt());
                        o.setCol(s.nextInt());
                        o.setLifePoints(s.nextInt());
                        o.setPlayer(player);
                        m.add(o,o.getRow(),o.getCol());
                        player.getSpearmans().add(o);
                        player.getSoldiers().add(o);
                    }
                    if (str.equals("A")){
                        Cavalry o= new Cavalry();
                        o.setRow(s.nextInt());
                        o.setCol(s.nextInt());
                        o.setLifePoints(s.nextInt());
                        o.setPlayer(player);
                        m.add(o,o.getRow(),o.getCol());
                        player.getCavalries().add(o);
                        player.getSoldiers().add(o);
                    }
                    if (str.equals("C")){
                        Catapult o= new Catapult();
                        o.setRow(s.nextInt());
                        o.setCol(s.nextInt());
                        o.setLifePoints(s.nextInt());
                        o.setPlayer(player);
                        m.add(o,o.getRow(),o.getCol());
                        player.getCatapults().add(o);
                        player.getSoldiers().add(o);
                    }
                    if (str.equals("W")){
                        Worker o= new Worker();
                        o.setRow(s.nextInt());
                        o.setCol(s.nextInt());
                        o.setLifePoints(s.nextInt());
                        o.setPlayer(player);
                        m.add(o,o.getRow(),o.getCol());
                        player.getWorkers().add(o);
                    }
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void load_binary(String binaryFile) {
        try {
            ObjectInputStream i=new ObjectInputStream(new FileInputStream(binaryFile));
            Object obj=i.readObject();
            if (obj instanceof Map){
                this.map= (Map) obj;
            }
            else
                System.out.println("Mapi binaryide okuyamadı");
            Object obj1=i.readObject();
            if (obj1 instanceof ArrayList && ((ArrayList<?>) obj1).get(0) instanceof Player){
                players= (ArrayList<Player>) obj1;
            }
            else
                System.out.println("players binaryide okuyamadı");

            int no=i.readInt();
            numPlayers=no;
            i.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public Player getPlayer(int x) throws AgeOfEmpiresException {
        for (int i=0; i<players.size(); i++){
            if(players.get(i).getOrder()== x){
                if (players.get(i).getTurn())
                    return players.get(i);
            }
        }
        throw new AgeOfEmpiresException("");
    }


    @Override
    public Map getMap() {
        return map;
    }

    @Override
    public void save_text(String filename) {
        try {
            PrintWriter p=new PrintWriter(new FileOutputStream(filename));
            p.println(numPlayers);
            for(int i=0; i<players.size(); i++){
                p.println(players.get(i).getOrder());
                p.println(players.get(i).getTurn());
                p.println(players.get(i).getGold());
                p.println(players.get(i).getWood());
                p.println(players.get(i).getStone());
                int x=players.get(i).soldiers.size()+players.get(i).workers.size()+players.get(i).towers.size();
                if (players.get(i).getUniversity()!=null){
                    x++;
                }
                x=x+1;
                p.println(x);

                if(players.get(i).getUniversity()!=null){
                    University u=players.get(i).getUniversity();
                    p.println(u.getSymbol());
                    p.println(u.getRow());
                    p.println(u.getCol());
                    p.println(u.getLifePoints());
                    p.println(u.getCatapultCounter());
                    p.println(u.getCavalryCounter());
                    p.println(u.getInfCounter());
                }

                MainBuilding m=players.get(i).getMainBuilding();
                p.println(m.getSymbol());
                p.println(m.getRow());
                p.println(m.getCol());
                p.println(m.getLifePoints());

                for (int j=0; j<players.get(i).soldiers.size(); j++){
                    Soldier soldier=players.get(i).soldiers.get(j);
                    p.println(soldier.getSymbol());
                    p.println(soldier.getRow());
                    p.println(soldier.getCol());
                    p.println(soldier.getLifePoints());
                }
                for (int j=0; j<players.get(i).workers.size(); j++){
                    Worker worker=players.get(i).workers.get(j);
                    p.println(worker.getSymbol());
                    p.println(worker.getRow());
                    p.println(worker.getCol());
                    p.println(worker.getLifePoints());
                }
                for (int j=0; j<players.get(i).towers.size(); j++){
                    Tower tower=players.get(i).towers.get(j);
                    p.println(tower.getSymbol());
                    p.println(tower.getRow());
                    p.println(tower.getCol());
                    p.println(tower.getLifePoints());
                }

            }
            p.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void save_binary(String filename) {
        try {
            ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(filename));
            o.writeObject(map);
            o.writeObject(players);
            o.writeInt(numPlayers);
            o.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }










}
