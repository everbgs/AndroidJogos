package com.example.administrador.afundanavios;

import AndGraph.AGGameManager;
import AndGraph.AGInputManager;
import AndGraph.AGScene;
import AndGraph.AGScreenManager;
import AndGraph.AGSoundManager;
import AndGraph.AGSprite;

/**
 * Created by Administrador on 08/08/2015.
 */
public class Credits extends AGScene {

    private AGSprite btnVoltar;
    private int codClique;

    public Credits(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(0.8f, 0.8f, 0.8f);

        btnVoltar = createSprite(R.drawable.voltar, 1, 1);
        btnVoltar.fadeIn(0);
        btnVoltar.setScreenPercent(30, 10);
        btnVoltar.vrPosition.fX = AGScreenManager.iScreenWidth - (btnVoltar.getSpriteWidth() / 2) - 10;
        btnVoltar.vrPosition.fY = AGScreenManager.iScreenHeight - (btnVoltar.getSpriteHeight() / 2) - 10;

        codClique = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

        if (AGInputManager.vrTouchEvents.screenClicked()) {

            if (btnVoltar.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrSoundEffects.play(codClique);
                vrGameManager.setCurrentScene(1);
            }
        }

    }
}
