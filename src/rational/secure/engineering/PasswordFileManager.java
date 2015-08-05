/*********************************************************************** 
  PasswordFileManager.java 

  Copyright (C) 2006 Neil Daswani 

  This file is also available at http://www.learnsecurity.com/ntk 
 ***********************************************************************/

package rational.secure.engineering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class PasswordFileManager {

	public static final char DELIMITER = ':';
	public static final String DELIMITER_STR = "" + DELIMITER;

	public static Hashtable<String, String> load(InputStream pwdFile) {
		Hashtable<String, String> userMap = new Hashtable<String, String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(pwdFile));

			String line;
			while ((line = br.readLine()) != null) {
				int delim = line.indexOf(DELIMITER_STR);
				String username = line.substring(0, delim);
				String hpwd = line.substring(delim + 1);
				userMap.put(username, hpwd);
			}
		} catch (Exception e) {
			System.err.println("Warning: Could not load password file.");
		}
		return userMap;
	}

	public static void store(String pwdFile, Hashtable userHashTable) throws Exception {
		try {
			FileWriter fw = new FileWriter(pwdFile);

			Iterator<Entry<String, String>> it = userHashTable.entrySet().iterator();
			while (it.hasNext()) {
				String uname = (String) it.next().getKey();
				fw.write(uname + DELIMITER_STR
						+ userHashTable.get(uname).toString());
				fw.write(System.getProperty("line.separator"));
			}
			fw.close();
		} catch (Exception e) {
			System.out.println("error writing to password file!");
		}
	}
}

class PasswordFileManagerHashedSalted extends PasswordFileManager {

	public static Hashtable load(String pwdFile) {
		Hashtable userMap = new Hashtable();
		try {
			FileReader fr = new FileReader(pwdFile);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				int delim = line.indexOf(DELIMITER_STR);
				String username = line.substring(0, delim);
				PasswordFileManagerHashedTuple ur = new PasswordFileManagerHashedTuple(line.substring(delim + 1));
				userMap.put(username, ur);
			}
		} catch (Exception e) {
			System.err.println("Warning: Could not load password file.");
		}
		return userMap;
	}
}

class PasswordFileManagerHashedTuple {

	private String dHpwd;
	private int dSalt;

	public PasswordFileManagerHashedTuple(String p, int s) {
		dHpwd = p;
		dSalt = s;
	}

	/** Constructs a HashedPasswordTuple pair from a line in the password file. */
	public PasswordFileManagerHashedTuple(String line) throws Exception {
		StringTokenizer st = new StringTokenizer(line, PasswordFileManagerHashedSalted.DELIMITER_STR);
		dHpwd = st.nextToken(); // hashed + salted password
		dSalt = Integer.parseInt(st.nextToken()); // salt
	}

	public String getHashedPassword() {
		return dHpwd;
	}

	public int getSalt() {
		return dSalt;
	}

	public String toString() {
		return (dHpwd + PasswordFileManagerHashedSalted.DELIMITER_STR + ("" + dSalt));
	}
}