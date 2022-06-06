import java.util.ArrayList;

public class Ruler {
    ArrayList<Player> players;
    ArrayList<Player> finishedPlayers;

    public Ruler(int playerCount){
        players = new ArrayList<>();
        finishedPlayers = new ArrayList<>();
        for(int i=0;i<playerCount;i++){
            players.add(new Player(i));
        }
    }

    public static int diceRoll(){
        return (int) (Math.random()*6 + 1);
    }

    public boolean isFreeMovable() {
        return finishedPlayers.size() <= 0;
    }

    public boolean move(String moveSet){
        for(char c : moveSet.toLowerCase().toCharArray()){

        }
        //todo : return isFreeMovable and isPathValid / actually move
        return false;
    }
}
