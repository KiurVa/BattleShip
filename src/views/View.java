package views;

import controllers.Controller;
import models.Model;
import views.panels.GameBoard;
import views.panels.InfoBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class View extends JFrame {
    private Model model;
    private GameBoard gameBoard; //Mängulaud
    private InfoBoard infoBoard; //teadetetahvel
    private JPanel scoreboardPanel; // Panel põhiaknas edetabeli jaoks
    private JPanel container;

    public View(Model model) {
        super("Laevade pommitamine");
        this.model = model;
        gameBoard = new GameBoard(model); //Mänulaua loomine
        infoBoard = new InfoBoard(); //Loob teadetetahvli

        container = new JPanel(new BorderLayout());
        container.add(gameBoard, BorderLayout.CENTER); //Mängulaud ujuvale osale
        container.add(infoBoard, BorderLayout.EAST);
        add(container);
        // TEST Frame ja Panel Layout managerid
//        System.out.println("JFRame        " + this.getLayout());
//        System.out.println("container     " + container.getLayout());
//        System.out.println("GameBoard     " +  gameBoard.getLayout());
//        System.out.println("InfoBoard     " +  infoBoard.getLayout());
//        System.out.println("pnlCompoments " + infoBoard.getPnlComponent().getLayout());
    }

    /**
     * Näitab edetabelit põhiaknas
     * @param table edetabeli tabel
     * @param title edetabeli nimi
     */
    public void showMainWindowScoreboard(JTable table, String title) {
        //Eemaldab containeri, millel on infoboard ja gameboard
        remove(container);

        // Loob uue paneeli
        scoreboardPanel = new JPanel(new BorderLayout());
        scoreboardPanel.setBorder(BorderFactory.createTitledBorder(title));

        // Lisab tabeli koos kerimisribaga
        JScrollPane scrollPane = new JScrollPane(table);
        scoreboardPanel.add(scrollPane, BorderLayout.CENTER);

        // Loob sulgemise nupu
        JButton closeButton = new JButton("Sulge");
        closeButton.addActionListener(_ -> {
            remove(scoreboardPanel);
            add(container);
            revalidate();
            repaint();
        });

        // Paneb nupu alumisse serva
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(closeButton);
        scoreboardPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Lisab paneeli põhiaknasse
        add(scoreboardPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void registerGameBoardMouse(Controller controller) {
        gameBoard.addMouseListener(controller);
        gameBoard.addMouseMotionListener(controller);
    }

    public void registerComboBox(ItemListener itemListener) {
        infoBoard.getComboSize().addItemListener(itemListener);
    }

    public void registerNewGameButton(ActionListener actionListener) {
        infoBoard.getBtnNewGame().addActionListener(actionListener);
    }
    public void registerScoreBoardButton(ActionListener actionListener) {
        infoBoard.getBtnScoreboard().addActionListener(actionListener);
    }

    public void setScoreSizeControlsEnabled(boolean enabled) {
        infoBoard.setScoreSizeControlsEnabled(enabled);
    }

    //GETTERS
    public JLabel getLblMouseXY() {
        return infoBoard.getLblMouseXY();
    }

    public JLabel getLblID() {
        return infoBoard.getLblID();
    }

    public JLabel getLblRowCol() {
        return infoBoard.getLblRowCol();
    }

    public JLabel getLblTime() {
        return infoBoard.getLblTime();
    }

    public JLabel getLblShip() {
        return infoBoard.getLblShip();
    }

    public JLabel getLblGameBoard() {
        return infoBoard.getLblGameBoard();
    }

    public JComboBox<String> getComboSize() {
        return infoBoard.getComboSize();
    }

    public JButton getBtnNewGame() {
        return infoBoard.getBtnNewGame();
    }

    public JButton getBtnScoreboard() {
        return infoBoard.getBtnScoreboard();
    }

    public JRadioButton getRdoFile() {
        return infoBoard.getRdoFile();
    }

    public JRadioButton getRdoDb() {
        return infoBoard.getRdoDb();
    }

    public JCheckBox getChkWhere() {
        return infoBoard.getChkWhere();
    }
}
