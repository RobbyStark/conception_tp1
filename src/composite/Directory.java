/*
 * Class that represents a composite Directory object, containing Directory and
 * FileLead objects.
 */

package composite;

import java.io.File;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class Directory extends Node {
	// Array that contains directories and files contained by this directory.
	private ArrayList<Node> nodes_ = new ArrayList<Node>();
	
	/*
	 * Class constructor.
	 */
	public Directory(File file) {
		file_ = file;
	}
	
	/*
	 * Add a new directory to the node.
	 */
	public Node addDirectory(File file) {
		Node node = new Directory(file);
		this.nodes_.add(node);
		// Create the new DefaultMutableTreeNode object.
		DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file.getName());
		this.getTreeNode().add(treeNode);
		node.setTreeNode(treeNode);
		return node;
	}
	
	/*
	 * Add a new file (leaf) to the node.
	 */
	public Node addFileLeaf(File file) {
		Node node = new FileLeaf(file);
		this.nodes_.add(node);
		// Create the new DefaultMutableTreeNode object.
		DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file.getName());
		this.getTreeNode().add(treeNode);
		node.setTreeNode(treeNode);
		return node;
	}
}