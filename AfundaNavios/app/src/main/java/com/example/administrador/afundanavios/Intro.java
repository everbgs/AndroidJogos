package com.example.administrador.afundanavios;

import AndGraph.AGGameManager;
import AndGraph.AGScene;
import AndGraph.AGScreenManager;
import AndGraph.AGSoundManager;
import AndGraph.AGSprite;

/**
 * Created by Administrador on 08/08/2015.
 */
public class Intro extends AGScene {
    private AGSprite vrSpriteUno;
    private boolean exibeAnimacao;

    public Intro(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {

        vrSpriteUno = createSprite(R.drawable.logouno, 1, 1);
        vrSpriteUno.fadeIn(1000);
        vrSpriteUno.vrPosition.fX = AGScreenManager.iScreenWidth / 2;
        vrSpriteUno.vrPosition.fY = AGScreenManager.iScreenHeight / 2;

        vrSpriteUno.setScreenPercent(80, 20);

        AGSoundManager.vrMusic.loadMusic("musica.mp3", true);
        AGSoundManager.vrMusic.play();
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    //metodo obrigatorio definido pela cena
    @Override
    public void loop() {

        if (vrSpriteUno.fadeEnded()) {
            if ((exibeAnimacao = !exibeAnimacao))
                vrSpriteUno.fadeOut(1000);
            else
                vrGameManager.setCurrentScene(1);
        }
    }
}
