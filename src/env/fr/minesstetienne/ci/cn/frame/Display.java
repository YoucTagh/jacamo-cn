package fr.minesstetienne.ci.cn.frame;

import javax.swing.*;

/**
 * @author YoucTagh
 */
public class Display extends JFrame {

    private JTextArea textArea;
    private static int n = 0;

    public Display(String name) {
        setTitle(".:: "+name+" console ::.");

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        textArea = new JTextArea(30, 60);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);


        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(5));
        pack();
        setLocation(n*40, n*80);
        setVisible(true);

        n++;
    }

    public void addText(final String s){
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                textArea.append(s+"\n");
            }
        });
    }
}