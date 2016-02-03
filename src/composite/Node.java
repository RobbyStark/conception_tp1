/**
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
		
	/**
	 * Returns the File handle of the node.
	 * @param Nothing
	 * @return the File handle of the node.
	 */
	public File getFileHandle() {
		return file_;
	}
	
	/**
	 * Return the object within the tree component.
	 * @param Nothing
	 * @return the object within the tree component.
	 */
	public DefaultMutableTreeNode getTreeNode() {
		return treeNode_;
	}
	
	/**
	 * Set the object within the tree component.
	 * @param treeNode the tree represented in this Node object
	 * @return Nothing
	 */
	public void setTreeNode(DefaultMutableTreeNode treeNode) {
		treeNode_ = treeNode;
	}
	
	/**
	 * Adds a new directory to the node. This method as to be overloaded by the Directory child class
	 * @param node the new directory to be added to this Node object
	 */
	public Node addDirectory(File node) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Adds a new file (leaf) to the node. This method as to be overloaded by the Directory child class
	 * @param node the new file to be added to this Node object
	 */
	public Node addFileLeaf(File node) {
		throw new UnsupportedOperationException();
	}
}