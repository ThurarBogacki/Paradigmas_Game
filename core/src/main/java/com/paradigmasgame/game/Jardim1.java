// package com.Jardim.game;

// import com.Jardim.game.screens.FadeScreen;
// import com.Jardim.game.screens.Start;
// import com.badlogic.gdx.Game;
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Screen;
// import com.badlogic.gdx.audio.Music;
// import com.badlogic.gdx.audio.Sound;
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.math.Interpolation;
// import com.badlogic.gdx.scenes.scene2d.Actor;
// import com.badlogic.gdx.scenes.scene2d.InputEvent;
// import com.badlogic.gdx.scenes.scene2d.Stage;
// import com.badlogic.gdx.scenes.scene2d.ui.Image;
// import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
// import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
// import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
// import com.badlogic.gdx.utils.ScreenUtils;

// public class Jardim implements Screen {
//     private SpriteBatch batch;
//     private Texture background;
//     private Stage stage;
//     private Game game;
//     private Image playButton;
//     private Image title;
//     private Drawable playButtonDrawable;
//     private Drawable playButtonOverDrawable;
//     private Start startScreenInstance;
//     private Music backgroundMusic;
//     public Sound soundClickButton;


//     // Construtor
//     public Jardim(Game game) {
//         this.game = game;
//     }

//     public Jardim() {

//     }

//     //método que inicia a transição de tela
//     private void startScreenTransition() {
//         startScreenInstance = new Start(game);
//         FadeScreen.FadeInfo fadeOut = new FadeScreen.FadeInfo(FadeScreen.FadeType.OUT, Color.BLACK, Interpolation.smoother, 1f);
//         FadeScreen fadeScreen = new FadeScreen(game, fadeOut, this, startScreenInstance, Interpolation.smoother, 1f);
//         game.setScreen(fadeScreen);
//     }

//     @Override
//     public void show() {
        
//         batch = new SpriteBatch();
//         background = new Texture("jardim.jpg");
//         stage = new Stage();

//         // Configuração da imagem do botão
//         playButtonDrawable = new TextureRegionDrawable(new Texture("play.png"));

//         // Configuração do botão
//         playButton = new Image(playButtonDrawable);
//         playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, 105);

//         // Configuração da imagem do botão quando passa o mouse
//         playButtonOverDrawable = new TextureRegionDrawable(new Texture("play2.png"));

//         // Configuração do som do botão
//         soundClickButton = Gdx.audio.newSound(Gdx.files.internal("./assets/soundClickButton.mp3"));

//         // Adiciona um ouvinte de clique à imagem
//         playButton.addListener(new ClickListener() {
//             @Override
//             public void clicked(InputEvent event, float x, float y) {
//                 // Ao clicar no botão, inicia a transição de tela para Start
//                 startScreenTransition();
//             }

//             @Override
//             public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                 playButton.setDrawable(playButtonOverDrawable);
//             }

//             @Override
//             public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                 playButton.setDrawable(playButtonDrawable);
//             }

//             // Método que muda a tela para a tela de seleção de mapa quando o botão de play é clicado
//             @Override
//             public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                 soundClickButton.play();
//                 startScreenTransition();
//                 return true;
//             }
//         });

//         // Configuração da imagem do título
//         Drawable titleDrawable = new TextureRegionDrawable(new Texture("LOGO.png"));

//         // Configuração do título
//         title = new Image(titleDrawable);
//         title.setPosition(Gdx.graphics.getWidth() / 2 - title.getWidth() / 2, playButton.getY() + playButton.getHeight() + 280);

//         // Adiciona as imagens ao palco
//         stage.addActor(playButton);
//         stage.addActor(title);

//         // Configura o palco como o processador de entrada
//         Gdx.input.setInputProcessor(stage);

//         // configura musica de fundo
//         backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("./assets/backgroundMusic.mp3"));
//         backgroundMusic.setLooping(true);
//         backgroundMusic.setVolume(0.5f);
//         backgroundMusic.play();
    
//     }

//     @Override
//     public void render(float delta) {
//         ScreenUtils.clear(1, 0, 0, 1);

//         batch.begin();
//         batch.draw(background, 0, 0);
//         batch.end();

//         stage.act(Gdx.graphics.getDeltaTime());
//         stage.draw();
//     }

//     @Override
//     public void resize(int width, int height) {
//         // Este método é chamado quando a tela é redimensionada
//     }

//     @Override
//     public void pause() {
//         // Este método é chamado quando o jogo é pausado
//     }

//     @Override
//     public void resume() {
//         // Este método é chamado quando o jogo é retomado após estar pausado
//     }

//     @Override
//     public void hide() {
//         // Este método é chamado quando a tela não está mais visível
//     }

//     @Override
//     public void dispose() {
//         batch.dispose();
//         background.dispose();
//         stage.dispose();
//     }
// }