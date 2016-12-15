import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Chessboard {
	
	long WK;
	long WQ;
	long WB;
	long WN;
	long WR;
	long WP;
	long BK;
	long BQ;
	long BB;
	long BN;
	long BR;
	long BP;
	
	long EP=0b0L;
	String history = "";
	boolean CWK=true;//true=castle is possible
	boolean CWQ=true;
	boolean CBK=true;
	boolean CBQ=true;
	boolean WhiteToMove = true; //white starts first by default
	long[] boardInformation;

	//Board Instantiation
	public Chessboard(){
		
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
	public Chessboard(int standard){
		if (standard==1){
			initiateStandardChess();
		}
		else if (standard==0){
			initiateChess960();
		}
		else{
			initiateDebugChess();
			history = "6163";
		}
		boardInformation = new long[] {this.WK,this.WQ,this.WB,this.WN,this.WR,this.WP,this.BK,this.BQ,this.BB,this.BN,this.BR,this.BP};
	}
	public Chessboard(String fenString){
		importFEN(fenString);
		updateBoardInformation();
	}
	public void updateBoardInformation(){
		boardInformation = new long[] {WK,WQ,WB,WN,WR,WP,BK,BQ,BB,BN,BR,BP};
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
			   val = val + Integer.toString(8-i);
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
	
	//Types of Boards
    public void initiateDebugChess() {
        WP=0L;WN=0L;WB=0L;WR=0L;WQ=0L;WK=0L;BP=0L;BN=0L;BB=0L;BR=0L;BQ=0L;BK=0L;
        String chessBoard[][]={
                {"r","q","q","q","k","b","n","r"},
                {"p","p","p","p","p","p","p","p"},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," ","P"," "," "," "},
                {"P","P","P","P"," ","P","P","P"},
                {"R","Q","Q","Q","K","B","N","R"}};
        arrayToBitboards(chessBoard);
    }
    public void initiateStandardChess() {
        WP=0L;WN=0L;WB=0L;WR=0L;WQ=0L;WK=0L;BP=0L;BN=0L;BB=0L;BR=0L;BQ=0L;BK=0L;
        String chessBoard[][]={
                {"r","n","b","q","k","b","n","r"},
                {"p","p","p","p","p","p","p","p"},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {"P","P","P","P","P","P","P","P"},
                {"R","N","B","Q","K","B","N","R"}};
        arrayToBitboards(chessBoard);
    }
    public void initiateChess960() {
    	WP=0L;WN=0L;WB=0L;WR=0L;WQ=0L;WK=0L;BP=0L;BN=0L;BB=0L;BR=0L;BQ=0L;BK=0L;
        String chessBoard[][]={
            {" "," "," "," "," "," "," "," "},
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"P","P","P","P","P","P","P","P"},
            {" "," "," "," "," "," "," "," "}};
        //step 1:
        int random1=(int)(Math.random()*8);
        chessBoard[0][random1]="b";
        chessBoard[7][random1]="B";
        //step 2:
        int random2=(int)(Math.random()*8);
        while (random2%2==random1%2) {
            random2=(int)(Math.random()*8);
        }
        chessBoard[0][random2]="b";
        chessBoard[7][random2]="B";
        //step 3:
        int random3=(int)(Math.random()*8);
        while (random3==random1 || random3==random2) {
            random3=(int)(Math.random()*8);
        }
        chessBoard[0][random3]="q";
        chessBoard[7][random3]="Q";
        //step 4:
        int random4a=(int)(Math.random()*5);
        int counter=0;
        int loop=0;
        while (counter-1<random4a) {
            if (" ".equals(chessBoard[0][loop])) {counter++;}
            loop++;
        }
        chessBoard[0][loop-1]="n";
        chessBoard[7][loop-1]="N";
        int random4b=(int)(Math.random()*4);
        counter=0;
        loop=0;
        while (counter-1<random4b) {
            if (" ".equals(chessBoard[0][loop])) {counter++;}
            loop++;
        }
        chessBoard[0][loop-1]="n";
        chessBoard[7][loop-1]="N";
        //step 5:
        counter=0;
        while (!" ".equals(chessBoard[0][counter])) {
            counter++;
        }
        chessBoard[0][counter]="r";
        chessBoard[7][counter]="R";
        while (!" ".equals(chessBoard[0][counter])) {
            counter++;
        }
        chessBoard[0][counter]="k";
        chessBoard[7][counter]="K";
        while (!" ".equals(chessBoard[0][counter])) {
            counter++;
        }
        chessBoard[0][counter]="r";
        chessBoard[7][counter]="R";
        arrayToBitboards(chessBoard);
    }
    public void importFEN(String fenString){
        //not chess960 compatible
    	//Examples:
    	// rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
    	// rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2
    	WP=0; WN=0; WB=0;
        WR=0; WQ=0; WK=0;
        BP=0; BN=0; BB=0;
        BR=0; BQ=0; BK=0;
        CWK=false; CWQ=false;
        CBK=false; CBQ=false;
		int charIndex = 0;
		int boardIndex = 0;
		//trueBoardIndex = (7-boardIndex/8)*8 + boardIndex%8;
		while (fenString.charAt(charIndex) != ' ')
		{
			switch (fenString.charAt(charIndex++))
			{
			case 'P':
				WP |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'p':
				BP |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'N':
				WN |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'n':
				BN |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'B':
				WB |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'b':
				BB |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'R':
				WR |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'r':
				BR |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'Q':
				WQ |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'q':
				BQ |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'K':
				WK |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'k':
				BK |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case '/':
				break;
			case '1': boardIndex++;
				break;
			case '2': boardIndex += 2;
				break;
			case '3': boardIndex += 3;
				break;
			case '4': boardIndex += 4;
				break;
			case '5': boardIndex += 5;
				break;
			case '6': boardIndex += 6;
				break;
			case '7': boardIndex += 7;
				break;
			case '8': boardIndex += 8;
				break;
			default:
				break;
			}
		}
		WhiteToMove = (fenString.charAt(++charIndex) == 'w');
		charIndex += 2;
		while (fenString.charAt(charIndex) != ' ')
		{
			switch (fenString.charAt(charIndex++))
			{
			case '-':
				break;
			case 'K': CWK = true;
				break;
			case 'Q': CWQ = true;
				break;
			case 'k': CBK = true;
				break;
			case 'q': CBQ = true;
				break;
			default:
				break;
			}
		}
		if (fenString.charAt(++charIndex) != '-')
		{
			EP = Moves.FileMasks8[fenString.charAt(charIndex++) - 'a'];
		}
		//TODO 
		//the rest of the fenString is not yet utilized
		//Keeping track of move count
    }
    
    //Helper Functions
    public void arrayToBitboards(String[][] chessBoard) {
        String Binary;
        for (int i=0;i<64;i++) {
            Binary="0000000000000000000000000000000000000000000000000000000000000000";
            Binary=Binary.substring(i+1)+"1"+Binary.substring(0, i);
            switch (chessBoard[7-i/8][i%8]) {
                case "P": WP+=convertStringToBitboard(Binary);
                    break;
                case "N": WN+=convertStringToBitboard(Binary);
                    break;
                case "B": WB+=convertStringToBitboard(Binary);
                    break;
                case "R": WR+=convertStringToBitboard(Binary);
                    break;
                case "Q": WQ+=convertStringToBitboard(Binary);
                    break;
                case "K": WK+=convertStringToBitboard(Binary);
                    break;
                case "p": BP+=convertStringToBitboard(Binary);
                    break;
                case "n": BN+=convertStringToBitboard(Binary);
                    break;
                case "b": BB+=convertStringToBitboard(Binary);
                    break;
                case "r": BR+=convertStringToBitboard(Binary);
                    break;
                case "q": BQ+=convertStringToBitboard(Binary);
                    break;
                case "k": BK+=convertStringToBitboard(Binary);
                    break;
            }
        }
    }
    public static long convertStringToBitboard(String Binary) {
        if (Binary.charAt(0)=='0') {//not going to be a negative number
            return Long.parseLong(Binary, 2);
        } else {
            return Long.parseLong("1"+Binary.substring(2), 2)*2;
        }
    }
}
