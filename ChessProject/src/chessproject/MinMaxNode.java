/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject;

/**
 *
 * @author victo
 */
public class MinMaxNode implements Comparable<MinMaxNode>{
    private int[] from;
    private int[] to;
    private int score;

    public MinMaxNode(int[] from, int[] to, int score) {
        this.from = from;
        this.to = to;
        this.score = score;
    }

    public int[] getFrom() {
        return from;
    }

    public int[] getTo() {
        return to;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(MinMaxNode o) {
        return Integer.compare(score, o.score);
    }
}
