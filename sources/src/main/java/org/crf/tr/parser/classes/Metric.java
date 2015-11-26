package org.crf.tr.parser.classes;

import static java.lang.String.format;

import java.util.Arrays;

public class Metric {
	private int elements;
	private int coveredElements;
	private int methods;
	private int coveredMethods;
	private int statements;
	private int coveredStatements;
	
	public Metric(int parseInt, int parseInt2, int parseInt3, int parseInt4,
			int parseInt5, int parseInt6) {
		this.elements = parseInt;
		this.coveredElements = parseInt2;
		this.methods = parseInt3;
		this.coveredMethods = parseInt4;
		this.statements = parseInt5;
		this.coveredStatements = parseInt6;
	}
	public Metric() {
		// TODO Auto-generated constructor stub
	}
	public int getElements() {
		return elements;
	}
	public void setElements(int elements) {
		this.elements = elements;
	}
	public int getCoveredElements() {
		return coveredElements;
	}
	public void setCoveredElements(int coveredElements) {
		this.coveredElements = coveredElements;
	}
	public int getMethods() {
		return methods;
	}
	public void setMethods(int methods) {
		this.methods = methods;
	}
	public int getCoveredMethods() {
		return coveredMethods;
	}
	public void setCoveredMethods(int coveredMethods) {
		this.coveredMethods = coveredMethods;
	}
	public int getStatements() {
		return statements;
	}
	public void setStatements(int statements) {
		this.statements = statements;
	}
	public int getCoveredStatements() {
		return coveredStatements;
	}
	public void setCoveredStatements(int coveredStatements) {
		this.coveredStatements = coveredStatements;
	}
	
	@Override
	public final String toString() {
		return format("%s %s %s %s %s %s", Integer.toString(elements), Integer.toString(coveredElements), Integer.toString(methods), Integer.toString(coveredMethods), Integer.toString(statements), Integer.toString(coveredStatements)); 
		
	}
	
}
