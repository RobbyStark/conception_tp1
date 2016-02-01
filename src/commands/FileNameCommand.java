package commands;

import java.io.File;

public class FileNameCommand implements ICommand {
	
	@Override
	public String run(File file) {
		return file.getName();
	}

	@Override
	public boolean getSupportFolder() {
		return false;
	}

	@Override
	public boolean getSupportFile() {
		return true;
	}
}