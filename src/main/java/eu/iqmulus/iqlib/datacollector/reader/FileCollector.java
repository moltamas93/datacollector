package eu.iqmulus.iqlib.datacollector.reader;

import java.io.File;
import java.util.ArrayList;
/**
 * The FileCollector class collect all files with certain extensions in a directory including subdirectories. 
 *
 */
public class FileCollector {
	/**
	 * A list of file objects.
	 */
	ArrayList<File> files = new ArrayList<File>();
	/**
	 * An array of extensions of files.
	 */
	String[] extensions;
	
	/**
	 * @param file is a valid directory or a valid file
	 * @param extensions 
	 */
	public FileCollector(File file, String[] extensions) {
		this.extensions = extensions; 
		files = collectAllFilesFromDir(file);
	}
	
	/**
	 * collectAllFilesFromDir method returns a list of files in the specified directory.
	 * Recursively walk a directory tree and return a List of all.
	 * @param file is a valid directory or a valid file, which can be read.
	 * @return ArrayList<File>
	 */
	public ArrayList<File> collectAllFilesFromDir(File file) {
		ArrayList<File> fileList = new ArrayList<File>();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				fileList.addAll(collectAllFilesFromDir(f));
			}
		} else {
			if (isValidType(file.getName())) {
				fileList.add(file);	
			}
		}
		return fileList;
	}
	
	/**
	 * isValidType method match extension of filename.
	 * If "extensions" is null, all files are returned.
	 * @param name File name
	 * @return boolean
	 */
	public boolean isValidType(String name) {
		for( String str : this.extensions ) {
			if (name.toLowerCase().endsWith(str)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<File> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}

	public String[] getExtensions() {
		return extensions;
	}

	public void setExtensions(String[] extensions) {
		this.extensions = extensions;
	}
	
}
