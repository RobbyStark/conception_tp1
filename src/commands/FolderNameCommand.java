package commands;

import java.io.File;

public class FolderNameCommand implements ICommand {

	@Override
	public String run(File file) {
		return file.getName();
	}

	@Override
	public boolean getSupportFolder() {
		return true;
	}

	@Override
	public boolean getSupportFile() {
		return false;
	}
}