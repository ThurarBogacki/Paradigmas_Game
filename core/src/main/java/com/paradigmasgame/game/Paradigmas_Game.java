package com.paradigmasgame.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class Paradigmas_Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image, tNave, tMissile, tEnemy;
    private Sprite nave, missile;
    private float posX, posY, xMissile, yMissile;
    private final int velocity = 5;
    private boolean atack, gameOver, win;
    private long lastEnemyTime;
    private float enemySpawnInterval = 1.0f; // Intervalo inicial entre spawns (em segundos)
    private float timeSinceLastSpawn = 0.0f; // Tempo acumulado desde o último spawn
    private Array<Enemy> enemies;
    private int score, numQuestions;
    private int power;
    private BitmapFont bitmap;

    private int currentQuestionIndex;
    private ArrayList<Question> questions;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("planetario.png");
        tNave = new Texture("podrao.png");
        nave = new Sprite(tNave);
        posX = 0;
        posY = 0;
        xMissile = posX;
        yMissile = posY;
        win = false;
        atack = false;
        numQuestions = 3;
        gameOver = false;
        tMissile = new Texture("missile.png");
        missile = new Sprite(tMissile);

        tEnemy = new Texture("haskell.png");
        enemies = new Array<>();

        score = 0;
        power = 3;

        bitmap = new BitmapFont();
        bitmap.setColor(Color.WHITE);

        // Criando as perguntas e respostas
        questions = new ArrayList<>();
        questions.add(new Question("Qual é a capital do Brasil?", new String[]{"Brasília", "Rio de Janeiro", "São Paulo", "Belo Horizonte"}, 0));
        questions.add(new Question("Quanto é 2 + 2?", new String[]{"3", "4", "5", "6"}, 1));
        questions.add(new Question("Qual a cor do céu?", new String[]{"Azul", "Verde", "Amarelo", "Vermelho"}, 0));
        questions.add(new Question(" ", new String[]{" ", " ", " ", " "}, 0));

        currentQuestionIndex = 0;
        spawnEnemiesForCurrentQuestion();
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
    this.moveNave();
    this.moveMissile();
    this.moveEnemies();

    timeSinceLastSpawn += deltaTime;

    // Checa se é hora de spawnar um novo inimigo
    if (timeSinceLastSpawn >= enemySpawnInterval && !gameOver) {
        spawnEnemy();
        timeSinceLastSpawn = 0; // Reseta o temporizador
    }

    ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
    batch.begin();
    batch.draw(image, 0, 0);
    if (!gameOver) {
        if (atack) {
            batch.draw(missile, xMissile, yMissile);
        }
        batch.draw(nave, posX, posY);

        for (Enemy enemy : enemies) {
            batch.draw(tEnemy, enemy.rectangle.x, enemy.rectangle.y);

            // Desenhando o texto das respostas com fonte maior
            float textX = enemy.rectangle.x + 10;
            float textY = enemy.rectangle.y + tEnemy.getHeight() + 20;

            bitmap.getData().setScale(2.0f); // Aumenta o tamanho da fonte para as respostas

            bitmap.setColor(Color.BLACK);
            bitmap.draw(batch, enemy.text, textX + 1, textY - 1); // Sombra preta
            bitmap.setColor(Color.WHITE);
            bitmap.draw(batch, enemy.text, textX, textY); // Texto branco

            bitmap.getData().setScale(1.0f); // Volta ao tamanho original após desenhar
        }

        bitmap.getData().setScale(2.0f);
        bitmap.draw(batch, "Pergunta: " + questions.get(currentQuestionIndex).question, 20, Gdx.graphics.getHeight() - 20);
        bitmap.draw(batch, "Score: " + score, 20, Gdx.graphics.getHeight() - 50);
        bitmap.draw(batch, "Power: " + power, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 20);
    } else {
        if (win) {
            bitmap.draw(batch, "YOU WIN", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);
        } else {
            bitmap.draw(batch, "GAME OVER", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);
        }
        bitmap.draw(batch, "PRESS ENTER", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 50);
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            restartGame();
        }
    }

    batch.end();
}

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        tNave.dispose();
        tMissile.dispose();
        tEnemy.dispose();
        bitmap.dispose();
    }

    private void moveNave() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && posX < Gdx.graphics.getWidth() - nave.getWidth()) {
            posX += velocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && posX > 0) {
            posX -= velocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && posY < Gdx.graphics.getHeight() - nave.getHeight()) {
            posY += velocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && posY > 0) {
            posY -= velocity;
        }
    }

    private void moveMissile() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !atack) {
            atack = true;
            yMissile = posY + nave.getHeight() / 2 - 12;
        }

        if (atack) {
            if (xMissile < Gdx.graphics.getWidth()) {
                xMissile += 20;
            } else {
                xMissile = posX + nave.getWidth() / 2;
                atack = false;
            }
        } else {
            xMissile = posX + nave.getWidth() / 2;
            yMissile = posY + nave.getHeight() / 2 - 12;
        }
    }

    private void spawnEnemiesForCurrentQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);

    // Seleciona uma resposta aleatória para o inimigo
    int answerIndex = MathUtils.random(currentQuestion.answers.length - 1);

    float yPosition = MathUtils.random(50, Gdx.graphics.getHeight() - 50);

    Rectangle enemyRectangle = new Rectangle(
            Gdx.graphics.getWidth(),
            yPosition - (tEnemy.getHeight() / 2)+20,
            tEnemy.getWidth(),
            tEnemy.getHeight());
    
    enemies.add(new Enemy(enemyRectangle, currentQuestion.answers[answerIndex], answerIndex == currentQuestion.correctIndex));
}

private void moveEnemies() {
    boolean allEnemiesOut = true;
    boolean correctHit = false; // Sinalizador para verificar acerto correto

    for (Iterator<Enemy> iter = enemies.iterator(); iter.hasNext(); ) {
        Enemy enemy = iter.next();
        enemy.rectangle.x -= 400 * Gdx.graphics.getDeltaTime();

        // Colisão com o míssil
        if (collide(enemy.rectangle.x, enemy.rectangle.y, enemy.rectangle.width, enemy.rectangle.height, xMissile, yMissile, missile.getWidth(), missile.getHeight()) && atack) {
            if (enemy.isCorrect) {
                score++;
                correctHit = true; // Marca que a resposta correta foi atingida
            } else {
                power--;
                if (power <= 0) {
                    gameOver = true;
                }
            }
            atack = false;
            iter.remove();
        } else if (enemy.rectangle.x + tEnemy.getWidth() < 0) {
            iter.remove();
        }

        if (enemy.rectangle.x + tEnemy.getWidth() > 0) {
            allEnemiesOut = false;
        }
    }

    // Se a resposta correta foi atingida, avança para a próxima pergunta
    if (correctHit) {
        currentQuestionIndex = currentQuestionIndex + 1;
        if (currentQuestionIndex >= numQuestions) {
            enemies.clear();
            win = true; // Define vitória quando todas as perguntas forem respondidas
            gameOver = true; // Termina o jogo
        }
        enemies.clear(); // Agora pode limpar a lista sem problemas
        spawnEnemiesForCurrentQuestion();
    }

    // Caso todos os inimigos saiam da tela, continua spawnando
    if (allEnemiesOut && !gameOver) {
        spawnEnemy();
    }
}


    private boolean collide(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return x1 + w1 > x2 && x1 < x2 + w2 && y1 + h1 > y2 && y1 < y2 + h2;
    }

    private void restartGame() {
        enemies.clear();
        score = 0;
        power = 3;
        posX = 0;
        posY = 0;
        gameOver = false;
        win = false;
        currentQuestionIndex = 0;
        spawnEnemiesForCurrentQuestion();
    }

    private void spawnEnemy() {
        Question currentQuestion = questions.get(currentQuestionIndex);
    
        // Seleciona uma resposta aleatória para o inimigo
        int answerIndex = MathUtils.random(currentQuestion.answers.length - 1);
    
        float yPosition = MathUtils.random(50, Gdx.graphics.getHeight() - 50);
    
        Rectangle enemyRectangle = new Rectangle(
                Gdx.graphics.getWidth(),
                yPosition - tEnemy.getHeight() / 2,
                tEnemy.getWidth(),
                tEnemy.getHeight());
        
        enemies.add(new Enemy(enemyRectangle, currentQuestion.answers[answerIndex], answerIndex == currentQuestion.correctIndex));
    }
    

    private static class Enemy {
        Rectangle rectangle;
        String text;
        boolean isCorrect;

        Enemy(Rectangle rectangle, String text, boolean isCorrect) {
            this.rectangle = rectangle;
            this.text = text;
            this.isCorrect = isCorrect;
        }
    }

    private static class Question {
        String question;
        String[] answers;
        int correctIndex;

        Question(String question, String[] answers, int correctIndex) {
            this.question = question;
            this.answers = answers;
            this.correctIndex = correctIndex;
        }
    }
}
