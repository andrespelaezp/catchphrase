package com.zproject.apelaez.catchphrase.game;

public interface GameContract {

    interface View {

        void start();

        void play(boolean loop, float speed);

        void buzzTimeout();

        void showMsg(int resId);

        void nextWord(int pos);
    }

    interface UserActionsListener {

        void initGame();

        void exitGame();

        void finishGame(int redId);

        void swipe();
    }

}
