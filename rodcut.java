// Created by James Trimble (on Twitter @jamestrimble)
// A description of the problem is available here, http://users.csc.calpoly.edu/~dekhtyar/349-Spring2010/lectures/lec09.349.pdf
public class rodcut {

	static int iterations = 0;
	static int goal = 20;
	static Integer memo[][] = new Integer[goal+1][10];

	public static void main(String[] args) {
		Integer pieces[][] = {{1,2,3,4,5,6,7,8,9,10},{1,5,4,9,10,17,17,20,24,30}};
		
		for (int i = 0; i < goal+1; i++) {
			for (int j = 0; j < 10; j++) {
				memo[i][j] = -1;
			}
		}
		
		System.out.println("\nMax Revenue = "+naive(goal,pieces[0].length-1,pieces));
		System.out.println("Iterations = "+iterations);
		
		iterations = 0;
		
		System.out.println("\nMax Revenue = "+topdown(goal,pieces[0].length-1,pieces));
		System.out.println("Iterations = "+iterations);	
		
		iterations = 0;
		
		System.out.println("\nMax Revenue = "+bottomup(goal,pieces[0].length-1,pieces));
		System.out.println("Iterations = "+iterations);	
	}

	public static Integer naive(Integer avail, Integer i, Integer[][] pieces) {
		rodcut.iterations++;
		//System.out.print("naive("+avail+","+i+")  ");
		//if (iterations % 5 == 0) System.out.print("\n");
		
		int length = 0;
		int value = 1;
		
		if (i < 0 || avail == 0) {
			return 0;
		}
		else if (avail < pieces[length][i]) {
			return naive(avail, i-1, pieces);
		}
		else {
			return Math.max(
					pieces[value][i]+naive(avail-pieces[length][i], pieces[length].length-1, pieces),
					naive(avail, i-1, pieces)
			);
		}
	}
	
	public static Integer topdown(Integer avail, Integer i, Integer[][] pieces) {
		//System.out.println("naive("+avail+","+i+")");
		rodcut.iterations++;
		int length = 0;
		int value = 1;
		
		if (i < 0 || avail == 0) {
			return 0;
		}
		else if (avail < pieces[length][i]) {
			return memoize(avail, i-1, pieces);
		}
		else {		
			return Math.max(
					pieces[value][i]+memoize(avail-pieces[length][i], pieces[length].length-1, pieces),
					memoize(avail, i-1, pieces)
			);
		}
	}
	
	public static Integer memoize(Integer avail, Integer i, Integer [][] pieces) {
		if (i < 0 || avail == 0) {
			 return 0;
		}
		else if (memo[avail][i] == -1) {
			memo[avail][i] = topdown(avail, i, pieces);
			return memo[avail][i];
		}
		else {
			return memo[avail][i];
		}
	}
	
	public static Integer bottomup(Integer avail, Integer i, Integer[][] pieces) {
		Integer best[][] = new Integer[2][avail+1];
		Integer value = 1, length = 0;
		
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < avail+1; y++) {
				best[x][y] = -1;
			}
		}
		
		best[0][0] = 0;
		best[1][0] = 0;
		
		for (int targetLength = 1; targetLength <= avail; targetLength++) {
			for (int currentPiece = 0; currentPiece < pieces[0].length; currentPiece++) {
				rodcut.iterations++;
				if (targetLength-pieces[length][currentPiece] >= 0 && 
						best[value][targetLength-pieces[length][currentPiece]]+pieces[value][currentPiece] > best[value][targetLength]) {
					best[value][targetLength] = best[value][targetLength-pieces[length][currentPiece]]+pieces[value][currentPiece];
					best[length][targetLength] = best[length][targetLength-pieces[length][currentPiece]]+pieces[length][currentPiece];
				}
			}
		}
		
		return best[value][avail];
	}
}


