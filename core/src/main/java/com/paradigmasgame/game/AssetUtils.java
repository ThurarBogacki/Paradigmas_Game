package com.Jardim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetUtils {
    public static Texture playButton;
    public static Texture playButtonHighlight;
    public static Texture title;
    public static Texture backgroundMenu;

    public static Sound soundCorrect;
    public static Sound soundWrong;
    public static Sound backgroundMusic;
    public static Sound soundClickButton;

    
    private AssetUtils() {
    }

    public static void initAssets() {
        loadAssets();
    }

    private static void loadAssets() {
        playButton = new Texture("play.png");
        playButtonHighlight = new Texture("play2.png");
        title = new Texture("logo.png");
        backgroundMenu = new Texture("jardim.jpg");

        soundCorrect = Gdx.audio.newSound(Gdx.files.internal("./assets/soundCorrect.mp3"));
        soundWrong = Gdx.audio.newSound(Gdx.files.internal("./assets/soundWrong.mp3"));
        backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("./assets/backgroundMusic.mp3"));
        soundClickButton = Gdx.audio.newSound(Gdx.files.internal("./assets/soundClickButton.mp3"));   
    }
}