// package paradigmas.gauchovoador;
package com.paradigmasgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    private final Paradigmas_Game game;
    private final Bagualo bagualo;
    private final Quiz quiz;
    private OptionCircles optionCircles;
    private final Texture backgroundTexture;
    private final Sound correctSound;
    private final Sound wrongSound;
    private final Music soundtrack;

    public GameScreen(final Paradigmas_Game game) {
        this.game = game;
        quiz = new Quiz();
        bagualo = new Bagualo(3);
        backgroundTexture = new Texture("img/quarta-bg.png");

        correctSound = Gdx.audio.newSound(Gdx.files.internal("audio/correct-sfx.ogg"));
        wrongSound = Gdx.audio.newSound(Gdx.files.internal("audio/wrong-sfx.ogg"));
        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("audio/soundtrack.ogg"));
        soundtrack.setLooping(true);
        soundtrack.play();
    }

    @Override
    public void render(float delta) {
        game.updateCoordinates();
        advanceLogic();

        ScreenUtils.clear(Color.BLACK);

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0);
        game.batch.end();

        optionCircles.renderCircles();

        game.batch.begin();
        bagualo.render(game.batch, game.font);
        optionCircles.renderText(game.batch, game.font);
        game.batch.end();
    }


    private void advanceLogic() {
        game.updateCoordinates();

        if (!quiz.hasNext()) {
            endgame(EndScreen.Result.WIN);
        }

        if ((optionCircles == null || optionCircles.allOutOfBounds())) {
            optionCircles = new OptionCircles(quiz.next(), 2.8f);
        }

        optionCircles.update();
        bagualo.update();

        OptionCircles.IntersectionWithCircles intersectionResult = optionCircles.hitsAnyCircle(bagualo.getBoundingRectangle());
        switch (intersectionResult) {
            case NO:
                break;
            case YES_WRONG:
                wrongSound.play();
                optionCircles.setActive(false);
                bagualo.decreaseLive();
                if (bagualo.getLives() == 0) {
                    endgame(EndScreen.Result.LOSE);
                }
                break;
            case YES_CORRECT:
                correctSound.play();
                optionCircles.setActive(false);
                bagualo.increaseScore(5);
                break;
        }
    }

    void endgame(EndScreen.Result result) {
        soundtrack.stop();
        game.setScreen(new EndScreen(game, result, bagualo.getScore()));
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
    public void dispose() {
        correctSound.dispose();
        wrongSound.dispose();
        soundtrack.dispose();
    }
}