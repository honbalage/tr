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
		return EntityConstraints.checkNull( entity );
	}
	
	@Override
	public final Optional<E> store(final K key, final E entity) {
		if (_store.putIfAbsent( key, entity ) == null)
			return Optional.of( entity );
		return Optional.empty( );
	}
	
	@Override
	public final Optional<E> update(final K key, final E entity) {
		final E prev = _store.put( key, entity );
		return EntityConstraints.checkNull( prev );
	}

	@Override
	public final Optional<E> remove(final K key) {
		final E prev = _store.remove( key );
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
	
	private final Map< K, E > _store;
}
