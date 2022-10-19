package pt.isel

import java.io.File
import java.lang.IllegalArgumentException
import kotlin.test.*

class StorageTest {
    class DummyEntity(val key: Int, val address: String = "")
    object DummySerializer : StringSerializer<DummyEntity> {
        override fun write(obj: DummyEntity) = "${obj.key};${obj.address}"
        override fun parse(input: String): DummyEntity {
            val words = input.split(";")
            return DummyEntity(words[0].toInt(), words[1])
        }
    }
    val dummyId = 8734638
    val folder = "out"
    @BeforeTest fun setup() {
        val f = File("$folder/$dummyId.txt")
        if(f.exists()) f.delete()
    }

    @Test fun `Create entity with existing id throws Exception`() {
        val fs = FileStorage(folder, DummySerializer, ::DummyEntity)
        fs.new(dummyId)
        val err = assertFailsWith<IllegalArgumentException> { fs.new(dummyId) }
        assertEquals("There is already an entity with given id $dummyId", err.message)
    }
    @Test fun `Load entity with unknown id returns null`() {
        val fs = FileStorage(folder, DummySerializer, ::DummyEntity)
        assertNull(fs.load(987356085))
    }
    @Test fun `Load entity with valid id`() {
        val fs = FileStorage(folder, DummySerializer, ::DummyEntity)
        fs.new(dummyId)
        assertNotNull(fs.load(dummyId))
    }
    @Test fun `Save and load an entity with valid id`() {
        val fs = FileStorage(folder, DummySerializer, ::DummyEntity)
        fs.new(dummyId)
        fs.save(dummyId, DummyEntity(dummyId, "Rua Rosa"))
        val dummy = fs.load(dummyId)
        assertNotNull(dummy)
        assertEquals(dummyId, dummy.key)
        assertEquals("Rua Rosa", dummy.address)
    }
}
