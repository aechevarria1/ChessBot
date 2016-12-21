import java.util.Random;

public class Orion {
	// Pieces
    static long WP=0L,WN=0L,WB=0L,WR=0L,WQ=0L,WK=0L,BP=0L,BN=0L,BB=0L,BR=0L,BQ=0L,BK=0L,EP=0L;
    //Castling Rights and whose turn it is
    static boolean CWK=true,CWQ=true,CBK=true,CBQ=true,WhiteToMove=true;//true=castle is possible
    //???
    static long UniversalWP=0L,UniversalWN=0L,UniversalWB=0L,UniversalWR=0L,
            UniversalWQ=0L,UniversalWK=0L,UniversalBP=0L,UniversalBN=0L,
            UniversalBB=0L,UniversalBR=0L,UniversalBQ=0L,UniversalBK=0L,
            UniversalEP=0L;
    //Other Needed Constants
    static int searchDepth=4,moveCounter;
    static int MATE_SCORE=50000,NULL_INT=Integer.MIN_VALUE;
    public static void main(String[] args) {
        //Zobrist.zobristFillArray();
        //BoardGeneration.importFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        //UCI.inputPrint();
    	//Test take piece (best move f4e5)
        //BoardGeneration.importFEN("r1bqkb2/2pp1p1r/p3p2p/1p2n1pn/NPP1PP2/3P4/P1Q1N1PP/R1B1KB1R w KQq - 1 11 ");
        //UCI.inputPrint();
    	//Test Avoid Stalemate and black can checkmate (do not d2c2)
        //BoardGeneration.importFEN("8/6k1/8/8/8/8/3q1bp1/K7 b - - 2 1");
        //BoardGeneration.importFEN("k7/3Q1BP1/8/8/8/8/6K1/8 w - - 2 1 ");
        //UCI.inputPrint();
    	//Test Avoid checkmate in 2 moves and black can checkmate (do not protect knight)
        //BoardGeneration.importFEN("r3k1n1/p2pppb1/bpn3p1/4P3/5P1P/1PPq4/P2PN1P1/RN2K2R w q - 3 20");
        //BoardGeneration.importFEN("rn2k2r/p2pn1p1/1ppQ4/5p1p/4p3/BPN3P1/P2PPPB1/R3K1N1 b Qkq - 3 1");
        //UCI.inputPrint();
    	//Mate in 3?
    	//BoardGeneration.importFEN("rnbk1Q2/8/4p3/pBB1Np2/P7/2P5/5PPP/R4K1R b - - 0 28");
    	
    	
        //BoardGeneration.importFEN("r1bqkbnr/ppppppp1/2n5/7p/8/P3P1P1/1PPP1P1P/RNBQKBNR w KQkq - 0 4");
        //BoardGeneration.importFEN("rnbqkbnr/1ppp1p1p/p3p1p1/8/7P/2N5/PPPPPPP1/R1BQKBNR b KQkq - 0 1");
        //UCI.inputPrint();
    	
    	
        //long startTime = System.currentTimeMillis();
        //System.out.println(PrincipalVariation.pvSearch(-1000,1000,WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ,!WhiteToMove,0));
        //System.out.println("Searched "+moveCounter+" moves");
        //long endTime = System.currentTimeMillis();
        //System.out.println("That took " + (endTime - startTime) + " milliseconds");
        UCI.uciCommunication();
    }
}