package org.crf.tr.parser.classes;

import static java.lang.String.format;

public class Method {
	private String name;
	private int called;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCalled() {
		return called;
	}
	public void setCalled(int called) {
		this.called = called;
	}
	
	@Override
	public final String toString() {
		return format("%s #%s\n", name, Integer.toString(called));
	}
}
