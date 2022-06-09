import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //File path and player count
        String[] preRequisiteResult = new UIPrerequisiteDialog("default.map").run();

        int playerCount = Integer.parseInt(preRequisiteResult[1]);
        Ruler ruler = null;
        try {
            ruler = new Ruler(playerCount, preRequisiteResult[0]);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        //play stage
        UIModel uiModel = new UIModel(ruler);
        UIViewController uiViewController = new UIViewController(uiModel);
        uiViewController.showGameFrame();
    }

    public void consoleTest(){
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
        while(!ruler.gameShouldEnd()){
            int diceResult = RuleBook.diceRoll();
            System.out.println("\nplayerid, dice, bridgeCard : " + ruler.currentPlayer.getPlayerId() + " " + diceResult + " " + ruler.currentPlayer.getBridgeCard());
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
            System.out.println("current position " + ruler.currentPlayer.getCurrPos());
            ruler.nextPlayer();
        }

        //game result stage
        ruler.getScoreSortedPlayers();

        System.out.println("end");
    }
}