/**
 * Class that represents a leaf object FileLeaf.
 */

package composite;

import java.io.File;

public class FileLeaf extends Node {
	/**
	 * Class constructor.
	 * @param file The file to be represented by the FileLeaf object.
	 */
	public FileLeaf(File file) {
		file_ = file;
	}
}