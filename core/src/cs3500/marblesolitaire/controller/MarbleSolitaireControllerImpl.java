package cs3500.marblesolitaire.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Class implementation of MarbleSolitaireController. An object of MarbleSolitaireController that
 * represents a controller for a game.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
  private final Readable rd;
  private final Appendable ap;

  /**
   * Constructs a controller by taking in Readable and Appendable Readable and Appendable are two
   * existing interfaces in Java that abstract input and output respectively.
   *
   * @param rd Interface in java that abstract input
   * @param ap Interface in java that abstract output
   * @throws IllegalArgumentException if Readable or Appendable is null
   */
  public MarbleSolitaireControllerImpl(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Constructor Error");
    }
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * Method that gives out appropriate messages given the {@linkplain Output}.
   * <table>
   * <thead>
   * <tr><th>Field</th><th>Message</th></tr>
   * </thead>
   * <tr><td>Quit</td><td>Gives out Message for when the user quits the game</td></tr>
   * <tr><td>Over</td><td>Gives out message for when the game is over</td></tr>
   * </table>
   *
   * @param o     A static field of enumeration class {@linkplain Output}
   * @param model An object of MarbleSolitaireModel
   */
  private void outputCondition(Output o, MarbleSolitaireModel model) {
    try {
      switch (o) {
        case Quit:
          this.ap.append("Game quit!" + "\n");
          this.ap.append("State of game when quit:" + "\n");
          this.ap.append(model.getGameState() + "\n");
          this.ap.append(String.format("Score: %s", model.getScore()) + "\n");
          break;
        case Over:
          this.ap.append("Game over!\n");
          this.ap.append(model.getGameState() + "\n");
          this.ap.append(String.format("Score: %s", model.getScore()) + "\n");
          break;
        default:
      }
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  /**
   * Removed if condition asking for game over. Moved if condition for quit game input to
   * NumberFormatException
   *
   * @param model an object of MarbleSolitaireModel
   */
  @Override
  public void playGame(MarbleSolitaireModel model) {
    try {
      if (model == null) {
        throw new IllegalArgumentException("Model can't be null");
      }
      int[] arr = new int[4];
      String scan1;
      Scanner scan = new Scanner(this.rd);
      while (!model.isGameOver()) {
        this.ap.append(model.getGameState() + "\n");
        this.ap.append(String.format("Score: %s", model.getScore()) + "\n");
        for (int i = 0; i < arr.length; )
        {
          scan1 = scan.next();
          try {
            if (Integer.parseInt(scan1) < 0) {
              throw new IllegalArgumentException();
            }
            arr[i] = Integer.parseInt(scan1);
            i++;
          } catch (NumberFormatException n) {
            if (scan1.equals("Q") || scan1.equals("q")) {
              outputCondition(Output.Quit, model);
              return;
            } else {
              this.ap.append("Invalid input. Enter a new input\n");
            }
          } catch (IllegalArgumentException e) {
            this.ap.append("Invalid input. Enter a new input\n");
          }
        }
        try {
          model.move(arr[0] - 1, arr[1] - 1, arr[2] - 1, arr[3] - 1);
        } catch (IllegalArgumentException a) {
          this.ap.append(String.format("Invalid move. Play again. %s", a) + "\n");
        }
      }
      outputCondition(Output.Over, model);
    } catch (IOException e) {
      throw new IllegalStateException();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException();
    }
  }
}