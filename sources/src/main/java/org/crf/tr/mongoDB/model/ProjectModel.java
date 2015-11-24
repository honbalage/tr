package org.crf.tr.mongoDB.model;

import org.crf.tr.model.Project.TestFramework;

public class ProjectModel {
	private int id;
    private String name;
    private TestFramework frameWork;
     
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public TestFramework getFrameWork() {
        return frameWork;
    }
    public void setFrameWork(TestFramework frameWork) {
        this.frameWork = frameWork;
    }

}
