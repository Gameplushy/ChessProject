package chessproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author victo
 */
public class ChessProject extends JFrame implements AutreEventListener {

    ChessBoard chessBoard;
    BoardPressController controller;
    JButton[][] buttonGrid;
    
    public ChessProject(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttonGrid = new JButton[7][8];
        setLayout(new GridLayout(7,8));
        chessBoard = new ChessBoard();
        chessBoard.feedAEN(this); 
        controller = new BoardPressController(chessBoard);
        for(int i=0;i<7*8;i++){
            JButton tile = new JButton();
            tile.setPreferredSize(new Dimension(50,50));
            Color colTile = (i%2==(i/8)%2)?new Color(0,0,0): new Color(255,255,255); //Les tuiles noires ont une parité de colonne/rangée égale          
            tile.setBackground(colTile);
            tile.setActionCommand(((Integer)(6-(i/8))).toString()+" "+((Integer)(i%8)).toString());
            buttonGrid[6-(i/8)][i%8] = tile;
            tile.addActionListener(controller);
            add(tile);
        }
        chessBoard.initialiserPlateau();
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

    @Override
    public void actionADeclancher(AutreEvent evt) {
        String[] commList = evt.getDonnee().toString().split(":");
        switch(commList[0]){
            case "ADD":{
                int abs = Integer.parseInt(commList[1]), ord = Integer.parseInt(commList[2]);
                String piecepng = "Chess_";
                switch(commList[3]){
                    case "QUEEN" : piecepng+="q"; break;
                    case "KING" : piecepng+="k"; break;
                    case "BISHOP" : piecepng+="b"; break;
                    case "KNIGHT" : piecepng+="n"; break;
                    case "PAWN" : piecepng+="p"; break;
                    case "ROOK" : piecepng+="r"; break;
                }
                piecepng+=(commList[4].equals("true")?"l":"d")+"t60.png";
                Icon ico = new ImageIcon("src/images/"+piecepng);
                buttonGrid[abs][ord].setIcon(ico);
                break;
            }
            case "DEL":{
                int abs = Integer.parseInt(commList[1]), ord = Integer.parseInt(commList[2]);
                buttonGrid[abs][ord].setIcon(null);
                break;
            }
            default:
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.                
        }
    }
    
}
