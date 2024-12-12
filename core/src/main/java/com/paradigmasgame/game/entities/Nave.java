// package com.paradigmasgame.game.entities;

// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.Sprite;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.math.Rectangle;
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Input;

// public class Nave {

//     private Sprite sprite;
//     private Rectangle bounds;
//     private float speed;

//     public Nave(Texture texture, float x, float y, float speed) {
//         this.sprite = new Sprite(texture);
//         this.sprite.setPosition(x, y);
//         this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
//         this.speed = speed;
//     }

//     public void update(float deltaTime) {
//         if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//             move(-speed * deltaTime, 0);
//         }
//         if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//             move(speed * deltaTime, 0);
//         }
//         if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//             move(0, speed * deltaTime);
//         }
//         if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//             move(0, -speed * deltaTime);
//         }

//         // Limitar a nave à tela
//         float screenWidth = Gdx.graphics.getWidth();
//         float screenHeight = Gdx.graphics.getHeight();

//         if (bounds.x < 0) bounds.x = 0;
//         if (bounds.y < 0) bounds.y = 0;
//         if (bounds.x + bounds.width > screenWidth) bounds.x = screenWidth - bounds.width;
//         if (bounds.y + bounds.height > screenHeight) bounds.y = screenHeight - bounds.height;

//         sprite.setPosition(bounds.x, bounds.y);
//     }

//     private void move(float dx, float dy) {
//         bounds.x += dx;
//         bounds.y += dy;
//     }

//     public void render(SpriteBatch batch) {
//         sprite.draw(batch);
//     }

//     public Rectangle getBounds() {
//         return bounds;
//     }
// }