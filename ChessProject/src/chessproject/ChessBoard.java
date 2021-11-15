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
public class ChessBoard {
    Piece[][] board;
    //ArrayList<Piece> aiArsenal;
    
    public ChessBoard(){
        board = new Piece[7][8];
        //aiArsenal = new ArrayList<>();
        for(int i=0;i<8;i++){ //Add Pawns
            Piece whitePawn = new Piece(true,PieceType.PAWN);
            Piece blackPawn = new Piece(false,PieceType.PAWN);
            placePiece(whitePawn,new int[] {1,i});
            placePiece(blackPawn,new int[] {5,i});
            //aiArsenal.add(blackPawn); 
        }
        
    }
    
    public void placePiece(Piece p, int[] coord){
        board[coord[0]][coord[1]]=p;
        //Prevenir la vue;
    }
    
    public ArrayList<int[]> checkPossibleMoves(Piece p, int[] coord){
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        //Depends on type of piece
        
        //for(int[] c : possibleMoves)
        //    if(!isMovePossible(p.isWhite,c)) possibleMoves.remove(c);       
        return possibleMoves;
    }
    
    public boolean isMovePossible(boolean isAColor,int[] coord){
        if(board[coord[0]][coord[1]]==null) return true;
        else return isAColor!=board[coord[0]][coord[1]].isWhite;
    }
}
