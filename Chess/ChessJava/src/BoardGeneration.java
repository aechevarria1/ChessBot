import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardGeneration {

	//Board Instantiation
	public static void initiateLoadedGame(String directory, String infoType){
		//To load a chess game.
		//TODO
	}

	public static long[] getBoardInformation(){
		long[] boardInformation = new long[] {Orion.WK,Orion.WQ,Orion.WB,Orion.WN,Orion.WR,Orion.WP,Orion.BK,Orion.BQ,Orion.BB,Orion.BN,Orion.BR,Orion.BP};
		return boardInformation;
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
		long[] boardInformation = getBoardInformation();
		long [] whitePieces = Arrays.copyOfRange(boardInformation, 0, 6);
		long [] blackPieces = Arrays.copyOfRange(boardInformation, 6, 12);
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
    public static void initiateDebugChess() {
        long WP=0L,WN=0L,WB=0L,WR=0L,WQ=0L,WK=0L,BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L;
        String chessBoard[][]={
                {"r","q","q","q","k","b","n","r"},
                {"p","p","p","p","p","p","p","p"},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," ","P"," "," "," "},
                {"P","P","P","P"," ","P","P","P"},
                {"R","Q","Q","Q","K","B","N","R"}};
        arrayToBitboards(chessBoard,WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK);
    }
    public static void initiateStandardChess() {
        long WP=0L,WN=0L,WB=0L,WR=0L,WQ=0L,WK=0L,BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L;
        String chessBoard[][]={
                {"r","n","b","q","k","b","n","r"},
                {"p","p","p","p","p","p","p","p"},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {"P","P","P","P","P","P","P","P"},
                {"R","N","B","Q","K","B","N","R"}};
        arrayToBitboards(chessBoard,WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK);
    }
    public static void initiateChess960() {
    	long WP=0L,WN=0L,WB=0L,WR=0L,WQ=0L,WK=0L,BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L;
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
        arrayToBitboards(chessBoard,WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK);
    }
    
    public static void importFEN(String fenString){
        //not chess960 compatible
    	//Examples:
    	// rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
    	// rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2
    	Orion.WP=0; Orion.WN=0; Orion.WB=0;
    	Orion.WR=0; Orion.WQ=0; Orion.WK=0;
    	Orion.BP=0; Orion.BN=0; Orion.BB=0;
    	Orion.BR=0; Orion.BQ=0; Orion.BK=0;
    	Orion.CWK=false; Orion.CWQ=false;
        Orion.CBK=false; Orion.CBQ=false;
		int charIndex = 0;
		int boardIndex = 0;
		//trueBoardIndex = (7-boardIndex/8)*8 + boardIndex%8;
		while (fenString.charAt(charIndex) != ' ')
		{
			switch (fenString.charAt(charIndex++))
			{
			case 'P':
				Orion.WP |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'p':
				Orion.BP |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'N':
				Orion.WN |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'n':
				Orion.BN |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'B':
				Orion.WB |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'b':
				Orion.BB |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'R':
				Orion.WR |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'r':
				Orion.BR |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'Q':
				Orion.WQ |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'q':
				Orion.BQ |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'K':
				Orion.WK |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
				boardIndex++;
				break;
			case 'k':
				Orion.BK |= (1L << ((7-boardIndex/8)*8 + boardIndex%8));
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
		Orion.WhiteToMove = (fenString.charAt(++charIndex) == 'w');
		charIndex += 2;
		while (fenString.charAt(charIndex) != ' ')
		{
			switch (fenString.charAt(charIndex++))
			{
			case '-':
				break;
			case 'K': Orion.CWK = true;
				break;
			case 'Q': Orion.CWQ = true;
				break;
			case 'k': Orion.CBK = true;
				break;
			case 'q': Orion.CBQ = true;
				break;
			default:
				break;
			}
		}
		if (fenString.charAt(++charIndex) != '-')
		{
			Orion.EP = Moves.FileMasks8[fenString.charAt(charIndex++) - 'a'];
		}
		//TODO 
		//the rest of the fenString is not yet utilized
		//Keeping track of move count
    }
    
    //Helper Functions
    public static void arrayToBitboards(String[][] chessBoard,long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK) {
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
        drawArray(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK);
        Orion.WP=WP; Orion.WN=WN; Orion.WB=WB;
        Orion.WR=WR; Orion.WQ=WQ; Orion.WK=WK;
        Orion.BP=BP; Orion.BN=BN; Orion.BB=BB;
        Orion.BR=BR; Orion.BQ=BQ; Orion.BK=BK;
    }
    public static long convertStringToBitboard(String Binary) {
        if (Binary.charAt(0)=='0') {//not going to be a negative number
            return Long.parseLong(Binary, 2);
        } else {
            return Long.parseLong("1"+Binary.substring(2), 2)*2;
        }
    }
	public static void drawArray(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN,
			long BB, long BR, long BQ, long BK) {
        String chessBoard[][]=new String[8][8];
        for (int i=0;i<64;i++) {
            chessBoard[i/8][i%8]=" ";
        }
        for (int i=0;i<64;i++) {
            if (((WP>>i)&1)==1) {chessBoard[7-i/8][i%8]="P";}
            if (((WN>>i)&1)==1) {chessBoard[7-i/8][i%8]="N";}
            if (((WB>>i)&1)==1) {chessBoard[7-i/8][i%8]="B";}
            if (((WR>>i)&1)==1) {chessBoard[7-i/8][i%8]="R";}
            if (((WQ>>i)&1)==1) {chessBoard[7-i/8][i%8]="Q";}
            if (((WK>>i)&1)==1) {chessBoard[7-i/8][i%8]="K";}
            if (((BP>>i)&1)==1) {chessBoard[7-i/8][i%8]="p";}
            if (((BN>>i)&1)==1) {chessBoard[7-i/8][i%8]="n";}
            if (((BB>>i)&1)==1) {chessBoard[7-i/8][i%8]="b";}
            if (((BR>>i)&1)==1) {chessBoard[7-i/8][i%8]="r";}
            if (((BQ>>i)&1)==1) {chessBoard[7-i/8][i%8]="q";}
            if (((BK>>i)&1)==1) {chessBoard[7-i/8][i%8]="k";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
	}
}
