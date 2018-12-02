package de.jcup.yamlconverter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class YamlConverter {

	private Yaml yamlParser;
	private ObjectMapper jsonMapper;

	public YamlConverter() {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		this.yamlParser = new Yaml(options);
		jsonMapper = new ObjectMapper();
		jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public File createNewFile(String ending, File origin) {
		File outputFile = new File(origin.getParentFile(), origin.getName() + ending);
		return outputFile;

	}

	public File convertFromJson(File jsonFile) throws IOException {
		return convertFromJson(jsonFile, createNewFile(".yaml", jsonFile));
	}

	public File convertFromJson(File jsonFile, File outputFile) throws IOException {
		try (FileReader fileReader = new FileReader(jsonFile); FileWriter fileWriter = new FileWriter(outputFile)) {
			Map<Object, Object> map = readJSONasMap(fileReader);
			
			yamlParser.dump(map, fileWriter);
		}
		return outputFile;
	}

	protected Map<Object, Object> readJSONasMap(FileReader fileReader) throws IOException {
		JsonNode tree = jsonMapper.readTree(fileReader);

		TypeReference<Map<Object, Object>> typeRef = new TypeReference<Map<Object, Object>>() {
		};
		Map<Object, Object> map = jsonMapper.convertValue(tree, typeRef);
		return map;
	}

	// https://stackoverflow.com/questions/23744216/how-do-i-convert-from-yaml-to-json-in-java
	// https://stackoverflow.com/questions/29340383/convert-map-to-json-using-jackson
	public File convertToJson(File yamlFile) throws IOException {
		return convertToJson(yamlFile, createNewFile(".json", yamlFile));
	}

	public File convertToJson(File yamlFile, File outputFile) throws IOException {
		try (FileReader fileReader = new FileReader(yamlFile); FileWriter fileWriter = new FileWriter(outputFile)) {
			Object object = yamlParser.loadAll(fileReader);
			jsonMapper.writeValue(fileWriter, object);
		}
		return outputFile;
	}

	public File convertToXML(File yamlFile) {
		
		return null;
	}
	
}
