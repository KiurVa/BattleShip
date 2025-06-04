package controllers.listeners;

import models.Model;
import views.View;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MyComboBoxListener implements ItemListener {
    private Model model;
    private View view;
    public MyComboBoxListener(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if  (e.getStateChange() == ItemEvent.SELECTED) {
            String number =  e.getItem().toString(); // Võta väärtus tekstina
            int size = Integer.parseInt(number); //Tee eelnev nr string täisarvuks
            view.getLblGameBoard().setText(String.format("%d x %d", size, size));
            model.setBoardSize(size); //Määrab uue mängulaua suuruse
            view.pack(); //Et suurus muutuks
            view.repaint(); //Joonista uuesti
        }
    }
}
