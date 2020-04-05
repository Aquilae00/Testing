package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.Coding;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Class abstraction for Solitaire Model for Objects of MarbleSolitaireModel.
 */
public abstract class AbstractSolitaireModel implements MarbleSolitaireModel {
  protected Coding[][] board_state;
  protected int arm;
  protected int srow;
  protected int scol;
  protected int front_gap;
  protected int width;
  protected int last_slot;
  protected int v_gap;
  protected int height;
  //Variant and invariant are for determining the pyramid edges
  protected int variant;
  protected int invariant;
  protected static final int DEFAULT_ARM = 3;
  protected static final int DEFAULT_SROW = 3;
  protected static final int DEFAULT_SCOL = 3;

  /**
   * Given the from row, from col, to row, to col positions, determine if all the arguments are
   * pointing to invalid location and illegal move. The first 4 condition make sure the positions
   * are not negative. The (5-8) conditions make sure the positions don't go out of bound. The
   * (9-12) make sure the positions are not outside the main board with marbles. The (13) checks for
   * diagonal move. The (14) checks for empty slot at destination position. The (15) checks for
   * marbles at original positions. (16-17) checks for distance more than 2.
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow   the row number of the position to be moved to (starts at 0)
   * @param toCol   the column number of the position to be moved to (starts at 0)
   * @return true if the positions are invalid, false if positions are valid
   */
  private boolean isInvalidMove(int fromRow, int fromCol, int toRow, int toCol) {
    int jumped_over_v = (toCol + fromCol) / 2;
    int jumped_over_h = (toRow + fromRow) / 2;
    return toRow < 0
            || toCol < 0
            || fromCol < 0
            || fromRow < 0
            || toRow > height - 1
            || toCol > width - 1
            || fromCol > width - 1
            || fromRow > height - 1
            || Math.abs(toRow - fromRow) >= 1 && Math.abs(toCol - fromCol) >= 1
            || this.board_state[toRow][toCol] != Coding.Empty
            || this.board_state[fromRow][fromCol] != Coding.Marbles
            || Math.abs(toRow - fromRow) != 2 && Math.abs(toRow - fromRow) != 0
            || Math.abs(toCol - fromCol) != 2 && Math.abs(toCol - fromCol) != 0
            || this.board_state[fromRow][jumped_over_v] != Coding.Marbles
            || this.board_state[jumped_over_h][fromCol] != Coding.Marbles;
  }

  /**
   * Move a single marble from a given position to another given position. A move is valid only if
   * the from and to positions are valid. Specific implementations may place additional constraints
   * on the validity of a move.
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow   the row number of the position to be moved to (starts at 0)
   * @param toCol   the column number of the position to be moved to (starts at 0)

   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    int jumped_over_v = (toCol + fromCol) / 2;
    int jumped_over_h = (toRow + fromRow) / 2;

    if (isInvalidMove(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException(String.format("Invalid empty cell position (%s,%s)",
              toRow, toCol));
    }

    if (this.board_state[fromRow][fromCol] == Coding.Marbles
            && Math.abs(toRow - fromRow) <= 2
            && Math.abs(toCol - fromCol) <= 2
            && this.board_state[toRow][toCol] == Coding.Empty) {
      if (toRow == fromRow) {
        this.board_state[toRow][jumped_over_v] = Coding.Empty;
      }
      if (toCol == fromCol) {
        this.board_state[jumped_over_h][toCol] = Coding.Empty;
      }
      this.board_state[fromRow][fromCol] = Coding.Empty;
      this.board_state[toRow][toCol] = Coding.Marbles;
    }
  }

  @Override
  public boolean isGameOver() {
    boolean condition = true;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (board_state[x][y] == Coding.Marbles) {
          condition = condition && !(x < width - 2
                  && board_state[x + 2][y] == Coding.Empty
                  && board_state[x + 1][y] == Coding.Marbles);
          condition = condition && !(x > 1
                  && board_state[x - 2][y] == Coding.Empty
                  && board_state[x - 1][y] == Coding.Marbles);
          condition = condition && !(y < height - 2
                  && board_state[x][y + 2] == Coding.Empty
                  && board_state[x][y + 1] == Coding.Marbles);
          condition = condition && !(y > 1
                  && board_state[x][y - 2] == Coding.Empty
                  && board_state[x][y - 1] == Coding.Marbles);
        }
      }
    }
    return condition;
  }

  /**
   * Initialize the board in the form of Coding Enumerations.
   * <table>
   * <thead>
   * <th>Number</th><th>String</th>
   * </thead>
   * <tr><td>Invalid</td><td>Represents " "</td></tr>
   * <tr><td>Marbles</td><td>Represents "O"</td></tr>
   * <tr><td>Empty</td><td>Represents "_"</td></tr>
   * <tr><td>OutOfBound</td><td>Represents "" (Nothing)</td></tr>
   * </table>
   *
   * <p>
   * Version change: Added the use of variant and invariant. The purpose is to give the model the
   * ability to customize the board with respect to model type, constructs the board with
   * diminishing number of edges for European and Triangle Edges
   * </p>
   * @return the board in form of Coding enumeration
   */
  protected Coding[][] initBoard() {
    int original_invariant = this.invariant;
    Coding[][] board = new Coding[height][width];
    for (int y = 0; y < height; ++y) {

      for (int x = 0; x < width; ++x) {
        if ((x >= front_gap - (variant % invariant))
                && x < last_slot + (variant % invariant)) {
          board[y][x] = Coding.Marbles;
        } else if (x < front_gap) {
          board[y][x] = Coding.Invalid;
        } else if (x >= last_slot) {
          board[y][x] = Coding.OutofBound;
        }
      }
      if (y < v_gap - 1) {
        variant++;
      } else if (y >= height - v_gap - 1) {
        invariant = original_invariant;
        variant = Math.abs(variant - 1);
      } else {
        variant = v_gap;
        invariant = this.arm;
      }
    }
    if (board[srow][scol] == Coding.Invalid
            || board[srow][scol] == Coding.OutofBound) {
      throw new IllegalArgumentException("Invalid Position");
    } else {
      board[this.srow][this.scol] = Coding.Empty;
    }
    return board;
  }

  @Override
  public String getGameState() {
    StringBuilder string_board = new StringBuilder();
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        switch (this.board_state[x][y]) {
          case Invalid:
            string_board.append("  ");
            break;
          case Marbles:
            if (y < width - 1 && (this.board_state[x][y + 1] == Coding.Marbles
                    || this.board_state[x][y + 1] == Coding.Empty)) {
              string_board.append("O ");
            } else {
              string_board.append("O");
            }
            break;
          case Empty:
            if (y < width - 1 && (this.board_state[x][y + 1] == Coding.Marbles
                    || this.board_state[x][y + 1] == Coding.Empty)) {
              string_board.append("_ ");
            } else {
              string_board.append("_");
            }
            break;
          default:
        }
      }
      if (x < height - 1) {
        string_board.append("\n");
      }
    }
    return string_board.toString();
  }

  @Override
  public int getScore() {
    int score = 0;
    for (Coding[] iArray : this.board_state) {
      for (Coding c : iArray) {
        if (c == Coding.Marbles) {
          score += 1;
        }
      }
    }
    return score;
  }

  @Override
  public Coding[][] getBoard() {
    return this.board_state;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }
}
