package util;

import java.util.Random;

public class GeradorUtil {
	
	String vetorString[] = new String[58];
	
	public GeradorUtil() {
		
		vetorString[0] = "BR";
		
		vetorString[1] = "A";
		vetorString[2] = "a";
		
		vetorString[45] = "-";

		vetorString[3] = "B";
		vetorString[4] = "b";
		
		vetorString[46] = "/";
		
		vetorString[5] = "C";
		vetorString[25] = "c";
		
		vetorString[47] = ":";
		
		vetorString[6] = "D";
		vetorString[26] = "d";
		
		vetorString[48] = "_";
		
		vetorString[7] = "E";
		vetorString[27] = "e";
		
		vetorString[49] = "=";
		
		vetorString[8] = "F";
		vetorString[28] = "f";

		vetorString[50] = " ";
		vetorString[51] = "-b";
		vetorString[52] = "_x";
		vetorString[53] = "BR_";
		vetorString[54] = "-b1";
		vetorString[55] = "BR-";
		vetorString[56] = "-BR";
		vetorString[57] = "R-";
		
		vetorString[9] = "G";
		vetorString[29] = "g";

		vetorString[10] = "H";
		vetorString[30] = "h";

		vetorString[11] = "I";
		vetorString[31] = "i";

		vetorString[12] = "J";
		vetorString[32] = "j";

		vetorString[13] = "K";
		vetorString[33] = "k";

		vetorString[14] = "L";
		vetorString[34] = "l";

		vetorString[15] = "M";
		vetorString[35] = "m";

		vetorString[16] = "N";
		vetorString[36] = "n";

		vetorString[17] = "O";
		vetorString[37] = "o";

		vetorString[18] = "P";
		vetorString[38] = "p";

		vetorString[19] = "Q";
		vetorString[39] = "q";

		vetorString[20] = "R";
		vetorString[40] = "r";

		vetorString[21] = "S";
		vetorString[41] = "s";

		vetorString[22] = "T";
		vetorString[42] = "t";

		vetorString[23] = "V";
		vetorString[43] = "v";

		vetorString[24] = "X";
		vetorString[44] = "x";

	}
	
	public String gerador() {
		//  
		return vetorString[randomInteger(50)] + vetorString[randomFase1(50)] + randomFase4() + vetorString[randomFase3(50)];
	}
	
	public int randomInteger(int n) {
		Random aleatorioInt = new Random();
		return aleatorioInt.nextInt(n);
	}
	public int randomFase1(int n) {
		Random aleatorioInt = new Random();
		return aleatorioInt.nextInt(n);
	}
	public int randomFase2(int n) {
		Random aleatorioInt = new Random();
		return aleatorioInt.nextInt(n);
	}
	public int randomFase3(int n) {
		Random aleatorioInt = new Random();
		return aleatorioInt.nextInt(n);
	}
	public int randomFase4() {
		Random aleatorioInt = new Random();
		return aleatorioInt.nextInt(400);
	}
}
