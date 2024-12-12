package com.Jardim.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class MyLabel {
    private Label label;

    public MyLabel(String text, float x, float y, int fontSize, Color fontColor) {
        // Cria uma nova fonte BitmapFont com o tamanho especificado
        BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.getData().setScale(fontSize / font.getCapHeight()); // Ajusta o tamanho da fonte

        // Cria um novo estilo de rótulo com a cor especificada
        LabelStyle style = new LabelStyle();
        style.font = font;
        style.fontColor = fontColor;

        // Cria um novo rótulo com o texto, estilo e posição especificados
        label = new Label(text, style);
        label.setPosition(x, y);
    }

    public Label getLabel() {
        return label;
    }
}