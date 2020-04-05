package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl;
import cs3500.marblesolitaire.view.IView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

public class MyGdxGame extends ApplicationAdapter {
	private MarbleSolitaireModel mm;
	private IView vw;
	private OrthographicCamera cam;
	
	@Override
	public void create () {
		 mm = new MarbleSolitaireModelImpl();
		vw  = new MarbleSolitaireView(mm);
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		vw.displayView();
	}
	
	@Override
	public void dispose () {
		vw.shapeRendDispose();
	}
}
