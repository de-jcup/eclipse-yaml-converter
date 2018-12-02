package de.jcup.yamlconverter;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;

public class YamlConverterPropertyTester extends PropertyTester {

	public static final String PROPERTY_NAMESPACE = "de.jcup.yamlconverter";
	public static final String PROPERTY_IS_JSON = "isJSonFile";
	public static final String PROPERTY_IS_YAML = "isYamlFile";
	private static final List<String> JSON_NAMES = Arrays.asList("json");
	private static final List<String> YAML_NAMES = Arrays.asList("yaml", "yml");

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (!(receiver instanceof IFile)) {
			/* not supported */
			return false;
		}
		IFile file = (IFile) receiver;
		List<String> fileEndings = null;
		if (PROPERTY_IS_JSON.equals(property)) {
			fileEndings = JSON_NAMES;
		} else if (PROPERTY_IS_YAML.equals(property)) {
			fileEndings = YAML_NAMES;
		}
		if (fileEndings != null) {
			if (Boolean.TRUE.equals(expectedValue)) {
				String extension = file.getFileExtension();
				if (fileEndings.contains(extension)) {
					return true;
				}
			}
		}
		return false;
	}
}