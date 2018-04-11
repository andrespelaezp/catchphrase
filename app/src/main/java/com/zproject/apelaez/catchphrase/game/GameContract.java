package com.zproject.apelaez.catchphrase.game;

public interface GameContract {

    interface View {

        void play(boolean loop, float speed);

        void buzzTimeout();

        void showMsg(int resId);

        void nextWord(int pos);
    }

    interface UserActionsListener {

        void initGame();

        void exitGame();

        void exitGame(int redId);

        void swipe();
    }

}
