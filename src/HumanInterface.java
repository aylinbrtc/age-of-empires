public interface HumanInterface {

    void attack(int x, int y) throws AgeOfEmpiresException;

    void move(int x, int y) throws AgeOfEmpiresException;

    int getLifePoints();

    void defense(Item item,int x, int y);



}