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
        drawBoard(ruler.getMap2dDimension()[0], ruler.getMap2dDimension()[1], ruler.getMap2d(), ruler.getPlayerList());
    }

    public void showGameFrame(){
        setSize(1200, 700);
        setLayout(new BorderLayout());

        add(mapPanel);
        add(sidePanel, BorderLayout.EAST);

        setVisible(true);
    }

    public void update(int width, int height, MapCellBase[][] map2d, ArrayList<Player> players){
        remove(mapPanel);
        remove(sidePanel);
        drawMap(width, height, map2d, players);
        drawPanel(width, height, map2d, players);
        add(mapPanel);
        add(sidePanel, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    public void drawBoard(int width, int height, MapCellBase[][] map2d, ArrayList<Player> players) {
        drawMap(width, height, map2d, players);
        drawPanel(width, height, map2d, players);
        showGameFrame();
    }


    void drawMap(int width, int height, MapCellBase[][] map2d, ArrayList<Player> players){
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
    }

    void drawPanel(int width, int height, MapCellBase[][] map2d, ArrayList<Player> players){
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel statusPanel = new JPanel();

        String statusString = uiModel.getInfoPanelString();
        JTextArea statusArea = new JTextArea(statusString);
        statusArea.setOpaque(false);
        statusArea.setEditable(false);
        statusPanel.add(statusArea);

        JPanel ioPanel = new JPanel();
        ioPanel.setLayout(new GridLayout(5,1,1,1));
        String turnString = uiModel.getIOPanelTurnString();
        JTextArea turnArea = new JTextArea(turnString);
        turnArea.setOpaque(false);
        turnArea.setEditable(false);
        ioPanel.add(turnArea);
        JButton rollDiceButton = new JButton("Roll Dice");
        rollDiceButton.addActionListener(e -> {
            rollDiceButton.setEnabled(false);
            int diceRoll = RuleBook.diceRoll();
            int bridgeCard = ruler.currentPlayer.getBridgeCard();
            int maxMvmt = ruler.currentPlayer.getMvmtAbs(diceRoll);
            String mvmtString = diceRoll + " - " + bridgeCard + " = " + maxMvmt;
            JTextArea mvmtTextArea = new JTextArea(mvmtString);
            mvmtTextArea.setOpaque(false);
            mvmtTextArea.setEditable(false);
            ioPanel.add(mvmtTextArea);

            JTextField moveSet = new JTextField();
            ioPanel.add(moveSet);

            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(e1 -> {
                String mvSet = moveSet.getText();
                if(uiModel.ruler.inputPlayerMoveSet(maxMvmt, mvSet)){
                    if(ruler.gameShouldEnd()){
                        new UIResultDialog(ruler.getScoreSortedPlayers()).run();
                        sidePanel.remove(ioPanel);
                    }
                    uiModel.ruler.nextPlayer();
                    moveSet.setText("");
                    update(width, height, map2d, players);
                }
                else{
                    moveSet.setText("");
                }
            });
            ioPanel.add(submitButton);

            sidePanel.revalidate();
            sidePanel.repaint();
        });
        ioPanel.add(rollDiceButton);
        statusArea.setOpaque(false);
        statusArea.setEditable(false);

        sidePanel.add(statusPanel);
        sidePanel.add(ioPanel);

    }
}