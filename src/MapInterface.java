public interface MapInterface {


    /* boş haritanın çizimi. Haritada item'lar konumlarına göre ilgili yere yazılacak.
     * MainBuilding -> M
     * Tower -> T
     * University -> U
     * Swordmans ->K
     * Archer -> O
     * Spearsman -> S
     * Cavalry -> A
     * Catapult -> C
     * Worker -> W
     * MainBuilding'in içinde bir işçi vs. varsa da yine de M yazar.
     */
    default public void print() {
        System.out.println(status());

    }

    // boş harita hali. bu metot güncellenmeli
    default public String status() {
        String s = "";
        for(int i=0;i<50;i++)
        {
            for(int j=0;j<100;j++)
                s +="_";
            s += "\n";
        }
        return s;

    }

}
