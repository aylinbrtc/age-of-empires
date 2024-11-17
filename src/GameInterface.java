public interface GameInterface {

    Player getPlayer(int x) throws AgeOfEmpiresException;

    Map getMap();

    void save_text(String filename);

    void save_binary(String filename);

}
