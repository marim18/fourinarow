import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GUI extends JFrame implements ActionListener {
    private int size;
    private int count = 0;
    private ArrayList records = new ArrayList();
    private int amountOfPlayers;
    private JButton[][] buttons;
    private Board[][] gameBoard;
    private ImageIcon emptyCell = new ImageIcon("src/graphics/emptyCell.png");
    private ImageIcon player1 = new ImageIcon("src/graphics/redCell.png");
    private ImageIcon player2 = new ImageIcon("src/graphics/yellowCell.png");
    private Sound playSound = new Sound();
    private PrintHighscore print = new PrintHighscore();
    private Timer timer;
    private int minute = 0, sec = 0;
    private static int pvpCount = 0, pvcCount = 0;


    public GUI() {
        this.setTitle("Four on a row");

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game Menu");
        menuBar.add(gameMenu);
        JMenuItem reset = new JMenuItem("Restart");
        gameMenu.add(reset);
        reset.addActionListener(this::resetPerformed);
        JMenuItem showPVPHighscore = new JMenuItem("Show Player vs Player Match History");
        gameMenu.add(showPVPHighscore);
        showPVPHighscore.addActionListener(this::openPVPHighscore);
        JMenuItem showPVCHighscore = new JMenuItem("Show Player vs Computer Match History");
        gameMenu.add(showPVCHighscore);
        showPVCHighscore.addActionListener(this::openPVCHighscore);

        gameInfo();
        dynamicAllocation();
        buttons = new JButton[size][size];

        this.setLayout(new GridLayout(size, size));
        createEmptyBoard();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        timer = new Timer(1000, this);
        timer.start();
        try
        {
            LoadHighscores(records);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.setVisible(true);
    }

    public void openPVPHighscore(ActionEvent e)
    {
        print.printPvP();
    }
    public void openPVCHighscore(ActionEvent e)
    {
        print.printPvC();
    }
    public void resetPerformed(ActionEvent e)
    {
        timer.stop();
        pvpCount--;
        pvcCount--;
        startAgain();
    }


    public void gameInfo()
    {
        String buttons[] = {"Player vs Player", " Player vs CPU"};
        int y = JOptionPane.showOptionDialog(null,
                "Choose gamemode",
                "Gamemode selection",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                buttons,
                "default"
        );

        if (y == 0)
        {
            amountOfPlayers = 2;
            pvpCount++;
        }
        else
            {
            amountOfPlayers = 1;
            pvcCount++;
            }

        String inpSize = JOptionPane.showInputDialog("Enter Board size between 4-8");
        int boardSize = Integer.parseInt(inpSize);
        if (boardSize < 4)
        {
            JOptionPane.showMessageDialog(null,
                    "Can not be lower than 4",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        else if (boardSize > 8)
        {
            JOptionPane.showMessageDialog(null,
                    "Can not be higher than 9",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        size = boardSize;
    }


    public void dynamicAllocation()
    {
        gameBoard = new Board[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                gameBoard[i][j] = new Board();
            }
        }
    }


    public void addButtons()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                buttons[i][j] = new JButton(emptyCell);
                buttons[i][j].addActionListener(this);
                buttons[i][j].setBackground(Color.BLUE);
                buttons[i][j].setForeground(Color.BLUE);
                this.add(buttons[i][j]);
            }
        }
    }


    public void createEmptyBoard()
    {
        for (int i = size - 2; i >= 0; i--)
        {
            for (int j = size - 1; j >= 0; j--)
            {
                gameBoard[i][j].setWhichPlayerPlaced(-99);
            }
        }
        addButtons();
    }


    public void checkWinner(int winner)
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (gameBoard[i][j].getWhichPlayerPlaced() == winner)
                {
                    // vertical cehck
                    if (i + 3 < size)
                    {
                        if (gameBoard[i + 1][j].getWhichPlayerPlaced() == winner && gameBoard[i + 2][j].getWhichPlayerPlaced() == winner && gameBoard[i + 3][j].getWhichPlayerPlaced() == winner)
                        {
                            timer.stop();
                            if (winner == 1)
                            {
                                showResult(1);
                            }
                            else if (winner == 2)
                            {
                                showResult(2);
                            }
                        }
                    }
                    // horizontal check
                    if (j + 3 < size)
                    {
                        if (gameBoard[i][j + 1].getWhichPlayerPlaced() == winner && gameBoard[i][j + 2].getWhichPlayerPlaced() == winner && gameBoard[i][j + 3].getWhichPlayerPlaced() == winner)
                        {
                            timer.stop();
                            if (winner == 1)
                            {
                                showResult(1);
                            }
                            else if (winner == 2)
                            {
                                showResult(2);
                            }
                        }
                    }
                    // diagonal left to right check
                    if (i < size - 3 && j < size - 3)
                    {
                        if (gameBoard[i + 1][j + 1].getWhichPlayerPlaced() == winner && gameBoard[i + 2][j + 2].getWhichPlayerPlaced() == winner && gameBoard[i + 3][j + 3].getWhichPlayerPlaced() == winner)
                        {
                            timer.stop();
                            if (winner == 1)
                            {
                                showResult(1);
                            }
                            else if (winner == 2)
                            {
                                showResult(2);
                            }
                        }
                    }
                    // diagonal right to left check
                    if (i < size - 3 && j - 3 >= 0)
                    {
                        if (gameBoard[i + 1][j - 1].getWhichPlayerPlaced() == winner && gameBoard[i + 2][j - 2].getWhichPlayerPlaced() == winner && gameBoard[i + 3][j - 3].getWhichPlayerPlaced() == winner)
                        {
                            timer.stop();
                            if (winner == 1)
                            {
                                showResult(1);
                            }
                            else if (winner == 2)
                            {
                                showResult(2);
                            }
                        }
                    }
                }
            }
        }
    }


    public void showResult(int winnerPlayer)
    {
        if (winnerPlayer == 1)
        {
            playSound.winnerSound();
            Wingame("Red player");
            JOptionPane.showMessageDialog(null,
                    "Winner is Red",
                    "Game finished",
                    JOptionPane.INFORMATION_MESSAGE);
            playAgain();
        }
        else {
            playSound.winnerSound();
            Wingame("Yellow player");
            JOptionPane.showMessageDialog(null,
                    "Winner is Yellow",
                    "Game finished",
                    JOptionPane.INFORMATION_MESSAGE);
            playAgain();
        }
    }


    public void playAgain()
    {
        String buttons[] = {"Play again", "Exit"};
        int y = JOptionPane.showOptionDialog(null,
                "Would you like to play again?",
                "Game finished",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                buttons,
                "default"
        );

        if (y == 0) {
            startAgain();
        } else {
            System.exit(0);
        }
    }


    public void startAgain()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                gameBoard[i][j].setWhichPlayerPlaced(-99);
                buttons[i][j].setIcon(emptyCell);
            }
        }
        try {
            LoadHighscores(records);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.setVisible(false);
        GUI newGame = new GUI();
    }


    public void warningMessage()
    {
        JOptionPane.showMessageDialog(null,
                "Cell is not available", "Invalid move",
                JOptionPane.WARNING_MESSAGE);
    }


    public void emptyUpperCell(int rowPos, int columnPos)
    {
        try {
            gameBoard[rowPos - 1][columnPos].setWhichPlayerPlaced(0);
        } catch (Exception ex) {
        }
    }


    public void Wingame(String playername)
    {
        if (amountOfPlayers == 1)
        {
            for (int i = 1; i <= pvcCount; i++)
            {
                records.add("Game Number:" + i + " Winner: " + playername + ", Time: Minutes:" + minute + " Seconds:" + sec);
            }
        }
        else if (amountOfPlayers == 2)
        {
            for (int i = 1; i <= pvpCount; i++)
            {
                records.add("Game Number:" + i + " Winner: " + playername + ", Time: Minutes:" + minute + " Seconds:" + sec);
            }
        }

        String Scores = "";
        for (int i = 0; i < records.size(); i++) {
            Scores = (records.get(i) + "\n");
        }
        FileWriter saving = null;

        if (amountOfPlayers == 1)
        {
            try {
                saving = new FileWriter("src/PvCHighscore.txt",true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (amountOfPlayers == 2)
        {
            try {
                saving = new FileWriter("src/PvPHighscore.txt",true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            saving.write(Scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            saving.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(records);
    }


    public void LoadHighscores(ArrayList<String> Highscores) throws FileNotFoundException
    {
        Scanner comupter = new Scanner(new FileReader("src/PvCHighscore.txt"));
        Scanner player = new Scanner(new FileReader("src/PvPHighscore.txt"));
        if (amountOfPlayers == 1)
        {
            try {
                while (comupter.hasNext()){
                    records.add(comupter.next());
                }
                comupter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (amountOfPlayers == 2)
        {
            try {
                while (player.hasNext()){
                    records.add(player.next());
                }
                player.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void moveComputer()
    {
        int l, m;
        boolean placed = false;
        for (l = size - 1; (l >= 0) && !placed; l--)
        {
            for (m = 0; (m < size) && !placed; m++)
            {
                int n = (int)(Math.random()*size);
                if (gameBoard[n][m].getWhichPlayerPlaced() == 0)
                {
                    buttons[n][m].setIcon(player2);
                    gameBoard[n][m].setWhichPlayerPlaced(2);
                    checkWinner(2);
                    placed = true;
                    emptyUpperCell(n, m);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(timer))
        {
           sec += 1;

            if (sec == 60)
            {
                minute += 1;
                sec = 0;
            }
        }

            int drawCheck = size * size;
            if (amountOfPlayers == 1)
            {
                try
                {
                    for (int i = size - 1; i >= 0; i--)
                    {
                        for (int j = 0; j <= size - 1; j++)
                        {
                            if (buttons[i][j] == e.getSource())
                            {
                                if (count % 2 == 0)
                                {
                                    for (int k = 0; k <= size; i++)
                                    {
                                        if (gameBoard[i - k][j].getWhichPlayerPlaced() == 0)
                                        {
                                            //playSound.placedSound();
                                            buttons[i - k][j].setIcon(player1);
                                            gameBoard[i - k][j].setWhichPlayerPlaced(1);
                                            checkWinner(1);
                                            break;
                                        }
                                    }
                                    emptyUpperCell(i, j);
                                    count++;
                                    break;
                                }
                                if (count % 2 == 1)
                                {
                                    moveComputer();
                                    //playSound.placedSound();
                                    count++;
                                    break;
                                } else {
                                    warningMessage();
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    warningMessage();
                }
            }
            else if (amountOfPlayers == 2)
            {
                try
                {
                    int placed = 0;
                    for (int i = size - 1; i >= 0; i--)
                    {
                        for (int j = 0; j <= size - 1; j++)
                        {
                            if (placed == 0 && buttons[i][j] == e.getSource())
                            {
                                if (count % 2 == 0)
                                {
                                    for (int k = 0; k <= size; i++)
                                    {
                                        if (gameBoard[i - k][j].getWhichPlayerPlaced() == 0 && count % 2 == 0)
                                        {
                                           // playSound.placedSound();
                                            buttons[i - k][j].setIcon(player1);
                                            gameBoard[i - k][j].setWhichPlayerPlaced(1);
                                            checkWinner(1);
                                            placed = 1;
                                            break;
                                        }
                                    }
                                    emptyUpperCell(i, j);
                                    count++;
                                    break;
                                }
                                if (count % 2 == 1)
                                {
                                    for (int k = 0; k <= size; i++)
                                    {
                                        if (gameBoard[i - k][j].getWhichPlayerPlaced() == 0 && count % 2 == 1)
                                        {
                                            //playSound.placedSound();
                                            buttons[i - k][j].setIcon(player2);
                                            gameBoard[i - k][j].setWhichPlayerPlaced(2);
                                            checkWinner(2);
                                            placed = 1;
                                            break;
                                        }
                                    }
                                    emptyUpperCell(i, j);
                                    count++;
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    warningMessage();
                }
            }
            if (count == drawCheck)
            {
                timer.stop();
                Wingame("Draw");
                JOptionPane.showMessageDialog(null,
                        "It is a draw!",
                        "Game finished",
                        JOptionPane.INFORMATION_MESSAGE);
                playAgain();
            }
    }
}