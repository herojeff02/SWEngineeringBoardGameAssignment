import javax.swing.*;
import java.util.ArrayList;

public class UIModel {
    Ruler ruler;
    public UIModel(Ruler ruler){
        this.ruler = ruler;
    }

    public String getInfoPanelString(){
        ArrayList<Player> list = ruler.getPlayerList();
        StringBuilder result = new StringBuilder();
        for(Player player : list){
            int id = player.getPlayerId();
            int bridgeCard = player.getBridgeCard();
            String cardString = player.getToolCardString();
            int P = cardString.split("P", -1).length-1;
            int H = cardString.split("H", -1).length-1;
            int S = cardString.split("S", -1).length-1;
            int total = player.getScore();
            String line = "Player "+id+"\n"+bridgeCard+" * Bridge Card\n";
            line += P + " * P\n";
            line += H + " * H\n";
            line += S + " * S\n";
            line += total + " = total";

            result.append(line).append("\n\n");
        }
        return result.toString();
    }

    public String getIOPanelTurnString(){
        return "Turn of Player " + ruler.currentPlayer.getPlayerId();
    }

}
