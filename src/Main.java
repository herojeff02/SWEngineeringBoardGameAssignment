import javax.swing.*;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        boolean guiMode = true;
        if (guiMode){
            //File path and player count
            String[] preRequisiteResult = new UIPrerequisiteDialog("default.map").run();

            int playerCount = Integer.parseInt(preRequisiteResult[1]);
            Ruler ruler = null;
            try {
                ruler = new Ruler(playerCount, preRequisiteResult[0]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage(), "File Not Found", JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }

            //play stage
            UIModel uiModel = new UIModel(ruler);
            UIViewController uiViewController = new UIViewController(uiModel);
            uiViewController.showGameFrame();
        }
        else{
            UIClassicViewController uiClassicViewController = new UIClassicViewController();
        }
    }
}