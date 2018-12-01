package de.jcup.yamlconverter.popup.actions;

import java.io.File;
import java.io.IOException;

import de.jcup.yamlconverter.Activator;
import de.jcup.yamlconverter.YamlConverter;

public class ConvertYAMLToXMLAction extends AbstractConverterAction {

	protected File convert(File jsonFile) throws Exception {
		return converter.convertToXML(jsonFile);
	}

}
