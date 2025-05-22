package models;

import java.util.ArrayList;

public class Model {
    private int boardSize = 10; //Mängulaua suurus
    private ArrayList<GridData> gridData;

    public Model() {
        gridData = new ArrayList<>();
    }

    //GETTERS
    public int getBoardSize() {
        return boardSize;
    }
    public ArrayList<GridData> getGridData() {
        return gridData;
    }

    //SETTERS
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
    public void setGridData(ArrayList<GridData> gridData) {
        this.gridData = gridData;
    }
}
