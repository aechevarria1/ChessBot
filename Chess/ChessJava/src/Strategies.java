
public class Strategies {
	public static String Strategy1(String moves){
		int index=(int)(Math.floor(Math.random()*(moves.length()/4))*4);
		return moves.substring(index,index+4);
	}
	public static String Strategy2(String moves){
        /*
         * Always checkmate if possible. Otherwise choose a random move.
         */
		int index=(int)(Math.floor(Math.random()*(moves.length()/4))*4);
		String defaultMove = moves.substring(index,index+4);
        String bestMove = "";
        int start=0,end=0;
        for (int i=0;i<moves.length();i+=4) {
        	//Get the start and end position for checking castling
            if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                start=(Character.getNumericValue(moves.charAt(i)))+(Character.getNumericValue(moves.charAt(i+1))*8);
                end=(Character.getNumericValue(moves.charAt(i+2)))+(Character.getNumericValue(moves.charAt(i+3))*8);;
            } else if (moves.charAt(i+3)=='P') {//pawn promotion
                if (Character.isUpperCase(moves.charAt(i+2))) {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[6]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[7]);
                } else {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[1]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[0]);
                }
            } else if (moves.charAt(i+3)=='E') {//en passant
                if (moves.charAt(i+2)=='W') {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[4]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[5]);
                } else {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[3]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[2]);
                }
            }
            //Always check if we are breaking the castling rights
            boolean CWKt=Chessboard.CWK,CWQt=Chessboard.CWQ,CBKt=Chessboard.CBK,CBQt=Chessboard.CBQ;
            if (((1L<<start)&Chessboard.WK)!=0) {CWKt=false; CWQt=false;}
            if (((1L<<start)&Chessboard.BK)!=0) {CBKt=false; CBQt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.WR&(1L<<7))!=0) {CWKt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.WR&(1L))!=0) {CWQt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.BR&(1L<<63))!=0) {CBKt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.BR&(1L<<56))!=0) {CBQt=false;}
            
            //Do the moves
            long EPt=Moves.makeMoveEP(Chessboard.WP|Chessboard.BP,moves.substring(i,i+4)),
            WPt=Moves.makeMove(Chessboard.WP, moves.substring(i,i+4), 'P'),
            WNt=Moves.makeMove(Chessboard.WN, moves.substring(i,i+4), 'N'),
            WBt=Moves.makeMove(Chessboard.WB, moves.substring(i,i+4), 'B'),
            WRt=Moves.makeMove(Chessboard.WR, moves.substring(i,i+4), 'R'),
            WQt=Moves.makeMove(Chessboard.WQ, moves.substring(i,i+4), 'Q'),
            WKt=Moves.makeMove(Chessboard.WK, moves.substring(i,i+4), 'K'),
            BPt=Moves.makeMove(Chessboard.BP, moves.substring(i,i+4), 'p'),
            BNt=Moves.makeMove(Chessboard.BN, moves.substring(i,i+4), 'n'),
            BBt=Moves.makeMove(Chessboard.BB, moves.substring(i,i+4), 'b'),
            BRt=Moves.makeMove(Chessboard.BR, moves.substring(i,i+4), 'r'),
            BQt=Moves.makeMove(Chessboard.BQ, moves.substring(i,i+4), 'q'),
            BKt=Moves.makeMove(Chessboard.BK, moves.substring(i,i+4), 'k');
            WRt=Moves.makeMoveCastle(Chessboard.WR, Chessboard.WK|Chessboard.BK, moves.substring(i,i+4), 'R');
            BRt=Moves.makeMoveCastle(Chessboard.BR, Chessboard.WK|Chessboard.BK, moves.substring(i,i+4), 'r');
            boolean WhiteToMovet=!Chessboard.WhiteToMove;
            String nextMoves = "";
            boolean isChecked;
            if (WhiteToMovet) {
            	nextMoves=Moves.possibleMovesW(WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt,EPt,CWKt,CWQt,CBKt,CBQt);
            	isChecked = (WKt&Moves.unsafeForWhite(WPt, WNt, WBt, WRt, WQt, WKt, BPt, BNt, BBt, BRt, BQt, BKt))!=0;
            } else {
            	nextMoves=Moves.possibleMovesB(WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt,EPt,CWKt,CWQt,CBKt,CBQt);
            	isChecked = (BKt&Moves.unsafeForBlack(WPt, WNt, WBt, WRt, WQt, WKt, BPt, BNt, BBt, BRt, BQt, BKt))!=0;
            }
            //If opponent will have no moves, then do that move.
            nextMoves = Moves.filterMoves(nextMoves,WhiteToMovet,WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt,EPt);
            if (nextMoves.length()==0&&isChecked){
            	return moves.substring(i,i+4);
            }
        }
        //If no checkmates were possible
        return defaultMove;
	}
	
	public static String Strategy3(String moves){
        /*
         * Always checkmate if possible. Then choose a move to minimize the value of the opponents pieces. Otherwise choose a random move.
         */
		int bestScore = -100;
        String bestMove = "";
        int start=0,end=0;
        for (int i=0;i<moves.length();i+=4) {
        	//Get the start and end position for checking castling
            if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                start=(Character.getNumericValue(moves.charAt(i)))+(Character.getNumericValue(moves.charAt(i+1))*8);
                end=(Character.getNumericValue(moves.charAt(i+2)))+(Character.getNumericValue(moves.charAt(i+3))*8);;
            } else if (moves.charAt(i+3)=='P') {//pawn promotion
                if (Character.isUpperCase(moves.charAt(i+2))) {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[6]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[7]);
                } else {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[1]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[0]);
                }
            } else if (moves.charAt(i+3)=='E') {//en passant
                if (moves.charAt(i+2)=='W') {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[4]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[5]);
                } else {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[3]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[2]);
                }
            }
            //Always check if we are breaking the castling rights
            boolean CWKt=Chessboard.CWK,CWQt=Chessboard.CWQ,CBKt=Chessboard.CBK,CBQt=Chessboard.CBQ;
            if (((1L<<start)&Chessboard.WK)!=0) {CWKt=false; CWQt=false;}
            if (((1L<<start)&Chessboard.BK)!=0) {CBKt=false; CBQt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.WR&(1L<<7))!=0) {CWKt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.WR&(1L))!=0) {CWQt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.BR&(1L<<63))!=0) {CBKt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.BR&(1L<<56))!=0) {CBQt=false;}
            
            //Do the moves
            long EPt=Moves.makeMoveEP(Chessboard.WP|Chessboard.BP,moves.substring(i,i+4)),
            WPt=Moves.makeMove(Chessboard.WP, moves.substring(i,i+4), 'P'),
            WNt=Moves.makeMove(Chessboard.WN, moves.substring(i,i+4), 'N'),
            WBt=Moves.makeMove(Chessboard.WB, moves.substring(i,i+4), 'B'),
            WRt=Moves.makeMove(Chessboard.WR, moves.substring(i,i+4), 'R'),
            WQt=Moves.makeMove(Chessboard.WQ, moves.substring(i,i+4), 'Q'),
            WKt=Moves.makeMove(Chessboard.WK, moves.substring(i,i+4), 'K'),
            BPt=Moves.makeMove(Chessboard.BP, moves.substring(i,i+4), 'p'),
            BNt=Moves.makeMove(Chessboard.BN, moves.substring(i,i+4), 'n'),
            BBt=Moves.makeMove(Chessboard.BB, moves.substring(i,i+4), 'b'),
            BRt=Moves.makeMove(Chessboard.BR, moves.substring(i,i+4), 'r'),
            BQt=Moves.makeMove(Chessboard.BQ, moves.substring(i,i+4), 'q'),
            BKt=Moves.makeMove(Chessboard.BK, moves.substring(i,i+4), 'k');
            WRt=Moves.makeMoveCastle(WRt, Chessboard.WK|Chessboard.BK, moves.substring(i,i+4), 'R');
            BRt=Moves.makeMoveCastle(BRt, Chessboard.WK|Chessboard.BK, moves.substring(i,i+4), 'r');
            boolean WhiteToMovet=!Chessboard.WhiteToMove;
            String nextMoves = "";
            boolean isChecked;
            if (WhiteToMovet) {
            	nextMoves=Moves.possibleMovesW(WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt,EPt,CWKt,CWQt,CBKt,CBQt);
            	isChecked = (WKt&Moves.unsafeForWhite(WPt, WNt, WBt, WRt, WQt, WKt, BPt, BNt, BBt, BRt, BQt, BKt))!=0;
            } else {
            	nextMoves=Moves.possibleMovesB(WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt,EPt,CWKt,CWQt,CBKt,CBQt);
            	isChecked = (BKt&Moves.unsafeForBlack(WPt, WNt, WBt, WRt, WQt, WKt, BPt, BNt, BBt, BRt, BQt, BKt))!=0;
            }
            //If opponent will have no moves, then do that move.
            nextMoves = Moves.filterMoves(nextMoves,WhiteToMovet,WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt,EPt);
            if (nextMoves.length()==0&&isChecked){
            	System.out.println("info score 100");
            	return moves.substring(i,i+4);
            }
            
            //Count opponents pieces.
            int numQueens,numRooks,numBishops,numKnights,numPawns;
            if (Chessboard.WhiteToMove) {
            	numQueens = bitCount(BQt);
            	numRooks = bitCount(BRt);
            	numBishops = bitCount(BBt);
            	numKnights = bitCount(BNt);
            	numPawns = bitCount(BPt);
            }else{
            	numQueens = bitCount(WQt);
            	numRooks = bitCount(WRt);
            	numBishops = bitCount(WBt);
            	numKnights = bitCount(WNt);
            	numPawns = bitCount(WPt);
            	
            }
            int score = 100-numQueens*9-numRooks*5-(numKnights+numBishops)*3-numPawns;
            if (nextMoves.length()==0){
            	score-=50;
            }
            if (score>bestScore||bestScore==-100){
            	bestScore = score;
            	bestMove = moves.substring(i,i+4);
            }
            else if(score==bestScore){
            	bestMove = bestMove.concat(moves.substring(i,i+4));
            }
        }
		int index=(int)(Math.floor(Math.random()*(bestMove.length()/4))*4);
		System.out.println("info score cp "+bestScore);
		return bestMove.substring(index,index+4);
	}
	public static String Strategy4(String moves){
        /*
         * Always checkmate if possible. Then choose a move to minimize the value of the opponents pieces. Otherwise choose a random move.
         * Also factors in own pieces.
         */
		int bestScore = -100;
        String bestMove = "";
        int start=0,end=0;
        for (int i=0;i<moves.length();i+=4) {
        	//Get the start and end position for checking castling
            if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                start=(Character.getNumericValue(moves.charAt(i)))+(Character.getNumericValue(moves.charAt(i+1))*8);
                end=(Character.getNumericValue(moves.charAt(i+2)))+(Character.getNumericValue(moves.charAt(i+3))*8);;
            } else if (moves.charAt(i+3)=='P') {//pawn promotion
                if (Character.isUpperCase(moves.charAt(i+2))) {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[6]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[7]);
                } else {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[1]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[0]);
                }
            } else if (moves.charAt(i+3)=='E') {//en passant
                if (moves.charAt(i+2)=='W') {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[4]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[5]);
                } else {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[3]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[2]);
                }
            }
            //Always check if we are breaking the castling rights
            boolean CWKt=Chessboard.CWK,CWQt=Chessboard.CWQ,CBKt=Chessboard.CBK,CBQt=Chessboard.CBQ;
            if (((1L<<start)&Chessboard.WK)!=0) {CWKt=false; CWQt=false;}
            if (((1L<<start)&Chessboard.BK)!=0) {CBKt=false; CBQt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.WR&(1L<<7))!=0) {CWKt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.WR&(1L))!=0) {CWQt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.BR&(1L<<63))!=0) {CBKt=false;}
            if ((((1L<<start)|(1L<<end))&Chessboard.BR&(1L<<56))!=0) {CBQt=false;}
            
            //Do the moves
            long EPt=Moves.makeMoveEP(Chessboard.WP|Chessboard.BP,moves.substring(i,i+4)),
            WPt=Moves.makeMove(Chessboard.WP, moves.substring(i,i+4), 'P'),
            WNt=Moves.makeMove(Chessboard.WN, moves.substring(i,i+4), 'N'),
            WBt=Moves.makeMove(Chessboard.WB, moves.substring(i,i+4), 'B'),
            WRt=Moves.makeMove(Chessboard.WR, moves.substring(i,i+4), 'R'),
            WQt=Moves.makeMove(Chessboard.WQ, moves.substring(i,i+4), 'Q'),
            WKt=Moves.makeMove(Chessboard.WK, moves.substring(i,i+4), 'K'),
            BPt=Moves.makeMove(Chessboard.BP, moves.substring(i,i+4), 'p'),
            BNt=Moves.makeMove(Chessboard.BN, moves.substring(i,i+4), 'n'),
            BBt=Moves.makeMove(Chessboard.BB, moves.substring(i,i+4), 'b'),
            BRt=Moves.makeMove(Chessboard.BR, moves.substring(i,i+4), 'r'),
            BQt=Moves.makeMove(Chessboard.BQ, moves.substring(i,i+4), 'q'),
            BKt=Moves.makeMove(Chessboard.BK, moves.substring(i,i+4), 'k');
            WRt=Moves.makeMoveCastle(WRt, Chessboard.WK|Chessboard.BK, moves.substring(i,i+4), 'R');
            BRt=Moves.makeMoveCastle(BRt, Chessboard.WK|Chessboard.BK, moves.substring(i,i+4), 'r');
            boolean WhiteToMovet=!Chessboard.WhiteToMove;
            String nextMoves = "";
            boolean isChecked;
            if (WhiteToMovet) {
            	nextMoves=Moves.possibleMovesW(WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt,EPt,CWKt,CWQt,CBKt,CBQt);
            	isChecked = (WKt&Moves.unsafeForWhite(WPt, WNt, WBt, WRt, WQt, WKt, BPt, BNt, BBt, BRt, BQt, BKt))!=0;
            } else {
            	nextMoves=Moves.possibleMovesB(WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt,EPt,CWKt,CWQt,CBKt,CBQt);
            	isChecked = (BKt&Moves.unsafeForBlack(WPt, WNt, WBt, WRt, WQt, WKt, BPt, BNt, BBt, BRt, BQt, BKt))!=0;
            }
            //If opponent will have no moves, then do that move.
            nextMoves = Moves.filterMoves(nextMoves,WhiteToMovet,WPt,WNt,WBt,WRt,WQt,WKt,BPt,BNt,BBt,BRt,BQt,BKt,EPt);
            if (nextMoves.length()==0&&isChecked){
            	System.out.println("info score 100");
            	return moves.substring(i,i+4);
            }
            
            //Count opponents pieces.
            int numQueens,numRooks,numBishops,numKnights,numPawns,numMyQueens,numMyRooks,numMyBishops,numMyKnights,numMyPawns;
            if (Chessboard.WhiteToMove) {
            	numQueens = bitCount(BQt);
            	numRooks = bitCount(BRt);
            	numBishops = bitCount(BBt);
            	numKnights = bitCount(BNt);
            	numPawns = bitCount(BPt);
            	numMyQueens = bitCount(WQt);
            	numMyRooks = bitCount(WRt);
            	numMyBishops = bitCount(WBt);
            	numMyKnights = bitCount(WNt);
            	numMyPawns = bitCount(WPt);
            }else{
            	numQueens = bitCount(WQt);
            	numRooks = bitCount(WRt);
            	numBishops = bitCount(WBt);
            	numKnights = bitCount(WNt);
            	numPawns = bitCount(WPt);
            	numMyQueens = bitCount(BQt);
            	numMyRooks = bitCount(BRt);
            	numMyBishops = bitCount(BBt);
            	numMyKnights = bitCount(BNt);
            	numMyPawns = bitCount(BPt);
            	
            }
            int score = -numQueens*9-numRooks*5-(numKnights+numBishops)*3-numPawns;
            score = score+numMyQueens*9+numMyRooks*5+(numMyKnights+numMyBishops)*3+numMyPawns;
            if (nextMoves.length()==0){
            	score-=50;
            }
            if (score>bestScore||bestScore==-100){
            	bestScore = score;
            	bestMove = moves.substring(i,i+4);
            }
            else if(score==bestScore){
            	bestMove = bestMove.concat(moves.substring(i,i+4));
            }
        }
		int index=(int)(Math.floor(Math.random()*(bestMove.length()/4))*4);
		System.out.println("info score cp "+bestScore);
		return bestMove.substring(index,index+4);
	}
	public static int bitCount(long i) {
	    /*
	     * Counts the number of 1's in the binary form of a long.
	     */
		int count = 0;
		while (i!=0){
			count++;
			i = (i>>>Long.numberOfTrailingZeros(i))>>>1;
		}
	    return count;
	}
	
	public static String alphaBeta(int depth,double beta,double alpha,String move,int player){
		//TODO
		//generate moves
		String possibleMoves="";
		//filter moves
	    if ((depth==0)|| possibleMoves.length()==0){
	    	//TODO evaluate board
	    	double evaluation = 0.0;
	        return move+(evaluation*(player*2-1));
	    }
	    if (player==1){
	    	String newMove = null, selectedMove;
	    	double selectedScore = 0;
	        double v = -99999;
	        for (int i=0;i<possibleMoves.length();i+=4){
	        	newMove = possibleMoves.substring(i, i+4);
	        	String result = alphaBeta(depth-1, beta, alpha,newMove, 1);
	        	selectedMove = result.substring(0,4);
	        	selectedScore = Double.parseDouble(result.substring(4));
	            v = Math.max(v, selectedScore);
	            alpha = Math.max(alpha, v);
	            if (beta <= alpha){
	            	break; //(* beta cut-off *)
	            }
	                
	        }

	        return newMove+selectedScore;
	    }

	    else{
	    	String newMove, selectedMove;
	    	double selectedScore;
	        double v = 99999;
	        for (int i=0;i<possibleMoves.length();i+=4){
	        	newMove = possibleMoves.substring(i, i+4);
	        	String result = alphaBeta(depth-1, beta, alpha,newMove, 1);
	        	selectedMove = result.substring(0,4);
	        	selectedScore = Double.parseDouble(result.substring(4));
	            v = Math.min(v, selectedScore);
	            beta = Math.min(beta, v);
	            if (beta <= alpha){
	            	 break; //(* alpha cut-off *)
	            }	               
	        }
	        return newMove+selectedScore;
	    }

		
		//returns in the form of 1234b###### (5 character move then score)
		//return "";
	}
}
