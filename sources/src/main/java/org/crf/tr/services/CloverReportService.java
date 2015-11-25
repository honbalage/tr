package org.crf.tr.services;

import java.io.IOException;

import org.crf.tr.model.Project.TestFramework;
import org.crf.tr.mongoDB.MongoDBJDBC;
import org.crf.tr.parser.Parser;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class CloverReportService {

	public static void insertProjectToMongo(String name, TestFramework fw, String path){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json;
		try {
			json = ow.writeValueAsString(Parser.readJSONtoArray(Parser.parseXML(Parser.XMLtoString(path))));
			MongoDBJDBC.createProjectDBObject(name, fw.toString(), json);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static String getProjectFromMongo(String name, String date){
		return MongoDBJDBC.getProjectDBObject(name, date);
	}
}
