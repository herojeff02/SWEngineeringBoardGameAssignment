import java.io.FileNotFoundException;
import java.util.*;

public class Ruler {
    Player currentPlayer;
    boolean finishedPlayerPresent = false;
    int playerCount;
    RuleBook ruleBook;

    public Ruler(int playerCount, String filePath) throws IllegalArgumentException, FileNotFoundException {
        ruleBook = new RuleBook(playerCount, filePath);
        if(playerCount > 4 || playerCount < 2){
            throw new IllegalArgumentException("Number of players must be 2, 3, or 4.");
        }
        this.playerCount = playerCount;
        currentPlayer = ruleBook.getPlayer(0);
    }

    public boolean inputPlayerMoveSet(int diceRoll, String moveSet){
        // todo : return new Position or Error
        if(moveSet.equals("")){
            currentPlayer.deductBridgeCard();
            return true;
        }
        return ruleBook.move(currentPlayer.getPlayerId(), moveSet, diceRoll);
    }

    public boolean nextPlayer() {
        ArrayList<Player> playerList = ruleBook.getPlayerList();
        int nextPlayerIndex = ruleBook.getNextUnfinishedPlayerIndex(playerList.indexOf(currentPlayer));
        if (nextPlayerIndex == -1) {
            return false;
        } else {
            currentPlayer = playerList.get(nextPlayerIndex);
            return true;
        }
    }

    public ArrayList<MapCellBase> getMapArray(){
        return ruleBook.getMapArray();
    }

    public boolean gameShouldEnd(){
        return ruleBook.gameShouldEnd();
    }

    public int currentPlayerMvmtMax(int diceResult){
        return currentPlayer.getMvmtAbs(diceResult);
    }

    public ArrayList<Player> getScoreSortedPlayers() {
        ArrayList<Player> result = new ArrayList<>(ruleBook.getPlayerList());
        result.sort(Comparator.comparingInt(Player::getScore).reversed());
        return result;
    }
}