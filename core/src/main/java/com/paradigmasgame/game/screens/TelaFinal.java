package com.Jardim.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TelaFinal extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background;
    private Texture fimTexture;
    private Texture resultadoTexture;
    private Stage stage;
    private Game game;
    private BitmapFont font;
    private int correctAnswers;

    public TelaFinal(Game game, int correctAnswers){
        this.game = game;
        this.correctAnswers = correctAnswers;
    }

    public TelaFinal(Game game) {
    }

    public void show(){
        batch = new SpriteBatch();
        background = new Texture("final.jpeg");
        fimTexture = new Texture("fim.png");
        resultadoTexture = new Texture("resultado.png");
        font = new BitmapFont();
        stage = new Stage();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        float resultadoX = Gdx.graphics.getWidth() / 2 - resultadoTexture.getWidth() / 2;
        float resultadoY = Gdx.graphics.getHeight() / 2 - resultadoTexture.getHeight() / 2;
        batch.draw(resultadoTexture, resultadoX, resultadoY);
        batch.draw(fimTexture, Gdx.graphics.getWidth() / 2 - fimTexture.getWidth() / 2, Gdx.graphics.getHeight() / 2 + resultadoTexture.getHeight() / 2);
        String message;
        if (correctAnswers >= 2) {
            message = "Parabéns, você foi bem";
        } else {
            message = "Sinto muito, você foi mal";
        }
        GlyphLayout layout = new GlyphLayout(font, message);
        float textWidth = layout.width;
        font.draw(batch, message, resultadoX + resultadoTexture.getWidth() / 2 - textWidth / 2, resultadoY + resultadoTexture.getHeight() / 2);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        fimTexture.dispose();
        resultadoTexture.dispose();
        font.dispose();
    }
}