package org.crf.tr.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.crf.tr.model.CloverReport;
import org.crf.tr.model.Project.TestFramework;
import org.crf.tr.mongoDB.MongoDBJDBC;
import org.crf.tr.parser.Parser;
import org.crf.tr.parser.classes.Metric;
import org.crf.tr.parser.classes.MongoProject;
import org.crf.tr.parser.classes.Project;
import org.crf.tr.parser.classes.ProjectClass;
import org.crf.tr.parser.classes.ProjectFile;
import org.crf.tr.parser.classes.ProjectPackage;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public static CloverReport getProjectByIDFromMongo(String id){
		return parseMongoProject( MongoDBJDBC.getProjectByID(id) );
	}
	
	public static CloverReport getProjectByDateFromMongo(String name, String date){
		return parseMongoProject( MongoDBJDBC.getProjectByDate(name, date) );
	}
	
	public static CloverReport parseMongoProject(String p){
            try {
            	MongoProject report = new MongoProject();
            	
            	JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(p);
				
				// get date from the JSON object
				String date = (String) jsonObject.get("date");
				report.setDate(date);
				
				JSONArray parsedProjects = (JSONArray) jsonParser.parse((String) jsonObject.get("coverage_data"));
				Iterator i = parsedProjects.iterator();
				
				while(i.hasNext()){
					Project project = new Project();
					JSONObject parsedProject = (JSONObject) i.next();
					project.setName(parsedProject.get("name").toString());
					
					JSONObject projectMetricData = (JSONObject) parsedProject.get("metric");
					project.setMetric( getMetricData(projectMetricData));

					ArrayList<ProjectPackage> packages = new ArrayList<ProjectPackage>();
					JSONArray projectPackages = (JSONArray) parsedProject.get("packages");
					Iterator projectPackagesI = projectPackages.iterator();
					while(projectPackagesI.hasNext()){
						ProjectPackage projectPackage = new ProjectPackage();
						ProjectFile packageFile = new ProjectFile();
						JSONObject parsedPackage = (JSONObject) projectPackagesI.next();
						projectPackage.setName(parsedPackage.get("name").toString());
						
						JSONObject packageMetricData = (JSONObject) parsedPackage.get("metric");
						projectPackage.setMetric(getMetricData(packageMetricData));
						
						JSONObject packageFileData = (JSONObject) parsedPackage.get("file");
						packageFile.setPath(packageFileData.get("path").toString());
						
						JSONObject fileMetricData = (JSONObject) packageFileData.get("metric");
						packageFile.setMetric( getMetricData(fileMetricData));
						
						ArrayList<ProjectClass> classes = new ArrayList<ProjectClass>();
						JSONArray projectClasses = (JSONArray) packageFileData.get("classes");
						Iterator projectClassesI = projectClasses.iterator();
						while(projectClassesI.hasNext()){
							ProjectClass projectClass = new ProjectClass();
							JSONObject parsedClass = (JSONObject) projectClassesI.next();
							projectClass.setName(parsedClass.get("name").toString());
							JSONObject classMetricData = (JSONObject) parsedClass.get("metric");
							projectClass.setMetric(getMetricData(classMetricData));
							classes.add(projectClass);
						}
						
							
						packageFile.setClasses(classes);
						projectPackage.setFile(packageFile);
						packages.add(projectPackage);
					}
					project.setPackages(packages);
					report.setProject(project);
					_log.info(project.toString());
				}
				return new CloverReport(report);
			} catch (ParseException | NullPointerException e) {
				_log.error(e.toString());
			}
            return null;
	}
	
	public static Metric getMetricData(JSONObject data){
		return new Metric(	Integer.parseInt(data.get("elements").toString()),
				Integer.parseInt(data.get("coveredElements").toString()),
				Integer.parseInt(data.get("methods").toString()),
				Integer.parseInt(data.get("coveredMethods").toString()),
				Integer.parseInt(data.get("statements").toString()),
				Integer.parseInt(data.get("coveredStatements").toString()
				));
	}
	
	private static final Logger _log = LoggerFactory.getLogger( CloverReportService.class );
}
