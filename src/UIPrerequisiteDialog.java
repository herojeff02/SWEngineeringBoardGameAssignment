import javax.swing.*;
import java.awt.*;

public class UIPrerequisiteDialog extends JDialog{
    String filePath;
    int playerCount = 2;
    String[] preRequisiteResult = new String[]{"", ""};
    public UIPrerequisiteDialog(String defaultPath){
        filePath = defaultPath;
        JPanel prerequisiteFrame = new JPanel();
        prerequisiteFrame.setSize(600, 180);
        prerequisiteFrame.setLayout(new BorderLayout());


//File Chooser Panel
        JPanel fileChooserPanel = new JPanel();
        JTextField filePathField;
        JButton pathSelectButton;
        JFileChooser fileChooser;


        fileChooserPanel.setLayout(new GridLayout(2,1,2,1));
        filePathField = new JTextField(defaultPath);
        pathSelectButton = new JButton("Select a new file");
        fileChooser = new JFileChooser();
        pathSelectButton.addActionListener(e -> {
            int dialogApproved = fileChooser.showOpenDialog(fileChooserPanel);
            if (dialogApproved == JFileChooser.APPROVE_OPTION) {
                filePath = fileChooser.getSelectedFile().toString();
                filePathField.setText(filePath);
            }
        });
        fileChooserPanel.add(filePathField);
        fileChooserPanel.add(pathSelectButton);
        prerequisiteFrame.add(fileChooserPanel);


//Player Counter Panel
        JPanel playerCounterPanel;
        JRadioButton u2, u3, u4;

        playerCounterPanel = new JPanel();
        playerCounterPanel.setLayout(new GridBagLayout());
        playerCounterPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        u2 = new JRadioButton("2 Players");
        u2.setSelected(true);
        u3 = new JRadioButton("3 Players");
        u4 = new JRadioButton("4 Players");
        playerCounterPanel.add(u2);
        playerCounterPanel.add(u3);
        playerCounterPanel.add(u4);
        u2.addActionListener(e -> {
            playerCount = 2;
            u3.setSelected(false);
            u4.setSelected(false);
        });
        u3.addActionListener(e -> {
            playerCount = 3;
            u2.setSelected(false);
            u4.setSelected(false);
        });
        u4.addActionListener(e -> {
            playerCount = 4;
            u2.setSelected(false);
            u3.setSelected(false);
        });
        prerequisiteFrame.add(playerCounterPanel, BorderLayout.EAST);


//Start Button
        JButton startButton = new JButton("Start!");
        startButton.addActionListener(e -> {
            preRequisiteResult[0] = filePath;
            preRequisiteResult[1] = String.valueOf(playerCount);
            dispose();
        });
        prerequisiteFrame.add(startButton, BorderLayout.SOUTH);

        getContentPane().add(prerequisiteFrame);
        pack();
    }
    public String[] run(){
        setModal(true);
        setVisible(true);
        return new String[]{filePath, String.valueOf(playerCount)};
    }
}
