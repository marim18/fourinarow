public class Board {

    private int cellPosition;
    private int rowPosition;
    private int playerPlaced;


    public Board()
    {
        cellPosition = 0;
        rowPosition  =  0;
        playerPlaced = 0;
    }

    public Board( int newCellPosition,  int newRowPosition)
    {
        cellPosition = newCellPosition;
        rowPosition  =  newRowPosition;
    }


    public void setWhichPlayerPlaced(int newPlayerPlaced)
    {
        playerPlaced = newPlayerPlaced;
    }

    public int getWhichPlayerPlaced()
    {
        return playerPlaced;
    }




}
