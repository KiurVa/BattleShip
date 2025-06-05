package models;

import java.util.Random;
import java.util.stream.IntStream;

public class Game {
    private int boardSize; //Mängulaua suurus
    private int[][] boardMatrix; //Mängulaual asuvad laevad
    private Random random = new Random(); //juhuslikkuse jaoks
    //private int[] ships = {4, 3, 3, 2, 2, 2, 1}; //Laeva pikkused (US)
    private int[] ships = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; //Laeva pikkus (EE)
    //private int[] ships = {5, 4, 4, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1, 1}; //Laeva pikkus
    private int shipsCounter = 0;
    private int clickCounter = 0;

    public Game(int boardSize) {
        this.boardSize = boardSize;
        boardMatrix = new int[boardSize][boardSize]; //Uue mängulaua loomine
    }

    /**
     * Näita konsoolis mängulaua sisu
     */
    public void showGameBoard() {
        System.out.println(); //Tühi rida
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                System.out.print(boardMatrix[row][col] + " ");
            }
            System.out.println(); //Veeru lõpus uuele reale
        }
    }

    public void setupGameBoard() {
        boardMatrix = new int[boardSize][boardSize]; //Uus laua suurus
        int shipsTotal = ships.length; //Kui palju on laevu kokku
        int shipsPlaced = 0; //Kui palju on laevu paigutatud
        //TODO laevade järjekorra segamine

        while (shipsPlaced < shipsTotal) {
            int length = ships[shipsPlaced]; //Millist laeva paigutada (laeva pikkus)
            boolean placed = false; //Laeva pole üaigutatud

            //Valime juhusliku alguspunkti
            int startRow = random.nextInt(boardSize); //Rida
            int startCol = random.nextInt(boardSize); //Veerg

            //Käime kogu laua läbi alates sellest punktist
            outerLoop:
            //Lihtsalt silt(label) ehk nimi for loopile
            for (int rOffset = 0; rOffset < boardSize; rOffset++) { //Rida
                int r = (startRow + rOffset) % boardSize;
                for (int cOffset = 0; cOffset < boardSize; cOffset++) { //Veerg
                    int c = (startCol + cOffset) % boardSize;

                    boolean vertical = random.nextBoolean(); //Määrame juhuslikult kas vertikaalne või horisontaalne
                    if (tryPlaceShip(r, c, length, vertical) || tryPlaceShip(r, c, length, !vertical)) {
                        placed = true;
                        break outerLoop;
                    }
                }
            }

            if(placed) {
                shipsPlaced++; //Järgmine laev
            } else {
                //Kui ei leitud sobivat kohta, katkestame ja alustame uuesti
                setupGameBoard(); //Ise enda välja kutsumine
                return;
            }
        }

        //Eemaldame ajutised kaitsetsoonid (9-d) jättes alles ainult laevad ja vesi
        replaceNineToZero();
    }

    private void replaceNineToZero() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (boardMatrix[row][col] == 9) {
                    boardMatrix[row][col] = 0;
                }
            }
        }
    }

    private boolean tryPlaceShip(int row, int col, int length, boolean vertical) {
        //Kontrolli kas laev üldse mahub mängulauale
        if(vertical && row + length > boardSize) return false;
        if(!vertical && col + length > boardSize) return false;

        //Kontrolli kas sihtpiirkond on vaba (s.h. kaitsetsoon)
        if(!canPlaceShip(row, col, length, vertical)) return false;

        //Kirjutame laeva mängulauale: paigutame igasse lahtrisse laeva pikkuse
        for(int i = 0; i < length; i++) {
            int r = vertical ? row + i : row; //Kasutame rida või siis mitte, olenevalt suunast
            int c = vertical ? col : col + i; //Sama veeru kohta
            boardMatrix[r][c] = length; //Määrame laeva lahtrisse selle pikkuse
        }

        //Määrame ümber laeva kaitsetsooni(vältimaks kontaktset paigutust)
        makeSurrounding(row, col, length, vertical);
        return true;
    }

    private void makeSurrounding(int row, int col, int length, boolean vertical) {
        Area area = getShipSurroundingArea(row, col,  length, vertical);
        //Käime ala igas lahtris ja kui seal on vesi(0), siis märgmine selle kaitseks (9)
        for(int r = area.startRow; r <= area.endRow; r++) {
            for(int c = area.startCol; c <= area.endCol; c++) {
                if(boardMatrix[r][c] == 0) { //Kas on vesi
                    boardMatrix[r][c] = 9; //pane kaitse
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int length, boolean vertical) {
        Area area = getShipSurroundingArea(row, col, length, vertical); //Saame laeva ümbritseva ala
        //Kontrollime igat lahtrit alal - kui kuskil pole tühjust(0), katkestame
        for(int r = area.startRow; r <= area.endRow; r++) {
            for(int c = area.startCol; c <= area.endCol; c++) {
                if(boardMatrix[r][c] > 0 && boardMatrix[r][c] <=5) return false; //Midagi ees, ei sobi
            }
        }
        return true; //Kõik kohad olid vabad
    }

    private Area getShipSurroundingArea(int row, int col, int length, boolean vertical) {
        //Arvutame ümbritseva ala piirid, hoides neid mängu laua piires
        int startRow = Math.max(0, row -1);
        int endRow = Math.min(boardSize -1, vertical ? row + length : row + 1);
        int startCol = Math.max(0, col -1);
        int endCol = Math.min(boardSize -1, vertical ? col + 1 : col + length);
        return new Area(startRow, startCol, endRow, endCol);
    }

    /**
     * Selles lahtris klikkis kasutaja hiirega, kas sai pihta või läks mööda
     * @param row rida
     * @param col veerg
     * @param what millega tegu (7 pihtas, 8 mööda)
     */
    public void setUserClick(int row, int col, int what) {
        if (what == 7) {
            boardMatrix[row][col] = 7; //Pihtas
        } else if (what == 8) {
            boardMatrix[row][col] = 8; //Möödas
        }
    }

    //GETTERS

    public int[][] getBoardMatrix() {
        return boardMatrix;
    }

    public int getClickCounter() {
        return clickCounter;
    }

    public int getShipsCounter() {
        return shipsCounter;
    }

    /**
     * laevade tükkide summa. 4+4+3+3jne
     * @return laeva pikkuste summa
     */
    public int getShipsParts() {
        return IntStream.of(ships).sum();
    }

    /**
     * kas mäng on läbi
     * @return tagastab true kui läbi, false ei ole läbi
     */
    public boolean isGameOver() {
        return getShipsParts() == getShipsCounter();
    }

    //SETTERS

    /**
     * Suurendab klikkide arvu
     * @param clickCounter ette antud väärtus (1)
     */
    public void setClickCounter(int clickCounter) {
        this.clickCounter += clickCounter;
    }

    /**
     * Suurendab leitud laevade kogust etteantud väärtust võrra
     * @param shipsCounter etteantud väärtus (1)
     */
    public void setShipsCounter(int shipsCounter) {
        this.shipsCounter += shipsCounter;
    }
}
