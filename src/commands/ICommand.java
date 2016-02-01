/*
 * Interface of the commands.
 */

package commands;

import java.io.File;

public abstract interface ICommand {
	
	/*
	 * Method to run the command
	 */
	public String run(File file);
	
	/*
	 * Method that returns whether the command is usable with folders.
	 */
	public boolean getSupportFolder();
	
	/*
	 * Method that returns whether the command is usable with files.
	 */
	public boolean getSupportFile();
}