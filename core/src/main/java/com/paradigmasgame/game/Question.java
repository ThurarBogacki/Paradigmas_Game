// package paradigmas.gauchovoador;
package com.paradigmasgame.game;

import com.badlogic.gdx.utils.Array;

public class Question {
    private final int id;
    private final String text;
    private final Array<String> options;
    private final int answer;

   // Construtor usado na serialização do arquivo JSON na classe Quiz.
    private Question() {
        id = -1;
        text = null;
        options = null;
        answer = -1;
    }
    public Question(int id, String text, Array<String> options, int answer) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", options=" + options +
                ", answer=" + answer +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Array<String> getOptions() {
        return options;
    }

    public int getAnswer() {
        return answer;
    }
}