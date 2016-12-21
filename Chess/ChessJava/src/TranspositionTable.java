import java.util.HashMap;
import java.util.HashSet;

public class TranspositionTable {
	public static HashSet<Long> allKeys = new HashSet<Long>();
	public static HashMap<Long,String> bestMoveMap = new HashMap<Long,String>();
	public static HashMap<Long,Integer> evaluationMap = new HashMap<Long,Integer>();
	public static HashMap<Long,Integer> alphaMap = new HashMap<Long,Integer>();
	public static HashMap<Long,Integer> betaMap = new HashMap<Long,Integer>();
	
	public static void addValue(long key,String bestMove,int score,int alpha,int beta){
		allKeys.add(key);
		bestMoveMap.put(key, bestMove);
		evaluationMap.put(key,score);
		alphaMap.put(key, alpha);
		betaMap.put(key, beta);
	}
	public static boolean contains(long key){
		return allKeys.contains(key);
	}
	public static String getBestMove(long key){
		return bestMoveMap.get(key);
	}
	public static int getEvaluation(long key){
		return evaluationMap.get(key);
	}
	public static int getAlpha(long key){
		return alphaMap.get(key);
	}
	public static int getBeta(long key){
		return betaMap.get(key);
	}
}
