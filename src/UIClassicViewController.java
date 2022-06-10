import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UIClassicViewController {
    public UIClassicViewController(){
        //param stage
        Ruler ruler = null;
        int playerCount = 0;
        Scanner scanner = new Scanner(System.in);
        while(playerCount < 2 || playerCount > 4){
            System.out.println("input player count : ");
            playerCount = scanner.nextInt();
        }
        try{
            ruler = new Ruler(playerCount, "default.map");
        } catch (IllegalArgumentException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        assert ruler != null;

        //play stage
        int mapHeight = ruler.getMap2dDimension()[0];
        int mapWidth = ruler.getMap2dDimension()[1];
        MapCellBase[][] mapCells2d = ruler.getMap2d();
        while(!ruler.gameShouldEnd()){
            int diceResult = RuleBook.diceRoll();
            for(int i = 0; i< mapHeight; i++){
                for(int j = 0; j< mapWidth; j++){
                    if(mapCells2d[i][j] != null) {
                        System.out.printf("%3s", mapCells2d[i][j].getCellType());
                    }
                    else{
                        if(j > 0 && mapCells2d[i][j-1] != null && mapCells2d[i][j-1].getCellType().equals("B")){
                            System.out.print("  -");
                        }
                        else{
                            System.out.print("   ");
                        }
                    }
                }
                System.out.println();
            }
            System.out.println("\nplayerid, dice - bridgeCard : " +
                    ruler.currentPlayer.getPlayerId()
                    + " " +
                    (diceResult - ruler.currentPlayer.getBridgeCard()));
            String input = "";
            scanner = new Scanner(System.in);
            boolean moveResult = false;
            while(!moveResult){
                // moveset length check
                input = "";
                while(!(input.length() == ruler.currentPlayerMvmtMax(diceResult))){
                    System.out.println("input moveset : ");
                    input = scanner.nextLine();
                    if(input.length() == 0){
                        break;
                    }
                }
                // valid move check
                moveResult = ruler.inputPlayerMoveSet(diceResult, input.toLowerCase());
            }
            System.out.println("new position " + ruler.currentPlayer.getCurrPos());
            ArrayList<Player> list = ruler.getPlayerList();
            for(int i=0;i<list.size();i++){
                String result = "";
                result += "Player " + i + " : ";
                //todo : manipulate string
                result += "position - " + list.get(i).getCurrPos();
                result += ", tool cards - " + list.get(i).getToolCardString();
                result += ", bridge cards - " + list.get(i).getBridgeCard();
                result += ", score - " + list.get(i).getScore();
                System.out.println(result);
            }
            ruler.nextPlayer();
        }

        //game result stage
        ArrayList<Player> list = ruler.getScoreSortedPlayers();
        System.out.println("Player " + list.get(0).getPlayerId() + " won!");
    }
}
