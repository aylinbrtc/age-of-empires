import java.util.ArrayList;

public class MainBuilding extends Building {
    public MainBuilding() {
        setLifePoints(100);
    }

    @Override
    public String getSymbol() {
        return "M";
    }

    public void isDeadMain() {
        if (getLifePoints() <= 0) {
            for (int i = 0; i < player.takeMap().getMap().length; i++) {
                for (int j = 0; j < player.takeMap().getMap()[i].length; j++) {
                    for (int k = 0; k < player.takeMap().getMap()[i][j].size(); k++) {
                        if (player.takeMap().getMap()[i][j].get(k).player.getOrder() == player.getOrder()) {
                            Item item = player.takeMap().getMap()[i][j].get(k);
                            player.takeMap().removeInMap(item);
                        }
                    }
                }
            }

            for(int l=0 ; l< player.workers.size(); l++){
                player.takeMap().removeInMap(player.workers.get(l));
            }player.g.getPlayers().remove(player);
        }
    }
}
