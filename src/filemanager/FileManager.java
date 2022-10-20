package filemanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {
	
	/*
	 * Validate file and Print content
	 */
	
	public static void printFileContent(String fileLocation) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))){
		    String line = br.readLine();
		    while (line != null) {
		    	System.out.println(line);
		        line = br.readLine();
		    }
		} catch (IOException e) {
			System.err.println("[!] Error fichero ("+ fileLocation +") no encontrado");
		}
	}
	
}
