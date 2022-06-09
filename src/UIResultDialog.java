import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UIResultDialog extends JDialog{
    public UIResultDialog(ArrayList<Player> result){
        JPanel resultPanel = new JPanel();
        resultPanel.setSize(200, 100);

        resultPanel.setLayout(new GridBagLayout());
        StringBuilder str = new StringBuilder("\n");
        for(int i = 0;i<result.size();i++){
            str.append(resultStringBuilder(result.get(i), i));
        }
        JTextArea resultTextArea = new JTextArea(str.toString());
        resultTextArea.setBackground(new Color(255,255,255,0));
        resultTextArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        resultPanel.add(resultTextArea);

        add(resultPanel);
        setEnabled(false);
        setResizable(false);

        pack();
        setVisible(true);
        setModal(true);
    }

    public String resultStringBuilder(Player player, int rank){
        if(rank == 0){
            return "Player "+ player.getPlayerId() + " won with " + player.getToolCardScore() + " points!\n\n";
        }
        else{
            return "Player "+ player.getPlayerId() + " came " +(rank+1)+ " place with "+ player.getToolCardScore() + " points!\n";
        }
    }

    public void run(){
        setModal(true);
        setVisible(true);
    }
}
