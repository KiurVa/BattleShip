package models;

import java.util.ArrayList;

public class Model {
    private int boardSize = 10; //Mängulaua suurus
    private ArrayList<GridData> gridData;
    private Game game; //Laevade info mängulaual

    public Model() {
        gridData = new ArrayList<>();
    }

    /**
     * Tagastab hiire koordinaatide järgi massiivi indeksi ehk id
     * @param mouseX hiire x-koordinaat
     * @param mouseY hiire y-koordinaat
     * @return lahtri id
     */
    public int checkGridIndex(int mouseX, int mouseY) {
        int result = -1; //Viga
        int index = 0;
        for(GridData gd : gridData) {
            if(mouseX > gd.getX() && mouseX <= (gd.getX() + gd.getWidth()) && mouseY > gd.getY() && mouseY <= (gd.getY() + gd.getHeight())) {
                result = index;
            }
            index++;
        }
        return result;
    }

    /**
     * Tagastab mängulaua reanumbri saadud id põhjal
     * @param id mängulaua lahtri id
     * @return mängulaua rea number
     */
    public int getRowById(int id) {
        if(id != -1) {
            return gridData.get(id).getRow();
        }
        return -1;
    }

    /**
     * Tagastab mängulaua veeru numbri saadud id põhjal
     * @param id mängulaua id
     * @return mängulaua veeru number
     */
    public int getColById(int id) {
        if(id != -1) {
            return gridData.get(id).getCol();
        }
        return -1;
    }

    public void setupNewGame() {
        game =  new Game(boardSize);
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

    //SETTERS
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
    public void setGridData(ArrayList<GridData> gridData) {
        this.gridData = gridData;
    }
}
