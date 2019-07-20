package com.falifa.draftbuddy.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.falifa.draftbuddy.ui.controller.BaseController;

public class Log {

	public static void err(String message) {
		try {
			System.out.println(buildLog("[ERROR]\t") + message);
		} catch (Exception e) {
			System.out.println("[ERROR]\t" + message);
		}
	}

	public static void info(String message) {
		try {
			System.out.println(buildLog("[INFO]\t") + message);
		} catch (Exception e) {
			System.out.println("[INFO]\t" + message);
		}
	}
	
	public static void deb(String message) {
		try {
			System.out.println(buildLog("[DEBUG]\t") + message);
		} catch (Exception e) {
			System.out.println("[DEBUG]\t" + message);
		}
	}
	
	private static String buildLog(String level) {
		return level + String.format("%-10s", "[" + BaseController.roundNum + "." + BaseController.pickNumber + "]") + String.format("%-8s :: ", BaseController.currentDrafter.getName());
	}

	public static void logDraftResults(String draftBoard) throws IOException {
		File file = getProperFile("draftLog.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
		out.write(draftBoard);
		out.close();
	}
	
	public static File getProperFile(String fileName) {
		String path = System.getProperty("user.dir");
		File loc = new File(path + "\\src\\main\\" + fileName);
		File locLinux = new File(path + "/" + fileName);
		File locWindows = new File(path + "\\" + fileName);
		return loc.exists() ? loc : locLinux.exists() ? locLinux : locWindows.exists() ? locWindows : new File(fileName);
	}
}
