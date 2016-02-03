import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import composite.*;
import mvc.FileManagerModel;
import commands.*;

public class FileManagerUnitTests {

	@Test
	public void file_path() {
		
		String name = "test.txt";
		File file = new File (name);
		String path = file.getAbsolutePath();
		
		FileManagerModel model = new FileManagerModel();
		Node node = new FileLeaf(file);
		model.setNodeSelected(node);
		
		String result = model.runCommand("AbsolutePathCommand");
		
		assertTrue(path.equals(result));	
				
	}
	
	@Test
	public void file_fileName() {
		
		String name = "test.txt";
		File file = new File (name);
		
		FileManagerModel model = new FileManagerModel();
		Node node = new FileLeaf(file);
		model.setNodeSelected(node);
		ICommand command = new FileNameCommand();
		boolean isSupport = command.getSupportFile();
		
		String result = model.runCommand("FileNameCommand");
		
		assertTrue(name.equals(result) && isSupport);	
				
	}
	
	@Test
	public void file_folderName() {
		
		String name = "test.txt";
		File file = new File (name);
		
		FileManagerModel model = new FileManagerModel();
		Node node = new FileLeaf(file);
		model.setNodeSelected(node);
		ICommand command = new FolderNameCommand();
		boolean isSupport = command.getSupportFile();
		
		String result = model.runCommand("FolderNameCommand");
		
		assertTrue(name.equals(result) && !isSupport);	
				
	}

	@Test
	public void folder_path() {
		
		String name = "test";
		File file = new File (name);
		String path = file.getAbsolutePath();
		
		FileManagerModel model = new FileManagerModel();
		Node node = new Directory(file);
		model.setNodeSelected(node);
		
		String result = model.runCommand("AbsolutePathCommand");
		
		assertTrue(path.equals(result));	
				
	}
	
	@Test
	public void folder_fileName() {
		
		String name = "test.txt";
		File file = new File (name);
		
		FileManagerModel model = new FileManagerModel();
		Node node = new Directory(file);
		model.setNodeSelected(node);
		ICommand command = new FileNameCommand();
		boolean isSupport = command.getSupportFolder();
		
		String result = model.runCommand("FileNameCommand");
		
		assertTrue(name.equals(result) && !isSupport);	
				
	}
	
	@Test
	public void folder_folderName() {
		
		String name = "test.txt";
		File file = new File (name);
		
		FileManagerModel model = new FileManagerModel();
		Node node = new Directory(file);
		model.setNodeSelected(node);
		ICommand command = new FolderNameCommand();
		boolean isSupport = command.getSupportFolder();
		
		String result = model.runCommand("FolderNameCommand");
		
		assertTrue(name.equals(result) && isSupport);	
				
	}
	
}
