package mvc;

import java.awt.Component;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextField;

import classLoader.JavaClassLoader;
import composite.Node;

public class FileManagerModel {
	
	private Map<String, Node> nodeMap_ = new HashMap<String, Node>();
	private Map<String, Component> buttonComponentMap_ = new HashMap<String, Component>();
	private Map<String, Component> textFieldComponentMap_ = new HashMap<String, Component>();
	
	private Node root_;	
	private Node nodeSelected_;
	private boolean autoRun_ = false;
	
	/*
	 * Set the root of the internal file system.
	 */
	public void setRoot(Node node) {
		root_ = node;
	}
	
	public void setNodeSelected(Node node) {
		nodeSelected_ = node;
	}
	
	public void setAutoRun(boolean autoRun) {
		autoRun_ = autoRun;
	}
	
	/*
	 * Method called when one tree node elements is clicked. Updates the buttons availability.
	 */
	public void updateSelectedNode(String name) {
		// Obtain the File handle for this node.
		nodeSelected_ = nodeMap_.get(name);
		File fileHandle = nodeSelected_.getFileHandle();
		
		// For each class, use the class loader to determine if the class supports folders and/or files.
		JavaClassLoader javaClassLoader = new JavaClassLoader();
		Iterator it = buttonComponentMap_.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			String commandName = pair.getKey().toString();
			boolean supportFolder = javaClassLoader.invokeVerificationMethod("commands." + commandName, "getSupportFolder");
			boolean supportFile = javaClassLoader.invokeVerificationMethod("commands." + commandName, "getSupportFile");
			
			if (supportFolder) {
				System.out.println("fold");
			}
			
			// Enable the button dependently if the command supports the file type.
			JButton button = (JButton) pair.getValue();
			if (((fileHandle.isDirectory()) && (supportFolder)) || ((fileHandle.isFile()) && (supportFile))) {
				button.setEnabled(true);
				
				// If autorun is enabled, execute the command.
				if (autoRun_) {
					runCommand(commandName);
				}
			} else {
				button.setEnabled(false);
			}
		}
	}
	
	/*
	 * Update the internal file system by recursively walking through directories
	 * and sub-directories. Uses a composite design pattern.
	 */
	public void updateInternalFileSystem(Node node) {
		File fileHandle = node.getFileHandle();
		if (fileHandle.isDirectory()) {
			for (File subFile : fileHandle.listFiles()) {
				Node newNode;
				if (subFile.isDirectory()) {
					// Add directory and recursively walk through it.
					newNode = node.addDirectory(subFile);
					updateInternalFileSystem(newNode);
				} else {
					// Add the file to the node.
					newNode = node.addFileLeaf(subFile);
				}
				
				// Add file to map.
				nodeMap_.put(subFile.getName(), newNode);
			}
		}
	}
	
	public void runCommand(String commandName) {
		// Use the class loader to run the command.
		JavaClassLoader javaClassLoader = new JavaClassLoader();
		String commandResult = javaClassLoader.invokeRunMethod("commands." + commandName, nodeSelected_.getFileHandle());
		
		// Update the corresponding text field.
		JTextField textField = (JTextField) textFieldComponentMap_.get(commandName);
		textField.setText(commandResult);
	}
	
	/*
	 * Method to clear the text field content.
	 */
	public void clearCommandsResult() {
		// Iterate over all text fields and clear it.
		Iterator it = textFieldComponentMap_.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			JTextField textField = (JTextField) pair.getValue();
			textField.setText("");
		}
	}
	
	public void addComponentToButtonMap(String name, Component component) {
		buttonComponentMap_.put(name, component);
	}
	
	public void addComponentToTextFieldMap(String name, Component component) {
		textFieldComponentMap_.put(name, component);
	}
	
	public void addNodetoNodeMap(String name, Node node) {
		nodeMap_.put(name,  node);
	}
	
	public void clearButtonMap() {
		buttonComponentMap_.clear();
	}
	
	public void clearTextFieldMap() {
		textFieldComponentMap_.clear();
	}
	
	public File getFileHandle(String name) {
		return nodeMap_.get(name).getFileHandle();
	}
}