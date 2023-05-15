package mines;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// separte the MinesAdapter from Board class to understand better the relation between each other and modifier
// the identifier for some varibales in Board class 
// and added the override to the eventhundeler
public class MinesAdapter extends MouseAdapter {

    private Board board;

    public MinesAdapter(Board board) {
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        int cCol = x / board.Cell_size;
        int cRow = y / board.Cell_size;

        boolean rep = false;

        if (!board.inGame) {
            board.newGame();
            board.repaint();
        }

        if ((x < board.cols * board.Cell_size) && (y < board.rows * board.Cell_size)) {

            if (e.getButton() == MouseEvent.BUTTON3) {

                if (board.field[(cRow * board.cols) + cCol] > board.Mine_cell) {
                    rep = true;

                    if (board.field[(cRow * board.cols) + cCol] <= board.Covered_Mine_cell) {
                        if (board.mines_left > 0) {
                            board.field[(cRow * board.cols) + cCol] += board.Mark_for_cell;
                            board.mines_left--;
                            board.statusbar.setText(Integer.toString(board.mines_left));
                        } else
                            board.statusbar.setText("No marks left");
                    } else {

                        board.field[(cRow * board.cols) + cCol] -= board.Mark_for_cell;
                        board.mines_left++;
                        board.statusbar.setText(Integer.toString(board.mines_left));
                    }
                }

            } else {

                if (board.field[(cRow * board.cols) + cCol] > board.Covered_Mine_cell) {
                    return;
                }

                if ((board.field[(cRow * board.cols) + cCol] > board.Mine_cell) &&
                        (board.field[(cRow * board.cols) + cCol] < board.Marked_Mine_cell)) {

                    board.field[(cRow * board.cols) + cCol] -= board.Cover_for_cell;
                    rep = true;

                    if (board.field[(cRow * board.cols) + cCol] == board.Mine_cell)
                        board.inGame = false;
                    if (board.field[(cRow * board.cols) + cCol] == board.Empty_cell)
                        board.find_Empty_cells((cRow * board.cols) + cCol);
                }
            }

            if (rep)
                board.repaint();

        }
    }
}
