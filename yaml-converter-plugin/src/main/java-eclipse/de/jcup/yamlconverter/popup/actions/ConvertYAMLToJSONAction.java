package de.jcup.yamlconverter.popup.actions;

import java.io.File;

public class ConvertYAMLToJSONAction extends AbstractConverterAction {

	protected File convert(File jsonFile) throws Exception{
		return converter.convertToJson(jsonFile);
	}

}
