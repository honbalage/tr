/**
 * Repository.java
 */
package org.crf.tr.db;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
/**
 * K: key
 * E: entity
 * */
public interface Repository <K, E> {

	/**
	 * @return a {@link java.util.List} of all entities stored.
	 * */
	public List<E> listAll();

	/**
	 * Retrieves the entity corresponding to the given key.
	 * 
	 * @param key
	 * @return an {@link java.util.Optional} holding the entity
	 * */
	public Optional<E> find(final K key);

	/**
	 * Attempts to store the given entity and returns an empty result in case
	 * the entity already exists for the given key.
	 * 
	 * @param key
	 * @param entity
	 * @return an {@link java.util.Optional} holding the entity stored
	 * */
	public Optional<E> store(final K key, final E entity);

	/**
	 * Updates the entity corresponding to the given key with the values held
	 * by the entity passed as argument.
	 * 
	 * @param key
	 * @param entity
	 * @return an {@link java.util.Optional} holding the entity previously stored
	 * */
	public Optional<E> update(final K key, final E entity);
	
	/**
	 * Attempts to remove the entity corresponding to the given key and returns
	 * an empty value in case the entity was not previously stored.
	 * 
	 * @param key
	 * @param entity
	 * @return an {@link java.util.Optional} holding the entity removed
	 * */
	public Optional<E> remove(final K key);
}
