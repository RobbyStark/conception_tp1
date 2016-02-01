package commands;

import java.io.File;

public class AbsolutePathCommand implements ICommand {

	@Override
	public String run(File file) {
		return file.getAbsolutePath();
	}

	@Override
	public boolean getSupportFolder() {
		return true;
	}

	@Override
	public boolean getSupportFile() {
		return true;
	}
}