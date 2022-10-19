package pt.isel

/**
 * param K Type of entity's id
 * param T Type of the entity
 */
interface Storage<K, T> {
    /**
     * Requires a unique id.
     */
    fun new(id: K): T
    /**
     * @return null if there is no Enitity for given id.
     */
    fun load(id: K): T?
    /**
     * @throws IllegalArgumentException if there is no entity for that id.
     */
    fun save(id: K, obj: T)
    /**
     * @throws IllegalArgumentException if there is no entity for that id.
     */
    fun delete(id: K)
}
