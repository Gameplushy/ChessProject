/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject;

import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author victo
 */
public class EndGame extends JFrame{
    public EndGame(boolean whiteAlive){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2,1));
        JLabel flavorText = new JLabel("Le roi "+(whiteAlive?"noir":"blanc")+" est mort ! Longue vie au roi "+(whiteAlive?"blanc !":"noir !"));
        add(flavorText);
        ImageIcon kingpng = new ImageIcon("src/images/Chess_k"+(whiteAlive?"l":"d")+"t60.png"); 
        JLabel image = new JLabel(kingpng);
        add(image);
        this.pack();
        this.setVisible(true);
    }
}
