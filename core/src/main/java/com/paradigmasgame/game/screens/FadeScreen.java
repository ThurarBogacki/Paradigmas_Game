package com.Jardim.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;

public class FadeScreen extends ScreenAdapter {

    private Interpolation fadeInterpolation;
    private float fadeDuration;

    private final Game game;
    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;
    private float elapsed;
    private final FadeInfo fade;
    private Screen currentScreen;
    private Screen nextScreen;

    public FadeScreen(Game game, FadeInfo fade, Screen currentScreen, Screen nextScreen, Interpolation fadeInterpolation, float fadeDuration) {
        this.game = game;
        this.fade = fade;
        this.currentScreen = currentScreen;
        this.nextScreen = nextScreen;
        this.fadeInterpolation = fadeInterpolation;
        this.fadeDuration = fadeDuration;
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f);
        this.camera.update();
    }

    private void renderFade() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        float f = Math.min(1f, elapsed / fadeDuration);
        float opacity = fade.type == FadeType.OUT ? fade.interpolation.apply(f) : 1f - fade.interpolation.apply(f);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(fade.color.r, fade.color.g, fade.color.b, opacity);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void render(float delta) {
        if (currentScreen != null) {
            elapsed += delta;
            if (elapsed >= fadeDuration) {
                if (nextScreen != null) {
                    game.setScreen(nextScreen);
                    currentScreen.dispose();
                    currentScreen = null;
                } else {
                    game.setScreen(currentScreen);
                }
            }
        }
        Gdx.gl.glClearColor(0, 0, 0, 0);
        if (currentScreen != null) currentScreen.render(delta);
        renderFade();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    public void setScreen(QuestionScreen questionScreen) {
    }

    public void setScreen(Screen nextScreen) {
        this.nextScreen = nextScreen;
    }

    public enum FadeType { IN, OUT }

    public static class FadeInfo {
        public final FadeType type;
        public final Color color;
        public final Interpolation interpolation;
        public final float duration;

        public FadeInfo(FadeType type, Color color, Interpolation interpolation, float duration) {
            this.type = type;
            this.color = color;
            this.interpolation = interpolation;
            this.duration = duration;
        }
    }
}