import java.util.ArrayList;

public class MainBuilding extends Building {
    public MainBuilding() {
        setLifePoints(100);
    }

    @Override
    public String getSymbol() {
        return "M";
    }

    public boolean isDeadMain(int row, int col) {
        if (getLifePoints() <= 0) {
            player.g.getPlayers().remove(player);
            for (int i = 0; i < player.takeMap().getMap().length; i++) {
                for (int j = 0; j < player.takeMap().getMap()[i].length; j++) {
                    for (int k = 0; k < player.takeMap().getMap()[i][j].size(); k++) {
                        if (player.takeMap().getMap()[i][j].get(k).player.getOrder() == player.getOrder()) {
                            Item item = player.takeMap().getMap()[i][j].get(k);
                            player.takeMap().remove(item);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
