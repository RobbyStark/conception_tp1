/*
 * Class that constitutes the application's entry point. Initializes the model, controller and view.
 */

import java.awt.EventQueue;

import mvc.*;

public class FileManager {

	/**
	 * Application's entry point.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileManagerView fileManagerView = new FileManagerView();
					FileManagerModel fileManagerModel = new FileManagerModel();
					FileManagerController fileManagerController = new FileManagerController(fileManagerView, fileManagerModel);
					
					// First load the commands.
					fileManagerController.updateCommands();
					
					fileManagerView.frameFileManager_.setVisible(true);
					
					// Start a new thread to watch for a change in the commands directory.
					(new Thread(new WatchService(fileManagerController))).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
