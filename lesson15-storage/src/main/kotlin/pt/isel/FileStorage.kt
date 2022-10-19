package pt.isel

import java.io.File

class FileStorage<K, T>(
    private val folder: String,
    private val serializer: StringSerializer<T>,
    private val factory: (K) -> T
) : Storage<K,T> {
    private fun path(id: K) = "$folder/$id.txt"
    /**
     * Requires a unique id.
     */
    override fun new(id: K): T {
        /**
         * Validate that id is unique and there is no other file with that path.
         */
        val file = File(path(id))
        require(!file.exists()) { "There is already an entity with given id $id" }
        /**
         * 1. Create a new empty Entity object.
         * 2. Convert resulting Object in String
         * 2. Save former String to the file system.
         */
        val obj = factory(id) // T()
        val objStr = serializer.write(obj) // Entity -> String <=> obj -> String
        file.writeText(objStr)
        return obj
    }
    override fun load(id: K): T? {
        val file = File(path(id))
        if(!file.exists()) return null
        /**
         * 1. read the String content of a file
         * 2. String -> Entity
         */
        val objStr = file.readText()
        return serializer.parse(objStr)
    }

    override fun save(id: K, obj: T) {
        val file = File(path(id))
        require(file.exists()) { "There no entity with given id $id" }
        val objStr = serializer.write(obj) // Entity -> String <=> obj -> String
        file.writeText(objStr)
    }

    override fun delete(id: K) {
        val file = File(path(id))
        require(file.exists()) { "There no entity with given id $id" }
        file.delete()
    }
}
