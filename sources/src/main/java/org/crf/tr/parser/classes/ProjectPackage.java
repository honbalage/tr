package org.crf.tr.parser.classes;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

public class ProjectPackage {
	private String name;
	private Metric metric;
	private ProjectFile file = new ProjectFile();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Metric getMetric() {
		return metric;
	}
	public void setMetric(Metric metric) {
		this.metric = metric;
	}
	public ProjectFile getFile() {
		return file;
	}
	public void setFile(ProjectFile file) {
		this.file = file;
	}
	
	@Override
	public final String toString() {
		return format("name: %s metric: %s files: %s \n", name, metric.toString(), file.toString());
	}
}
