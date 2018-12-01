package de.jcup.yamlconverter;

import java.io.File;

public class TestFileAccess {

	public static final TestFileAccess INSTANCE = new TestFileAccess();

	private static File resourceFolder;

	private static File rootFolder;

	static {
		resourceFolder = new File("src/test/resources");
		if (!resourceFolder.exists()) {
			resourceFolder = new File("yaml-converter-plugin/src/test/resources");
		}
		if (!resourceFolder.exists()) {
			throw new IllegalStateException("Cannot load test file:" + resourceFolder);
		}
		rootFolder=resourceFolder.getParentFile().getParentFile();
	}

	public TestFileAccess() {

	}

	public File getTestFile(String path) {
		File file = new File(resourceFolder, path);
		if (file.exists()) {
			return file;
		}
		throw new IllegalStateException("Cannot load test file:" + file.getAbsolutePath());
	}

	public File newTempBuildFile(String fileName) {
		File file = new File(rootFolder, "build/temp");
		file.mkdirs();
		return new File(file, fileName);
	}
}
