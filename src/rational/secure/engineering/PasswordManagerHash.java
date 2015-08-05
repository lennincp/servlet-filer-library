/*********************************************************************** 
  PasswordManagerHash.java 

  Copyright (C) 2006 Neil Daswani 

  This file is also available at http://www.learnsecurity.com/ntk 
 ***********************************************************************/

package rational.secure.engineering;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.ServletContext;

public class PasswordManagerHash {

	private static Hashtable<String, String> userHashTable;
	private static String pwdFile;// = "pwdFileHash.txt";

	public static void setPwdFile(String pwdFile) {
		PasswordManagerHash.pwdFile = pwdFile;
	}

	public static void setUserHashTable(Hashtable<String, String> userHashTable) {
		PasswordManagerHash.userHashTable = userHashTable;
	}
	
	/** adds user credentials into the hashtable */
	public static void add(String username, String password) throws Exception {
		userHashTable.put(username, computeSHA(password));
	}

	/**
	 * stores the user credentials from the hashtable into the password word by
	 * calling the helper class function
	 */
	public static void flush() throws Exception {
		PasswordFileManager.store(pwdFile, userHashTable);
	}

	/** returns the SHA-256 hash of the provided preimage as a String */
	private static String computeSHA(String preimage) throws Exception {
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA-256");
		md.update(preimage.getBytes("UTF-8"));
		byte raw[] = md.digest();
		String encoded = new String(Base64Coder.encode(raw));
		return encoded;
	}

	/**
	 * compares user-entered password with the password in the
	 * hashtable/password file
	 */
	public static boolean checkPassword(String username, String password) {
		try {
			String p = (String) userHashTable.get(username);
			return (p == null) ? false : p.equals(computeSHA(password));
		} catch (Exception e) {}
		return false;
	}

	/** prints a given message into the console */
	public static void print(String msg) {
		System.out.println(msg);
	}

	/** returns the user input string */
	public static String getInput(String msg) throws Exception {
		System.out.print(msg);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		return input;
	}

	public static void init(ServletContext context, Class receiverClass, String filepath) { 
		pwdFile = context.getRealPath("/WEB-INF/classes/" + filepath); 
		userHashTable = PasswordFileManager.load(receiverClass.getClassLoader().getResourceAsStream(filepath));
	}
}