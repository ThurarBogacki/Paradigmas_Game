// package paradigmas.gauchovoador;
package com.paradigmasgame.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;

public class EndScreen implements Screen {
    private final Paradigmas_Game game;
    private final GlyphLayout layout;
    private final Sprite resultImage;

    public enum Result {
        LOSE,
        WIN,
    }

    public EndScreen(final Paradigmas_Game game, Result result, int score) {
        this.game = game;

        if (result == Result.LOSE) {
            resultImage = new Sprite(new Texture("img/you-lose.png"));
        } else if (result == Result.WIN) {
            resultImage = new Sprite(new Texture("img/you-win.png"));
        } else {
            throw new IllegalStateException();
        }
        resultImage.setSize(resultImage.getWidth() * 2/ 3f, resultImage.getHeight() * 2 / 3f);
        resultImage.setCenter(Paradigmas_Game.WORLD_WIDTH / 2f, Paradigmas_Game.WORLD_HEIGHT * 3 / 5f);

        layout = new GlyphLayout(game.font, String.format("Pontuação: %d", score), Color.YELLOW, 0, Align.center, false);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new HomeScreen(game));
            }
        }, 6);
    }

    @Override
    public void render(float delta) {
        game.updateCoordinates();

        ScreenUtils.clear(Color.DARK_GRAY);

        game.batch.begin();
        resultImage.draw(game.batch);
        game.font.draw(game.batch, layout, Paradigmas_Game.WORLD_WIDTH / 2f, Paradigmas_Game.WORLD_HEIGHT / 5f);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) { game.viewport.update(width, height); }

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