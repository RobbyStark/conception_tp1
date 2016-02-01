package mvc;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import classLoader.JavaClassLoader;
import composite.Directory;
import composite.FileLeaf;
import composite.Node;

public class FileManagerController {
	
	private FileManagerView fileManagerView_;
	private FileManagerModel fileManagerModel_;
	
	/*
	 * Class constructor.
	 */
	public FileManagerController(FileManagerView fileManagerView, FileManagerModel fileManagerModel) {
		this.fileManagerView_ = fileManagerView;
		this.fileManagerModel_ = fileManagerModel;
		
		// Tells the view that we add the controller as an action listener for some components.
		this.fileManagerView_.registerUpdateRootListener(new UpdateRootListener());
		this.fileManagerView_.registerClearListener(new ClearListener());
		this.fileManagerView_.registerAutoRunListener(new AutoRunListener());
	}
	
	/*
	 * Scans the commands directory for classes and load the commands.
	 */
	public void updateCommands() {
		try {
			// Create a new JavaClassLoader 
			ClassLoader classLoader = this.getClass().getClassLoader();
			
			int gridy = 0;
			
			// Empty the commands panel and model data.
			fileManagerView_.panelCommands_.removeAll();
			fileManagerModel_.clearButtonMap();
			fileManagerModel_.clearTextFieldMap();
			
			System.out.println("dsf");
			
			String filePath = new File("").getAbsolutePath().concat("/src/commands/");						
			File commandsDir = new File(filePath);
			
			File[] commandsFiles = commandsDir.listFiles();
			for (File file : commandsFiles) {
				if ((file.isFile()) && (!file.getName().contains("ICommand.java")) && (file.getName().endsWith(".java"))) {
					// Add command.
					//fileManagerModel_.addCommand(new ICommand);
					
					// Add command button.
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.anchor = GridBagConstraints.FIRST_LINE_START;
					gbc.gridy = gridy;
					gbc.gridx = 0;
					gbc.insets = new Insets(2,2,2,2);
					
					String name = file.getName().replace(".java", "");
					JButton button = new JButton(name);
					button.setName(name);
					button.setEnabled(false);
					button.addActionListener(new CommandButtonListener());
					fileManagerView_.panelCommands_.add(button, gbc);
					
					// Add the text field.
					JTextField textField = new JTextField();
					textField.setPreferredSize(new Dimension(150, 27));
					gbc.gridx = 1;
					fileManagerView_.panelCommands_.add(textField, gbc);
					
					//Add the button and text field to the maps.
					fileManagerModel_.addComponentToButtonMap(name, button);
					fileManagerModel_.addComponentToTextFieldMap(name, textField);
					
					gridy++;
				}
			}
			
			// Add filler to the GridBagLayout so that elements are pushed top left.
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridy = gridy;
			gbc.gridx = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			JPanel filler = new JPanel();
			fileManagerView_.panelCommands_.add(filler, gbc);
			
			fileManagerView_.panelCommands_.repaint();
			fileManagerView_.panelCommands_.revalidate();
		}
		catch (NullPointerException e) {
			// The commands directory is invalid.
			System.out.println("The specified commands directory is invalid.");
		}
	}
	
	/*
	 * Action listener for the update root button.
	 */
	class UpdateRootListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Open the file chooser.
			if (fileManagerView_.fileChooser_.showOpenDialog(fileManagerView_.frameFileManager_) == JFileChooser.APPROVE_OPTION) {
				// Initialize the root Node object.
				Node root;
				File file = fileManagerView_.fileChooser_.getSelectedFile();
				DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file.getName());
				if (file.isDirectory()) {
					root = new Directory(file);
				} else {
					root = new FileLeaf(file);
				}
				fileManagerModel_.setRoot(root);
				root.setTreeNode(treeNode);
				fileManagerModel_.updateInternalFileSystem(root);
				fileManagerModel_.addNodetoNodeMap(file.getName(), root);
				
				// Create the tree component.
				fileManagerView_.tree_ = new JTree(root.getTreeNode());
				fileManagerView_.scrollPanelFileManager_.setViewportView(fileManagerView_.tree_);
				fileManagerView_.tree_.repaint();
				fileManagerView_.tree_.revalidate();
				
				fileManagerView_.tree_.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
				fileManagerView_.tree_.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) fileManagerView_.tree_.getLastSelectedPathComponent();
						
						// If nothing is selected.
						if (node == null) {
							fileManagerModel_.setNodeSelected(null);
						} else {
							Object nodeInfo = node.getUserObject();
							fileManagerModel_.updateSelectedNode(nodeInfo.toString());
						}
					}
				});
			}
		}
	}
	
	/*
	 * Action listener for the clear button.
	 */
	class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			fileManagerModel_.clearCommandsResult();
		}
	}
	
	/*
	 * Action listener for the autorun checkbox.
	 */
	class AutoRunListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Sets the model attribute.
			fileManagerModel_.setAutoRun(((JCheckBox) e.getSource()).isSelected());
		}
	}
	
	/*
	 * Action listener for the command buttons.
	 */
	class CommandButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			fileManagerModel_.runCommand(((JButton) e.getSource()).getName());
		}
	}
}