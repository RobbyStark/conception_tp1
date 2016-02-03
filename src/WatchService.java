//

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

import mvc.FileManagerController;

public class WatchService implements Runnable {
	
	private FileManagerController fileManagerController_;
	
	public WatchService(FileManagerController fileManagerController) {
		fileManagerController_ = fileManagerController;
	}
	
	public void run() {
		try {
			java.nio.file.WatchService watcher = FileSystems.getDefault().newWatchService();
			
			String filePath = new File("").getAbsolutePath().concat("/src/commands/");		
			
			Path dir = Paths.get(filePath);
			dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

			System.out.println("Watch Service registered for dir: " + dir.getFileName());

			while (true) {
			WatchKey key;
			try {
			key = watcher.take();
			} catch (InterruptedException ex) {
			return;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
			WatchEvent.Kind<?> kind = event.kind();

			@SuppressWarnings("unchecked")
			WatchEvent<Path> ev = (WatchEvent<Path>) event;
			Path fileName = ev.context();

			System.out.println(kind.name() + ": " + fileName);
			System.out.println("TEST");
			fileManagerController_.updateCommands();
			
			}

			boolean valid = key.reset();
			if (!valid) {
			break;
			}
			}

			} catch (IOException ex) {
			System.err.println(ex);
			}
	}
}