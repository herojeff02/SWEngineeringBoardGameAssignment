import java.util.ArrayList;

public class RuleBook {
    ArrayList<Player> players;
    ArrayList<Player> finishedPlayers;
    MapBuilder mapBuilder;

    public RuleBook(int playerCount, String mapFilePath){
        players = new ArrayList<>();
        finishedPlayers = new ArrayList<>();
        for(int i=0;i<playerCount;i++){
            players.add(new Player(i));
        }
        mapBuilder = new MapBuilder(mapFilePath);
    }

    public static int diceRoll(){
        return (int) (Math.random()*6 + 1);
    }

    public boolean isFreeMovable() {
        return finishedPlayers.size() <= 0;
    }

    public boolean move(int playerIndex, String moveSet, int diceResult){
        Player player = players.get(playerIndex);
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
            MapCellBase cell = mapBuilder.getMapArrayList().get(cellIndex);

            boolean prevPossible = isFreeMovable();
            boolean nextEqual = cell.nextCellPos.contains(move);
            boolean prevEqual = cell.prevCellPos.contains(move);
            boolean isBridgeStart = cell.getCellType().equalsIgnoreCase("B");
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
            if(!prevPossible && !nextEqual && !isNextBridge){
                return false;
            }
            // freely movable, but next/prev cell are both
            if((prevPossible && !prevEqual && !nextEqual) && (isBridgeStart && !isNextBridge)){
                return false;
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
            else if (isBridgeStart && isNextBridge && cell instanceof MapBridgeCell){
                cellIndex = ((MapBridgeCell)cell).getPair().getMapIndex();
                player.addBridgeCard();
            }
        }

        // moved successfully -> set position of player
        player.setCurrPos(cellIndex);

        // award player tool points
        MapCellBase arrivalCell = mapBuilder.getMapArrayList().get(cellIndex);
        if(arrivalCell.getGeneralCellType().equals("GT")){
            player.addToolScore(arrivalCell.getCellType());
            ((MapCell)arrivalCell).removeTool();
        }
        else if(arrivalCell.getCellType().equals("SE")){
            player.addFinishScore(finishedPlayers.size());
            finishedPlayers.add(player);
        }
        return true;
    }
}
