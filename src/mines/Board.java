package mines;

import java.awt.Graphics;
import java.awt.Image;
import java.security.SecureRandom;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

// and changed here the indentifier for the variables from priavte to public 
public class Board extends JPanel {
    private static final long serialVersionUID = 6195235521361212179L;

    // chnaged the variable names
    public final int Num_images = 13;
    public final int Cell_size = 15;

    public final int Cover_for_cell = 10;
    public final int Mark_for_cell = 10;
    public final int Empty_cell = 0;
    public final int Mine_cell = 9;
    public final int Covered_Mine_cell = Mine_cell + Cover_for_cell;
    public final int Marked_Mine_cell = Covered_Mine_cell + Mark_for_cell;

    public final int Draw_mine = 9;
    public final int Draw_cover = 10;
    public final int Draw_mark = 11;
    public final int Draw_wrong_mark = 12;

    public int[] field;
    public boolean inGame;
    public int mines_left;
    public Image[] img;
    public final int mines = 40;
    public final int rows = 16;
    public final int cols = 16;
    public int all_cells;
    public JLabel statusbar;

    public Board(JLabel statusbar) {

        this.statusbar = statusbar;

        img = new Image[Num_images];

        for (int i = 0; i < Num_images; i++) {
            img[i] = (new ImageIcon(getClass().getClassLoader().getResource((i)
                    + ".gif"))).getImage();
        }

        setDoubleBuffered(true);

        addMouseListener(new MinesAdapter(this));
        newGame();

    }

    // changed the complexity of the function new game to reduce to 15 and makeing
    // the if blocks more readable
    public void newGame() {
        // we changed Random to SecureRandom to make it more Secure and better random
        // generator
        SecureRandom random = new SecureRandom();
        int i = 0;
        int position;
        int cell;

        inGame = true;
        mines_left = mines;
        all_cells = rows * cols;
        field = new int[all_cells];
        for (i = 0; i < all_cells; i++) {
            field[i] = Cover_for_cell;
        }
        statusbar.setText(Integer.toString(mines_left));

        i = 0;
        while (i < mines) {
            position = random.nextInt(all_cells);
            if (field[position] != Covered_Mine_cell) {
                field[position] = Covered_Mine_cell;
                i++;

                int current_col = position % cols;
                int start_col = Math.max(0, current_col - 1);
                int end_col = Math.min(cols - 1, current_col + 1);
                int start_row = Math.max(0, position / cols - 1);
                int end_row = Math.min(rows - 1, position / cols + 1);

                for (int row = start_row; row <= end_row; row++) {
                    for (int col = start_col; col <= end_col; col++) {
                        cell = row * cols + col;
                        if (field[cell] != Covered_Mine_cell) {
                            field[cell]++;
                        }
                    }
                }
            }
        }
    }

    // we changed the if blocks to reduce complexity and to make it more readable
    public void find_Empty_cells(int j) {
        int current_col = j % cols;
        int cell;

        int[] offsets = { -cols - 1, -1, cols - 1, -cols, cols, -cols + 1, cols + 1, 1 };

        for (int offset : offsets) {
            cell = j + offset;
            if (isValidCell(cell) && field[cell] > Mine_cell) {
                field[cell] -= Cover_for_cell;
                if (field[cell] == Empty_cell) {
                    find_Empty_cells(cell);
                }
            }
        }
    }

    private boolean isValidCell(int cell) {
        return cell >= 0 && cell < all_cells;
    }

    // reduced the cognitive complexity from 26 to 15 in the method paint
    public void paint(Graphics g) {
        int uncover = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int cell = field[(i * cols) + j];

                if (inGame && cell == Mine_cell)
                    inGame = false;

                if (!inGame) {
                    cell = processEndGameCell(cell);
                } else {
                    cell = processOngoingGameCell(cell);
                    if (cell > Mine_cell)
                        uncover++;
                }

                g.drawImage(img[cell], (j * Cell_size), (i * Cell_size), this);
            }
        }

        updateGameStatus(uncover);
    }

    private int processEndGameCell(int cell) {
        if (cell == Covered_Mine_cell) {
            return Draw_mine;
        } else if (cell == Marked_Mine_cell) {
            return Draw_mark;
        } else if (cell > Covered_Mine_cell) {
            return Draw_wrong_mark;
        } else if (cell > Mine_cell) {
            return Draw_cover;
        }
        return cell;
    }

    private int processOngoingGameCell(int cell) {
        if (cell > Covered_Mine_cell) {
            return Draw_mark;
        } else if (cell > Mine_cell) {
            return Draw_cover;
        }
        return cell;
    }

    private void updateGameStatus(int uncover) {
        if (uncover == 0 && inGame) {
            inGame = false;
            statusbar.setText("Game won");
        } else if (!inGame) {
            statusbar.setText("Game lost");
        }
    }

}