package de.jcup.yamlconverter;

import java.io.File;
import java.io.IOException;

public class ConvertJSONToYAMLHandler extends AbstractConverterHandler {

	@Override
	protected File convert(File jsonFile) throws IOException {
		return converter.convertFromJson(jsonFile);
	}

}
