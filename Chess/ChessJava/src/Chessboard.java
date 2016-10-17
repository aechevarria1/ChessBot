import java.util.ArrayList;
import java.util.List;

public class Chessboard {
	
	private long whiteKing;
	private long whiteQueen;
	private long whiteBishop;
	private long whiteKnight;
	private long whiteRook;
	private long whitePawn;
	private long blackKing;
	private long blackQueen;
	private long blackBishop;
	private long blackKnight;
	private long blackRook;
	private long blackPawn;

	//Board Instantiation
	public Chessboard(){
		//To make an instance of a new Chessboard
		whiteKing   = 0b0000000000001000L;
		whiteQueen  = 0b0000000000010000L;
		whiteBishop = 0b0000000000100100L;
		whiteKnight = 0b0000000001000010L;
		whiteRook   = 0b0000000010000001L;
		whitePawn   = 0b1111111100000000L;
		
		blackKing   = 0b0000100000000000000000000000000000000000000000000000000000000000L;
		blackQueen  = 0b0001000000000000000000000000000000000000000000000000000000000000L;
		blackBishop = 0b0010010000000000000000000000000000000000000000000000000000000000L;
		blackKnight = 0b0100001000000000000000000000000000000000000000000000000000000000L;
		blackRook   = 0b1000000100000000000000000000000000000000000000000000000000000000L;
		blackPawn   = 0b0000000011111111000000000000000000000000000000000000000000000000L;
	}
	public Chessboard(String directory, String infoType){
		//To load a chess game.
		//TODO
	}

	//Move Generation
	public Long generateKingMoves (int teamColor){
		//White Team Color = 1
		//Black Team Color = 0
		//TODO
		return new Long(1);
	}
	public Long generateQueenMoves (int teamColor){
		//White Team Color = 1
		//Black Team Color = 0
		//TODO
		return new Long(1);
	}
	public Long generateBishopMoves (int teamColor){
		//White Team Color = 1
		//Black Team Color = 0
		//TODO
		return new Long(1);
	}
	public Long generateKnightMoves (int teamColor){
		//White Team Color = 1
		//Black Team Color = 0
		//TODO
		return new Long(1);
	}
	public Long generateRookMoves (int teamColor){
		//White Team Color = 1
		//Black Team Color = 0
		//TODO
		return new Long(1);
	}
	public Long generatePawnMoves (int teamColor){
		//White Team Color = 1
		//Black Team Color = 0
		//TODO
		return new Long(1);
	}
	
	// Piece Combinations
	private Long whitePieces(){
		Long allWhitePieces = this.whiteKing|this.whiteQueen|this.whiteBishop|this.whiteKnight|this.whiteRook|this.whitePawn;
		return allWhitePieces;
	}
	private Long blackPieces(){
		Long allBlackPieces = this.blackKing|this.blackQueen|this.blackBishop|this.blackKnight|this.blackRook|this.blackPawn;
		return allBlackPieces;
	}
	private Long allPieces(){
		Long allPieces = this.whiteKing|this.whiteQueen|this.whiteBishop|this.whiteKnight|this.whiteRook|this.whitePawn|this.blackKing|this.blackQueen|this.blackBishop|this.blackKnight|this.blackRook|this.blackPawn;
		return allPieces;
	}
	
	//Visualizing Boards
	private static List<Integer> bitPositions(long number) {
		//Get the integer indeces of the ones in a 64 bit long
	    List<Integer> positions = new ArrayList<>();
	    int position = 63;
	    while (number != 0) {
	        if ((number & 1) != 0) {
	            positions.add(position);
	        }
	        position--;
	        number = number >>> 1;
	    }
	    return positions;
	}
	public static String gridToString(String[][] board){
		//Convert an 8 by 8 array into a string version of a board
		String val = new String();
		val = "\ta\tb\tc\td\te\tf\tg\th\n";
		   for(int i = 0; i < 8; i++){
			   val = val + Integer.toString(i+1);
		      for(int j = 0; j < 8; j++){
		    	  val = val + "\t" + board[i][j];
		      }
		      val = val + "\n";
		   }
		   return val;
		}
	public String[][] makeFullBoard(){
		//Create an 8x8 array which contains all the pieces of the board
		
		//Make board object and collect pieces
		String[][] board = new String[8][8];
		long [] whitePieces = {this.whiteKing, this.whiteQueen, this.whiteBishop, this.whiteKnight, this.whiteRook, this.whitePawn};
		long [] blackPieces = {this.blackKing, this.blackQueen, this.blackBishop, this.blackKnight, this.blackRook, this.blackPawn};
		String [] pieceTypes = {"K","Q","B","N","R","P"};
		
		//Populate the board
		for (int i=0;i<6;i++){
			long currentPieceWhite = whitePieces[i];
			long currenntPieceBlack = blackPieces[i];
			List<Integer> whitePieceIndeces = bitPositions(currentPieceWhite);
			List<Integer> blackPieceIndeces = bitPositions(currenntPieceBlack);
			for(int j:whitePieceIndeces){
				board[j/8][j%8] = "W"+pieceTypes[i];
			}
			for(int j:blackPieceIndeces){
				board[j/8][j%8] = "B"+pieceTypes[i];
			}
		}
		return board;
	}
	public String toString(){
		//Prints a representation of the board
		String[][] myBoard = this.makeFullBoard();
		return gridToString(myBoard);
	}
}
