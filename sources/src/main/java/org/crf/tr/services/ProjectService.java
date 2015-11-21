/**
 * ProjectService.java
 */
package org.crf.tr.services;

import java.util.List;
import java.util.Optional;

import org.crf.tr.db.factories.RepositoryFactory;
import org.crf.tr.db.Repository;
import org.crf.tr.model.Project;
import org.crf.tr.services.signals.EntityAlreadyExistsException;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class ProjectService {

	public final List<Project> listAll() {
		return RepositoryFactory.makeProjectRepo().listAll( );
	}

	public final Project store(final Project p) throws EntityAlreadyExistsException {
		final Repository< Project.Key, Project > repo = RepositoryFactory.makeProjectRepo();
		return EntityConstraints.checkUnicityOf(repo.store( p.key(), p ));
	}
	
	
	static final class EntityConstraints {
		static final Project checkUnicityOf(final Optional<Project> storeResult) throws EntityAlreadyExistsException {
			if (! storeResult.isPresent())
				throw new EntityAlreadyExistsException();
			return storeResult.get();
		}
	}
}
