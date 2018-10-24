package fr.HtSTeam.HtS.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lang {
	
	enum Langs {
		en_UK,
		fr_FR;
	}
	
	private static Langs lang = Langs.en_UK;
	
	/**
	 * Sets the displayed plugin's language 
	 * @param language
	 */
	public static void setLang(Langs language) {
		lang = language;
	}
	
	/**
	 * Returns the displayed plugin's language
	 * @return lang
	 */
	public static Langs getLang() {
		return lang;
	}
	
	/**
	 * Returns the value of this string in the current language
	 * @param string
	 * @return string
	 */
	public static String get(final String string) {
		try {
			File file = new File(Lang.class.getClassLoader().getResource("langs/" + lang.toString() + ".lang").getFile());
			try (Stream<String> stream = Files.lines(file.toPath())) {
				List<String> list = stream.filter(s -> string.matches(s.split("=")[0])).collect(Collectors.toList());
				if (list.isEmpty() || list.get(0).isEmpty())
					return l(string);
				else {
					String str = list.get(0).split("=")[1];
					for(String s : Arrays.asList(string.split("[.]")))
						if (!list.get(0).split("=")[0].contains(s))
							str = str.replaceFirst("(-REPLACE-)", s);
					return str;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return string;
			}
		} catch (NullPointerException e) {
			return string;
		}
	}
	
	/**
	 * Returns the value of this string in english
	 * @param string
	 * @return string
	 */
	private static String l(final String string) {
		try {
			File file = new File(Lang.class.getClassLoader().getResource("langs/en_UK.lang").getFile());
			try (Stream<String> stream = Files.lines(file.toPath())) {
				List<String> list = stream.filter(s -> string.matches(s.split("=")[0])).collect(Collectors.toList());
				if (list.isEmpty() || list.get(0).isEmpty())
					return string;
				else {
					String str = list.get(0).split("=")[1];
					for(String s : Arrays.asList(string.split("[.]")))
						if (!list.get(0).split("=")[0].contains(s))
							str = str.replaceFirst("(-REPLACE-)", s);
					return str;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return string;
			}
		} catch (NullPointerException e) {
			return string;
		}
	}
}