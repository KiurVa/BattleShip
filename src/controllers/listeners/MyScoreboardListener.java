package controllers.listeners;

import models.Model;
import views.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyScoreboardListener implements ActionListener {
    Model model;
    View view;

    public MyScoreboardListener(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Edetabel");
    }
}
