/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject;

import java.io.Serializable;

/**
 *
 * @author victo
 */
public class Chrono implements Serializable{
    int timeLeft;
    boolean yourTurn;
    transient Thread t;
    
    public Chrono(){
        timeLeft=3*60;
    }
    
    public void startTimer(){
        yourTurn=true;
        Runnable afficheMessage = new Runnable() {
            public void run() {
                try {
                    for (; yourTurn && (timeLeft > 0); timeLeft--) {
                        //decompte.setText("" + i);
                        System.out.println("Time left "+timeLeft);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ie) {
                }
                if (yourTurn) {
                    //decompte.setText("0");
                    //boutonArret.setEnabled(false);
                    System.out.println("0");
                    System.out.println("Trop tard! Perdu!");
                }
            }
        };
        t = new Thread(afficheMessage);
        t.start();
    }
    
    public void turnFinished(){
        yourTurn=false;
        t.interrupt();
    }
}
