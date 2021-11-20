/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author victo
 */
public class ChessBoard implements Serializable {
    Piece[][] board;
    Piece selectedPiece; int[] piecePosition;
    boolean isTurnForWhite;
    AutreEventNotifieur aen;
    ArrayList<int[]> possibleMoves;
    //ArrayList<Piece> aiArsenal;
    
    public ChessBoard(){
        aen = new AutreEventNotifieur();
        board = new Piece[7][8];
        isTurnForWhite=true; 
    }
    
    public void initialiserPlateau(){
        //aiArsenal = new ArrayList<>();
        for(int i=0;i<8;i++){ //Add Pawns
            Piece whitePawn = new Piece(true,PieceType.PAWN);
            Piece blackPawn = new Piece(false,PieceType.PAWN);
            placePiece(whitePawn,new int[] {1,i});
            placePiece(blackPawn,new int[] {5,i});
            //aiArsenal.add(blackPawn); 
        }
        for(int i : new int[] {0,7}){
            Piece whiteRook = new Piece(true,PieceType.ROOK);
            Piece blackRook = new Piece(false,PieceType.ROOK);
            placePiece(whiteRook, new int[] {0,i});
            placePiece(blackRook, new int[] {6,i});
        }
        for(int i : new int[] {1,6}){
            Piece whiteKnight = new Piece(true,PieceType.KNIGHT);
            Piece blackKnight = new Piece(false,PieceType.KNIGHT);
            placePiece(whiteKnight,new int[] {0,i});
            placePiece(blackKnight,new int[] {6,i});
        }
        for(int i : new int[] {2,5}){
            Piece whiteBishop = new Piece(true,PieceType.BISHOP);
            Piece blackBishop = new Piece(false,PieceType.BISHOP);
            placePiece(whiteBishop,new int[] {0,i});
            placePiece(blackBishop,new int[] {6,i});
        }
        Piece whiteQueen = new Piece(true,PieceType.QUEEN);
        Piece blackQueen = new Piece(false,PieceType.QUEEN);
        placePiece(whiteQueen,new int[] {0,3});
        placePiece(blackQueen,new int[] {6,3});
        Piece whiteKing = new Piece(true,PieceType.KING);
        Piece blackKing = new Piece(false,PieceType.KING);
        placePiece(whiteKing,new int[] {0,4});
        placePiece(blackKing,new int[] {6,4});
    }
    
    public void feedAEN(AutreEventListener ael){
        aen.addAutreEventListener(ael);
    }
    
    public void placePiece(Piece p, int[] coord){
        Piece capturedPiece = board[coord[0]][coord[1]];
        int winCondition = 0;
        if(capturedPiece!=null && capturedPiece.getType()==PieceType.KING){
            winCondition=capturedPiece.isWhite()?1:-1;
        }
        board[coord[0]][coord[1]]=p;
        aen.diffuserAutreEvent(new AutreEvent(this,"ADD:"+coord[0]+":"+coord[1]+":"+p.getType().toString()+":"+p.isWhite())); //Prevenir la vue
        if(winCondition!=0){
            System.out.println("Le roi "+(winCondition==1?"blanc":"noir")+" est mort ! Longue vie au roi "+(winCondition==1?"noir !":"blanc !")); //Transformer ça en pop-up qui éteint le jeu
        }
        if(selectedPiece.getType()==PieceType.PAWN && ((selectedPiece.isWhite() && coord[0]==6) || (!selectedPiece.isWhite() && coord[0]==0))){
            System.out.println("Promotion! (q)ueen (r)ook (k)night (b)ishop");
            //TODO
        }
    }
    
    public void doWhenPress(int abs, int ord){
        ArrayList<int[]> one = new ArrayList<>();
        one.add(new int[]{0,0});
        one.add(new int[]{1,2});   
        int[] two = new int[]{1,2};
        if(selectedPiece==null|| !isMoveLegal(new int[] {abs,ord})) checkPossibleMoves(abs,ord);
        else{
            deletePiece();
            placePiece(selectedPiece,new int[]{abs,ord});
            endMove();
        }; //move
    }
    
    private boolean isMoveLegal(int[] chosenMove){
        for(int[] legalMove : possibleMoves){
            if(Arrays.equals(legalMove, chosenMove)) return true;
        }
        return false;
    }
    
    private void deletePiece(){
        board[piecePosition[0]][piecePosition[1]] = null;
        aen.diffuserAutreEvent(new AutreEvent(this,"DEL:"+piecePosition[0]+":"+piecePosition[1]));
    }
    
    private void endMove(){
        selectedPiece=null;
        piecePosition=null;
        possibleMoves=null;
        isTurnForWhite=!isTurnForWhite;
    }
    
    private void checkPossibleMoves(int abs, int ord){
        selectedPiece = board[abs][ord];
        piecePosition = new int[]{abs,ord};
        possibleMoves = new ArrayList<>();
        if(selectedPiece==null||selectedPiece.isWhite()!=isTurnForWhite){ System.out.println("Not your piece!"); return;} //will be empty
        else System.out.println("Is a piece!");
        //Depends on type of piece
        switch(selectedPiece.getType()){
            case PAWN:
                int[] newCoord = new int[]{piecePosition[0]+(selectedPiece.isWhite()?1:-1),piecePosition[1]};
                if(isMovePossible(selectedPiece.isWhite(),newCoord)<=1) possibleMoves.add(newCoord);
                break;
            case BISHOP:
                int[][] deltaListB = new int[][]{new int[]{-1,-1},new int[]{1,1},new int[]{-1,1},new int[]{1,-1}};
                for(int[] delta : deltaListB){
                    checkMovesInLine(selectedPiece.isWhite(),piecePosition,delta,possibleMoves);
                }
                break;
            case ROOK:
                int[][] deltaListR = new int[][]{new int[]{-1,0},new int[]{1,0},new int[]{0,1},new int[]{0,-1}};
                for(int[] delta : deltaListR){
                    checkMovesInLine(selectedPiece.isWhite(),piecePosition,delta,possibleMoves);
                }
                break;
            case KNIGHT:
                int[][] deltaListN = new int[][]{new int[]{-1,-2},new int[]{1,2},new int[]{-1,2},new int[]{1,-2},new int[]{-2,1},new int[]{2,1},new int[]{-2,-1},new int[]{2,-1}};
                for(int[] delta : deltaListN){
                    int[] testedCoord = new int[] {piecePosition[0]+delta[0],piecePosition[1]+delta[1]};
                    if(isMovePossible(selectedPiece.isWhite(),testedCoord)<=1) possibleMoves.add(testedCoord);
                }
                break;
            case KING:
                int[][] deltaListK = new int[][]{new int[]{-1,-1},new int[]{1,1},new int[]{-1,1},new int[]{1,-1},new int[]{-1,0},new int[]{1,0},new int[]{0,1},new int[]{0,-1}};
                for(int[] delta : deltaListK){
                    int[] testedCoord = new int[] {piecePosition[0]+delta[0],piecePosition[1]+delta[1]};
                    if(isMovePossible(selectedPiece.isWhite(),testedCoord)<=1) possibleMoves.add(testedCoord);
                }
                break;
            case QUEEN:
                int[][] deltaListQ = new int[][]{new int[]{-1,-1},new int[]{1,1},new int[]{-1,1},new int[]{1,-1},new int[]{-1,0},new int[]{1,0},new int[]{0,1},new int[]{0,-1}};
                for(int[] delta : deltaListQ){
                    checkMovesInLine(selectedPiece.isWhite(),piecePosition,delta,possibleMoves);
                }
                break;
            
        }
        for(int[] c : possibleMoves)
            System.out.println(c[0]+""+c[1]);
        //return possibleMoves;
    }
    
    private void checkMovesInLine(boolean color, int[] coord, int[] deltas, ArrayList<int[]> moveList){
        int[] newCoord = new int[] {coord[0]+deltas[0],coord[1]+deltas[1]};
        while(isMovePossible(color,newCoord)==0){
            moveList.add(newCoord.clone());
            newCoord[0]+=deltas[0]; newCoord[1]+=deltas[1];
        }
        if(isMovePossible(color,newCoord)==1) moveList.add(newCoord);
    }
    
    /**
    * Returns : 
    * 0 : Empty cell
    * 1 : Opposite piece
    * 2 : Own piece
    * 3 : Out of board
    */
    private int isMovePossible(boolean isAColor,int[] coord){
        if(coord[0]<0||coord[0]>=7||coord[1]<0||coord[1]>=8) return 3;
        if(board[coord[0]][coord[1]]==null) return 0;
        if(isAColor!=board[coord[0]][coord[1]].isWhite()) return 1;
        return 2;
    }
}
