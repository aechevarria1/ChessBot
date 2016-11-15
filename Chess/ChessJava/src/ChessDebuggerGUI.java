import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ChessDebuggerGUI {
	//Make the GUI object that contains all other components
	private final JPanel gui = new JPanel(new GridLayout());
	// Make 8x8 grid for the game
	private JButton[][] chessBoardSquares = new JButton[8][8];
	
    private Image[][] chessPieceImages = new Image[2][6];
    
    // Make the panel for the chessboard
    private JPanel chessBoard;
    private static final String COLS = "ABCDEFGH";
    private static final String ROWS = "12345678";
    public static final int QUEEN = 0, KING = 1,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int[] STARTING_ROW = {
        ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
    };
    public static final int BLACK = 0, WHITE = 1;
    
    // Make the top and left labels for file and rank
	// Undo and Redo Buttons
	// New game button
	// Clear board button
	// Build Board button
	// Add specific pieces anywhere to the board
	// Clicking on the piece shows available moves
	// History of Moves Panel in the bottom
    
    ChessGUI() {
        initializeGui();
    }
    
    
    //Make chess piece images
    private final void createImages() {
        try {
            URL url = new URL("http://i.stack.imgur.com/memI0.png");
            BufferedImage bi = ImageIO.read(url);
            for (int ii = 0; ii < 2; ii++) {
                for (int jj = 0; jj < 6; jj++) {
                    chessPieceImages[ii][jj] = bi.getSubimage(
                            jj * 64, ii * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
