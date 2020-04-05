package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * This interface represents the operations offered by the marble solitaire controller. One object
 * of the Controller represents one controller of marble solitaire
 */
public interface MarbleSolitaireController {
  /**
   * Method that plays a new game of Marble Solitaire using the provided model.
   *
   * @param model an object of MarbleSolitaireModel
   * @throws IllegalArgumentException if passed in null model
   * @throws IllegalStateException    if the controller is unable to successfully receive input or
   *                                  transmit output.
   */
  void playGame(MarbleSolitaireModel model);
}
