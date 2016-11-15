import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ChessGUI {
	//Make the GUI object that contains all other components
	private final JPanel gui = new JPanel(new GridBagLayout());
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
    
    public final void initializeGui(){
    	// Make images for chess pieces
    	createImages();
    	// Set up the tools
    	GridBagConstraints toolConstraints = new GridBagConstraints();
    	toolConstraints.gridx = 1;
    	toolConstraints.gridy = 1;
    	toolConstraints.gridheight = 2;
    	toolConstraints.gridwidth = 10;
    	JPanel tools = makeTools();
    	
    	// Chessboard
    	makeChessBoard();
    	GridBagConstraints chessBoardConstraints = new GridBagConstraints();
    	chessBoardConstraints.gridx = 1; chessBoardConstraints.gridy = 4; chessBoardConstraints.gridheight = 8; chessBoardConstraints.gridwidth = 8;
    	gui.add(chessBoard, chessBoardConstraints);
    	gui.add(tools, toolConstraints);
    }
    
    private final JPanel makeTools(){
    	JPanel tools = new JPanel(new GridBagLayout());
    	// Make Components
    	JButton newButton = new JButton("New");
    	JButton loadButton = new JButton("Load");
    	JLabel whiteTimerLabel = new JLabel("White Timer");
    	JLabel blackTimerLabel = new JLabel("Black Timer");
    	JLabel whiteTimer = new JLabel("0:00");
    	JLabel blackTimer = new JLabel("0:00");
    	// Specify Constraints
    	GridBagConstraints c1 = new GridBagConstraints();
    	c1.gridx = 1; c1.gridy = 1; c1.gridheight = 2; c1.gridwidth = 2;
    	GridBagConstraints c2 = new GridBagConstraints();
    	c2.gridx = 3; c2.gridy = 1; c2.gridheight = 2; c2.gridwidth = 2;
    	GridBagConstraints c3 = new GridBagConstraints();
    	c3.gridx = 5; c3.gridy = 1; c3.gridheight = 1; c3.gridwidth = 3;
    	GridBagConstraints c4 = new GridBagConstraints();
    	c4.gridx = 8; c4.gridy = 1; c4.gridheight = 1; c4.gridwidth = 3;
    	GridBagConstraints c5 = new GridBagConstraints();
    	c5.gridx = 5; c5.gridy = 2; c5.gridheight = 1; c5.gridwidth = 3;
    	GridBagConstraints c6 = new GridBagConstraints();
    	c6.gridx = 8; c6.gridy = 2; c6.gridheight = 1; c6.gridwidth = 3;
    	
    	tools.add(newButton,c1);
    	tools.add(loadButton,c2);
    	tools.add(whiteTimerLabel,c3);
    	tools.add(blackTimerLabel,c4);
    	tools.add(whiteTimer,c5);
    	tools.add(blackTimer,c6);
    	return tools;
    	
    }

    private final void makeChessBoard(){
    	chessBoard = new JPanel(new GridBagLayout());
    	// Make Squares in chessboard
    	for (int i=0;i<8;i++){
    		for (int j=0;j<8;j++){
    			String squarelabel = COLS.substring(i, i+1) + ROWS.substring(j, j+1);
    			JButton newButton = new JButton(squarelabel);
    			chessBoardSquares[j][i] =  newButton;
    	    	GridBagConstraints c = new GridBagConstraints();
    	    	c.gridx = i; c.gridy = j; c.gridheight = 1; c.gridwidth = 1;
    	    	chessBoard.add(newButton,c);
    		}
    	}	
    }
   
    public final JComponent getGui() {
        return gui;
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
    
    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                ChessGUI cg = new ChessGUI();

                JFrame f = new JFrame("ChessGUI");
                f.add(cg.getGui());
                // Ensures JVM closes after frame(s) closed and
                // all non-daemon threads are finished
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // See http://stackoverflow.com/a/7143398/418556 for demo.
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
    }
}
