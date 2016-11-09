import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Chessboard {
	
	private long WK;
	private long WQ;
	private long WB;
	private long WN;
	private long WR;
	private long WP;
	private long BK;
	private long BQ;
	private long BB;
	private long BN;
	private long BR;
	private long BP;
	private long[] boardInformation;
	HashMap<Integer,Long> kingMoveMap;
	HashMap<Integer,Long> knightMoveMap;
	//Board Instantiation
	public Chessboard(){
		//To make an instance of a new Chessboard
		WK = 0b0000000000001000L;
		WQ = 0b0000000000010000L;
		WB = 0b0000000000100100L;
		WN = 0b0000000001000010L;
		WR = 0b0000000010000001L;
		WP = 0b1111111100000000L;
		
		BK = 0b0000100000000000000000000000000000000000000000000000000000000000L;
		BQ = 0b0001000000000000000000000000000000000000000000000000000000000000L;
		BB = 0b0010010000000000000000000000000000000000000000000000000000000000L;
		BN = 0b0100001000000000000000000000000000000000000000000000000000000000L;
		BR = 0b1000000100000000000000000000000000000000000000000000000000000000L;
		BP = 0b0000000011111111000000000000000000000000000000000000000000000000L;
		boardInformation = new long[] {this.WK,this.WQ,this.WB,this.WN,this.WR,this.WP,this.BK,this.BQ,this.BB,this.BN,this.BR,this.BP};
	}
	public Chessboard(String directory, String infoType){
		//To load a chess game.
		//TODO
	}

	//Preloading All Possible King and Knight Moves
	public void loadKnightMoves(){

		Long leftMostColumn         = 0b1000000010000000100000001000000010000000100000001000000010000000L;
		Long rightMostColumn        = 0b0000000100000001000000010000000100000001000000010000000100000001L;
		Long leftSecondMostColumn   = leftMostColumn>>>1;
		Long rightSecondMostColumn  = rightMostColumn<<1;

		for (int i=0;i<64;i++){
			Long s = 0b1L<<i;
			Long moves;
			Long a = s<<17;
			Long b = s<<15;
			Long c = s<<10;
			Long d = s<<6;
			Long e = s>>>6;
			Long f = s>>>10;
			Long g = s>>>15;
			Long h = s>>>17;
			//In the leftmost column
			if ((s & ~leftMostColumn)==0){
				moves = b|d|f|h;
			}
			//In the second leftmost column
			else if ((s & ~leftSecondMostColumn)==0){
				moves = a|b|d|f|g|h;
			}
			//In the rightmost column
			else if ((s & ~rightMostColumn)==0){
				moves = a|c|e|g;
			}
			//In the second rightmost column
			else if ((s & ~rightSecondMostColumn)==0){
				moves = a|b|c|e|g|h;
			}
			//In the middle columns
			else{
				moves = a|b|c|d|e|f|g|h;
			}
			knightMoveMap.put(i, moves);
		}
	}
	public void loadKingMoves(){
		//TODO
		kingMoveMap
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
		
		Long knights;
		Long validMoves = 0b0L;
		Long friendlyPieces;
		
		if (teamColor==1){
			knights = WN; 
			friendlyPieces = whitePieces();
		}
		else{
			knights=BN;
			friendlyPieces = blackPieces();
		}
		List<Integer> ind = bitPositions(knights);
		for (int i=0;i<ind.size();i++){
			validMoves = validMoves| knightMoveMap.get(63-ind.get(i));
		}
		validMoves = validMoves & ~friendlyPieces;
		return validMoves;
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
		
		Long validMoves = 0b0L;
		Long allPieces = allPieces();
		Long leftMostColumn  = 0b1000000010000000100000001000000010000000100000001000000010000000L;
		Long rightMostColumn = 0b0000000100000001000000010000000100000001000000010000000100000001L;
		
		if (teamColor==1){
			Long opponentPieces = blackPieces();
			//Standard Pawn Movement
			Long forwardMove  =  WP<<8 & ~allPieces;
			Long captureLeft  = (WP& ~leftMostColumn) <<9 & opponentPieces;
			Long captureRight = (WP& ~rightMostColumn)<<7 & opponentPieces;
			validMoves = forwardMove|captureLeft|captureRight;
			
			//double pawn movement
			Long unmovedWhitePawns = WP&(255L<<8);
			Long notBlockedWhitePawns = unmovedWhitePawns & ~(allPieces>>>8) & ~(allPieces>>>16);
			validMoves = validMoves | (notBlockedWhitePawns<<16);
			
			//Check if this does the same thing.
			//Long doubleMove = (unmovedWhitePawns<<16 & ~allPieces & ~(allPieces<<8)) & (255L<<24);
			//validMoves = validMoves | doubleMove;
			
			//En Passant
			//TODO
		}
		else if (teamColor==0){
			//Standard Pawn Movement
			Long opponentPieces = whitePieces();
			Long forwardMove  = BP>>>8 & ~allPieces;
			Long captureLeft  = (BP& ~leftMostColumn) >>>7 & opponentPieces;
			Long captureRight = (BP& ~rightMostColumn)>>>9 & opponentPieces;
			validMoves = forwardMove|captureLeft|captureRight;
			
			
			//double pawn movement
			Long unmovedBlackPawns = BP&(255L<<48);
			Long notBlockedBlackPawns = unmovedBlackPawns & ~(allPieces<<8) & ~(allPieces<<16);
			validMoves = validMoves | (notBlockedBlackPawns>>>16);
			
			//Check if this does the same thing.
			//Long doubleMove = (unmovedBlackPawns>>16 & ~allPieces & ~(allPieces>>8)) & (255L<<32);
			//validMoves = validMoves | doubleMove;
			
			//En Passant
			//TODO
		}
		return validMoves;
	}
	
	public void updateBoardInformation(){
		this.boardInformation = new long[] {this.WK,this.WQ,this.WB,this.WN,this.WR,this.WP,this.BK,this.BQ,this.BB,this.BN,this.BR,this.BP};
	}
	
	// Piece Combinations
	private Long whitePieces(){
		Long allWhitePieces = this.WK|this.WQ|this.WB|this.WN|this.WR|this.WP;
		return allWhitePieces;
	}
	private Long blackPieces(){
		Long allBlackPieces = this.BK|this.BQ|this.BB|this.BN|this.BR|this.BP;
		return allBlackPieces;
	}
	private Long allPieces(){
		Long allPieces = this.WK|this.WQ|this.WB|this.WN|this.WR|this.WP|this.BK|this.BQ|this.BB|this.BN|this.BR|this.BP;
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
		//long [] whitePieces = {this.whiteKing, this.whiteQueen, this.whiteBishop, this.whiteKnight, this.whiteRook, this.whitePawn};
		//long [] blackPieces = {this.blackKing, this.blackQueen, this.blackBishop, this.blackKnight, this.blackRook, this.blackPawn};
		long [] whitePieces = Arrays.copyOfRange(this.boardInformation, 0, 6);
		long [] blackPieces = Arrays.copyOfRange(this.boardInformation, 6, 12);
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
