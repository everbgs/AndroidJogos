package com.example.administrador.afundanavios;

import java.util.ArrayList;
import java.util.Random;

import AndGraph.AGGameManager;
import AndGraph.AGInputManager;
import AndGraph.AGScene;
import AndGraph.AGScreenManager;
import AndGraph.AGSoundManager;
import AndGraph.AGSprite;
import AndGraph.AGTimer;

/**
 * Created by Administrador on 08/08/2015.
 */
public class Game extends AGScene {

    private AGSprite btnVoltar;
    private AGSprite vrCanhao;
    private AGTimer vrTempoCanhao;
    private AGTimer vrTempoTiro;
    private AGSprite vrFundo;

    private AGSprite[] vetNavios;
    private ArrayList<AGSprite> vetBalas;
    private ArrayList<AGSprite> vetExplosoes;
    private AGSprite[] vetPlacar;


    private int codSomClique;
    private int codSomTiro;
    private int codSomExplosao;

    private Random sorteio;

    private int contador;

    public Game(AGGameManager pManager) {

        super(pManager);

        this.sorteio = new Random();
    }

    @Override
    public void init() {        

        vrFundo = createSprite(R.drawable.textmar, 1, 1);
        vrFundo.setScreenPercent(100, 100);
        vrFundo.vrPosition.fX = AGScreenManager.iScreenWidth / 2;
        vrFundo.vrPosition.fY = AGScreenManager.iScreenHeight / 2;

        codSomClique = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");
        codSomTiro = AGSoundManager.vrSoundEffects.loadSoundEffect("somtiro.mp3");
        codSomExplosao = AGSoundManager.vrSoundEffects.loadSoundEffect("explosao.wav");

        vrTempoCanhao = new AGTimer();
        vrTempoCanhao.restart(20);

        vrTempoTiro = new AGTimer();
        vrTempoTiro.restart(200);

        vetBalas = new ArrayList<AGSprite>();
        vetExplosoes = new ArrayList<AGSprite>();

        btnVoltar = createSprite(R.drawable.voltar, 1, 1);
        btnVoltar.setScreenPercent(30, 10);
        btnVoltar.vrPosition.fX = AGScreenManager.iScreenWidth - (btnVoltar.getSpriteWidth() / 2) - 10;
        btnVoltar.vrPosition.fY = AGScreenManager.iScreenHeight - (btnVoltar.getSpriteHeight() / 2) - 10;

        vetNavios = new AGSprite[2];

        for (int i = 0; i < vetNavios.length; i++) {
            vetNavios[i] = createSprite(R.drawable.navio, 1, 1);
            vetNavios[i].setScreenPercent(30, 20);
        }

        vetPlacar = new AGSprite[5];

        for (int i = 0; i < vetPlacar.length; i++) {
            vetPlacar[i] = createSprite(R.drawable.fonte, 4, 4);
            vetPlacar[i].bAutoRender = false;
            vetPlacar[i].setScreenPercent(10, 8);

            for (int j = 0; j <= 9; j++ )
                vetPlacar[i].addAnimation(1, false, j);

            if (i == 0)
                vetPlacar[i].vrPosition.fX = vetPlacar[i].getSpriteWidth() / 2;
            else
                vetPlacar[i].vrPosition.fX = vetPlacar[i - 1].vrPosition.fX + vetPlacar[i].getSpriteWidth() / 2;

            vetPlacar[i].vrPosition.fY = AGScreenManager.iScreenHeight -
                    vetPlacar[i].getSpriteHeight() / 2;
        }

        //posiciona e move o navio 1
        vetNavios[0].vrPosition.fX = AGScreenManager.iScreenWidth + (vetNavios[0].getSpriteWidth() / 2);
        vetNavios[0].vrPosition.fY = AGScreenManager.iScreenHeight - vetNavios[0].getSpriteHeight();
        vetNavios[0].moveTo(velocidadeNavio(), -vetNavios[0].getSpriteWidth() / 2, vetNavios[0].vrPosition.fY);

        vetNavios[1].vrPosition.fX = vetNavios[1].getSpriteWidth() / 2;
        vetNavios[1].vrPosition.fY = vetNavios[0].vrPosition.fY - vetNavios[1].getSpriteHeight();
        vetNavios[1].moveTo(velocidadeNavio(), AGScreenManager.iScreenWidth + vetNavios[1].getSpriteWidth() / 2,
                vetNavios[1].vrPosition.fY);

        vetNavios[1].iMirror = AGSprite.HORIZONTAL;


        vrCanhao = createSprite(R.drawable.canhao, 1, 1);
        vrCanhao.setScreenPercent(15, 20);
        vrCanhao.vrPosition.fX = AGScreenManager.iScreenWidth / 2;
        vrCanhao.vrPosition.fY = vrCanhao.getSpriteHeight() / 2;
    }

    @Override
    public void render() {
        super.render();

        for (int i = 0; i < vetPlacar.length; i++)
            vetPlacar[i].render();
    }

    private void atualizaPlacar() {
        int c = ++this.contador;
        for (int i = vetPlacar.length - 1; i >= 0 && c > 0; i--) {
            vetPlacar[i].setCurrentAnimation(c % 10);
            c /= 10;
        }

    }

    private AGSprite criarExplosao(float x, float y) {
        AGSprite vrTempSprite = null;
        AGSoundManager.vrSoundEffects.play(codSomExplosao);

        for (int i = 0; i < vetExplosoes.size(); i++) {
            if (vetExplosoes.get(i).bRecycled) {
                vetExplosoes.get(i).bRecycled = false;
                vetExplosoes.get(i).getCurrentAnimation().restart();
                vetExplosoes.get(i).vrPosition.setXY(x, y);

                return vetExplosoes.get(i);
            }
        }

        vrTempSprite = createSprite(R.drawable.explosao, 4, 2);

        vrTempSprite.addAnimation(30, false, 0, 7);
        vrTempSprite.vrPosition.setXY(x, y);
        vetExplosoes.add(vrTempSprite);

        return vrTempSprite;
    }


    private void atualizaExplosoes() {
        for (int i = 0; i < vetExplosoes.size(); i++) {
            if (vetExplosoes.get(i).getCurrentAnimation().isAnimationEnded()) {
                vetExplosoes.get(i).bRecycled = true;
            }
        }
    }

    private void trataColissao() {
        for (int ixBala = 0; ixBala < vetBalas.size(); ixBala++) {

            for (int ixNavio = 0; ixNavio < vetNavios.length; ixNavio++) {
                if (vetBalas.get(ixBala).bRecycled == false && vetBalas.get(ixBala).collide(vetNavios[ixNavio])) {
                    vetBalas.get(ixBala).bRecycled = true;
                    vetBalas.get(ixBala).bVisible = false;
                    vetNavios[ixNavio].bVisible = false;

                    criarExplosao(vetNavios[ixNavio].vrPosition.fX, vetNavios[ixNavio].vrPosition.fY);
                    atualizaPlacar();
                }
            }
        }
    }


    private AGSprite criaTiro(float x, float y) {
        AGSprite vrTemp = null;

        //tenta reciclar
        for (int i = 0; i < vetBalas.size(); i++) {
            if (vetBalas.get(i).bRecycled) {
                vetBalas.get(i).vrPosition.setXY(x, y);
                vetBalas.get(i).bRecycled = false;
                vetBalas.get(i).bVisible = true;
                return vetBalas.get(i);
            }
        }

        vrTemp = createSprite(R.drawable.bala, 1, 1);
        vrTemp.setScreenPercent(5, 3);
        vrTemp.vrPosition.setXY(x, y);
        vetBalas.add(vrTemp);
        return vrTemp;
    }

    private void atualizaTiro() {
        for (int i = 0; i < vetBalas.size(); i++) {
            if (vetBalas.get(i).bRecycled == false) {
                vetBalas.get(i).vrPosition.fY += 20;

                if (vetBalas.get(i).vrPosition.fY > AGScreenManager.iScreenHeight +
                        (vetBalas.get(i).getSpriteHeight() / 2)) {
                    vetBalas.get(i).bRecycled = true;
                }
            }
        }
    }

    private void disparaTiro() {
        vrTempoTiro.update();

        if (vrTempoTiro.isTimeEnded()) {
            if (AGInputManager.vrTouchEvents.screenClicked()) {
                AGSoundManager.vrSoundEffects.play(codSomTiro);

                criaTiro(vrCanhao.vrPosition.fX, vrCanhao.vrPosition.fY + vrCanhao.getSpriteHeight() / 2);
                vrTempoTiro.restart();
            }
        }
    }

    private int velocidadeNavio() {
        return this.sorteio.nextInt(3000) + 500;
    }

    private void trataNavios() {

        for (int i = 0; i < vetNavios.length; i++) {
            if (vetNavios[i].moveEnded() || vetNavios[i].bVisible == false) {
                if (vetNavios[i].iMirror == AGSprite.NONE) {
                    vetNavios[i].iMirror = AGSprite.HORIZONTAL;
                    vetNavios[i].bVisible = true;
                    vetNavios[i].vrPosition.setX(-vetNavios[i].getSpriteWidth() / 2);
                    vetNavios[i].moveTo(velocidadeNavio(), AGScreenManager.iScreenWidth + (vetNavios[i].getSpriteWidth() / 2),
                            vetNavios[i].vrPosition.fY);
                } else {
                    vetNavios[i].iMirror = AGSprite.NONE;
                    vetNavios[i].bVisible = true;
                    vetNavios[i].vrPosition.setX(AGScreenManager.iScreenWidth + vetNavios[i].getSpriteWidth() / 2);
                    vetNavios[i].moveTo(velocidadeNavio(), -(vetNavios[i].getSpriteWidth() / 2),
                            vetNavios[i].vrPosition.fY);
                }
            }
        }
    }

    @Override
    public void loop() {

        trataNavios();
        disparaTiro();
        atualizaTiro();

        if (AGInputManager.vrTouchEvents.screenClicked()) {

            if (btnVoltar.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrSoundEffects.play(codSomClique);
                vrGameManager.setCurrentScene(1);
                return;
            }
        }

        vrTempoCanhao.update();

        if (vrTempoCanhao.isTimeEnded()) {
            float largura = vrCanhao.getSpriteWidth() / 2;

            if (AGInputManager.vrAccelerometer.getAccelX() > 1) {
                vrCanhao.vrPosition.fX += 12;

                if (vrCanhao.vrPosition.fX > (AGScreenManager.iScreenWidth - largura)) {
                    vrCanhao.vrPosition.fX = AGScreenManager.iScreenWidth - largura;
                }
            } else if (AGInputManager.vrAccelerometer.getAccelX() < -1) {
                vrCanhao.vrPosition.fX -= 12;

                if (vrCanhao.vrPosition.fX < largura) {
                    vrCanhao.vrPosition.fX = largura;
                }
            }

            vrTempoCanhao.restart();
        }

        trataColissao();
        atualizaExplosoes();
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

}
