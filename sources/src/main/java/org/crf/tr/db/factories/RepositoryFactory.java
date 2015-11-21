/**
 * ReposiroyFactory.java
 */
package org.crf.tr.db.factories;

import org.crf.tr.db.Repository;
import org.crf.tr.db.impl.InMemoryRepo;
import org.crf.tr.model.Project;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class RepositoryFactory {

	public static final Repository<Project.Key, Project> makeProjectRepo() {
		return _projectRepo;
	}
	
	static final InMemoryRepo<Project.Key, Project> _projectRepo;
	static {
		_projectRepo = new InMemoryRepo<>( );
	}
}
