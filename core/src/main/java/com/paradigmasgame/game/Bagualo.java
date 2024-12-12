// package paradigmas.gauchovoador;
package com.paradigmasgame.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

public class Bagualo {
    private final Sprite sprite;
    private int lives;
    private int score;
    private final GlyphLayout layout;

    public Bagualo(int lives) {
        this.lives = lives;
        score = 0;
        layout = new GlyphLayout();

        sprite = new Sprite(new Texture("img/bagualo.png"));
        sprite.setSize(sprite.getWidth() * 0.7f, sprite.getHeight() * 0.7f);
        sprite.setOrigin(0, 0);
        sprite.setCenter(Paradigmas_Game.WORLD_WIDTH * 12f / 100, Paradigmas_Game.WORLD_HEIGHT * 50f / 100);
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        sprite.draw(batch);
        layout.setText(font, String.format("Vidas:    %d%nPontos:  %d", lives, score), Color.BLACK, 0, Align.left, false);
        font.draw(batch, layout, Paradigmas_Game.WORLD_WIDTH * 0.90f, Paradigmas_Game.WORLD_HEIGHT * 0.95f);
    }

    public void update() {
        float mouseY = Paradigmas_Game.worldCoordinates.y;
        sprite.setY(Interpolation.linear.apply(sprite.getY(), mouseY, 0.05f));
        sprite.setY(
                Math.max(
                        Math.min(
                                sprite.getY(),
                                Paradigmas_Game.WORLD_HEIGHT - sprite.getHeight() * 0.9f),
                        0
                )
        );
    }

    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle();
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int increase) {
        score += increase;
    }

    public boolean decreaseLive() {
        return --lives <= 0;
    }
}