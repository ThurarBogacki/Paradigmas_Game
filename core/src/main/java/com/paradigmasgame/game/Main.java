// package paradigmas.gauchovoador;

// import com.badlogic.gdx.Game;
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.graphics.OrthographicCamera;
// import com.badlogic.gdx.graphics.g2d.BitmapFont;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.math.Vector3;
// import com.badlogic.gdx.utils.viewport.FitViewport;
// import com.badlogic.gdx.utils.viewport.Viewport;

// public class Main extends Game {
//     public SpriteBatch batch;
//     public BitmapFont font;
//     public OrthographicCamera camera;
//     public Viewport viewport;
//     static public Vector3 worldCoordinates;

//     static public final float WORLD_WIDTH = 1280;
//     static public final float WORLD_HEIGHT = 720;

//     @Override
//     public void create() {
//         batch = new SpriteBatch();
//         font = new BitmapFont();

//         camera = new OrthographicCamera();
//         camera.setToOrtho(false);
//         viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
//         worldCoordinates = new Vector3();

//         this.setScreen(new HomeScreen(this));
//     }

//     public void updateCoordinates() {
//         camera.update();
//         worldCoordinates = camera.unproject(
//                 new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0),
//                 viewport.getScreenX(),
//                 viewport.getScreenY(),
//                 viewport.getScreenWidth(),
//                 viewport.getScreenHeight()
//         );
//     }

//     @Override
//     public void render() {
//         super.render();
//     }

//     @Override
//     public void dispose() {
//         batch.dispose();
//         font.dispose();
//     }

// }