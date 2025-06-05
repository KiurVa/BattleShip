package models;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Model {
    private int boardSize = 10; //Mängulaua suurus
    private ArrayList<GridData> gridData;
    private Game game; //Laevade info mängulaual
    //Edetabeli failiga seotud muutujad
    private String scoreFile = "scores.txt";
    private String[] columnNames = new String[]{"Nimi", "Aeg", "Klikke", "Laua suurus", "Mängu aeg"};
    //Edetabeli andmebaasiga seotud muutujad
    private String scoreDatabase = "scores.db";
    private String scoreTable = "scores";

    public Model() {
        gridData = new ArrayList<>();
    }

    /**
     * Tagastab hiire koordinaatide järgi massiivi indeksi ehk id
     *
     * @param mouseX hiire x-koordinaat
     * @param mouseY hiire y-koordinaat
     * @return lahtri id
     */
    public int checkGridIndex(int mouseX, int mouseY) {
        int result = -1; //Viga
        int index = 0;
        for (GridData gd : gridData) {
            if (mouseX > gd.getX() && mouseX <= (gd.getX() + gd.getWidth()) && mouseY > gd.getY() && mouseY <= (gd.getY() + gd.getHeight())) {
                result = index;
            }
            index++;
        }
        return result;
    }

    /**
     * Tagastab mängulaua reanumbri saadud id põhjal
     *
     * @param id mängulaua lahtri id
     * @return mängulaua rea number
     */
    public int getRowById(int id) {
        if (id != -1) {
            return gridData.get(id).getRow();
        }
        return -1;
    }

    /**
     * Tagastab mängulaua veeru numbri saadud id põhjal
     *
     * @param id mängulaua id
     * @return mängulaua veeru number
     */
    public int getColById(int id) {
        if (id != -1) {
            return gridData.get(id).getCol();
        }
        return -1;
    }

    public void setupNewGame() {
        game = new Game(boardSize);
    }

    public void drawUserBoard(Graphics g) {
        ArrayList<GridData> gdList = getGridData(); //See loodi laua joonistamisel
        int[][] matrix = game.getBoardMatrix(); //Siin on laevade vee jms info
        for (GridData gd : gdList) {
            int row = gd.getRow(); //rida
            int col = gd.getCol(); //veerg
            int cellValue = matrix[row][col]; //Väärtus 0, 1-5, 7 või 8

            //määrame värvi ja suuruse sõltuvalt lahtri väärtusest(cellValue)
            Color color = null; //Algselt värvi pole
            int padding = 0;

            switch (cellValue) {
                case 0: //vesi
                    color = new Color(0, 190, 255);
                    break;
                case 7: //Pihtas
                    color = Color.GREEN;
                    break;
                case 8: //Mööda
                    color = Color.RED;
                    padding = 3;
                    break;
                default:
                    if (cellValue >= 1 && cellValue <= 5) {
                        //Kommenteeri välja kui ei soovi mängulaua laevu näha
                        //color = new Color(0, 191, 255);
                    }


            }
            //Kui värv on määratud, joonista ruut
            if (color != null) {
                g.setColor(color); //Määra värv
                g.fillRect(gd.getX() + padding, gd.getY() + padding,
                        gd.getWidth() - 2 * padding, gd.getHeight() - 2 * padding);
            }
        }
    }

    /**
     * Edetabeli faili olemasolu ja sisu kontroll
     *
     * @return true kui on korras, false kui pole
     */
    public boolean checkFileExistsAndContent() {
        File file = new File(scoreFile); //Teeb faili failiobjektiks
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(scoreFile))) {
            String line = br.readLine();
            if (line == null) {
                return false;
            }
            String[] columns = line.split(";");
            return columns.length == columnNames.length;

        } catch (IOException e) {
            //throw new RuntimeException(e);
            return false;
        }
    }

    /**
     * Edetabeli faili sisu loetakse massiivi ja tagastatakse
     * @return ScoreData list(Edetabeli info)
     */
    public ArrayList<ScoreData> readFromFile() {
        ArrayList<ScoreData> scoreData = new ArrayList<>();
        File file = new File(scoreFile);
        if (file.exists()) {
            try(BufferedReader br = new BufferedReader(new FileReader(scoreFile))) {
                int lineNumber = 0;
                for(String line; (line = br.readLine()) != null; ) {
                    if(lineNumber > 0) {
                        String[] columns = line.split(";");
                        if(Integer.parseInt(columns[3]) == boardSize) {
                            String name = columns[0];
                            int gameTime = Integer.parseInt(columns[1]);
                            int clicks = Integer.parseInt(columns[2]);
                            int board = Integer.parseInt(columns[3]);
                            LocalDateTime played = LocalDateTime.parse(columns[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            scoreData.add(new ScoreData(name, gameTime, clicks, board, played));
                        }
                    }
                    lineNumber++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return scoreData;
    }

    //GETTERS
    public int getBoardSize() {
        return boardSize;
    }

    public ArrayList<GridData> getGridData() {
        return gridData;
    }

    public Game getGame() {
        return game;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public String getScoreFile() {
        return scoreFile;
    }

    public String getScoreDatabase() {
        return scoreDatabase;
    }

    public String getScoreTable() {
        return scoreTable;
    }

    //SETTERS
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setGridData(ArrayList<GridData> gridData) {
        this.gridData = gridData;
    }
}
