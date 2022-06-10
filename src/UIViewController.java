import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public void update(int height, int width, MapCellBase[][] map2d, ArrayList<Player> players){
        remove(mapPanel);
        remove(sidePanel);
        drawMap(height, width, map2d, players);
        drawPanel(height, width, map2d, players);
        add(mapPanel);
        add(sidePanel, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    public void drawBoard(int height, int width, MapCellBase[][] map2d, ArrayList<Player> players) {
        drawMap(height, width, map2d, players);
        drawPanel(height, width, map2d, players);
        showGameFrame();
    }


    void drawMap(int height, int width, MapCellBase[][] map2d, ArrayList<Player> players){
        mapPanel = new JPanel();

        mapPanel.setLayout(new GridLayout(height, width, 2, 2));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JButton jButton;
                if(map2d[i][j] != null) {
                    jButton = new JButton(map2d[i][j].getCellType());
                    int count = 0;
                    for(Player player : players){
                        if(map2d[i][j].getMapIndex() == player.getCurrPos()){
                            count++;
                            jButton.setText(jButton.getText()+" "+player.getPlayerId());
                            jButton.setBackground(new Color(180,210,255));
                        }
                    }
                    if (count >= 2){
                        jButton.setBackground(new Color(200,180,255));
                    }
                }
                else{
                    if(j > 0 && map2d[i][j-1] != null &&map2d[i][j-1].getCellType().equals("B")){
                        jButton = new JButton("===");
                        jButton.setBackground(new Color(200, 150, 100));
                    }
                    else {
                        jButton = new JButton("");
                        jButton.setBackground(new Color(100, 100, 100));
                    }
                }
                jButton.setEnabled(false);
                jButton.setOpaque(true);
                mapPanel.add(jButton);
            }
        }
    }

    void drawPanel(int height, int width, MapCellBase[][] map2d, ArrayList<Player> players){
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel statusPanel = new JPanel();

        String statusString = uiModel.getInfoPanelString();
        JTextArea statusArea = new JTextArea(statusString);
        statusArea.setOpaque(false);
        statusArea.setEditable(false);
        statusPanel.add(statusArea);

        JPanel ioPanel = new JPanel();
        ioPanel.setLayout(new GridLayout(6,1,1,1));
        String turnString = uiModel.getIOPanelTurnString();
        JTextArea turnArea = new JTextArea(turnString);
        turnArea.setOpaque(false);
        turnArea.setEditable(false);
        ioPanel.add(turnArea);
        JButton rollDiceButton = new JButton("Roll Dice");
        JButton passButton = new JButton("Pass");
        rollDiceButton.addActionListener(e -> {
            rollDiceButton.setEnabled(false);
            passButton.setEnabled(false);
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
                    update(height, width, map2d, players);
                }
                else{
                    moveSet.setText("");
                }
            });
            ioPanel.add(submitButton);

            sidePanel.revalidate();
            sidePanel.repaint();
        });
        passButton.addActionListener(e -> {
            uiModel.ruler.inputPlayerMoveSet(1, "");
            uiModel.ruler.nextPlayer();
            update(height, width, map2d, players);

            sidePanel.revalidate();
            sidePanel.repaint();
        });
        ioPanel.add(rollDiceButton);
        ioPanel.add(passButton);
        statusArea.setOpaque(false);
        statusArea.setEditable(false);

        sidePanel.add(statusPanel);
        sidePanel.add(ioPanel);

    }
}