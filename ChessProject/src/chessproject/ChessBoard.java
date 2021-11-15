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
        switch(p.type){
            case PAWN:
                int[] newCoord = new int[]{coord[0]+(p.isWhite?-1:1),coord[1]};
                if(isMovePossible(p.isWhite,newCoord)<=1) possibleMoves.add(newCoord);
                break;
            case BISHOP:
                int[][] deltaListB = new int[][]{new int[]{-1,-1},new int[]{1,1},new int[]{-1,1},new int[]{1,-1}};
                for(int[] delta : deltaListB){
                    checkMovesInLine(p.isWhite,coord,delta,possibleMoves);
                }
                break;
            case ROOK:
                int[][] deltaListR = new int[][]{new int[]{-1,0},new int[]{1,0},new int[]{0,1},new int[]{0,-1}};
                for(int[] delta : deltaListR){
                    checkMovesInLine(p.isWhite,coord,delta,possibleMoves);
                }
                break;
            case KNIGHT:
                int[][] deltaListN = new int[][]{new int[]{-1,-2},new int[]{1,2},new int[]{-1,2},new int[]{1,-2},new int[]{-2,1},new int[]{2,1},new int[]{-2,-1},new int[]{2,-1}};
                for(int[] delta : deltaListN){
                    int[] testedCoord = new int[] {coord[0]+delta[0],coord[1]+delta[1]};
                    if(isMovePossible(p.isWhite,testedCoord)<=1) possibleMoves.add(testedCoord);
                }
                break;
            case KING:
                int[][] deltaListK = new int[][]{new int[]{-1,-1},new int[]{1,1},new int[]{-1,1},new int[]{1,-1},new int[]{-1,0},new int[]{1,0},new int[]{0,1},new int[]{0,-1}};
                for(int[] delta : deltaListK){
                    int[] testedCoord = new int[] {coord[0]+delta[0],coord[1]+delta[1]};
                    if(isMovePossible(p.isWhite,testedCoord)<=1) possibleMoves.add(testedCoord);
                }
                break;
            case QUEEN:
                int[][] deltaListQ = new int[][]{new int[]{-1,-1},new int[]{1,1},new int[]{-1,1},new int[]{1,-1},new int[]{-1,0},new int[]{1,0},new int[]{0,1},new int[]{0,-1}};
                for(int[] delta : deltaListQ){
                    checkMovesInLine(p.isWhite,coord,delta,possibleMoves);
                }
                break;
            
        };
        //for(int[] c : possibleMoves)
        //    if(!isMovePossible(p.isWhite,c)) possibleMoves.remove(c);       
        return possibleMoves;
    }
    
    private void checkMovesInLine(boolean color, int[] coord, int[] deltas, ArrayList<int[]> moveList){
        int[] newCoord = new int[] {coord[0]+deltas[0],coord[1]+deltas[1]};
        while(isMovePossible(color,newCoord)<=1){
            moveList.add(newCoord.clone());
            newCoord[0]+=deltas[0]; newCoord[1]+=deltas[1];
        }
    }
    
    /**
    * Returns : 
    * 0 : Empty cell
    * 1 : Opposite piece
    * 2 : Own piece
    * 3 : Out of board
    */
    private int isMovePossible(boolean isAColor,int[] coord){
        if(coord[0]<0||coord[0]>7||coord[1]<0||coord[1]>8) return 3;
        if(board[coord[0]][coord[1]]==null) return 0;
        if(isAColor!=board[coord[0]][coord[1]].isWhite) return 1;
        return 2;
    }
}
