package net.maikeZ.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ShellUtils {

	/**
	 * execute shell command, default return result msg
	 * 
	 * @param command
	 *            command
	 * @param isRoot
	 *            whether need to run with root
	 * @return
	 * @see ShellUtils#execCommand(String[], boolean, boolean)
	 */
	public static ShellCommandResult execCommand(String command) {
		return execCommand(new String[] { command }, true);
	}

	/**
	 * execute shell commands, default return result msg
	 * 
	 * @param commands
	 *            command list
	 * @param isRoot
	 *            whether need to run with root
	 * @return
	 * @see ShellUtils#execCommand(String[], boolean, boolean)
	 */
	public static ShellCommandResult execCommand(List<String> commands) {
		return execCommand(
				commands == null ? null : commands.toArray(new String[] {}),
				true);
	}

	/**
	 * execute shell commands, default return result msg
	 * 
	 * @param commands
	 *            command array
	 * @param isRoot
	 *            whether need to run with root
	 * @return
	 * @see ShellUtils#execCommand(String[], boolean, boolean)
	 */
	public static ShellCommandResult execCommand(String[] commands) {
		return execCommand(commands, true);
	}

	/**
	 * execute shell command
	 * 
	 * @param command
	 *            command
	 * @param isRoot
	 *            whether need to run with root
	 * @param isNeedResultMsg
	 *            whether need result msg
	 * @return
	 * @see ShellUtils#execCommand(String[], boolean, boolean)
	 */
	public static ShellCommandResult execCommand(String command,
			boolean isNeedResultMsg) {
		return execCommand(new String[] { command }, isNeedResultMsg);
	}

	/**
	 * execute shell commands
	 * 
	 * @param commands
	 *            command list
	 * @param isRoot
	 *            whether need to run with root
	 * @param isNeedResultMsg
	 *            whether need result msg
	 * @return
	 * @see ShellUtils#execCommand(String[], boolean, boolean)
	 */
	public static ShellCommandResult execCommand(List<String> commands,
			boolean isNeedResultMsg) {
		return execCommand(
				commands == null ? null : commands.toArray(new String[] {}),
				isNeedResultMsg);
	}

	/**
	 * execute shell commands
	 * 
	 * @param commands
	 *            command array
	 * @param isRoot
	 *            whether need to run with root
	 * @param isNeedResultMsg
	 *            whether need result msg
	 * @return <ul>
	 *         <li>if isNeedResultMsg is false, {@link ShellCommandResult#successMsg}
	 *         is null and {@link ShellCommandResult#errorMsg} is null.</li>
	 *         <li>if {@link ShellCommandResult#result} is -1, there maybe some
	 *         excepiton.</li>
	 *         </ul>
	 */
	public static ShellCommandResult execCommand(String[] commands,
			boolean isNeedResultMsg) {
		int result = -1;
		if (commands == null || commands.length == 0) {
			return new ShellCommandResult(result, null, null);
		}

		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;
		StringBuilder successMsg = null;
		StringBuilder errorMsg = null;

		DataOutputStream os = null;
		try {
			String sh = "/system/bin/sh";
			process = Runtime.getRuntime().exec(sh);
			os = new DataOutputStream(process.getOutputStream());
			for (String command : commands) {
				if (command == null) {
					continue;
				}
				// donnot use os.writeBytes(commmand), avoid chinese charset
				// error
				os.write(command.getBytes());
				os.writeBytes("\n");
				os.flush();
			}
			os.writeBytes("exit\n");
			os.flush();

			result = process.waitFor();
			// get command result
			if (isNeedResultMsg) {
				successMsg = new StringBuilder();
				errorMsg = new StringBuilder();
				successResult = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				errorResult = new BufferedReader(new InputStreamReader(
						process.getErrorStream()));
				String s;
				while ((s = successResult.readLine()) != null) {
					successMsg.append(s);
					successMsg.append("\n");
				}
				while ((s = errorResult.readLine()) != null) {
					errorMsg.append(s);
					errorMsg.append("\n");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			if (successResult != null) {
				try {
					successResult.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			if (errorResult != null) {
				try {
					errorResult.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}

			if (process != null) {
				process.destroy();
			}
		}
		return new ShellCommandResult(result, successMsg == null ? null
				: successMsg.toString(), errorMsg == null ? null
				: errorMsg.toString());
	}

	/**
	 * result of command
	 * <ul>
	 * <li>{@link ShellCommandResult#result} means result of command, 0 means normal,
	 * else means error, same to excute in linux shell</li>
	 * <li>{@link ShellCommandResult#successMsg} means success message of command
	 * result</li>
	 * <li>{@link ShellCommandResult#errorMsg} means error message of command result</li>
	 * </ul>
	 * 
	 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a>
	 *         2013-5-16
	 */
	public static class ShellCommandResult {

		/** result of command **/
		public int result;
		/** success message of command result **/
		public String successMsg;
		/** error message of command result **/
		public String errorMsg;

		public ShellCommandResult(int result) {
			this.result = result;
		}

		public ShellCommandResult(int result, String successMsg, String errorMsg) {
			this.result = result;
			this.successMsg = successMsg;
			this.errorMsg = errorMsg;
		}
	}
}
