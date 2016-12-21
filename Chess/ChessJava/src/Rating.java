import java.util.Random;

public class Rating {
    public static int evaluate(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) {
        return Scoring7(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
    }
    public static int evaluate2(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove,boolean selfColorIsWhite) {
        return Scoring8(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ,WhiteToMove,selfColorIsWhite);
    }
    public static int Scoring2(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove){
    	/*
    	 * Uses a completely random system
    	 */
    	Random rand = new Random();
    	return rand.nextInt(101);
    }
    
    public static int Scoring3(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove){
    	/*
    	 * Minimized the scores of the opponent pieces
    	 */
        int numQueens,numRooks,numBishops,numKnights,numPawns;
        if (WhiteToMove) {
        	numQueens = bitCount(BQ);
        	numRooks = bitCount(BR);
        	numBishops = bitCount(BB);
        	numKnights = bitCount(BN);
        	numPawns = bitCount(BP);
        }else{
        	numQueens = bitCount(WQ);
        	numRooks = bitCount(WR);
        	numBishops = bitCount(WB);
        	numKnights = bitCount(WN);
        	numPawns = bitCount(WP);       	
        }
        int score = 100-numQueens*9-numRooks*5-(numKnights+numBishops)*3-numPawns;
    	return score;
    }
    public static int Scoring4(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove){
    	/*
    	 * Minimizes the score of the opponents pieces and maximizes the score of own pieces
    	 */
        int numQueens,numRooks,numBishops,numKnights,numPawns,numMyQueens,numMyRooks,numMyBishops,numMyKnights,numMyPawns;
        if (WhiteToMove) {
        	numQueens = bitCount(BQ);
        	numRooks = bitCount(BR);
        	numBishops = bitCount(BB);
        	numKnights = bitCount(BN);
        	numPawns = bitCount(BP);
        	numMyQueens = bitCount(WQ);
        	numMyRooks = bitCount(WR);
        	numMyBishops = bitCount(WB);
        	numMyKnights = bitCount(WN);
        	numMyPawns = bitCount(WP);
        }else{
        	numQueens = bitCount(WQ);
        	numRooks = bitCount(WR);
        	numBishops = bitCount(WB);
        	numKnights = bitCount(WN);
        	numPawns = bitCount(WP);
        	numMyQueens = bitCount(BQ);
        	numMyRooks = bitCount(BR);
        	numMyBishops = bitCount(BB);
        	numMyKnights = bitCount(BN);
        	numMyPawns = bitCount(BP);
        	
        }
        int score = 100 -numQueens*9-numRooks*5-(numKnights+numBishops)*3-numPawns;
        score = score+numMyQueens*9+numMyRooks*5+(numMyKnights+numMyBishops)*3+numMyPawns;
    	return score;
    }
    public static int Scoring5(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove){
    	/*
    	 * Weighs material value and number of available moves.
    	 * Also considers threatened pieces
    	 */
    	
    	//Number of available moves
        String moves;
        if (WhiteToMove) {
            moves=Moves.possibleMovesW(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ);
        } else {
            moves=Moves.possibleMovesB(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ);
        }
        moves = Moves.filterMoves(moves, WhiteToMove, WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, EP);
        int score = moves.length();

        //Material value
        long myQueens,yourQueens,myRooks,yourRooks,myKnights,yourKnights,myBishops,yourBishops,myPawns,yourPawns,myKing,yourKing;
        int numQueens,numRooks,numBishops,numKnights,numPawns,numMyQueens,numMyRooks,numMyBishops,numMyKnights,numMyPawns;
        if (WhiteToMove) {
        	myQueens=WQ;yourQueens=BQ;
        	myRooks=WR;yourRooks=BR;
        	myKnights=WN;yourKnights=BN;
        	myBishops=WB;yourBishops=BB;
        	myPawns=WP;yourPawns=BP;
        	myKing=WK;yourKing=BK;
        	
        }else{
        	myQueens=BQ;yourQueens=WQ;
        	myRooks=BR;yourRooks=WR;
        	myKnights=BN;yourKnights=WN;
        	myBishops=BB;yourBishops=WB;
        	myPawns=BP;yourPawns=WP;
        	myKing=BK;yourKing=WK;
        }
    	numQueens = bitCount(yourQueens);
    	numRooks = bitCount(yourRooks);
    	numBishops = bitCount(yourBishops);
    	numKnights = bitCount(yourKnights);
    	numPawns = bitCount(yourPawns);
    	numMyQueens = bitCount(myQueens);
    	numMyRooks = bitCount(myRooks);
    	numMyBishops = bitCount(myBishops);
    	numMyKnights = bitCount(myKnights);
    	numMyPawns = bitCount(myPawns);
        score = score - 900*numQueens-500*numRooks-300*numKnights-100*numPawns;
        score = score+900*numMyQueens+500*numMyRooks+300*numMyKnights+100*numMyPawns;
        if (numMyBishops>=2){
        	score+=numMyBishops*300;
        }
        else{
        	score+=numMyBishops*250;
        }
        if (numBishops>=2){
        	score-=numBishops*300;
        }
        else{
        	score-=numBishops*250;
        }
        
        //Weigh Threats
        long unsafeForMe,unsafeForYou;
        if (WhiteToMove) {
        	unsafeForMe=Moves.unsafeForWhite(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        	unsafeForYou=Moves.unsafeForBlack(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        } else {
        	unsafeForYou=Moves.unsafeForWhite(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        	unsafeForMe=Moves.unsafeForBlack(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        }
        
        int unsafeScore = 64*bitCount(yourPawns&unsafeForYou) + 300*bitCount((yourBishops|yourKnights)&unsafeForYou) + 500*bitCount(yourRooks&unsafeForYou) + 900*bitCount(yourQueens&unsafeForYou);
        unsafeScore = unsafeScore - 64*bitCount(myPawns&unsafeForMe) - 300*bitCount((myBishops|myKnights)&unsafeForMe) - 500*bitCount(myRooks&unsafeForMe) - 900*bitCount(myQueens&unsafeForMe);
        
        score += unsafeScore/2;
    	return score;
    }
    
    public static int Scoring6(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove){
    	/*
    	 * Weighs material value and number of available moves.
    	 * Also considers threatened pieces.
    	 * Less emphasis on threatened pieces than Scoring5
    	 */
    	
    	//Number of available moves
        String moves;
        if (WhiteToMove) {
            moves=Moves.possibleMovesW(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ);
        } else {
            moves=Moves.possibleMovesB(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ);
        }
        moves = Moves.filterMoves(moves, WhiteToMove, WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, EP);
        int score = moves.length();

        //Material value
        long myQueens,yourQueens,myRooks,yourRooks,myKnights,yourKnights,myBishops,yourBishops,myPawns,yourPawns,myKing,yourKing;
        int numQueens,numRooks,numBishops,numKnights,numPawns,numMyQueens,numMyRooks,numMyBishops,numMyKnights,numMyPawns;
        if (WhiteToMove) {
        	myQueens=WQ;yourQueens=BQ;
        	myRooks=WR;yourRooks=BR;
        	myKnights=WN;yourKnights=BN;
        	myBishops=WB;yourBishops=BB;
        	myPawns=WP;yourPawns=BP;
        	myKing=WK;yourKing=BK;
        	
        }else{
        	myQueens=BQ;yourQueens=WQ;
        	myRooks=BR;yourRooks=WR;
        	myKnights=BN;yourKnights=WN;
        	myBishops=BB;yourBishops=WB;
        	myPawns=BP;yourPawns=WP;
        	myKing=BK;yourKing=WK;
        }
    	numQueens = bitCount(yourQueens);
    	numRooks = bitCount(yourRooks);
    	numBishops = bitCount(yourBishops);
    	numKnights = bitCount(yourKnights);
    	numPawns = bitCount(yourPawns);
    	numMyQueens = bitCount(myQueens);
    	numMyRooks = bitCount(myRooks);
    	numMyBishops = bitCount(myBishops);
    	numMyKnights = bitCount(myKnights);
    	numMyPawns = bitCount(myPawns);
        score = score - 900*numQueens-500*numRooks-300*numKnights-100*numPawns;
        score = score+900*numMyQueens+500*numMyRooks+300*numMyKnights+100*numMyPawns;
        if (numMyBishops>=2){
        	score+=numMyBishops*300;
        }
        else{
        	score+=numMyBishops*250;
        }
        if (numBishops>=2){
        	score-=numBishops*300;
        }
        else{
        	score-=numBishops*250;
        }
        
        //Weigh Threats
        long unsafeForMe,unsafeForYou;
        if (WhiteToMove) {
        	unsafeForMe=Moves.unsafeForWhite(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        	unsafeForYou=Moves.unsafeForBlack(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        } else {
        	unsafeForYou=Moves.unsafeForWhite(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        	unsafeForMe=Moves.unsafeForBlack(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        }
        
        int unsafeScore = 64*bitCount(yourPawns&unsafeForYou) + 300*bitCount((yourBishops|yourKnights)&unsafeForYou) + 500*bitCount(yourRooks&unsafeForYou) + 900*bitCount(yourQueens&unsafeForYou);
        unsafeScore = unsafeScore - 64*bitCount(myPawns&unsafeForMe) - 300*bitCount((myBishops|myKnights)&unsafeForMe) - 500*bitCount(myRooks&unsafeForMe) - 900*bitCount(myQueens&unsafeForMe);
        
        score += unsafeScore/4;
    	return score;
    }
    

    public static int Scoring7(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove){
    	/*
    	 * Weighs material value and number of available moves.
    	 * Also considers threatened pieces.
    	 * Added consideration for threatening king
    	 */
    	
    	//Number of available moves
        String moves;
        if (WhiteToMove) {
            moves=Moves.possibleMovesW(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ);
        } else {
            moves=Moves.possibleMovesB(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ);
        }
        moves = Moves.filterMoves(moves, WhiteToMove, WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, EP);
        int score = 5000 + moves.length();
        //Material value
        long myQueens,yourQueens,myRooks,yourRooks,myKnights,yourKnights,myBishops,yourBishops,myPawns,yourPawns,myKing,yourKing;
        int numQueens,numRooks,numBishops,numKnights,numPawns,numMyQueens,numMyRooks,numMyBishops,numMyKnights,numMyPawns;
        if (WhiteToMove) {
        	myQueens=WQ;yourQueens=BQ;
        	myRooks=WR;yourRooks=BR;
        	myKnights=WN;yourKnights=BN;
        	myBishops=WB;yourBishops=BB;
        	myPawns=WP;yourPawns=BP;
        	myKing=WK;yourKing=BK;
        	
        }else{
        	myQueens=BQ;yourQueens=WQ;
        	myRooks=BR;yourRooks=WR;
        	myKnights=BN;yourKnights=WN;
        	myBishops=BB;yourBishops=WB;
        	myPawns=BP;yourPawns=WP;
        	myKing=BK;yourKing=WK;
        }
    	numQueens = bitCount(yourQueens);
    	numRooks = bitCount(yourRooks);
    	numBishops = bitCount(yourBishops);
    	numKnights = bitCount(yourKnights);
    	numPawns = bitCount(yourPawns);
    	numMyQueens = bitCount(myQueens);
    	numMyRooks = bitCount(myRooks);
    	numMyBishops = bitCount(myBishops);
    	numMyKnights = bitCount(myKnights);
    	numMyPawns = bitCount(myPawns);
        score = score - 900*numQueens-500*numRooks-300*numKnights-100*numPawns;
        score = score+900*numMyQueens+500*numMyRooks+300*numMyKnights+100*numMyPawns;
        if (numMyBishops>=2){
        	score+=numMyBishops*300;
        }
        else{
        	score+=numMyBishops*250;
        }
        if (numBishops>=2){
        	score-=numBishops*300;
        }
        else{
        	score-=numBishops*250;
        }
        
        //Weigh Threats
        long unsafeForMe,unsafeForYou;
        if (WhiteToMove) {
        	unsafeForMe=Moves.unsafeForWhite(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        	unsafeForYou=Moves.unsafeForBlack(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        } else {
        	unsafeForYou=Moves.unsafeForWhite(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        	unsafeForMe=Moves.unsafeForBlack(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        }
        
        int unsafeScore = 64*bitCount(yourPawns&unsafeForYou) + 300*bitCount((yourBishops|yourKnights)&unsafeForYou) + 500*bitCount(yourRooks&unsafeForYou) + 900*bitCount(yourQueens&unsafeForYou);
        unsafeScore = unsafeScore - 64*bitCount(myPawns&unsafeForMe) - 300*bitCount((myBishops|myKnights)&unsafeForMe) - 500*bitCount(myRooks&unsafeForMe) - 900*bitCount(myQueens&unsafeForMe);
        unsafeScore = unsafeScore + 1300*bitCount(yourKing&unsafeForYou) - 1300*bitCount(myKing&unsafeForMe);
        score += unsafeScore/2;
    	return score;
    }
   
    public static int Scoring8(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove,boolean selfColorIsWhite){
    	/*
    	 * Weighs material value and number of available moves.
    	 * Also considers threatened pieces.
    	 * Less emphasis on threatened pieces than Scoring5
    	 * Added consideration for threatening king
    	 */
    	
    	//Number of available moves
        String moves;
        if (selfColorIsWhite) {
            moves=Moves.possibleMovesW(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ);
        } else {
            moves=Moves.possibleMovesB(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ);
        }
        moves = Moves.filterMoves(moves, WhiteToMove, WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, EP);
        int score = moves.length();
        //Material value
        long myQueens,yourQueens,myRooks,yourRooks,myKnights,yourKnights,myBishops,yourBishops,myPawns,yourPawns,myKing,yourKing;
        int numQueens,numRooks,numBishops,numKnights,numPawns,numMyQueens,numMyRooks,numMyBishops,numMyKnights,numMyPawns;
        if (selfColorIsWhite) {
        	myQueens=WQ;yourQueens=BQ;
        	myRooks=WR;yourRooks=BR;
        	myKnights=WN;yourKnights=BN;
        	myBishops=WB;yourBishops=BB;
        	myPawns=WP;yourPawns=BP;
        	myKing=WK;yourKing=BK;
        	
        }else{
        	myQueens=BQ;yourQueens=WQ;
        	myRooks=BR;yourRooks=WR;
        	myKnights=BN;yourKnights=WN;
        	myBishops=BB;yourBishops=WB;
        	myPawns=BP;yourPawns=WP;
        	myKing=BK;yourKing=WK;
        }
    	numQueens = bitCount(yourQueens);
    	numRooks = bitCount(yourRooks);
    	numBishops = bitCount(yourBishops);
    	numKnights = bitCount(yourKnights);
    	numPawns = bitCount(yourPawns);
    	numMyQueens = bitCount(myQueens);
    	numMyRooks = bitCount(myRooks);
    	numMyBishops = bitCount(myBishops);
    	numMyKnights = bitCount(myKnights);
    	numMyPawns = bitCount(myPawns);
        score = score - 900*numQueens-500*numRooks-300*numKnights-100*numPawns;
        score = score+900*numMyQueens+500*numMyRooks+300*numMyKnights+100*numMyPawns;
        if (numMyBishops>=2){
        	score+=numMyBishops*300;
        }
        else{
        	score+=numMyBishops*250;
        }
        if (numBishops>=2){
        	score-=numBishops*300;
        }
        else{
        	score-=numBishops*250;
        }
        
        //Weigh Threats
        long unsafeForMe,unsafeForYou;
        if (selfColorIsWhite) {
        	unsafeForMe=Moves.unsafeForWhite(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        	unsafeForYou=Moves.unsafeForBlack(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        } else {
        	unsafeForYou=Moves.unsafeForWhite(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        	unsafeForMe=Moves.unsafeForBlack(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
        }
        
        int unsafeScore = 64*bitCount(yourPawns&unsafeForYou) + 300*bitCount((yourBishops|yourKnights)&unsafeForYou) + 500*bitCount(yourRooks&unsafeForYou) + 900*bitCount(yourQueens&unsafeForYou);
        unsafeScore = unsafeScore - 64*bitCount(myPawns&unsafeForMe) - 300*bitCount((myBishops|myKnights)&unsafeForMe) - 500*bitCount(myRooks&unsafeForMe) - 900*bitCount(myQueens&unsafeForMe);
        unsafeScore = unsafeScore + 900*bitCount(yourKing&unsafeForYou) - 900*bitCount(myKing&unsafeForMe);
        score += unsafeScore/4;
        //System.out.println(score);
    	return score;
    }
    
    public static int Scoring9(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove,boolean selfColorIsWhite){
    	/*
    	 * Minimized the scores of the opponent pieces
    	 */
        int numQueens,numRooks,numBishops,numKnights,numPawns;
        if (selfColorIsWhite) {
        	numQueens = bitCount(BQ);
        	numRooks = bitCount(BR);
        	numBishops = bitCount(BB);
        	numKnights = bitCount(BN);
        	numPawns = bitCount(BP);
        }else{
        	numQueens = bitCount(WQ);
        	numRooks = bitCount(WR);
        	numBishops = bitCount(WB);
        	numKnights = bitCount(WN);
        	numPawns = bitCount(WP);       	
        }
        int score = 100-numQueens*9-numRooks*5-(numKnights+numBishops)*3-numPawns;
    	return score;
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
}
