package net.maikeZ.util;

import java.io.File;
import java.io.IOException;

import net.maikeZ.util.ShellUtils.ShellCommandResult;

import android.content.Context;

public class MainThread extends Thread {
	private static Context context;

	public MainThread(Context context) {
		MainThread.context = context;
	}

	@Override
	public void run() {
		super.run();
		String basicPath = context.getCacheDir().getAbsolutePath();
		System.out.println("cache:"+basicPath);
		String cmd0 = "chmod 755 " + basicPath;
		ShellUtils.execCommand(cmd0);
		String jarPath = basicPath + "QAQ.jar";

		File dalFolder = new File(basicPath + "/dalvik-cache/");// 创建dalvikvm目录
		if (!dalFolder.exists()) {
			dalFolder.mkdir();
		}

		File jarFile = new File(jarPath);
		if (jarFile.exists()) {
			jarFile.delete();
		}
		try {
			jarFile.createNewFile();
			String cmd1 = "chmod 777 " + jarFile.getAbsolutePath();
			ShellUtils.execCommand(cmd1);
			GetFile.getFiles(jarPath);// bytes转jar
			String cmd2 = "chmod 777 " + jarPath;
			ShellUtils.execCommand(cmd2);
			String[] cmds = {
					"echo $ANDROID_DATA",
					"export ANDROID_DATA=" + basicPath,
					"echo $ANDROID_DATA",
					"dalvikvm -cp " + jarPath + " net.maikeZ.demo.MainMethod "
							+ "test " + "1" };
			
			ShellCommandResult result = ShellUtils.execCommand(cmds);
			System.out.println("result: "+result.result);
			System.out.println("succ: "+result.successMsg);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			deleteFile(jarPath);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}
}
