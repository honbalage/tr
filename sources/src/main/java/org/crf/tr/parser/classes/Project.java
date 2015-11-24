package org.crf.tr.parser.classes;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Project {
	private String name;
	private Metric metric;
	private ArrayList<ProjectPackage> packages = new ArrayList<ProjectPackage>();
	
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
	public ArrayList<ProjectPackage> getPackages() {
		return packages;
	}
	public void setPackages(ArrayList<ProjectPackage> packages) {
		this.packages = packages;
	}
	
	@Override
	public final String toString() {
		return format("name: %s metric: %s packages: %s", name, metric.toString(), packages.toString() );
	}
	
}
