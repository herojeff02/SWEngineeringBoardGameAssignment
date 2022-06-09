import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RuleBook {
    private ArrayList<Player> players;
    private ArrayList<Player> finishedPlayers;
    public MapBuilder mapBuilder;

    public RuleBook(int playerCount, String mapFilePath) throws FileNotFoundException {
        players = new ArrayList<>();
        finishedPlayers = new ArrayList<>();
        for(int i=0;i<playerCount;i++){
            players.add(new Player(i));
        }
        mapBuilder = new MapBuilder(mapFilePath);
    }

    public boolean isFreeMovable() {
        return finishedPlayers.size() <= 0;
    }

    public boolean move(int playerIndex, String moveSet, int diceResult){
        Player player = getPlayer(playerIndex);
        int mvmtRange = player.getMvmtAbs(diceResult);
        char[] moves = moveSet.toUpperCase().toCharArray();

        // wait a turn to deduct a bridge card
        if(moves.length == 0){
            player.deductBridgeCard();
            return true;
        }

        int cellIndex = player.getCurrPos();
        for(int i=0;i<=mvmtRange && i<moves.length;i++){
            String move = String.valueOf(moves[i]);
            MapCellBase cell = getCell(cellIndex);

            boolean prevPossible = isFreeMovable();
            boolean nextEqual = cell.nextCellPos.contains(move);
            boolean prevEqual = cell.prevCellPos.contains(move);
            boolean isBridgeStart = cell.getCellType().equals("B");
            boolean isNextBridge = false;
            if(cell.getCellType().equals("B")){
                if(move.equals("R")){
                    isNextBridge = true;
                }
            }
            else if(cell.getCellType().equals("b")){
                if(move.equals("L") && prevPossible){
                    isNextBridge = true;
                }
            }

            // not freely movable, and next cell is invalid
            if(!prevPossible){
                if(!nextEqual && !isNextBridge){
                    return false;
                }
            }
            // freely movable, but next/prev cell are both invalid
            else{
                if(!prevEqual && !nextEqual && !isBridgeStart && !isNextBridge){
                    return false;
                }
            }

            // next cell
            if(nextEqual){
                cellIndex += 1;
            }
            // prev cell
            else if (prevEqual && prevPossible){
                cellIndex -= 1;
            }
            // cross bridge
            else if (isNextBridge && cell instanceof MapBridgeCell){
                cellIndex = ((MapBridgeCell)cell).getPair().getMapIndex();
                player.addBridgeCard();
            }

            if(getCell(cellIndex).getCellType().equals("SE")){
                playerFinished(player);
                return true;
            }
        }

        // moved successfully -> set position of player
        player.setCurrPos(cellIndex);

        // award player tool points
        playerDidLandOnToolCell(playerIndex);
//        MapCellBase arrivalCell = getCell(cellIndex);
//        if(arrivalCell.getGeneralCellType().equals("GT")){
//            player.addToolScore(arrivalCell.getCellType());
//            ((MapCell)arrivalCell).removeTool();
//        }
        return true;
    }

    public Player getPlayer(int playerIndex){
        int index;
        if(playerIndex > players.size()){
            index = players.size() - 1;
        }
        else index = Math.max(playerIndex, 0);
        return players.get(index);
    }

    public ArrayList<Player> getPlayerList(){
        return players;
    }

    private void playerFinished(Player player){
        player.addFinishScore(finishedPlayers.size());
        player.setCurrPos(getMapArray().size()-1);
        finishedPlayers.add(player);
    }

    public int getPlayerCellIndex(int playerIndex){
        return getPlayer(playerIndex).getCurrPos();
    }

    public MapCellBase getCell(int cellIndex){
        return getMapArray().get(cellIndex);
    }
    public boolean playerDidLandOnToolCell(int playerIndex){
        int cellIndex = getPlayer(playerIndex).getCurrPos();
        MapCellBase arrivalCell = getCell(cellIndex);
        if(arrivalCell.getGeneralCellType().equals("GT")){
            getPlayer(playerIndex).addToolScore(arrivalCell.getCellType());
            ((MapCell)arrivalCell).removeTool();
            return true;
        }
        else{
            return false;
        }
    }
    public static int diceRoll(){
        return (int) (Math.random()*6 + 1);
    }

    public boolean playerDidFinish(int playerIndex){
        int cellIndex = getPlayer(playerIndex).getCurrPos();
        MapCellBase cell = getCell(cellIndex);
        return cell.getCellType().equals("SE");
    }

    public int getNextUnfinishedPlayerIndex(int currentPlayer){
        for(int i = 1 ; i<players.size() ; i++){
            int index = Math.floorMod(i+currentPlayer, players.size());
            if(!finishedPlayers.contains(getPlayer(index))){
                return index;
            }
        }
        return -1;
    }

    public ArrayList<MapCellBase> getMapArray(){
        return mapBuilder.getMapArrayList();
    }

    public MapCellBase[][] getMap2d(){
        return mapBuilder.getMap2d();
    }

    public boolean gameShouldEnd(){
        return players.size() <= finishedPlayers.size() + 1;
    }
}
