package controllers.listeners;

import models.GameTimer;
import models.Model;
import views.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyNewGameListener implements ActionListener {
    private Model model;
    private View view;
    private GameTimer gameTimer;
    public MyNewGameListener(Model model, View view, GameTimer gameTimer) {
        this.model = model;
        this.view = view;
        this.gameTimer = gameTimer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameTimer.isRunning()) { //Mäng ei käi
            //See on uus lahendus
            new Thread(() -> {
                model.setupNewGame(); //Teeme uue mängu
                model.getGame().setupGameBoard();
                model.getGame().showGameBoard();
                view.getLblShip().setText(model.getGame().getShipsCounter() + "/" + model.getGame().getShipsParts());
                SwingUtilities.invokeLater(() -> {
                    view.getBtnNewGame().setText("Katkesta mäng");
                    gameTimer.start();
                });
            }).start();
        } else { //Mäng käib
            gameTimer.stop();
            view.getBtnNewGame().setText("Uus mäng");
        }
    }
}
