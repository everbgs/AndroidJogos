package com.example.administrador.afundanavios;

import android.app.Activity;
import android.os.Bundle;

import AndGraph.AGGameManager;
import AndGraph.AGInputManager;


public class MainActivity extends Activity {

    private AGGameManager vrManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*o parametro true indica que vai usar acelerometro*/
        vrManager = new AGGameManager(this, true);

        vrManager.addScene(new Intro(vrManager));
        vrManager.addScene(new Menu(vrManager));
        vrManager.addScene(new Game(vrManager));
        vrManager.addScene(new Help(vrManager));
        vrManager.addScene(new Credits(vrManager));

    }

    @Override
    protected void onPause() {
        super.onPause();
        vrManager.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AGInputManager.vrTouchEvents.bBackButtonClicked = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        vrManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vrManager.release();
        vrManager = null;
        System.gc();
    }
}
