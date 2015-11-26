package org.crf.tr.parser.classes;

import static java.lang.String.format;

import java.util.ArrayList;

public class ProjectClass {
	private String name;
	private Metric metric;
//	private ArrayList<Method> methods = new ArrayList<Method>();
	
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
//	public ArrayList<Method> getMethods() {
//		return methods;
//	}
//	public void setMethods(ArrayList<Method> methods) {
//		this.methods = methods;
//	}
	
	@Override
	public final String toString() {
		return format("name: %s  metric: %s \n", name, metric.toString() );
	}
}
