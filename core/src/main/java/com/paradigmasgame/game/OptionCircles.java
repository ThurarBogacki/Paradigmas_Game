// package paradigmas.gauchovoador;
package com.paradigmasgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class OptionCircles {
    public enum IntersectionWithCircles {
        NO,
        YES_CORRECT,
        YES_WRONG,
    }

    private final Array<String> options;
    private final int answer;
    private final Array<Circle> circles;

    private final String prompt;
    private final float speed;
    private final float circleRadius = Paradigmas_Game.WORLD_HEIGHT * 8 / 100f;
    private final ShapeRenderer renderer;
    private final GlyphLayout layout;
    public boolean active;

    public OptionCircles(Question question, float speed) {
        renderer = new ShapeRenderer();
        options = question.getOptions();
        answer = question.getAnswer();
        prompt = question.getText();
        layout = new GlyphLayout();
        active = true;
        this.speed = speed;
        circles = new Array<>();

        randomizeCircles();
    }

    private void randomizeCircles() {
        assert options.size == 4 : "Question does not have 4 options.";
        Random rand = new Random();
        for (int i = 0; i < options.size; i++) {
            float xPos;
            if (circles.notEmpty()) {
                xPos = circles.peek().x + 2 * circleRadius + rand.nextFloat(Paradigmas_Game.WORLD_WIDTH / 8f);
            } else {
                xPos = Paradigmas_Game.WORLD_WIDTH * 5 / 4f;
            }
            circles.add(new Circle(
                    xPos,
                    rand.nextFloat(circleRadius, Paradigmas_Game.WORLD_HEIGHT - circleRadius),
                    circleRadius
            ));
        }
    }

    public boolean allOutOfBounds() {
        boolean r = true;
        for (Circle c : circles) {
            if (c.x + c.radius > 0) {
                r = false;
                break;
            }
        }
        return r;
    }

    public IntersectionWithCircles hitsAnyCircle(Rectangle rect) {
        if (!active) return IntersectionWithCircles.NO;

        for (int i = 0; i < circles.size; i++) {
            if (Intersector.overlaps(circles.get(i), rect)) {
                if (i == answer) {
                    return IntersectionWithCircles.YES_CORRECT;
                }
                return IntersectionWithCircles.YES_WRONG;
            }
        }
        return IntersectionWithCircles.NO;
    }

    public void update() {
        for (Circle c : circles) {
            c.x -= (active) ? speed : 2 * speed;
        }
    }

    public void renderCircles() {
        if (active) {
            renderer.setColor(150/256f, 200/256f, 50/256f, 1f);
            renderer.begin(ShapeRenderer.ShapeType.Filled);

            Gdx.gl.glLineWidth(4);
            for (Circle c : circles) {
                renderer.circle(c.x, c.y, c.radius, 100);
            }
            renderer.end();
        }

        renderer.setColor(Color.BLACK);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (Circle c : circles) {
            renderer.circle(c.x, c.y, c.radius, 100);
        }
        renderer.end();
    }

    public void renderText(SpriteBatch batch, BitmapFont font) {
        if (!active) return;

        layout.setText(font, prompt, Color.BLACK, 0f, Align.center, false);
        font.draw(batch, layout,  Paradigmas_Game.WORLD_WIDTH / 2, Paradigmas_Game.WORLD_HEIGHT * 0.95f);

        // Colocar texto em uma bola é mais difícil do que parece.
        for (int i = 0; i < 4; i++) {
            layout.setText(font, options.get(i), Color.BLACK, circleRadius * 1.8f, Align.center, true);
            font.draw(batch, layout, circles.get(i).x - circleRadius * 0.9f, circles.get(i).y + layout.height / 2f);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}