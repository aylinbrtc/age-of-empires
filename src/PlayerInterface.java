public interface PlayerInterface {


    int getWood(); // kaç odunu olduğunu döner

    int getGold(); // kaç altını olduğunu döner

    int getStone(); // kaç taşı olduğunu döner

    int getTowerCount(); // kaç kulesi olduğunu döner

    int getWorkerCount(); // kaç işçisi olduğunu döner


    int getSoldierCount(); // kaç askeri olduğunu döner


    // bir şey yapmadan pass geçme işlemi
    void pass();

    //  index'inci askeri döner
    Soldier getSoldier(int index);

    //  index'inci worker'ı döner
    Worker getWorker(int index);

    // belirtilen item'ı almaya çalışır. Alamıyorsa ya da öyle bir item yoksa  AgeofEmpiresException'ı atmalı
    void purchase(Item item);

    University getUniversity();

    // index'inci tower'i döner
    Tower getTower(int index);

}
