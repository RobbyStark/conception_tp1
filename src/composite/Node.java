/*
 * Class that acts as an interface for the Directory and FileLead classes, represents
 * the tree structure of the tree component.
 */

package composite;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class Node {
	// File handle of the node.
	File file_;
	
	// Object within the tree component.
	DefaultMutableTreeNode treeNode_;
		
	/*
	 * Return the File handle of the node.
	 */
	public File getFileHandle() {
		return file_;
	}
	
	/*
	 * Return the object within the tree component.
	 */
	public DefaultMutableTreeNode getTreeNode() {
		return treeNode_;
	}
	
	/*
	 * Set the object within the tree component.
	 */
	public void setTreeNode(DefaultMutableTreeNode treeNode) {
		treeNode_ = treeNode;
	}
	
	/*
	 * Add a new directory to the node.
	 */
	public Node addDirectory(File node) {
		throw new UnsupportedOperationException();
	}
	
	/*
	 * Add a new file (leaf) to the node.
	 */
	public Node addFileLeaf(File node) {
		throw new UnsupportedOperationException();
	}
}