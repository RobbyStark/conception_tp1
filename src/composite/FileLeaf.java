/*
 * Class that represent a leaf object FileLeaf.
 */

package composite;

import java.io.File;

public class FileLeaf extends Node {
	/*
	 * Class constructor.
	 */
	public FileLeaf(File file) {
		file_ = file;
	}
}