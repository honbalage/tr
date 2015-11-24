package org.crf.tr.parser;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.crf.tr.parser.classes.Method;
import org.crf.tr.parser.classes.Metric;
import org.crf.tr.parser.classes.Project;
import org.crf.tr.parser.classes.ProjectClass;
import org.crf.tr.parser.classes.ProjectFile;
import org.crf.tr.parser.classes.ProjectPackage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {

	public static void readXMLtoArray(Document doc)
	{
	    if(doc != null)
	    {
	    	ArrayList<Project> projects = new ArrayList<Project>();
	        NodeList projectList = doc.getElementsByTagName("project");
	        for (int i = 0; i < projectList.getLength(); i++){	        	
	        	Node projectNode = projectList.item(i);
	        	
	        	Project project = new Project();
	        	ArrayList<ProjectPackage> packages = new ArrayList<ProjectPackage>();
	        	
	            Element projectE = (Element) projectNode;
	            project.setName(projectE.getAttribute("name"));
	            Element metricE = (Element) projectE.getElementsByTagName("metrics").item(0);
	            Metric m = new Metric(Integer.parseInt(metricE.getAttribute("elements")), Integer.parseInt(metricE.getAttribute("coveredelements")), Integer.parseInt(metricE.getAttribute("methods")), Integer.parseInt(metricE.getAttribute("coveredmethods")), Integer.parseInt(metricE.getAttribute("statements")), Integer.parseInt(metricE.getAttribute("coveredstatements")));
	            project.setMetric(m);
	            
	            NodeList packageList = projectE.getElementsByTagName("package");
	            for (int j = 0; j < packageList.getLength(); j++){
	            	Node packageNode = packageList.item(j);
	            	
	            	ProjectPackage p = new ProjectPackage();
	            	ArrayList<ProjectClass> classes = new ArrayList<ProjectClass>();
	            	
	            	Element packageE = (Element) packageNode;
	            	p.setName(packageE.getAttribute("name"));
	            	metricE = (Element) packageE.getElementsByTagName("metrics").item(0);
	            	m = new Metric(Integer.parseInt(metricE.getAttribute("elements")), Integer.parseInt(metricE.getAttribute("coveredelements")), Integer.parseInt(metricE.getAttribute("methods")), Integer.parseInt(metricE.getAttribute("coveredmethods")), Integer.parseInt(metricE.getAttribute("statements")), Integer.parseInt(metricE.getAttribute("coveredstatements")));
	            	p.setMetric(m);
	            	Element fileE = (Element) packageE.getElementsByTagName("file").item(0);
	            	
	            	ProjectFile file = new ProjectFile();
	            	
	            	file.setPath(fileE.getAttribute("path"));
	            	metricE = (Element) fileE.getElementsByTagName("metrics").item(0);
	            	m = new Metric(Integer.parseInt(metricE.getAttribute("elements")), Integer.parseInt(metricE.getAttribute("coveredelements")), Integer.parseInt(metricE.getAttribute("methods")), Integer.parseInt(metricE.getAttribute("coveredmethods")), Integer.parseInt(metricE.getAttribute("statements")), Integer.parseInt(metricE.getAttribute("coveredstatements")));
	            	file.setMetric(m);

	            	NodeList classList = fileE.getElementsByTagName("class");
	            	for (int k = 0; k < classList.getLength(); k++){
	            		Node classNode = classList.item(k);
		            	
		            	ProjectClass c = new ProjectClass();
		            	ArrayList<Method> methods = new ArrayList<Method>();
		            	
		            	Element classE = (Element) classNode;
		            	c.setName(classE.getAttribute("name"));
		            	metricE = (Element) classE.getElementsByTagName("metrics").item(0);
		            	m = new Metric(Integer.parseInt(metricE.getAttribute("elements")), Integer.parseInt(metricE.getAttribute("coveredelements")), Integer.parseInt(metricE.getAttribute("methods")), Integer.parseInt(metricE.getAttribute("coveredmethods")), Integer.parseInt(metricE.getAttribute("statements")), Integer.parseInt(metricE.getAttribute("coveredstatements")));
		            	c.setMetric(m);
		            	
		            	classes.add(c);
	            	}
	            	file.setClasses(classes);
	            	
	            	p.setFile(file);
	            	packages.add(p);
	            }
	            project.setPackages(packages);
	            projects.add(project);
	        }
	    }
	}
	
	public static Document parseXML(String filePath) throws ParserConfigurationException, SAXException, IOException
	{
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document doc = db.parse(filePath);
	    doc.getDocumentElement().normalize();
	    return doc;
	}
}
