package com.hsun324.simplebukkit.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

public final class FileUtils
{
	
	/**
	 * Opens a safe and valid file writer for the specified file.
	 * @param file the file to open a file writer for
	 * @return the file writer is successful. null if not.
	 */
	public static Writer openFileWriter(File file)
	{
		Writer result = null;
		try
		{
			if(!file.exists() || !file.isFile())
				if(file.getParentFile().mkdirs())
					file.createNewFile();
			if(file.exists() && file.isFile())
				result = new FileWriter(file);
		}
		catch (Exception e) { }
		return result;
	}

	/**
	 * Opens a safe and valid file reader for the specified file.
	 * @param file the file to open a file reader for
	 * @return the file reader is successful. null if not.
	 */
	public static Reader openFileReader(File file)
	{
		Reader result = null;
		try
		{
			if(!file.exists() || !file.isFile())
				if(file.getParentFile().mkdirs())
					file.createNewFile();
			if(file.exists() && file.isFile())
				result = new FileReader(file);
		}
		catch (Exception e) { }
		return result;
	}

}
