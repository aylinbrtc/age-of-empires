public interface ItemInterface {


    int getX();  // x koordinatını yazar

    int getY();  // y koordinatını yazar

    int getLifePoints(); // kaç canı kalmışsa onu yazar

    String getSymbol(); // mapinterface'inde belirtilen her tür için olması gereken karakteri döner

    default String print_message() {
        return getSymbol() + "\t"  + getX() + "\t" + getY() + "\t" +  getLifePoints();
    }

}
