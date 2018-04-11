package com.zproject.apelaez.catchphrase.util;

import android.content.Context;

import com.zproject.apelaez.catchphrase.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utilities {

    public static Word getRandomWord(Context context) {
        int count = 0;
        int rand = randomValue(1, 490);

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open("dictionary.txt")));
            String mLine = reader.readLine();
            while (mLine != null) {
                mLine = reader.readLine();
                count++;
                if (count == rand)
                    return new Word(mLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Word("Catchphrase!");
    }

    public static int randomValue(int min, int max) {
        return min + (int) (Math.random() * max);
    }

}
