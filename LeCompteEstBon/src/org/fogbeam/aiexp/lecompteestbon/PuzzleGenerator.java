package org.fogbeam.aiexp.lecompteestbon;

import java.util.Random;

/* 
 	"Le Compte Est Bon" is a French number puzzle game where a player is given a target
 	number and 6 other numbers, and is then asked to find a combination of some (not necessarily all)
 	of the other six numbers, which can be combined using subtraction, addition, multiplication
 	and/or division, to generate the target number.   The game is played on the French TV
 	game show "Des chiffres et des lettres". https://en.wikipedia.org/wiki/Des_chiffres_et_des_lettres
 	
 	 In 1987, AI researcher Daniel Defays created an approach called "Numbo" to play the game.  In order to
 	 experiment with some Numbo like techniques, we need a quick way to generate valid games and show the
 	 solution(s).  This generator will generate a target number, the "brick" numbers, and will show
 	 at least one solution using the provided bricks.   No puzzle will be generated unless there is
 	 at least one valid solution.  The generator is not required to list all of the valid solutions
 	 and may show as few as one.  
 	 
 */

public class PuzzleGenerator 
{

	public static void main(String[] args) 
	{
		
		Random random = new Random();
		
		/* in the first cut, the number of puzzles to generate is hard-coded to 10 */
		for (int i = 0; i < 10; i++ )
		{
			
			// step one - generate our "target" number
			int target = random.nextInt( 245 ) + 10;
			
			// create an empty "bricks" array
			int[] bricks = {0,0,0,0,0,0};
			int numBricksFound = 0;
			
			while( numBricksFound < 6 )
			{
				// generate a trial "brick"
				
			}
			
			
			
		}

	}

}
