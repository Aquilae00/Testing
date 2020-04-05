package cs3500.marblesolitaire.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;

import cs3500.marblesolitaire.model.hw02.Coding;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import javafx.util.Pair;


public class MarbleSolitaireView implements IView {
    ShapeRenderer sr;
    MarbleSolitaireModel mm;
    HashMap<Integer, Pair<Integer,Integer>> hmap;

    public MarbleSolitaireView(MarbleSolitaireModel mm) {
        this.mm = mm;
        this.sr = new ShapeRenderer();
        hmap = new HashMap<Integer, Pair<Integer,Integer>>();
    }

    @Override
    public void displayView() {
        Coding[][] board_temp = mm.getBoard();
//        StringBuilder sb = new StringBuilder(mm.getGameState());
//        for (int i = 0; i < sb.length(); i++) {
//            if (sb.charAt(i) == ' ') {
//                sr.begin(ShapeRenderer.ShapeType.Filled);
//                sr.setColor(Color.GREEN);
//                sr.circle(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 64);
//                sr.end();
//            }
//        }
        for (int x = 0; x < mm.getWidth(); ++x) {
            for (int y = 0; y < mm.getHeight(); ++y) {
                switch (board_temp[x][y]) {
                    case Invalid:

                        break;
                    case Marbles:
                        hmap.put(x + y,new Pair(Gdx.graphics.getWidth() * 3 / 4 - (x * 100),Gdx.graphics.getHeight() * 3 / 4 - (y * 100)));
                        sr.begin(ShapeRenderer.ShapeType.Filled);
                        sr.setColor(Color.GREEN);
                        sr.circle(Gdx.graphics.getWidth() * 3 / 4 - (x * 100), Gdx.graphics.getHeight() * 3 / 4 - (y * 100), 40);
                        sr.end();
                        break;
                    case Empty:
                        hmap.put(x + y,new Pair(Gdx.graphics.getWidth() * 3 / 4 - (x * 100),Gdx.graphics.getHeight() * 3 / 4 - (y * 100)));
                        sr.begin(ShapeRenderer.ShapeType.Filled);
                        sr.setColor(Color.BLACK);
                        sr.circle(Gdx.graphics.getWidth() * 3 / 4 - (x * 100), Gdx.graphics.getHeight() * 3 / 4 - (y * 100), 40);
                        sr.end();
                        break;
                    default:
                }
            }
        }
    }

    @Override
    public void shapeRendDispose() {
        sr.dispose();
    }

    @Override
    public HashMap<Integer, Pair<Integer, Integer>> getMap() {
        return this.hmap;
    }
}
