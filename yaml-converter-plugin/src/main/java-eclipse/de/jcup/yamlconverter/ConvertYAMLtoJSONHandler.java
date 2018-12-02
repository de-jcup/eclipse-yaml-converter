package de.jcup.yamlconverter;

import java.io.File;
import java.io.IOException;

public class ConvertYAMLtoJSONHandler extends AbstractConverterHandler{

	@Override
	protected File convert(File yamlFile) throws IOException {
		return converter.convertToJson(yamlFile);
	}


}
