
package mvc;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

/**
 * The main View class. following MVC design, this class
 * displays the model
 */
public class FileManagerView extends JFrame {
	private static final long serialVersionUID = 1L;
	public JFrame frameFileManager_;
	public JScrollPane scrollPanelFileManager_;
	public JButton btnSelectRoot_;
	public JPanel panelCommands_;
	public JScrollPane scrollPanelCommands_;
	public JButton btnClear_;
	public JCheckBox chckbxAutoRun_;
	public JFileChooser fileChooser_;
	public JTree tree_;

	/**
	 * Initialize the contents of the frame.
	 */
	public FileManagerView() {
		frameFileManager_ = new JFrame();
		frameFileManager_.setTitle("LOG8430 - TP1 - File Manager");
		frameFileManager_.setBounds(100, 100, 550, 355);
		frameFileManager_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameFileManager_.getContentPane().setLayout(null);
		
		scrollPanelFileManager_ = new JScrollPane();
		scrollPanelFileManager_.setBounds(10, 11, 159, 239);
		frameFileManager_.getContentPane().add(scrollPanelFileManager_);
		
		btnSelectRoot_ = new JButton("Select a file or folder");
		btnSelectRoot_.setBounds(10, 261, 159, 40);
		frameFileManager_.getContentPane().add(btnSelectRoot_);
		
		panelCommands_ = new JPanel(new GridBagLayout());
		scrollPanelCommands_ = new JScrollPane(panelCommands_);
		scrollPanelCommands_.setBounds(179, 11, 345, 239);
		frameFileManager_.getContentPane().add(scrollPanelCommands_);
		
		btnClear_ = new JButton("Clear");
		btnClear_.setBounds(179, 261, 102, 40);
		frameFileManager_.getContentPane().add(btnClear_);
		
		chckbxAutoRun_ = new JCheckBox("AutoRun");
		chckbxAutoRun_.setBounds(447, 270, 77, 23);
		frameFileManager_.getContentPane().add(chckbxAutoRun_);
		
		fileChooser_ = new JFileChooser();
		fileChooser_.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}
	
	/**
	 * Method to register a new action listener for the select root button.
	 * @param actionListener the event listener for the select root button
	 */
	public void registerUpdateRootListener(ActionListener actionListener) {
		btnSelectRoot_.addActionListener(actionListener);
	}
	
	/**
	 * Method to register a new action listener for the clear button.
	 * @param actionListener the event listener for the clear button
	 */
	public void registerClearListener(ActionListener actionListener) {
		btnClear_.addActionListener(actionListener);
	}
	
	/**
	 * Method to register a new action listener for the autorun checkbox.
	 * @param actionListener the event listener for the autorun checkbox
	 */
	public void registerAutoRunListener(ActionListener actionListener) {
		chckbxAutoRun_.addActionListener(actionListener);
	}
}
