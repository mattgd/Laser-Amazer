package edu.ncsu.feddgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class SaveData {
	
	private static Scanner input;
	private static Object[] values;
	private static Runnable[] runs = new Runnable[] {
			() -> {
				GameInstance.levelCompleteDialogue = (boolean)values[0];
			},
			() -> {
				GameInstance.showTimer = (boolean)values[1]; 	//Shut up Matt, don't judge me on my lambda use
			},
			() -> {
				GameInstance.samplingLevel = (int)values[2];			
			},
			() -> {
				GameInstance.latestLevel = (int)values[3];			
			}
	};
	
	// Read from the file, generate a new one with defaults if such a file doesn't exist
	static {
		try {
			input = new Scanner(new File("saveGame.fd"));
		} catch (FileNotFoundException e) {
			try {
				List<String> defaults = Arrays.asList("true", "true", "4", "2");
				Files.write(Paths.get("saveGame.fd"), defaults, Charset.forName("UTF-8"));
			} catch (IOException e1) {}
			try {
				input = new Scanner(new File("saveGame.fd"));
			} catch (FileNotFoundException e1) {}
		}
		values = new Object[] {
			true,
			true,
			0,
			0
		};
	}
	
	/**
	 * Reads data from "saveGame.fd" in the root directory and stores the read data to the correct locations
	 */
	static void readData() {
		int i = 0;
		while (input.hasNextLine()) {
			String s = input.nextLine();
			if (values[i] instanceof Boolean) {
				values[i] = Boolean.parseBoolean(s);
				runs[i].run();
			} else if (values[i] instanceof Integer) {
				values[i] = Integer.parseInt(s);
				runs[i].run();
			}
			
			i++;
		}
	}
	
	/**
	 * Writes all of the options to disk
	 */
	public static void writeData() {
		try {
			List<String> defaults = Arrays.asList(
					Boolean.toString(GameInstance.levelCompleteDialogue), 
					Boolean.toString(GameInstance.showTimer),
					Integer.toString(GameInstance.samplingLevel),
					Integer.toString(GameInstance.latestLevel));
			Files.write(Paths.get("saveGame.fd"), defaults, Charset.forName("UTF-8"));
		} catch (IOException e1) {}
	}
}
