// package com.paradigmasgame.game;

// import java.util.ArrayList;
// import java.util.Iterator;

// import com.badlogic.gdx.ApplicationAdapter;
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Input;
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.BitmapFont;
// import com.badlogic.gdx.graphics.g2d.Sprite;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.math.MathUtils;
// import com.badlogic.gdx.math.Rectangle;
// import com.badlogic.gdx.utils.Array;
// import com.badlogic.gdx.utils.ScreenUtils;

// public class Paradigmas_Game extends ApplicationAdapter {
//     private SpriteBatch batch;
//     private Texture image, tNave, tMissile, tEnemy;
//     private Sprite nave, missile;
//     private float posX, posY, xMissile, yMissile;
//     private final int velocity = 3;
//     private boolean atack, gameOver, win;
//     private long lastEnemyTime;
//     private float enemySpawnInterval = 1.0f; 
//     private float timeSinceLastSpawn = 0.0f; 
//     private Array<Enemy> enemies;
//     private int score, numQuestions;
//     private int power;
//     private BitmapFont bitmap;

//     private int currentQuestionIndex;
//     private ArrayList<Question> questions;

//     @Override
//     public void create() {
//         batch = new SpriteBatch();
//         image = new Texture("planetario.png");
//         tNave = new Texture("podrao.png");
//         nave = new Sprite(tNave);
//         posX = 0;
//         posY = 0;
//         xMissile = posX;
//         yMissile = posY;
//         win = false;
//         atack = false;
//         numQuestions = 10;
//         gameOver = false;
//         tMissile = new Texture("missile.png");
//         missile = new Sprite(tMissile);

//         tEnemy = new Texture("haskell.png");
//         enemies = new Array<>();

//         score = 0;
//         power = 3;

//         bitmap = new BitmapFont();
//         bitmap.setColor(Color.WHITE);

//         questions = new ArrayList<>();
//         questions.add(new Question("1- Em que ano foi fundada a UFSM?", new String[]{"1950", "1960", "1961", "1965"}, 2));
//         questions.add(new Question("2- Onde está localizado o campus principal da UFSM?", new String[]{"Porto Alegre", "Marte", "Santa Maria", "Russia"}, 2));
//         questions.add(new Question("3- Qual o nome do mascote visto perambulando pela UFSM?", new String[]{"Cachorro", "Fedorento", "Podrao", "Auau"}, 2));
//         questions.add(new Question("4- Qual a melhor proteína do RU?", new String[]{"Salsichao", "Fricassê", "Bife Acebolado", "Guizado"}, 0));
//         questions.add(new Question("5- Qual o nome da Atlética dos Cursos de Tecnologia?", new String[]{"Indomáveis", "Java", "Tirana", "Anarquíca"},1));
//         questions.add(new Question("6- Qual o point das sextas dos alunos da UFSM?", new String[]{"Container", "Estudar1", "Estudar2", "Estudar3"}, 0));
//         questions.add(new Question("7- Qual o nome do monumento que Podrao está travando sua batalha?", new String[]{"Bosque", "Incubadora", "CT", "Planetário"}, 3));
//         questions.add(new Question("8- Qual bairro abriga o campus da UFSM em Santa Maria?", new String[]{"Medianeira", "Dores", "Camobi", "Centro"}, 2));
//         questions.add(new Question("9- Qual prédio abriga os cursos de Tecnologia?", new String[]{"CT", "CAL", "CCNE", "HUSM"}, 0));
//         questions.add(new Question("10- Onde era comemorada a famosa calourada?", new String[]{"Gare", "Brahma", "CT", "Liverpool"}, 1));
//         questions.add(new Question(" ", new String[]{" "," "," "," "}, 0));


//         currentQuestionIndex = 0;
//         spawnEnemiesForCurrentQuestion();
//     }

//     @Override
//     public void render() {
//         float deltaTime = Gdx.graphics.getDeltaTime();
//     this.moveNave();
//     this.moveMissile();
//     this.moveEnemies();

//     timeSinceLastSpawn += deltaTime;

//     if (timeSinceLastSpawn >= enemySpawnInterval && !gameOver) {
//         spawnEnemy();
//         timeSinceLastSpawn = 0; 
//     }

//     ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//     batch.begin();
//     batch.draw(image, 0, 0);
//     if (!gameOver) {
//         if (atack) {
//             batch.draw(missile, xMissile, yMissile);
//         }
//         batch.draw(nave, posX, posY);

//         for (Enemy enemy : enemies) {
//             batch.draw(tEnemy, enemy.rectangle.x, enemy.rectangle.y);

//             float textX = enemy.rectangle.x + 10;
//             float textY = enemy.rectangle.y + tEnemy.getHeight() + 20;

//             bitmap.getData().setScale(2.0f); 

//             bitmap.setColor(Color.BLACK);
//             bitmap.draw(batch, enemy.text, textX + 1, textY - 1); 
//             bitmap.setColor(Color.WHITE);
//             bitmap.draw(batch, enemy.text, textX, textY); 

//             bitmap.getData().setScale(1.0f); 
//         }

//         bitmap.getData().setScale(2.0f);
//         bitmap.draw(batch, "Pergunta: " + questions.get(currentQuestionIndex).question, 20, Gdx.graphics.getHeight() - 20);
//         bitmap.draw(batch, "Score: " + score, 20, Gdx.graphics.getHeight() - 50);
//         bitmap.draw(batch, "Power: " + power, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 20);
//     } else {
//         if (win) {
//             bitmap.draw(batch, "YOU WIN", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);
//             bitmap.draw(batch, "SCORE: " + score, Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 30);
//         } else {
//             bitmap.draw(batch, "GAME OVER", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);
//         }
//         bitmap.draw(batch, "PRESS ENTER", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 50);
//         if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
//             restartGame();
//         }
//     }

//     batch.end();
// }

//     @Override
//     public void dispose() {
//         batch.dispose();
//         image.dispose();
//         tNave.dispose();
//         tMissile.dispose();
//         tEnemy.dispose();
//         bitmap.dispose();
//     }

//     private void moveNave() {
//         if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && posX < Gdx.graphics.getWidth() - nave.getWidth()) {
//             posX += velocity;
//         }
//         if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && posX > 0) {
//             posX -= velocity;
//         }
//         if (Gdx.input.isKeyPressed(Input.Keys.UP) && posY < Gdx.graphics.getHeight() - nave.getHeight()) {
//             posY += velocity;
//         }
//         if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && posY > 0) {
//             posY -= velocity;
//         }
//     }

//     private void moveMissile() {
//         if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !atack) {
//             atack = true;
//             yMissile = posY + nave.getHeight() / 2 - 12;
//         }

//         if (atack) {
//             if (xMissile < Gdx.graphics.getWidth()) {
//                 xMissile += 10;
//             } else {
//                 xMissile = posX + nave.getWidth() / 2;
//                 atack = false;
//             }
//         } else {
//             xMissile = posX + nave.getWidth() / 2;
//             yMissile = posY + nave.getHeight() / 2 - 12;
//         }
//     }

//     private void spawnEnemiesForCurrentQuestion() {
//         Question currentQuestion = questions.get(currentQuestionIndex);

//     int answerIndex = MathUtils.random(currentQuestion.answers.length - 1);

//     float yPosition = MathUtils.random(50, Gdx.graphics.getHeight() - 50);

//     Rectangle enemyRectangle = new Rectangle(
//             Gdx.graphics.getWidth(),
//             yPosition - (tEnemy.getHeight() / 2)+20,
//             tEnemy.getWidth(),
//             tEnemy.getHeight());
    
//     enemies.add(new Enemy(enemyRectangle, currentQuestion.answers[answerIndex], answerIndex == currentQuestion.correctIndex));
// }

// private void moveEnemies() {
//     boolean allEnemiesOut = true;
//     boolean correctHit = false; 

//     for (Iterator<Enemy> iter = enemies.iterator(); iter.hasNext(); ) {
//         Enemy enemy = iter.next();
//         enemy.rectangle.x -= 400 * Gdx.graphics.getDeltaTime();

//         if (collide(enemy.rectangle.x, enemy.rectangle.y, enemy.rectangle.width, enemy.rectangle.height, xMissile, yMissile, missile.getWidth(), missile.getHeight()) && atack) {
//             if (enemy.isCorrect) {
//                 score++;
//                 correctHit = true; 
//             } else {
//                 power--;
//                 score--;
//                 if (power <= 0) {
//                     gameOver = true;
//                 }
//             }
//             atack = false;
//             iter.remove();
//         } else if (enemy.rectangle.x + tEnemy.getWidth() < 0) {
//             iter.remove();
//         }

//         if (enemy.rectangle.x + tEnemy.getWidth() > 0) {
//             allEnemiesOut = false;
//         }
//     }

//     if (correctHit) {
//         currentQuestionIndex = currentQuestionIndex + 1;
//         if (currentQuestionIndex >= numQuestions) {
//             enemies.clear();
//             win = true; 
//             gameOver = true; 
//         }
//         enemies.clear(); 
//         spawnEnemiesForCurrentQuestion();
//     }

//     if (allEnemiesOut && !gameOver) {
//         spawnEnemy();
//     }
// }


//     private boolean collide(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
//         return x1 + w1 > x2 && x1 < x2 + w2 && y1 + h1 > y2 && y1 < y2 + h2;
//     }

//     private void restartGame() {
//         enemies.clear();
//         score = 0;
//         power = 3;
//         posX = 0;
//         posY = 0;
//         gameOver = false;
//         win = false;
//         currentQuestionIndex = 0;
//         spawnEnemiesForCurrentQuestion();
//     }

//     private void spawnEnemy() {
//         Question currentQuestion = questions.get(currentQuestionIndex);

//         int answerIndex = MathUtils.random(currentQuestion.answers.length - 1);
    
//         float yPosition = MathUtils.random(50, Gdx.graphics.getHeight() - 50);
    
//         Rectangle enemyRectangle = new Rectangle(
//                 Gdx.graphics.getWidth(),
//                 yPosition - tEnemy.getHeight() / 2,
//                 tEnemy.getWidth(),
//                 tEnemy.getHeight());
        
//         enemies.add(new Enemy(enemyRectangle, currentQuestion.answers[answerIndex], answerIndex == currentQuestion.correctIndex));
//     }
    

//     private static class Enemy {
//         Rectangle rectangle;
//         String text;
//         boolean isCorrect;

//         Enemy(Rectangle rectangle, String text, boolean isCorrect) {
//             this.rectangle = rectangle;
//             this.text = text;
//             this.isCorrect = isCorrect;
//         }
//     }

//     private static class Question {
//         String question;
//         String[] answers;
//         int correctIndex;

//         Question(String question, String[] answers, int correctIndex) {
//             this.question = question;
//             this.answers = answers;
//             this.correctIndex = correctIndex;
//         }
//     }
// }


package com.Jardim.game;

import com.Jardim.game.screens.FadeScreen;
import com.Jardim.game.screens.Start;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jardim implements Screen {
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Game game;
    private Image playButton;
    private Image title;
    private Drawable playButtonDrawable;
    private Drawable playButtonOverDrawable;
    private Start startScreenInstance;
    private Music backgroundMusic;
    public Sound soundClickButton;


    // Construtor
    public Jardim(Game game) {
        this.game = game;
    }

    public Jardim() {

    }

    //método que inicia a transição de tela
    private void startScreenTransition() {
        startScreenInstance = new Start(game);
        FadeScreen.FadeInfo fadeOut = new FadeScreen.FadeInfo(FadeScreen.FadeType.OUT, Color.BLACK, Interpolation.smoother, 1f);
        FadeScreen fadeScreen = new FadeScreen(game, fadeOut, this, startScreenInstance, Interpolation.smoother, 1f);
        game.setScreen(fadeScreen);
    }

    @Override
    public void show() {
        
        batch = new SpriteBatch();
        background = new Texture("jardim.jpg");
        stage = new Stage();

        // Configuração da imagem do botão
        playButtonDrawable = new TextureRegionDrawable(new Texture("play.png"));

        // Configuração do botão
        playButton = new Image(playButtonDrawable);
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, 105);

        // Configuração da imagem do botão quando passa o mouse
        playButtonOverDrawable = new TextureRegionDrawable(new Texture("play2.png"));

        // Configuração do som do botão
        soundClickButton = Gdx.audio.newSound(Gdx.files.internal("./assets/soundClickButton.mp3"));

        // Adiciona um ouvinte de clique à imagem
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ao clicar no botão, inicia a transição de tela para Start
                startScreenTransition();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playButton.setDrawable(playButtonOverDrawable);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                playButton.setDrawable(playButtonDrawable);
            }

            // Método que muda a tela para a tela de seleção de mapa quando o botão de play é clicado
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soundClickButton.play();
                startScreenTransition();
                return true;
            }
        });

        // Configuração da imagem do título
        Drawable titleDrawable = new TextureRegionDrawable(new Texture("LOGO.png"));

        // Configuração do título
        title = new Image(titleDrawable);
        title.setPosition(Gdx.graphics.getWidth() / 2 - title.getWidth() / 2, playButton.getY() + playButton.getHeight() + 280);

        // Adiciona as imagens ao palco
        stage.addActor(playButton);
        stage.addActor(title);

        // Configura o palco como o processador de entrada
        Gdx.input.setInputProcessor(stage);

        // configura musica de fundo
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("./assets/backgroundMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
    
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Este método é chamado quando a tela é redimensionada
    }

    @Override
    public void pause() {
        // Este método é chamado quando o jogo é pausado
    }

    @Override
    public void resume() {
        // Este método é chamado quando o jogo é retomado após estar pausado
    }

    @Override
    public void hide() {
        // Este método é chamado quando a tela não está mais visível
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        stage.dispose();
    }
}