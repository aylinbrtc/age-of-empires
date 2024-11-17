public interface HumanInterface {

    // (x,y) koordinatlarındaki item'a saldır. Saldıramazsa AgeofEmpiresException'ı at
    void attack(int x, int y);

    // (x,y) koordinatlarına git. Gidemezse AgeofEmpiresException'ı at. Eğer gideceği koordinat komple bloke olmuşsa gidemez.
    // gidebileceği mesafenin dışında ise gidemez.
    void move(int x, int y);

    int getLifePoints(); // kaç canı kaldığını yazar



}