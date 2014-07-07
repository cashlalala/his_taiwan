/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package multilingual;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * @author opo10818
 */
public class Language {

	private static Language lang;

	private static ResourceBundle rb;

	public static Language getInstance() {
		return getInstance(Locale.getDefault());
	}

	public String getString(String id) {
		return rb.getString(id);
	}

	public static Language getInstance(Locale locale) {
		if (null == lang) {
			lang = new Language(locale);
		}
		return lang;
	}

	private Language(Locale locale) {
		File resourceFile = new File("lang/");
		if (resourceFile.exists()) { // for testing purpose or alternative approach
			URL[] urls = new URL[1];
			try {
				urls[0]  = resourceFile.toURI().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			ClassLoader loader = new URLClassLoader(urls);
			rb = ResourceBundle.getBundle("language", locale, loader);			
		}
		else //basically we use the RC in classpath
			rb = ResourceBundle.getBundle("lang.language", locale);
	}

	public String setlanguage(String Title) {
		// try {
		//
		// FileReader reader = new FileReader("./lib/language");
		// char buffer[] = new char[100000];
		// // 利用 Reader 物件的 read 方法讀文字資料
		// reader.read(buffer);
		// String[] paragraph = new String(buffer).split("@");
		// for (int i = 0; i < paragraph.length; i++) {
		// String[] line = new String(paragraph[i]).split("\n");
		// if (line[0].equals(Title)) {
		// return paragraph[i];
		// }
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		return "";
	}

	@Deprecated
	public String getLanguage(String line[], String key) {
		return rb.getString(key.toUpperCase());

		// String word = null;
		// for (int i = 1; i < line.length; i++) {
		// String[] col = line[i].split("=");
		// if (col[0].equals(character))
		// word = col[1];
		// }
		// return word;
	}
}
