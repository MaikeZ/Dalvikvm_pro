package net.maike.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BytesByJar {

	private static String desPath = "D:\\test\\bytes\\";
	private static String filePath = "D:\\test\\out.jar";

	public static void main(String[] args) throws Exception {
		boolean flag = delAllFile(desPath);
		if (!flag) {
			getBytes(filePath);
		}
	}

	private static void getBytes(String filePath) throws Exception {
		FileInputStream fin = new FileInputStream(filePath);
		FileChannel fcin = fin.getChannel();
		int capacity = fin.available();
		if (8192 < capacity) {
			capacity = 8192;
		}
		ByteBuffer buffer = ByteBuffer.allocate(capacity);
		StringBuffer sb = new StringBuffer();
		int i = 1;
		while (true) {
			buffer.clear();
			int r = fcin.read(buffer);
			if (r == -1) {
				break;
			}
			buffer.flip();
			String stri = "" + i;
			if (i < 10) {
				stri = "0" + stri;
			}
			String head = "package net.maikeZ.bytes;" + "\n"
					+ "public class byte" + stri + " {" + "\n"
					+ "	public static byte[] bytes = { ";
			sb.append(head);

			byte[] bytes = buffer.array();
			for (byte b : bytes) {
				sb.append(b).append(",");
			}

			String outfile = desPath + "byte" + stri + ".java";
			File file = new File(outfile);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fout = new FileOutputStream(outfile);
			String str = sb.toString();
			str = str.substring(0, str.length() - 1);

			String end = "};" + "\n" + "}";

			str = str + end;

			fout.write(str.getBytes());
			sb = new StringBuffer();
			i = i + 1;
			fout.close();
		}
		System.out.println("OK");
		fin.close();
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
		}
		return flag;
	}

}
