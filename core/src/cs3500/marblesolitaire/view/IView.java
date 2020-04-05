package cs3500.marblesolitaire.view;

import java.util.HashMap;

import javafx.util.Pair;

public interface IView {
    void displayView();

    void shapeRendDispose();

    HashMap<Integer, Pair<Integer,Integer>> getMap();
}
