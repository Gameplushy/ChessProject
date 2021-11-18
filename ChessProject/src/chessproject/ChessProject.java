package chessproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author victo
 */
public class ChessProject extends JFrame {

    ChessBoard chessBoard;
    BoardPressController controller;
    
    public ChessProject(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7,8));
        chessBoard = new ChessBoard();
        controller = new BoardPressController(chessBoard);
        for(int i=0;i<7*8;i++){
            JButton tile = new JButton();
            tile.setPreferredSize(new Dimension(50,50));
            Color colTile = (i%2==(i/8)%2)?new Color(0,0,0): new Color(255,255,255); //Les tuiles noires ont une parité de colonne/rangée égale          
            tile.setBackground(colTile);
            tile.setActionCommand(((Integer)(6-(i/8))).toString()+" "+((Integer)(i%8)).toString());
            tile.addActionListener(controller);
            add(tile);
        }
        this.pack();
        this.setVisible(true);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new ChessProject();
            }
        });
        // TODO code application logic here
    }
    
}
