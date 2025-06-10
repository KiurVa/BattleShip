package views.panels;

import models.GridData;
import models.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameBoard extends JPanel {
    private Model model;
    private int startX = 5; //Vasak ülemine x koordinaat framil. paneel sinna peale
    private int startY = 5;
    private int width = 30; // Ruudu laius
    private int height = 30; // Ruudu kõrgus
    private String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};

    public GameBoard(Model model) {
        this.model = model;
        setBackground(new Color(189, 202, 211));
    }

    @Override
    public Dimension getPreferredSize() {
        int w = (width * model.getBoardSize()) + width + (2 * startX);
        int h = (height * model.getBoardSize()) + height + (2 * startY);
        return new Dimension(w, h);
    }

    /**
     * Joonistada ruudustik ja muu mängualaua
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);

        //Kirjastiil ja suurus mängulaual
        g.setFont(new Font("Verdana", Font.BOLD, 14));
        //Tähetik koos ruutudega
        drawColumnAlphabet(g); //graafika kaasa antud
        //Vasakule serva numbrid ruutudega
        drawRowColumn(g);

        if (model.getGame() != null /*&& !model.getGame().isGameOver()*/) { //Eemaldatud osa, mis kontrollib mängu lõppu, et lõppseis jääks lauale
            model.drawUserBoard(g);
        }
        //Joonistab jooned laevade alale
        drawGameGrid(g);
    }

    private void drawColumnAlphabet(Graphics g) {
        int i = 0; //tähestiku massiivi esimese tähe indeks
        g.setColor(Color.WHITE); //Tähetiku joonestamine värv

        for (int x = startX; x <= (width * model.getBoardSize()) + width; x += width) {
            g.drawRect(x, startY, width, height);  //Joonistab ruudu 30x30
            if (x > startX) {
                g.drawString(alphabet[i], x + (width / 2) - 5, 2 * (startY + startY) + 5);
                i++;
            }
        }
    }

    private void drawRowColumn(Graphics g) {
        int i = 1;
        g.setColor(Color.RED);

        for (int y = startY + height; y <= (height * model.getBoardSize()) + height; y += height) {
            g.drawRect(startX, y, width, height);
            if (i<10) { //numbrid 1-9
                g.drawString(String.valueOf(i), startX + (width /2)-5, y+2*(startY + startY));
            } else { //Ülejäänud numbrid
                g.drawString(String.valueOf(i), startX + (width /2)-10, y+2*(startY + startY));
            }
            i++;
        }
    }

    private void drawGameGrid(Graphics g) {
        ArrayList<GridData> matrix = new ArrayList<>(); //tühja maatriksi tegemine
        g.setColor(Color.BLACK);

        int x = startX+ width;
        int y = startY + height;
        int i = 1;

        for (int r = 0; r < model.getBoardSize(); r++) { //Rida
            for  (int c = 0; c < model.getBoardSize(); c++) { //Veerud
                g.drawRect(x, y, width, height); //Joonistab ühe ruudu
                matrix.add(new GridData(r, c, x, y, width, height));
                x+=width;
            }
            //Järgmise reale minek
            y = (startY + height) + (height * i);
            i++;
            x = startX + width;
        }
        model.setGridData(matrix);
    }
}


