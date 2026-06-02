package parser;

public class LexerHelper {
	
	public static int lexemeToInt(String str) {
		try {
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}

	// TODO: Implement the lexemeToChar and lexemeToReal methods

	public static double lexemeToReal(String str) {
		try {
			return Double.parseDouble(str);
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}

	public static char lexemeToChar(String str) {
		try {
			if (str.length() == 3){
				return str.charAt(1);
			} else if (Character.isDigit(str.charAt(2))){
				String str_sin_comillas = str.substring(1,str.length()-1);
				int number = Integer.parseInt(str_sin_comillas.substring(1));
				char result = (char) number;
				return result;
			} else if (Character.isLetter(str.charAt(2))) {
				switch (str.charAt(2)) {
					case 'n': return '\n';
					case 't': return '\t';
				}
			}
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return '0';
	}
}
