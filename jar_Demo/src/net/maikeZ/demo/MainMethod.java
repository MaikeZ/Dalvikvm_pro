package net.maikeZ.demo;

public class MainMethod {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logs.start();
		Logs.out("Hello Dalvikvm!");
		Logs.out("sleep 20s");
		try {
			Thread.sleep(20*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (args.length > 0) {
			for (String str : args) {
				Logs.out(str);
			}
		}
	}
}
