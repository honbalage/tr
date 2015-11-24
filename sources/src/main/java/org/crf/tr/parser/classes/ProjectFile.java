package org.crf.tr.parser.classes;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

public class ProjectFile {
	private String path;
	private Metric metric;
	private ArrayList<ProjectClass> classes = new ArrayList<ProjectClass>();
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Metric getMetric() {
		return metric;
	}
	public void setMetric(Metric metric) {
		this.metric = metric;
	}
	public ArrayList<ProjectClass> getClasses() {
		return classes;
	}
	public void setClasses(ArrayList<ProjectClass> methods) {
		this.classes = methods;
	}
	
	@Override
	public final String toString() {
		return format("path: %s  metric: %s classes: %s\n", path, metric.toString(), classes.toString() );
	}
}
