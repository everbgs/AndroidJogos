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
public class Menu extends AGScene {

    private AGSprite btnJogar;
    private AGSprite btnSobre;
    private AGSprite btnSair;

    private int codClique;

    public Menu(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
        AGSoundManager.vrMusic.setVolume(50,50);

        if (AGSoundManager.vrMusic.isPlaying() == false) {
            AGSoundManager.vrMusic.play();
        }

        codClique = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");

        setSceneBackgroundColor(0.8f, 0.8f, 0.8f);

        btnJogar = createSprite(R.drawable.btnjogar, 1, 1);
        btnJogar.setScreenPercent(70, 20);
        btnJogar.fadeIn(0);
        btnJogar.vrPosition.fX = AGScreenManager.iScreenWidth / 2;
        btnJogar.vrPosition.fY = (AGScreenManager.iScreenHeight / 2) + (btnJogar.getSpriteHeight()) + 32;


        btnSobre = createSprite(R.drawable.btnsobre, 1, 1);
        btnSobre.setScreenPercent(70, 20);
        btnSobre.fadeIn(0);
        btnSobre.vrPosition.fX = AGScreenManager.iScreenWidth / 2;
        btnSobre.vrPosition.fY = AGScreenManager.iScreenHeight / 2;


        btnSair = createSprite(R.drawable.btnsair, 1, 1);
        btnSair.setScreenPercent(70, 20);
        btnSair.fadeIn(0);
        btnSair.vrPosition.fX = AGScreenManager.iScreenWidth / 2;
        btnSair.vrPosition.fY = (AGScreenManager.iScreenHeight / 2) - (btnSair.getSpriteHeight()) - 32;

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {
        AGSoundManager.vrMusic.stop();
    }

    @Override
    public void loop() {

        if (AGInputManager.vrTouchEvents.screenClicked()) {

            if (btnJogar.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrSoundEffects.play(codClique);
                vrGameManager.setCurrentScene(2);
            } else if (btnSobre.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrMusic.pause();
                AGSoundManager.vrSoundEffects.play(codClique);
                vrGameManager.setCurrentScene(4);
            } else if (btnSair.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrSoundEffects.play(codClique);
                vrGameManager.vrActivity.finish();
            }
        }
    }


}
