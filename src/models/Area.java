package models;

public class Area {
    int startRow; //Ala ülemise rea indeks
    int startCol; // Ala vaskpoolse veeru indeks
    int endRow; //Ala alumise rea indeks
    int endCol; //Ala parempoolse veeru indeks

    public Area(int startRow, int startCol, int endRow, int endCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
    }
}
