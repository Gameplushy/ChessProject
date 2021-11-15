/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject;

import java.util.ArrayList;

/**
 *
 * @author victo
 */
public class Piece {
    boolean isWhite;
    PieceType type;
    
    
    public Piece(boolean isWhite, PieceType type){
        this.isWhite=isWhite;
        this.type = type;
    }
}
