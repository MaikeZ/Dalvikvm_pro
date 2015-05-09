package net.maikeZ.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logs {
	private static String LOGPATH = "/mnt/sdcard/";
	private static FileOutputStream fos = null;

	public static void start() {
		startGetFos();
	}

	public static void startGetFos() {
		if (fos != null) {
			return;
		}
		File dir = new File(LOGPATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File logfile = new File(LOGPATH + File.separator + "Dalvikvm.txt");
		if (!logfile.exists()) {
			try {
				logfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fos = new FileOutputStream(logfile, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fos = null;
		}
		return;
	}

	public static void out(String msg) {
		if (fos != null) {
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String buff = dateFormat.format(now) + "---" + msg + "\r\n";
			System.out.println(buff);
			try {
				fos.write(buff.getBytes());
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
