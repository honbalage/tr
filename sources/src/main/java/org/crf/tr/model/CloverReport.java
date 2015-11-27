package org.crf.tr.model;

import org.crf.tr.parser.classes.MongoProject;


public class CloverReport {
	private MongoProject project;
	
	public CloverReport(MongoProject report) {
		project = report;
	}

	public MongoProject getProject() {
		return project;
	}

	public void setProject(MongoProject project) {
		this.project = project;
	}

}
