package views.panels;

import javax.swing.*;
import java.awt.*;

public class InfoBoard extends JPanel {

    private JPanel pnlComponent =  new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();

    //Kaks kirjastiili
    private Font fontBold = new Font("Verdana", Font.BOLD, 14);
    private Font fontNormal = new Font("Verdana", Font.PLAIN, 14);

    //Võimalikud mängulaua suurused
    private String[] boardSizes = {"10", "11", "12", "13", "14", "15"};

    //Labelid ehk sildid infotahvlil, mille väärtusi vaja muuta ehk vaja pääseda ligi
    private JLabel lblMouseXY;
    private JLabel lblID;
    private JLabel lblRowCol;
    private JLabel lblTime;
    private JLabel lblShip;
    private JLabel lblGameBoard;
    //Combobox valimaks mängulaua suurus
    private JComboBox<String> comboSize;
    //Nupud uus mäng ja edetabel
    private JButton btnNewGame;
    private JButton btnScoreboard;

    public InfoBoard() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(400, 188));
        setBackground(new Color(198, 193, 97));
        //Sellele paneelile lähevad kõik komponendid
        pnlComponent.setBackground(new Color(188, 202, 146));
        gbc.anchor = GridBagConstraints.WEST; //Joondamine vasakule
        gbc.insets = new Insets(2,2,2,2); //Jätab labelite(teksti) vahele 2 pikslit
        //Teeme read nii, et veerud mõlemad. Meetotitena. reana
        setupLine1();
        setupLine2();
        setupLine3();
        setupLine4();
        setupLine5();
        setupLine6();
        setupComboBox();
        setupButtons();
        add(pnlComponent);
    }

    private void setupLine1() {
        //esimese rea esimene veerg
        JLabel label = new JLabel("Hiir");
        label.setFont(fontBold);
        gbc.gridx = 0; //Veerg
        gbc.gridy = 0; //Rida
        pnlComponent.add(label, gbc);
        //Esimese rea teine veerg
        lblMouseXY = new JLabel("x = 0 & y = 0");
        lblMouseXY.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlComponent.add(lblMouseXY, gbc);
    }
    private void setupLine2() {
        JLabel label = new JLabel("Lahtri ID");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlComponent.add(label, gbc);
        lblID = new JLabel("Teadmata");
        lblID.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlComponent.add(lblID, gbc);
    }
    private void setupLine3() {
        JLabel label = new JLabel("Rida : Veerg");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlComponent.add(label, gbc);
        lblRowCol = new JLabel("0 : 0");
        lblRowCol.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 2;
        pnlComponent.add(lblRowCol, gbc);
    }
    private void setupLine4() {
        JLabel label = new JLabel("Mängu aeg");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlComponent.add(label, gbc);
        lblTime = new JLabel("00:00");
        lblTime.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 3;
        pnlComponent.add(lblTime, gbc);
    }
    private void setupLine5() {
        JLabel label = new JLabel("Laevad");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 4;
        pnlComponent.add(label, gbc);
        lblShip = new JLabel("0 / 0");
        lblShip.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 4;
        pnlComponent.add(lblShip, gbc);
    }
    private void setupLine6() {
        JLabel label = new JLabel("Laua suurus");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 5;
        pnlComponent.add(label, gbc);
        lblGameBoard = new JLabel("10 x 10");
        lblGameBoard.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 5;
        pnlComponent.add(lblGameBoard, gbc);
    }
    private void setupComboBox() {
        JLabel label = new JLabel("Vali laua suurus");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 6;
        pnlComponent.add(label, gbc);
        comboSize  = new JComboBox<>(boardSizes);
        comboSize.setFont(fontNormal);
        comboSize.setPreferredSize(new Dimension(105, 25));
        gbc.gridx = 1;
        gbc.gridy = 6;
        pnlComponent.add(comboSize, gbc);
    }
    private void setupButtons() {
        JLabel label = new JLabel("Nupud");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridheight = 2; //Teeb kaks rida üheks
        gbc.gridwidth = 1; //Üks veerg
        gbc.anchor = GridBagConstraints.WEST; //Paigutus vasakule
        gbc.fill = GridBagConstraints.NONE; //Ei venita labelit
        pnlComponent.add(label, gbc);
        btnNewGame = new JButton("Uus mäng");
        btnNewGame.setFont(fontNormal);
        btnNewGame.setPreferredSize(new Dimension(105, 25));
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridheight = 1;
        pnlComponent.add(btnNewGame, gbc);
        btnScoreboard = new JButton("Edetabel");
        btnScoreboard.setFont(fontNormal);
        btnScoreboard.setPreferredSize(new Dimension(105, 25));
        gbc.gridx = 1;
        gbc.gridy = 8;
        pnlComponent.add(btnScoreboard, gbc);
    }
}
