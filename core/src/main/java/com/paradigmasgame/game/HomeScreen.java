// package paradigmas.gauchovoador;
package com.paradigmasgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class HomeScreen implements Screen {
    private final Paradigmas_Game game;
    private final Sprite logo;
    private final Sprite playButton;
    private final Sound clickSound;

    public HomeScreen(final Paradigmas_Game game) {
        this.game = game;

        logo = new Sprite(new Texture("img/logo.png"));
        logo.setSize(logo.getWidth() / 4f, logo.getHeight() / 4f);
        logo.setCenter(Paradigmas_Game.WORLD_WIDTH / 2f, Paradigmas_Game.WORLD_HEIGHT * 4 / 5f);

        playButton = new Sprite(new Texture("img/play.png"));
        playButton.setSize(playButton.getWidth() / 3f, playButton.getHeight() / 3f);
        playButton.setCenter(Paradigmas_Game.WORLD_WIDTH / 2f, Paradigmas_Game.WORLD_HEIGHT /3f);

        clickSound = Gdx.audio.newSound(Gdx.files.internal("audio/correct-sfx.ogg"));
    }

    @Override
    public void render(float delta) {
        game.updateCoordinates();

        checkButtonPress();

        ScreenUtils.clear(Color.DARK_GRAY);

        game.batch.begin();
        logo.draw(game.batch);
        playButton.draw(game.batch);
        game.batch.end();
    }

    void checkButtonPress() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Rectangle rect = playButton.getBoundingRectangle();
            if (Paradigmas_Game.worldCoordinates.x > rect.x && Paradigmas_Game.worldCoordinates.x < rect.x + rect.width &&
                    Paradigmas_Game.worldCoordinates.y > rect.y && Paradigmas_Game.worldCoordinates.y < rect.y + rect.height) {
                clickSound.play();
                game.setScreen(new GameScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
    }

    @Override
    public void show() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }
}