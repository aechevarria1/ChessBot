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
	HashMap<Integer,Long> kingMoveMap = new HashMap<Integer,Long>();
	HashMap<Integer,Long> knightMoveMap = new HashMap<Integer,Long>();
	HashMap<Integer,HashMap<Long,Long>> fileOccupancyMoves = new HashMap<Integer,HashMap<Long,Long>>(); // maps square number to a hashmap that maps occupancy to allowed moves
	
	//For calculating diagonal sliding moves
	HashMap<Integer,HashMap<Long,Long>> diagOccupancyMoves = new HashMap<Integer,HashMap<Long,Long>>(); // maps square number to a hashmap that maps occupancy to allowed moves
	int[] CW45Order = { 7, 6,15, 5,14,23, 4,13,
					 22,31, 3,12,21,30,39, 2,
					 11,20,29,38,47, 1,10,19,
					 28,37,46,55, 0, 9,18,27,
					 36,45,54,63, 8,17,26,35,
					 44,53,62,16,25,34,43,52,
					 61,24,33,42,51,60,32,41,
					 50,59,40,49,58,48,57,56};
	int[] CCW45Order= { 0, 8, 1,16, 9, 2,24,17,
					 10, 3,32,25,18,11, 4,40,
					 33,26,19,12, 5,48,41,34,
					 27,20,13, 6,56,49,42,35,
					 28,21,14, 7,57,50,43,36,
					 29,22,15,58,51,44,37,30,
					 23,59,52,45,38,31,60,53,
					 46,39,61,54,47,62,55,63};
	HashMap<Integer,Integer> CW45Map = new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> CCW45Map = new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> reverseCW45Map = new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> reverseCCW45Map = new HashMap<Integer,Integer>();
	int [] positionRows = {0,1,1,2,2,2,3,3,3,3,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6,6,6,6,7,7,7,7,7,7,7,7,8,8,8,8,8,8,8,9,9,9,9,9,9,10,10,10,10,10,11,11,11,11,12,12,12,13,13,14};
	int [] calculatedShifts = {0,1,3,6,10,15,21,28,36,43,49,54,58,61,63};
	int [] sizeOfRow = {1,2,3,4,5,6,7,8,7,6,5,4,3,2,1};

	//Board Instantiation
	public Chessboard(){
		build45RotationMaps();
		loadKingMoves();
		loadKnightMoves();
		loadSlidingHorizOccupancyHashMap();
		loadSlidingDiagOccupancyHashMapDiag();
		
		//To make an instance of a new Chessboard
		WK = 0b0000000000010000L;
		WQ = 0b0000000000001000L;
		WB = 0b0000000000100100L;
		WN = 0b0000000001000010L;
		WR = 0b0000000010000001L;
		WP = 0b1111111100000000L;
		
		BK = 0b0001000000000000000000000000000000000000000000000000000000000000L;
		BQ = 0b0000100000000000000000000000000000000000000000000000000000000000L;
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
	public Chessboard(long[] givenBoardInformation){
		//To load a chess game from a collection of information about pieces.
		build45RotationMaps();
		loadKingMoves();
		loadKnightMoves();
		loadSlidingHorizOccupancyHashMap();
		loadSlidingDiagOccupancyHashMapDiag();
		
		WK = givenBoardInformation[0];
		WQ = givenBoardInformation[1];
		WB = givenBoardInformation[2];
		WN = givenBoardInformation[3];
		WR = givenBoardInformation[4];
		WP = givenBoardInformation[5];
		BK = givenBoardInformation[6];
		BQ = givenBoardInformation[7];
		BB = givenBoardInformation[8];
		BN = givenBoardInformation[9];
		BR = givenBoardInformation[10];
		BP = givenBoardInformation[11];
		boardInformation = givenBoardInformation;
	}

	//Preloading All Possible King and Knight and Sliding Moves
	public void loadKnightMoves(){
		//Pre-loads the possible moves a knight can make.
		Long rightMostColumn       = 0b1000000010000000100000001000000010000000100000001000000010000000L;
		Long leftMostColumn        = 0b0000000100000001000000010000000100000001000000010000000100000001L;
		Long leftSecondMostColumn  = leftMostColumn<<1;
		Long rightSecondMostColumn = rightMostColumn>>>1;

		for (int i=0;i<64;i++){
			Long s = 0b1L<<i;
			Long moves;
			Long a = s<<15;
			Long b = s<<17;
			Long c = s<<6;
			Long d = s<<10;
			Long e = s>>>10;
			Long f = s>>>6;
			Long g = s>>>17;
			Long h = s>>>15;
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
		//Pre-loads the possible moves a king can make.
		Long rightMostColumn = 0b1000000010000000100000001000000010000000100000001000000010000000L;
		Long leftMostColumn  = 0b0000000100000001000000010000000100000001000000010000000100000001L;

		for (int i=0;i<64;i++){
			Long s = 0b1L<<i;
			Long moves;
			Long a = s<<7;
			Long b = s<<8;
			Long c = s<<9;
			Long d = s>>>1;
			Long e = s<<1;
			Long f = s>>>9;
			Long g = s>>>8;
			Long h = s>>>7;
			//In the leftmost column
			if ((s & ~leftMostColumn)==0){
				moves = b|c|e|g|h;
			}
			//In the rightmost column
			else if ((s & ~rightMostColumn)==0){
				moves = a|b|d|f|g;
			}
			//In the middle columns
			else{
				moves = a|b|c|d|e|f|g|h;
			}
			kingMoveMap.put(i, moves);
		}
	}
	public void loadSlidingHorizOccupancyHashMap(){
		//Pre-loads the available moves for horizontally sliding pieces. Columns must be converted to rows before use for vertical movement.

		for (int i=0;i<64;i++){
			fileOccupancyMoves.put(i, new HashMap<Long,Long>());
			int rowNum = i/8;
			int shift = 8*rowNum;
			Long maxOccupancy = 255L;
			Long currentPosition = 0b1L<<(i-shift); 
			for (Long j=0L;j<=maxOccupancy;j++){
				Long occupancy = j | currentPosition;
				if (!fileOccupancyMoves.get(i).containsKey(occupancy)){
					Long moves = 0b0L;

					// Get moves to the right
					int k=1;
					Long newMove = 0b1L;
					while (newMove!=0){
						newMove = ((currentPosition<<k)& maxOccupancy); //only keep moves on this row
						moves = moves | newMove;
						newMove = newMove & ~occupancy; //stop when we hit another piece
						k = k+1;
					}
					// Get moves to the left
					k=1;
					newMove = 0b1L;
					while (newMove!=0){
						newMove = ((currentPosition>>>k)& maxOccupancy); //only keep moves on this row
						moves = moves | newMove; //add this to the moves
						newMove = newMove & ~occupancy; //stop when we hit another piece
						k = k+1;
					}
					moves = moves<<shift;
					fileOccupancyMoves.get(i).put(occupancy, moves);
				}
				
			}
		}
		
	}
	
	public void loadSlidingDiagOccupancyHashMapDiag(){
		//Pre-loads the available moves for diagonally sliding pieces.

		for (int i=0;i<64;i++){
			diagOccupancyMoves.put(i, new HashMap<Long,Long>());
			int rowNum = positionRows[i];
			int shift = calculatedShifts[rowNum];
			int size = sizeOfRow[rowNum];
			Long maxOccupancy = (long) (Math.pow(2,size)-1);
			Long currentPosition = 0b1L<<(i-shift); 
			for (Long j=0L;j<=maxOccupancy;j++){
				Long occupancy = j | currentPosition;
				if (!diagOccupancyMoves.get(i).containsKey(occupancy)){
					Long moves = 0b0L;

					// Get moves to the right
					int k=1;
					Long newMove = 0b1L;
					while (newMove!=0){
						newMove = ((currentPosition<<k)& maxOccupancy); //only keep moves on this row
						moves = moves | newMove;
						newMove = newMove & ~occupancy; //stop when we hit another piece
						k = k+1;
					}
					// Get moves to the left
					k=1;
					newMove = 0b1L;
					while (newMove!=0){
						newMove = ((currentPosition>>>k)& maxOccupancy); //only keep moves on this row
						moves = moves | newMove; //add this to the moves
						newMove = newMove & ~occupancy; //stop when we hit another piece
						k = k+1;
					}
					moves = moves<<shift;
					diagOccupancyMoves.get(i).put(occupancy, moves);
				}
				
			}
		}
		
	}
	
	//Move Generation
	public Long generateKingMoves (int teamColor){
		//White Team Color = 1
		//Black Team Color = 0
		
		//TODO castling
		
		Long king;
		Long validMoves = 0b0L;
		Long friendlyPieces;
		if (teamColor==1){
			king = WK;
			friendlyPieces = whitePieces();
		}
		else{
			king = BK;
			friendlyPieces = blackPieces();
		}
		List<Integer> ind = bitPositions(king);
		for (int i=0;i<ind.size();i++){
			validMoves = validMoves| kingMoveMap.get(ind.get(i));
		}
		validMoves = validMoves & ~friendlyPieces;
		return validMoves;
	}
	
	public Long generateQueenMoves (int teamColor){
		//White Team Color = 1
		//Black Team Color = 0
		
		Long queens;
		Long validMoves;
		Long friendlyPieces;
		Long allPieces = allPieces();
		if (teamColor==1){
			queens = WQ;
			friendlyPieces = whitePieces();
		}
		else{
			queens = BQ;
			friendlyPieces = blackPieces();
		}
		
		//Along the rows
		List<Integer> ind = bitPositions(queens);
		Long horizontals = 0b0L;
		Long verticals = 0b0L;
		
		for (int i=0;i<ind.size();i++){
			int shiftNum = 8*((ind.get(i))/8);
			Long occupancyNum = (allPieces>>>shiftNum)& 0b11111111L;
			horizontals = horizontals|fileOccupancyMoves.get(ind.get(i)).get(occupancyNum);
		}
		
		//Along the columns
		Long rotatedQueens = rotateCW90Deg(queens);
		Long rotatedAllPieces = rotateCW90Deg(allPieces);
		ind = bitPositions(rotatedQueens);
		for (int i=0;i<ind.size();i++){
			int shiftNum = 8*((ind.get(i))/8);
			Long occupancyNum = (rotatedAllPieces>>>shiftNum)& 0b11111111L;
			verticals = verticals|fileOccupancyMoves.get(ind.get(i)).get(occupancyNum);
		}
		verticals = rotateCCW90Deg(verticals);
		
		//Along the A8-H1 diagonal
		Long diagonalsCW = 0b0L;
		Long queensCW = rotateCW45Deg(queens);
		Long allPiecesCW = rotateCW45Deg(allPieces);
		ind = bitPositions(queensCW);
		for (int i=0;i<ind.size();i++){
			int rowNum = positionRows[ind.get(i)];
			int shiftNum = calculatedShifts[rowNum];
			int size = sizeOfRow[rowNum];
			Long maxOccupancy = (long) (Math.pow(2,size)-1);
			Long occupancyNum = (allPiecesCW>>>shiftNum)& maxOccupancy;
			diagonalsCW = diagonalsCW|diagOccupancyMoves.get(ind.get(i)).get(occupancyNum);
		}
		diagonalsCW = reverseRotateCW45Deg(diagonalsCW);
		
		//Along the A1-H8 diagonal
		Long diagonalsCCW = 0b0L;
		Long queensCCW = rotateCCW45Deg(queens);
		Long allPiecesCCW = rotateCCW45Deg(allPieces);
		ind = bitPositions(queensCCW);
		for (int i=0;i<ind.size();i++){
			int rowNum = positionRows[ind.get(i)];
			int shiftNum = calculatedShifts[rowNum];
			int size = sizeOfRow[rowNum];
			Long maxOccupancy = (long) (Math.pow(2,size)-1);
			Long occupancyNum = (allPiecesCCW>>>shiftNum)& maxOccupancy;
			diagonalsCCW = diagonalsCCW|diagOccupancyMoves.get(ind.get(i)).get(occupancyNum);
		}
		diagonalsCCW = reverseRotateCCW45Deg(diagonalsCCW);

		
		validMoves = (horizontals|verticals|diagonalsCW|diagonalsCCW)& ~friendlyPieces;
		return validMoves;
	}
	public Long generateBishopMoves (int teamColor){
		//White Team Color = 1
		//Black Team Color = 0

		Long bishops;
		Long validMoves = 0b0L;
		Long friendlyPieces;
		Long allPieces = allPieces();
		if (teamColor==1){
			bishops = WB;
			friendlyPieces = whitePieces();
		}
		else{
			bishops = BB;
			friendlyPieces = blackPieces();
		}
		
		//Along the A8-H1 diagonal

		Long diagonalsCW = 0b0L;
		Long bishopsCW = rotateCW45Deg(bishops);
		Long allPiecesCW = rotateCW45Deg(allPieces);
		List<Integer> ind = bitPositions(bishopsCW);
		
		for (int i=0;i<ind.size();i++){
			int rowNum = positionRows[ind.get(i)];
			int shiftNum = calculatedShifts[rowNum];
			int size = sizeOfRow[rowNum];
			Long maxOccupancy = (long) (Math.pow(2,size)-1);
			Long occupancyNum = (allPiecesCW>>>shiftNum)& maxOccupancy;
			diagonalsCW = diagonalsCW|diagOccupancyMoves.get(ind.get(i)).get(occupancyNum);
		}
		diagonalsCW = reverseRotateCW45Deg(diagonalsCW);
		
		//Along the A1-H8 diagonal
		Long diagonalsCCW = 0b0L;
		Long bishopsCCW = rotateCCW45Deg(bishops);
		Long allPiecesCCW = rotateCCW45Deg(allPieces);
		ind = bitPositions(bishopsCCW);
		for (int i=0;i<ind.size();i++){
			int rowNum = positionRows[ind.get(i)];
			int shiftNum = calculatedShifts[rowNum];
			int size = sizeOfRow[rowNum];
			Long maxOccupancy = (long) (Math.pow(2,size)-1);
			Long occupancyNum = (allPiecesCCW>>>shiftNum)& maxOccupancy;
			diagonalsCCW = diagonalsCCW|diagOccupancyMoves.get(ind.get(i)).get(occupancyNum);
		}
		diagonalsCCW = reverseRotateCCW45Deg(diagonalsCCW);
		validMoves = (diagonalsCW|diagonalsCCW)& ~friendlyPieces;
		return validMoves;
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
			validMoves = validMoves| knightMoveMap.get(ind.get(i));
		}
		validMoves = validMoves & ~friendlyPieces;
		return validMoves;
	}
	public Long generateRookMoves (int teamColor){
		//White Team Color = 1
		//Black Team Color = 0
		Long rooks;
		Long validMoves = 0b0L;
		Long friendlyPieces;
		Long allPieces = allPieces();
		if (teamColor==1){
			rooks = WR;
			friendlyPieces = whitePieces();
		}
		else{
			rooks = BR;
			friendlyPieces = blackPieces();
		}
		
		List<Integer> ind = bitPositions(rooks);
		Long horizontals = 0b0L;
		Long verticals = 0b0L;
		
		for (int i=0;i<ind.size();i++){
			int shiftNum = 8*((ind.get(i))/8);
			Long occupancyNum = (allPieces>>>shiftNum)& 0b11111111L;
			horizontals = horizontals|fileOccupancyMoves.get(ind.get(i)).get(occupancyNum);
		}
		
		Long rotatedRooks = rotateCW90Deg(rooks);
		Long rotatedAllPieces = rotateCW90Deg(allPieces);
		ind = bitPositions(rotatedRooks);
		for (int i=0;i<ind.size();i++){
			int shiftNum = 8*((ind.get(i))/8);
			Long occupancyNum = (rotatedAllPieces>>>shiftNum)& 0b11111111L;
			verticals = verticals|fileOccupancyMoves.get(ind.get(i)).get(occupancyNum);
		}
		verticals = rotateCCW90Deg(verticals);	
		validMoves = (horizontals|verticals)& ~friendlyPieces;
		
		return validMoves;
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
	
	//Rotating bitboards
	public Long rotateCW90Deg(Long bitboard){
		//Rotates a given bitboard clockwise 90 degrees

		Long result = 0L;
		for (int i=0;i<64;i++){
			Long valAti = (bitboard>>>i)&0b1L;
			int amountToShift = (((i >> 3) | (i << 3)) & 63) ^ 56;
			result = result | (valAti<<amountToShift);
		}
 		
		return result;
	}
	public Long rotateCCW90Deg(Long bitboard){
		//Rotates a given bitboard counterclockwise 90 degrees
		/*
		Long result = 0L;
		for (int i=0;i<64;i++){
			Long valAti = (bitboard>>>i)&0b1L;
			int amountToShift = 8*(7-i%8)+(i/8);
			result = result | (valAti<<amountToShift);
		}
		*/
			
		Long result = 0L;
		for (int i=0;i<64;i++){
			Long valAti = (bitboard>>>i)&0b1L;
			int amountToShift = (((i >> 3) | (i << 3)) & 63) ^ 7;
			result = result | (valAti<<amountToShift);
		}
		 
		return result;
	}
	public void build45RotationMaps(){
		for(int i=0;i<64;i++){
			CW45Map.put(CW45Order[i],i);
			CCW45Map.put(CCW45Order[i],i);
			reverseCW45Map.put(i, CW45Order[i]);
			reverseCCW45Map.put(i, CCW45Order[i]);
			
		}

	}
	public Long rotateCW45Deg(Long bitboard){
		//Rotates a given bitboard clockwise 45 degrees
		Long result = 0L;
		for (int i=0;i<64;i++){
			Long valAti = (bitboard>>>i)&0b1L;
			Integer amountToShift = CW45Map.get(i);
			result = result | (valAti<<amountToShift);
		}
		return result;
	}
	public Long reverseRotateCW45Deg(Long bitboard){
		//Rotates a given bitboard clockwise 45 degrees
		Long result = 0L;
		for (int i=0;i<64;i++){
			Long valAti = (bitboard>>>i)&0b1L;
			Integer amountToShift = reverseCW45Map.get(i);
			result = result | (valAti<<amountToShift);
		}
		return result;
	}
	public Long rotateCCW45Deg(Long bitboard){
		//Rotates a given bitboard counterclockwise 45 degrees
	
		Long result = 0L;
		for (int i=0;i<64;i++){
			Long valAti = (bitboard>>>i)&0b1L;
			Integer amountToShift =  CCW45Map.get(i);
			result = result | (valAti<<amountToShift);
		}
		return result;
	}
	public Long reverseRotateCCW45Deg(Long bitboard){
		//Rotates a given bitboard clockwise 45 degrees
		Long result = 0L;
		for (int i=0;i<64;i++){
			Long valAti = (bitboard>>>i)&0b1L;
			Integer amountToShift = reverseCCW45Map.get(i);
			result = result | (valAti<<amountToShift);
		}
		return result;
	}
	
	
	//Visualizing Boards
	private static List<Integer> bitPositions(long number) {
		//Get the integer indeces of the ones in a 64 bit long
	    List<Integer> positions = new ArrayList<>();
	    int position = 0;
	    while (number != 0) {
	        if ((number & 1) != 0) {
	            positions.add(position);
	        }
	        position++;
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
				board[7-(j/8)][(j%8)] = "W"+pieceTypes[i];
			}
			for(int j:blackPieceIndeces){
				board[7-(j/8)][(j%8)] = "B"+pieceTypes[i];
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
