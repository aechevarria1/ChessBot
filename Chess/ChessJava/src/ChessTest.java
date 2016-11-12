import static org.junit.Assert.*;

import org.junit.Test;

public class ChessTest {

	@Test
	public void testTrue() {
		assert(true);
		//fail("Not yet implemented");
		//assertEquals(1,1);
		
	}
	
	@Test
	public void StartingWhiteMoves() {
		Chessboard myBoard = new Chessboard();
		Long whiteMoves = myBoard.generatePawnMoves(1);
		assert(whiteMoves==0b11111111111111110000000000000000L);
		//fail("Not yet implemented");
	}
	
	@Test
	public void startingBlackMoves() {
		Chessboard myBoard = new Chessboard();
		Long blackMoves = myBoard.generatePawnMoves (0);
		assert(blackMoves==0b111111111111111100000000000000000000000000000000L);
		//fail("Not yet implemented");
	}
	
	@Test
	public void testRotateCW() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b0000000100000001000000010000000100000001000000010000000100000001L;
		Long expectedResult = 0b1111111100000000000000000000000000000000000000000000000000000000L;
		Long result = myBoard.rotateCW90Deg (bitboard);
		assert(result.equals(expectedResult));
		//fail("Not yet implemented");
	}
	
	@Test
	public void testRotateCCW() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b1111111100000000000000000000000000000000000000000000000000000000L;
		Long expectedResult = 0b0000000100000001000000010000000100000001000000010000000100000001L;
		Long result = myBoard.rotateCCW90Deg (bitboard);
		assert(result.equals(expectedResult));
		//fail("Not yet implemented");
	}
	@Test
	public void testRotateCW2() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b0000000100000001000000010000000100000001000000010000000100000001L;
		Long expectedResult = 0b1000000010000000100000001000000010000000100000001000000010000000L;
		Long result = myBoard.rotateCW90Deg (bitboard);
		result = myBoard.rotateCW90Deg (result);
		assert(result.equals(expectedResult));
		//fail("Not yet implemented");
	}
	
	@Test
	public void testRotateCCW2() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b1111111100000000000000000000000000000000000000000000000000000000L;
		Long expectedResult = 0b0000000000000000000000000000000000000000000000000000000011111111L;
		Long result = myBoard.rotateCCW90Deg (bitboard);
		result = myBoard.rotateCCW90Deg (result);
		assert(result.equals(expectedResult));
		//fail("Not yet implemented");
	}
	@Test
	public void testRotateCW3() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b0000000100000001000000010000000100000001000000010000000100000001L;
		Long expectedResult = 0b0000000000000000000000000000000000000000000000000000000011111111L;
		Long result = myBoard.rotateCW90Deg (bitboard);
		result = myBoard.rotateCW90Deg (result);
		result = myBoard.rotateCW90Deg (result);
		assert(result.equals(expectedResult));
		//fail("Not yet implemented");
	}
	
	@Test
	public void testRotateCCW3() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b1111111100000000000000000000000000000000000000000000000000000000L;
		Long expectedResult = 0b1000000010000000100000001000000010000000100000001000000010000000L;
		Long result = myBoard.rotateCCW90Deg (bitboard);
		result = myBoard.rotateCCW90Deg (result);
		result = myBoard.rotateCCW90Deg (result);
		assert(result.equals(expectedResult));
		//fail("Not yet implemented");
	}
	@Test
	public void testRotateCW4() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b0000000100000001000000010000000100000001000000010000000100000001L;
		Long expectedResult = 0b0000000100000001000000010000000100000001000000010000000100000001L;
		Long result = myBoard.rotateCW90Deg (bitboard);
		result = myBoard.rotateCW90Deg (result);
		result = myBoard.rotateCW90Deg (result);
		result = myBoard.rotateCW90Deg (result);
		assert(result.equals(expectedResult));
		//fail("Not yet implemented");
	}
	
	@Test
	public void testRotateCCW4() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b1111111100000000000000000000000000000000000000000000000000000000L;
		Long expectedResult = 0b1111111100000000000000000000000000000000000000000000000000000000L;
		Long result = myBoard.rotateCCW90Deg (bitboard);
		result = myBoard.rotateCCW90Deg (result);
		result = myBoard.rotateCCW90Deg (result);
		result = myBoard.rotateCCW90Deg (result);
		assert(result.equals(expectedResult));
		//fail("Not yet implemented");
	}
	
	@Test
	public void knightInMiddle() {
		Long WN             = 0b0000000000000000000000000000100000000000000000000000000000000000L;
		Long WP             = 0b0L;
		Long expectedResult = 0b0000000000010100001000100000000000100010000101000000000000000000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void knightOnLeft() {
		Long WN             = 0b0000000000000000000000010000000000000000000000000000000000000000L;
		Long WP             = 0b0L;
		Long expectedResult = 0b0000001000000100000000000000010000000010000000000000000000000000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void knightOnLeft2() {
		Long WN             = 0b0000000000000000000000000000000000000000000000100000000000000000L;
		Long WP             = 0b0L;
		Long expectedResult = 0b0000000000000000000000000000010100001000000000000000100000000101L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void knightOnRight() {
		Long WN             = 0b0000000000000000100000000000000000000000000000000000000000000000L;
		Long WP             = 0b0L;
		Long expectedResult = 0b0100000000100000000000000010000001000000000000000000000000000000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void knightOnRight2() {
		Long WN             = 0b0000000000000000000000000000000001000000000000000000000000000000L;
		Long WP             = 0b0L;
		Long expectedResult = 0b0000000000000000101000000001000000000000000100001010000000000000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void knightOnTop() {
		Long WN             = 0b0000100000000000000000000000000000000000000000000000000000000000L;
		Long WP             = 0b0L;
		Long expectedResult = 0b0000000000100010000101000000000000000000000000000000000000000000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void knightOnTop2() {
		Long WN             = 0b0000000000001000000000000000000000000000000000000000000000000000L;
		Long WP             = 0b0L;
		Long expectedResult = 0b0010001000000000001000100001010000000000000000000000000000000000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void knightOnBottom() {
		Long WN             = 0b0000000000000000000000000000000000000000000000000000000000000100L;
		Long WP             = 0b0L;
		Long expectedResult = 0b0000000000000000000000000000000000000000000010100001000100000000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void knightOnBottom2() {
		Long WN             = 0b0000000000000000000000000000000000000000000000000010000000000000L;
		Long WP             = 0b0L;
		Long expectedResult = 0b0000000000000000000000000000000001010000100010000000000010001000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void knightInCorner() {
		Long WN             = 0b0000000000000000000000000000000000000000000000000000000000000001L;
		Long WP             = 0b0L;
		Long expectedResult = 0b0000000000000000000000000000000000000000000000100000010000000000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void twoKnights() {
		Long WN             = 0b0000000000000000000000000001000000000000000000000000001000000000L;
		Long WP             = 0b0L;
		Long expectedResult = 0b0000000000101000010001000000000001000101001010000000000000001000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void twoKnightsAndPawns() {
		Long WN             = 0b0000000000000000000000000001000000000000000000000000001000000000L;
		Long WP             = 0b0000000000000010010000000000000000000000000010000000000000001000L;
		Long expectedResult = 0b0000000000101000000001000000000001000101001000000000000000000000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKnightMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void testRotate45CW() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b1000000001000000001000000001000000001000000001000000001000000001L;
		Long expectedResult = 0b0000000000000000000000000000111111110000000000000000000000000000L;
		Long result = myBoard.rotateCW45Deg (bitboard);
		assert(result.equals(expectedResult));
		//fail("Not yet implemented");
	}
	@Test
	public void testRotate45CCW() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b0000000100000010000001000000100000010000001000000100000010000000L;
		Long expectedResult = 0b0000000000000000000000000000111111110000000000000000000000000000L;
		Long result = myBoard.rotateCCW45Deg (bitboard);
		assert(result.equals(expectedResult));
		//fail("Not yet implemented");
	}
	@Test
	public void testRotate45CWUnrotate() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b0000000100000001000000010000000100000001000000010000000100000001L;
		Long result = myBoard.rotateCW45Deg (bitboard);
		result = myBoard.reverseRotateCW45Deg (result);
		assert(result.equals(bitboard));
		//fail("Not yet implemented");
	}
	@Test
	public void testRotate45CCWUnrotate() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b0000000100000001000000010000000100000001000000010000000100000001L;
		Long result = myBoard.rotateCCW45Deg (bitboard);
		result = myBoard.reverseRotateCCW45Deg (result);
		assert(result.equals(bitboard));
		//fail("Not yet implemented");
	}
	
	@Test
	public void testKings() {
		Long WK             = 0b0001000000000000000000010000100000000000000000001000000000010001L;
		Long WP             = 0b0000000000000000000000000000000000001000000000000000001000000000L;
		Long expectedResult = 0b0010100000111011000111100001011100010100110000000111100111101010L;
		long[] givenBoardInformation = {WK,0b0L,0b0L,0b0L,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateKingMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void testRooks() {
		Long WR             = 0b0000100000000000000000000000000000100000000000000000000000000001L;
		Long WP             = 0b0000001000000010010000000000000000001000000010000000000000000000L;
		Long expectedResult = 0b1111010100101001001010010010100111010001001000010010000111111110L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,0b0L,WR,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateRookMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void testBishops() {
		Long WB             = 0b0000100000000000000000000000000000100000000000000000000000000001L;
		Long WP             = 0b0000001000000010010000000000000000001000000010000000000000000000L;
		Long expectedResult = 0b0000000000010100101010100101000110000000010101001000101000000100L;
		long[] givenBoardInformation = {0b0L,0b0L,WB,0b0L,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateBishopMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void testQueens() {
		Long WQ             = 0b0000100000000000000000000000000000100000000000000000000000000001L;
		Long WP             = 0b0000001000000010010000000000000000001000000010000000000000000000L;
		Long expectedResult = 0b1111010100111101101010110111100111010001011101011010101111111110L;
		long[] givenBoardInformation = {0b0L,WQ,0b0L,0b0L,0b0L,WP,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generateQueenMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void testWhitePawns() {
		Long WP             = 0b0000100000100100000000000010000000000000000000000000010000001000L;
		Long BN             = 0b0010000000000000010001100000000000000100001010000000000000000000L;
		Long expectedResult = 0b0000010000000000011000000000000000000000000011000000100000000000L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,0b0L,0b0L,WP,0b0L,0b0L,0b0L,BN,0b0L,0b0L};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generatePawnMoves(1);
		assert(result.equals(expectedResult));
	}
	@Test
	public void testBlackPawns() {
		Long BP             = 0b0000100000100100000000000010000000000000000000000010010000001000L;
		Long WN             = 0b0010000000000000010001100000000000000100001010000000000000000000L;
		Long expectedResult = 0b0000000000001000011000100000000000100000000000000000000000100100L;
		long[] givenBoardInformation = {0b0L,0b0L,0b0L,WN,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L,0b0L,BP};
		Chessboard myBoard = new Chessboard(givenBoardInformation);
		Long result = myBoard.generatePawnMoves(0);
		assert(result.equals(expectedResult));
	}

}
