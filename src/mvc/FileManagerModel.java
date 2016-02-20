
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
/**
 * The main Model class. following MVC design, this class
 * holds data to be accessed by the controller and displayed by the view
 */
public class FileManagerModel {
	
	private Map<String, Node> nodeMap_ = new HashMap<String, Node>();
	private Map<String, Component> buttonComponentMap_ = new HashMap<String, Component>();
	private Map<String, Component> textFieldComponentMap_ = new HashMap<String, Component>();
	
	private Node nodeSelected_;
	private boolean autoRun_ = false;

	/**
	 * Sets the selected node
	 * @param node the selected node
	 */
	public void setNodeSelected(Node node) {
		nodeSelected_ = node;
	}

	/**
	 * Sets the autorun function's state
	 * @param autoRun sets the autorun function's state
	 */
	public void setAutoRun(boolean autoRun) {
		autoRun_ = autoRun;
	}
	
	/**
	 * Method called when one tree node elements is clicked. Updates the buttons availability.
	 * @param name The name of the clicked node
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

			
			// Enable the button dependently if the command supports the file type.
			JButton button = (JButton) pair.getValue();
			if (((fileHandle.isDirectory()) && (supportFolder)) || ((fileHandle.isFile()) && (supportFile))) {
				button.setEnabled(true);
				
				// If autorun is enabled, execute the command.
				if (autoRun_) {
					String commandResult = runCommand(commandName);
					getTextFieldComponent(commandName).setText(commandResult);
				}
			} else {
				button.setEnabled(false);
			}
		}
	}
	
	/**
	 * Update the internal file system by recursively walking through directories
	 * and sub-directories. Uses a composite design pattern.
	 * @param node the root node of the tree on which to perform the update
	 */
	public void updateInternalFileSystem(Node node) {
		File fileHandle = node.getFileHandle();
		if (fileHandle != null) {
			if (fileHandle.isDirectory()) {
				for (File subFile : fileHandle.listFiles()) {
					if (subFile != null) {
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
		}
	}
	/**
	 * runs the command
	 * @param commandName the name of the command
	 * @return the command's result
	 */	
	public String runCommand(String commandName) {
		// Use the class loader to run the command.
		JavaClassLoader javaClassLoader = new JavaClassLoader();
		String commandResult = javaClassLoader.invokeRunMethod("commands." + commandName, nodeSelected_.getFileHandle());
		
		// Update the result of the command.
		return commandResult;
	}
	
	/**
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
	/**
	 * Method to add a component to the button map
	 * @param name the component's name
	 * @param component the component to add
	 */	
	public void addComponentToButtonMap(String name, Component component) {
		buttonComponentMap_.put(name, component);
	}
	/**
	 * Method to add a component to the text field's map
	 * @param name the component's name
	 * @param component the component to add
	 */	
	public void addComponentToTextFieldMap(String name, Component component) {
		textFieldComponentMap_.put(name, component);
	}
	/**
	 * Method to add a node the nodes map
	 * @param name the node's name
	 * @param node the node to add
	 */	
	public void addNodetoNodeMap(String name, Node node) {
		nodeMap_.put(name,  node);
	}
	/**
	 * Method to clear the button map.
	 */	
	public void clearButtonMap() {
		buttonComponentMap_.clear();
	}
	/**
	 * Method to clear the text field map.
	 */	
	public void clearTextFieldMap() {
		textFieldComponentMap_.clear();
	}
	/**
	 * Method to get the file handle.
	 * @param name the name of the file
	 * @return the file handle
	 */	
	public File getFileHandle(String name) {
		return nodeMap_.get(name).getFileHandle();
	}
	/**
	 * Method to get the textfield component
	 * @param componentName the name of the component on which to get the text field
	 * @return the textfield component
	 */	
	JTextField getTextFieldComponent(String componentName) {
		return (JTextField) textFieldComponentMap_.get(componentName);
	}
}