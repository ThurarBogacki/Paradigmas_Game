package com.Jardim.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

public class QuestionScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Game game;
    private Drawable answerButtonDrawable;
    private Drawable answerButtonOverDrawable;
    private Drawable correctAnswerDrawable;
    private Drawable wrongAnswerDrawable;
    private Image[] answerButtons;
    private Image question;
    private BitmapFont font;
    private Label questionLabel;
    private Label[] answerLabels;
    private String[] questions = {"Em que ano foi fundado o jardim botânico?", "Quantos hectares o Jardim Botânico possui?", "Quantas plantas o Jardim Botânico possui?"};
    private String[][] answers = {
            {"1981", "2002", "1900", "1980"}, // Respostas para a primeira pergunta
            {"13", "12", "1", "50"},
            {"2.500", "100", "3.000", "50.000"}// Respostas para a segunda pergunta
    };

    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private TelaFinal finalScreenInstance;

    public static Sound soundCorrect;
    public static Sound soundWrong;
    public static Sound soundGame;
    public static Sound soundFinalGood;
    public static Sound soundFinalBad;
    public static Sound soundClickButton;

    

    public QuestionScreen(Game game) {
        this.game = game;
    }

    private void finalScreenTransition() {
        TelaFinal finalScreenInstance = new TelaFinal(game, correctAnswers);
        FadeScreen.FadeInfo fadeOut = new FadeScreen.FadeInfo(FadeScreen.FadeType.OUT, Color.BLACK, Interpolation.smoother, 1f);
        FadeScreen fadeScreen = new FadeScreen(game, fadeOut, this, finalScreenInstance, Interpolation.smoother, 1f);
        game.setScreen(fadeScreen);
    }

    @Override
    public void show() {
        // Configuração dos sons
        soundCorrect = Gdx.audio.newSound(Gdx.files.internal("./assets/soundCorrect.mp3"));
        soundWrong = Gdx.audio.newSound(Gdx.files.internal("./assets/soundWrong.mp3"));



        batch = new SpriteBatch();
        background = new Texture("question2.jpeg");
        stage = new Stage();

        // Configuração da imagem de pergunta
        Drawable questionDrawable = new TextureRegionDrawable(new Texture("pergunta.png"));

        // Configuração da pergunta
        question = new Image(questionDrawable);
        question.setPosition(Gdx.graphics.getWidth() / 2 - question.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        // Adiciona a pergunta ao palco
        stage.addActor(question);

        // Configuração da imagem do botão
        answerButtonDrawable = new TextureRegionDrawable(new Texture("resposta.png"));
        answerButtonOverDrawable = new TextureRegionDrawable(new Texture("resposta2.png"));
        correctAnswerDrawable = new TextureRegionDrawable(new Texture("verificar.png"));
        wrongAnswerDrawable = new TextureRegionDrawable(new Texture("errado.png"));

        // Configuração da fonte
        font = new BitmapFont();

        // Configuração do estilo do rótulo
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        // Configuração do rótulo da pergunta
        questionLabel = new Label("Em que ano foi fundado o jardim botânico?", labelStyle);
        questionLabel.setPosition(question.getX() + question.getWidth() / 2 - questionLabel.getWidth() / 2, question.getY() + question.getHeight() / 2 - questionLabel.getHeight() / 2);

        // Adiciona o rótulo da pergunta ao palco
        stage.addActor(questionLabel);

        // Configuração dos botões de resposta
        final Image correctAnswerImage = new Image(correctAnswerDrawable);
        correctAnswerImage.setVisible(false); // Inicialmente, a imagem está oculta

        answerButtons = new Image[4];
        for (int i = 0; i < 4; i++) {
            final int answerIndex = i; // Necessário para acessar i dentro do ClickListener
            answerButtons[i] = new Image(answerButtonDrawable);
            int row = i / 2; // 0 para os dois primeiros botões, 1 para os dois últimos
            int col = i % 2; // 0 para os botões da esquerda, 1 para os da direita
            answerButtons[i].setPosition(Gdx.graphics.getWidth() / 2 - answerButtons[i].getWidth() / 2 - 140 + col * (answerButtons[i].getWidth() + 10), question.getY() - (row + 1) * (answerButtons[i].getHeight() + 10));

            // Adiciona um ouvinte de clique à imagem
            answerButtons[i].addListener(new ClickListener() {
                final Image wrongAnswerImage = new Image(wrongAnswerDrawable); // Agora é final

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (answerIndex == 0) { // Se a resposta estiver correta
                        correctAnswers++;
                        correctAnswerImage.setPosition(answerButtons[answerIndex].getX() + answerButtons[answerIndex].getWidth()/2 - correctAnswerImage.getWidth()/2,
                                answerButtons[answerIndex].getY() + answerButtons[answerIndex].getHeight()/2 - correctAnswerImage.getHeight()/2);
                        correctAnswerImage.setVisible(true);
                        soundCorrect.play();

                    } else { // Se a resposta estiver errada
                        wrongAnswerImage.setPosition(answerButtons[answerIndex].getX() + answerButtons[answerIndex].getWidth()/2 - wrongAnswerImage.getWidth()/2,
                                answerButtons[answerIndex].getY() + answerButtons[answerIndex].getHeight()/2 - wrongAnswerImage.getHeight()/2);
                        wrongAnswerImage.setVisible(true);
                        stage.addActor(wrongAnswerImage);
                        soundWrong.play();
                    }
                    if (currentQuestionIndex < questions.length) {
                        // ...
                    } else {
                        // Fim do jogo ou transição para outra tela
                        // Removemos a chamada para finalScreenTransition() aqui
                    }
                    Timer.schedule(new Timer.Task(){
                        @Override
                        public void run() {
                            wrongAnswerImage.setVisible(false);
                            // Atualize para a próxima pergunta
                            currentQuestionIndex++;
                            if (currentQuestionIndex < questions.length) {
                                questionLabel.setText(questions[currentQuestionIndex]);
                                for (int j = 0; j < 4; j++) {
                                    answerLabels[j].setText(answers[currentQuestionIndex][j]);
                                }
                            } else {
                                // Fim do jogo ou transição para outra tela
                                finalScreenTransition(); // Chamamos aqui quando todas as perguntas foram respondidas
                            }

                            // Faça os ícones desaparecerem
                            correctAnswerImage.setVisible(false);
                            wrongAnswerImage.setVisible(false);
                        }
                    }, 1); // Atraso de um segundo
                    // Agende uma tarefa para fazer o ícone correto sumir após um segundo
                    Timer.schedule(new Timer.Task(){
                        @Override
                        public void run() {
                            correctAnswerImage.setVisible(false);
                        }
                    }, 1);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                    ((Image) event.getTarget()).setDrawable(answerButtonOverDrawable);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                    ((Image) event.getTarget()).setDrawable(answerButtonDrawable);
                }
            });

            // Adiciona as imagens ao palco
            stage.addActor(answerButtons[i]);

        }

        // Configuração dos rótulos das respostas
        answerLabels = new Label[4];
        for (int i = 0; i < 4; i++) {
            answerLabels[i] = new Label(answers[currentQuestionIndex][i], labelStyle);
            answerLabels[i].setPosition(answerButtons[i].getX() + answerButtons[i].getWidth() / 2 - answerLabels[i].getWidth() / 2, answerButtons[i].getY() + answerButtons[i].getHeight() / 2 - answerLabels[i].getHeight() / 2);

            // Adiciona os rótulos das respostas ao palco
            stage.addActor(answerLabels[i]);
        }

        // Adicione a imagem ao palco aqui para que ela seja desenhada por cima dos outros atores
        stage.addActor(correctAnswerImage);

        // Configura o palco como o processador de entrada
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
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
    public void dispose() {
        batch.dispose();
        background.dispose();
        stage.dispose();
        font.dispose();
    }
}