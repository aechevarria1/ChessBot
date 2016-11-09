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
	public void testFalse() {
		assert(1==0);
		//fail("Not yet implemented");
	}
	
	@Test
	public void StartingWhiteMoves() {
		Chessboard myBoard = new Chessboard();
		Long whiteMoves = myBoard.generatePawnMoves (1);
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
}
