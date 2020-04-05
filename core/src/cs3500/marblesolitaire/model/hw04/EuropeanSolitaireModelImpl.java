package cs3500.marblesolitaire.model.hw04;

/**
 * Class Implementation of MarbleSolitaireModel.
 */
public class EuropeanSolitaireModelImpl extends AbstractSolitaireModel {

  /**
   * Constructs a EuropeanSolitaireModel using default values.
   */
  public EuropeanSolitaireModelImpl() {
    this(DEFAULT_ARM, DEFAULT_SROW, DEFAULT_SCOL);
  }

  /**
   * Constructs a European Solitaire Model and initialize a game board with the empty slot at the
   * center.
   *
   * @param armLength the arm thickness
   * @throws IllegalArgumentException if the arm thickness is not a positive odd number
   */
  public EuropeanSolitaireModelImpl(int armLength) {
    this(armLength, DEFAULT_SROW, DEFAULT_SCOL);
  }

  /**
   * Constructs a European Solitaire Model and initialize the game board so that the arm thickness
   * is 3 and the empty slot is at the position (sRow, sCol).
   *
   * @param row the y axis of empty slot
   * @param col the x axis of empty slot
   * @throws IllegalArgumentException if position is invalid
   */
  public EuropeanSolitaireModelImpl(int row, int col) {
    this(DEFAULT_ARM, row, col);
  }

  /**
   * Constructs a European Solitaire Model take in the arm thickness, row and column of the empty
   * slot in that order.
   *
   * @param armLength the arm thickness
   * @param row       the y axis of empty slot
   * @param col       the x axis of empty slot
   * @throws IllegalArgumentException if the arm thickness is not a positive odd number, or the
   *                                  empty cell position is invalid
   */
  public EuropeanSolitaireModelImpl(int armLength, int row, int col) {
    if (armLength < 0
            || armLength % 2 == 0
            || row < 0
            || col < 0) {
      throw new IllegalArgumentException("Invalid Input");
    }

    this.arm = armLength;
    front_gap = this.arm - 1;
    width = 3 * this.arm - 2;
    last_slot = width - front_gap;
    variant = 0;
    invariant = this.arm;

    v_gap = this.arm - 1;
    height = v_gap * 2 + this.arm;
    this.srow = row;
    this.scol = col;
    this.board_state = initBoard();

  }
}
