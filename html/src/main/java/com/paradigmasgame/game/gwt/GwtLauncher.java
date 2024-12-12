package com.paradigmasgame.game.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
// import com.paradigmasgame.game.Paradigmas_Game;

import com.Jardim.game.Jardim;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
        @Override
        public GwtApplicationConfiguration getConfig () {
            // Definindo um tamanho fixo para o jogo
            return new GwtApplicationConfiguration(1280, 720);  // Substitua pelos valores desejados
        }

        @Override
        public ApplicationListener createApplicationListener () {
            return (ApplicationListener) new Jardim();
        }
}
