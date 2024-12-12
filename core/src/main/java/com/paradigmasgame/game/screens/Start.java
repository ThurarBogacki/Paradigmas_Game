package com.Jardim.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class Start extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Game game;
    private Label questionLabel;
    private Image title;

    public Start(Game game) {
        this.game = game;
    }

    private void questionScreenTransition() {
        QuestionScreen questionScreenInstance = new QuestionScreen(game);
        FadeScreen.FadeInfo fadeOut = new FadeScreen.FadeInfo(FadeScreen.FadeType.OUT, Color.BLACK, Interpolation.smoother, 1f);
        FadeScreen fadeScreen = new FadeScreen(game, fadeOut, this, questionScreenInstance, Interpolation.smoother, 1f);
        game.setScreen(fadeScreen);
    }

    @Override
    public void show() {
        
        batch = new SpriteBatch();
        background = new Texture("start.jpeg");
        stage = new Stage();

        // Configuração da label da pergunta
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(); 
        labelStyle.fontColor = Color.BLACK; // Cor do texto
        questionLabel = new Label("", labelStyle);
        questionLabel.setAlignment(Align.center);

        // Adiciona os elementos à tabela
        Table table = new Table();
        table.setFillParent(true);
        table.center().top().padTop(100);
        table.add(questionLabel).expandX().center().padBottom(30).row();
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);


        // Configuração da imagem do título
        Drawable titleDrawable = new TextureRegionDrawable(new Texture("redirecionando.png"));

        // Configuração do título
        title = new Image(titleDrawable);
        title.setPosition(Gdx.graphics.getWidth() / 2 - title.getWidth() / 2, Gdx.graphics.getHeight() / 2 - title.getHeight() / 2);

        stage.addActor(title);

        // Configura o palco como o processador de entrada
        Gdx.input.setInputProcessor(stage);

        // Inicia o temporizador
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        questionScreenTransition();
                    }
                }, 2); // 2 segundos
            }
        }, 0); // Sem atraso inicial
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        stage.dispose();
    }
}