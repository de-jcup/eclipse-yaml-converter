package de.jcup.yamlconverter;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class YamlConverterTest {

	private YamlConverter converterToTest;


	@Before
	public void before() {
		converterToTest = new YamlConverter();
	}
	

	@Test
	public void is_able_to_convert_a_kubernetes_file_json_to_yaml() throws Exception{
		File file = TestFileAccess.INSTANCE.getTestFile("kubernetes-namespaces-test1.json");
		File target = TestFileAccess.INSTANCE.newTempBuildFile(file.getName()+"convertedToYaml.yaml");
		
		File result = converterToTest.convertFromJson(file,target);
		
		assertEquals(result,target);
	}
	
	
	@Test
	public void is_able_to_convert_a_kubernetes_file_json_to_yaml_and_back_are_same() throws Exception{
		File file = TestFileAccess.INSTANCE.getTestFile("kubernetes-namespaces-test1.json");
		File target1 = TestFileAccess.INSTANCE.newTempBuildFile(file.getName()+"convertedToYaml.yaml");
		File target2 = TestFileAccess.INSTANCE.newTempBuildFile(file.getName()+"backconvertedToJSON.json");
		
		File result1 = converterToTest.convertFromJson(file,target1);
		File result2 = converterToTest.convertToJson(result1,target2);
		
	}
	
	@Test
	public void is_able_to_convert_a_kubernetes_file_yaml_to_json() throws Exception{
		File file = TestFileAccess.INSTANCE.getTestFile("kubernetes-namespaces-test1.yaml");
		File target = TestFileAccess.INSTANCE.newTempBuildFile(file.getName()+"convertedToJSON.json");
		
		File result = converterToTest.convertToJson(file,target);
		
		assertEquals(result,target);
	}

}
