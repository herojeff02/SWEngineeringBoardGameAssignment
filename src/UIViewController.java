import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UIViewController extends JFrame {
    JPanel mapPanel, sidePanel;
    UIModel uiModel;
    Ruler ruler;
    public UIViewController(UIModel uiModel){
        this.uiModel = uiModel;
        ruler = uiModel.ruler;
        drawMap(ruler.getMap2dDimension()[0], ruler.getMap2dDimension()[1], ruler.getMap2d(), ruler.getPlayerList());
    }

    public void showGameFrame(){
        setSize(1000, 700);
        setLayout(new BorderLayout());

        add(mapPanel);
        add(sidePanel, BorderLayout.EAST);

        setVisible(true);
    }

    public void drawMap(int width, int height, MapCellBase[][] map2d, ArrayList<Player> players) {
        mapPanel = new JPanel();

        mapPanel.setLayout(new GridLayout(height, width, 2, 2));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JButton jButton;
                if(map2d[i][j] != null) {
                    jButton = new JButton(map2d[i][j].getCellType());
                    jButton.setLayout(new BorderLayout());
                    for(Player player : players){
                        if(map2d[i][j].getMapIndex() == player.getCurrPos()){
                            jButton.setText(jButton.getText()+player.getPlayerId());
                        }
                    }
                }
                else{
                    jButton = new JButton("");
                }
                jButton.setEnabled(false);
                mapPanel.add(jButton);
            }
        }




        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel statusPanel = new JPanel();

        String statusString = uiModel.getInfoPanelString();
        JTextArea statusArea = new JTextArea(statusString);
        statusArea.setBackground(new Color(0, 0, 0, 0));
        statusPanel.add(statusArea);

        JPanel ioPanel = new JPanel();
        String turnString = uiModel.getIOPanelTurnString();
        JTextArea turnArea = new JTextArea(turnString);
        statusArea.setBackground(new Color(0, 0, 0, 0));

        sidePanel.add(statusPanel);
        sidePanel.add(ioPanel);
    }

}