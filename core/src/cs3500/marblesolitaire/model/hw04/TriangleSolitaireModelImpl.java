package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.Coding;

/**
 * Class Implementation of MarbleSolitaireModel.
 */
public class TriangleSolitaireModelImpl extends AbstractSolitaireModel {

  /**
   * Creates a TriangleSolitaireModel using default values.
   */
  public TriangleSolitaireModelImpl() {
    this(5, 0, 0);
  }

  /**
   * Constructs a Triangular Marble solitaire that takes in dimension.
   *
   * @param dimension number of slots in the bottom-most row
   */
  public TriangleSolitaireModelImpl(int dimension) {
    this(dimension, 0, 0);
  }

  /**
   * Constructs a Triangular Marble solitaire that takes in empty slot at the specified row and
   * col.
   *
   * @param row the y axis of empty slot
   * @param col the x axis of empty slot
   */
  public TriangleSolitaireModelImpl(int row, int col) {
    this(5, row, col);
  }

  /**
   * Constructs a Triangular marble solitaire that takes in specified dimension and an empty slot at
   * the specified row and column.
   *
   * @param dimension number of slots in the bottom-most row
   * @param row       the y axis of empty slot
   * @param col       the x axis of empty slot
   * @throws IllegalArgumentException if the dimension is non-positive or the position is invalid
   */
  public TriangleSolitaireModelImpl(int dimension, int row, int col) {
    //Arm = Dimension
    this.arm = dimension;
    front_gap = this.arm - 1;
    width = this.arm;
    last_slot = width - front_gap;
    variant = 0;
    invariant = this.arm;
    v_gap = this.arm - 1;
    height = this.arm;
    if (dimension < 0
            || row < 0
            || col < 0
            || row >= dimension
            || col >= dimension) {
      throw new IllegalArgumentException("Invalid Input");
    }
    this.srow = row;
    this.scol = col;
    this.board_state = initBoard();

  }

  /**
   * The difference with AbstractSolitaireModel is that TriangleSolitaireModel only takes a quarter
   * of the abstract board.
   *
   * @return the board in form of Coding enumeration
   */
  @Override
  protected Coding[][] initBoard() {
    Coding[][] board = new Coding[height][width];
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        if (x < last_slot + (variant % invariant)) {
          board[y][x] = Coding.Marbles;
        } else if (x >= last_slot) {
          board[y][x] = Coding.OutofBound;
        }
      }
      variant++;
    }
    if (board[srow][scol] == Coding.Invalid
            || board[srow][scol] == Coding.OutofBound) {
      throw new IllegalArgumentException("Invalid Position");
    } else {
      board[this.srow][this.scol] = Coding.Empty;
    }
    return board;
  }

  /**
   * The difference with AbstractModel is that the Triangle Model getGameState needs to add spaces
   * in front of initial marbles.
   *
   * @return the game state as a string
   */
  @Override
  public String getGameState() {
    StringBuilder string_board = new StringBuilder();
    int num1;
    int num2 = width - last_slot + (variant % invariant);
    for (int x = 0; x < width; ++x) {
      num1 = num2;
      for (int y = 0; y < height; ++y) {
        switch (this.board_state[x][y]) {
          case Invalid:
            string_board.append(" ");
            break;
          case Marbles:
            while (num1 != 0) {
              string_board.append(" ");
              num1--;
            }
            if (y < width - 1 && (this.board_state[x][y + 1] == Coding.Marbles
                    || this.board_state[x][y + 1] == Coding.Empty)) {
              string_board.append("O ");
            } else {
              string_board.append("O");
            }
            break;
          case Empty:
            while (num1 != 0) {
              string_board.append(" ");
              num1--;
            }
            if (y < width - 1 && (this.board_state[x][y + 1] == Coding.Marbles
                    || this.board_state[x][y + 1] == Coding.Empty)) {
              string_board.append("_ ");
            } else {
              string_board.append("_");
            }
            break;
          default:
            break;
        }
      }
      num2--;
      if (x < height - 1) {
        string_board.append("\n");
      }
    }
    return string_board.toString();
  }

  /**
   * The difference with isInvalidMove in Abstract model is that the triangle model allows
   * simultaneous movement of distance two for row and column, thus the constraint for movement is
   * also different.
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow   the row number of the position to be moved to (starts at 0)
   * @param toCol   the column number of the position to be moved to (starts at 0)
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
            || Math.abs(toRow - fromRow) > 2 && Math.abs(toCol - fromCol) > 2
            || this.board_state[toRow][toCol] != Coding.Empty
            || this.board_state[fromRow][fromCol] != Coding.Marbles
            || Math.abs(toRow - fromRow) != 2 && Math.abs(toRow - fromRow) != 0
            || Math.abs(toCol - fromCol) != 2 && Math.abs(toCol - fromCol) != 0
            //added
            || board_state[jumped_over_h][jumped_over_v] != Coding.Marbles;
  }

  /**
   * The only difference with the move method in abstract model is added condition for where the
   * distance of toRow and toCol are 2 to the original positions.
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
      //added
      if (Math.abs(toCol - fromCol) == 2 && Math.abs(toRow - fromRow) == 2) {
        this.board_state[jumped_over_h][jumped_over_v] = Coding.Empty;
      }
      this.board_state[fromRow][fromCol] = Coding.Empty;
      this.board_state[toRow][toCol] = Coding.Marbles;
    }
  }

  /**
   * Difference with the abstract model isGameOver method is added condition for when distance
   * difference is 2 for both row and col.
   *
   * @return true if the game is over, false otherwise
   */
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
          //added
          condition = condition && !(y > 1 && x > 1
                  && board_state[x - 2][y - 2] == Coding.Empty
                  && board_state[x - 1][y - 1] == Coding.Marbles);
        }
      }
    }
    return condition;
  }
}
