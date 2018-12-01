package de.jcup.yamlconverter.popup.actions;

import java.io.File;

public class ConvertJSONtoYAMLAction extends AbstractConverterAction  {

	protected File convert(File jsonFile) throws Exception {
		return converter.convertFromJson(jsonFile);
	}

}
