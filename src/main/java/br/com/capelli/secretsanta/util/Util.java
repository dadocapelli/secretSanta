package br.com.capelli.secretsanta.util;

import java.util.Arrays;
import java.util.List;

public class Util {
	
	
	public static String retiraCaracteres(String str) {
		if (str != null) {
			String[] strAcentuada = { "ç", "Ç", "á", "Á", "ã", "Ã", "é", "É",
					"í", "Í", "ó", "Ó", "ô", "Ô", "õ", "Õ", "ú", "Ú", "º", "&",
					"#13", "ê", "Ê", "§", "ª", "Â", "Ä", "Ü", "´", "°", "¿",
					"â", "Ø", "`", "À", "Ñ", "ñ", "à", "€", "µ", "Å", "‡", "–",
					"¦", "ù", "Ù", "¯", "²", "³" };
			List<String> lsAcentuada = Arrays.asList(strAcentuada);

			String[] strTrocada = { "c", "C", "a", "A", "a", "A", "e", "E",
					"i", "I", "o", "O", "o", "O", "o", "O", "u", "U", ".", "e",
					" ", "e", "E", " ", " ", "A", "A", "U", " ", " ", " ", "a",
					"d", " ", "A", "N", "n", "a", " ", " ", "I", " ", " ", " ",
					"u", "U", " ", "2", "3" };
			List<String> lsTrocada = Arrays.asList(strTrocada);

			String words = str;
			char letra;
			String sLetra;
			for (int i = 0; i < words.length(); i++) {
				letra = words.charAt(i);
				sLetra = String.valueOf(letra);
				if (lsAcentuada.indexOf(sLetra) != -1) {
					words = words.substring(0, i)
							+ lsTrocada.get(lsAcentuada.indexOf(sLetra))
							+ words.substring(i + 1, words.length());
				}
			}
			return words.trim();
		} else {
			return (str);
		}
	}

}
