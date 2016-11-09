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
		Long expectedResult = 0b0000000000000000000000000000000000000000000000000000000011111111L;
		Long result = myBoard.rotateCW90Deg (bitboard);
		assert(result.equals(expectedResult));
		//fail("Not yet implemented");
	}
	
	@Test
	public void testRotateCCW() {
		Chessboard myBoard = new Chessboard();
		Long bitboard = 0b1111111100000000000000000000000000000000000000000000000000000000L;
		Long expectedResult = 0b1000000010000000100000001000000010000000100000001000000010000000L;
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
		Long expectedResult = 0b1111111100000000000000000000000000000000000000000000000000000000L;
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
		Long expectedResult = 0b0000000100000001000000010000000100000001000000010000000100000001L;
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
}
