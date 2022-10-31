package pt.isel

import com.mongodb.client.MongoDatabase
import org.litote.kmongo.deleteOneById
import org.litote.kmongo.findOneById
import org.litote.kmongo.replaceOneById
import java.io.File

class Doc(val _id: String, val data: String)

class MongoStorage<T>(
    val collectionName: String,
    val db: MongoDatabase,
    private val serializer: StringSerializer<T>,
    private val factory: (String) -> T
) : Storage<String,T> {

    val collection = db.getCollection(collectionName, Doc::class.java)
    /**
     * Requires a unique id.
     */
    override fun new(id: String): T {
        /**
         * Validate that id is unique and there is no other file with that path.
         */
        require(load(id) == null) { "There is already a document with given id $id" }
        /**
         * 1. Create a new empty Entity object.
         * 2. Convert resulting Object in String
         * 2. Save former String into a Document in MongoDB
         */
        val obj = factory(id) // T()
        val objStr = serializer.write(obj) // Entity -> String <=> obj -> String
        collection.insertOne(Doc(id, objStr))
        return obj
    }
    override fun load(id: String): T? {
        val doc = collection.findOneById(id) ?: return null

        /**
         * 1. read the String content of a document
         * 2. String -> Entity
         */
        val objStr = doc.data
        return serializer.parse(objStr)
    }

    override fun save(id: String, obj: T) {
        require(load(id) != null) { "There is no document with given id $id" }
        val objStr = serializer.write(obj) // Entity -> String <=> obj -> String
        collection.replaceOneById(id, Doc(id, objStr))
    }

    override fun delete(id: String) {
        require(load(id) != null) { "There is no document with given id $id" }
        collection.deleteOneById(id)
    }
}
