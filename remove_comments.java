/*	Program to remove single and multiline comments from a source code file. The program takes two arguments-
	the source file and the ouput file. 
	Author: Aseem Raina
	Date  : 16th January, 2016	
*/

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class remove_comments {
	public static void main(String args[]) throws IOException {
		remove_comments rm = new remove_comments();
		String res = "";
		if(args.length == 0) {
			System.out.println("Usage: java filename input_file output_file");
			System.exit(0);
		}
		FileReader in = null;
		FileReader out = null;
		String source = "";
		File file = new File(args[1]);
		if (!file.createNewFile()) {
			System.out.println("File already exists.");
			System.exit(0);
		}
		in = new FileReader(args[0]);
		out = new FileReader(args[1]);
		int c;
		while ((c = in.read()) != -1)					// store file contents in a string
			source += (char)c;
		if (in != null) 
			in.close();
		//System.out.println(rm.remove(source));
		res += rm.remove(source);						// final result
		Files.write(Paths.get(args[1]), res.getBytes());	// write result to a new file
	}

	public String remove(String s) {
		int len = s.length();
		String res = "";
		boolean flag1 = false;							// for single line comments
		boolean flag2 = false;							// for multiline comments
		for(int i = 0;i < len;i++) {					// iterate through string
			if(flag1 == true && s.charAt(i) == '\n')	// locate end of single line comment, set flag1 to false
				flag1 = false;
			else if(flag2 == true && s.charAt(i) == '*' && s.charAt(i+1) == '/') {		// locate end of multi line comment
				flag2 = false;
				i++;
			}
			else if (flag1 || flag2)		// ignore characters included in comments
            	continue;
            else if (s.charAt(i) == '/' && s.charAt(i+1) == '/') {		// locate start of a single line comment
            	flag1 = true;
            	i++;
            }
        	else if (s.charAt(i) == '/' && s.charAt(i+1) == '*') {		// locate start of a single line comment
            	flag2 = true;
            	i++;
            }
            else
            	res += s.charAt(i);						// append valid (not commented) characters to a string
		}
		return res;
	}
}