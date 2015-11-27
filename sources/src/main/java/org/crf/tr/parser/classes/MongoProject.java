package org.crf.tr.parser.classes;

import static java.lang.String.format;

public class MongoProject {
	private Project project;
	private String date;
	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public final String toString() {
		return format("project: %s  date: %s", project, date );
	}
}
