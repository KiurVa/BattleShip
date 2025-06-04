package controllers;

import controllers.listeners.MyComboBoxListener;
import controllers.listeners.MyNewGameListener;
import models.GameTimer;
import models.Model;
import views.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Controller implements MouseListener, MouseMotionListener {
    private Model model;
    private View view;
    private GameTimer gameTimer;
    private Timer guiTimer;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        gameTimer = new GameTimer(); //Loome aja objekti, aga ei käivita

        guiTimer = new Timer(1000, e->{
            if(gameTimer.isRunning()){
                this.view.getLblTime().setText(gameTimer.formatGameTime());
            }
        });
        guiTimer.start(); //Käivitab GUI taimeri, aga mängu aega mitte!

        //Listenerid
        view.registerComboBox(new MyComboBoxListener(model, view));
        view.registerNewGameButton(new MyNewGameListener(model, view, gameTimer)); //TODO GameTimer
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        String mouse = String.format("x= %03d & y= %03d", e.getX(), e.getY());
        view.getLblMouseXY().setText(mouse);
        //Loe id ja row ja col infot
        int id = model.checkGridIndex(e.getX(), e.getY());
        int row = model.getRowById(id);
        int col = model.getColById(id);
        if (id != -1) {
            view.getLblID().setText(String.valueOf(id + 1)); //ID näitamine
        }

        //Veeru ja Rea näitamine
        String rowcol = String.format("%d : %d", row + 1, col + 1);
        if (row == -1 || col == -1 ) {
            rowcol = "Pole mängulaual";
        }
        view.getLblRowCol().setText(rowcol);
    }
}
