package org.crf.tr.model;

import org.crf.tr.model.Project.TestFramework;
import org.crf.tr.mongoDB.MongoDBJDBC;
import org.crf.tr.parser.Parser;

public class CloverReport {
	
	public static void insertProjectToMongo(String name, TestFramework fw, String path){
		MongoDBJDBC.createProjectDBObject(name, fw.toString(), Parser.XMLtoString(path));
	}
	
	public static String getProjectFromMongo(String name, String date){
		return MongoDBJDBC.getProjectDBObject(name, date);
	}
}
