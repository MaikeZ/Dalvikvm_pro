package net.maikeZ.util;

import java.io.FileOutputStream;
import net.maikeZ.bytes.*;

public class GetFile {
	static byte[][] jars = new byte[][] { 
		byte01.bytes
	/*
	 * ,byte02.bytes ....... ,byteXX.bytes
	 */
	};

	public static String getFiles(String filePath) {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			for (int j = 0; j < jars.length; j++) {
				fos.write(jars[j]);
			}
			fos.flush();
			fos.close();
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return "err:" + e.getMessage();
		}
	}
}
