/**
 * InMemoryRepo.java
 */
package org.crf.tr.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.crf.tr.db.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class InMemoryRepo <K, E> implements Repository< K, E > {

	@Override
	public final List<E> listAll() {
		return _store.values().stream()
				              .collect(Collectors.toList());
	}

	@Override
	public final Optional<E> find(final K key) {
		final E entity = _store.get( key );
		_log.debug("Found: " + (entity == null ? "null" : entity.toString( )));
		return EntityConstraints.checkNull( entity );
	}

	@Override
	public final Optional<E> store(final K key, final E entity) {
		if (entity == null) return Optional.empty();

		if (_store.putIfAbsent( key, entity ) == null) {
			_log.debug("Storing: " + entity.toString( ));
			return Optional.of( entity );
		}
		return Optional.empty( );
	}

	@Override
	public final Optional<E> update(final K key, final E entity) {
		if (entity == null) return Optional.empty();
		final E prev = _store.put( key, entity );
		_log.debug("Updated: " + prev.toString( ));
		return EntityConstraints.checkNull( prev );
	}

	@Override
	public final Optional<E> remove(final K key) {
		final E prev = _store.remove( key );
		_log.debug("Removed: " + ( prev==null ? "null" : prev.toString() ));
		return EntityConstraints.checkNull( prev );
	}


	public InMemoryRepo() {
		super( );
		_store = new HashMap<>( );
	}

	
	static final class EntityConstraints {	
		static final <T> Optional<T> checkNull(final T entity) {
			if (entity == null) return Optional.empty();
			return Optional.of( entity );
		}
	}

	private static final Logger _log = LoggerFactory.getLogger( InMemoryRepo.class );
	private final Map< K, E > _store;
}
