import controllers.Controller;
import models.Model;
import views.View;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Model model = new Model();
                View view = new View(model);
                Controller controller = new Controller(model, view);

                //Hiire liikumise aktiveerimiseks
                view.registerGameBoardMouse(controller);


                view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Üleval paremal nupud toimima
                view.pack();
                view.setMinimumSize(new Dimension(755,380));
                view.setLocationRelativeTo(null); //Keskele
                view.setVisible(true); //Teeb Frame nähtavaks
            }
        });
    }
}
