package uk.co.sentinelweb.bitwatcher.domain


import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.jfixture.annotations.Fixture
import org.junit.Before
import org.junit.Test
import java.io.*
import java.util.*
import kotlin.test.assertEquals


class AccountDomainTest {

    @Fixture lateinit var domain:AccountDomain

    @Before
    fun setUp() {
        FixtureAnnotations.initFixtures(this)
    }

    @Test
    fun testSerialisable(){
        val serialised = serialize(domain)

        val actual = deserialise(serialised) as AccountDomain

        assertEquals(domain, actual)
    }
    // from https://stackoverflow.com/questions/134492/how-to-serialize-an-object-into-a-string
    /** Read the object from Base64 string.  */
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun deserialise(s: String): Any {
        val data = Base64.getDecoder().decode(s)
        val ois = ObjectInputStream(
                ByteArrayInputStream(data))
        val o = ois.readObject()
        ois.close()
        return o
    }

    /** Write the object to a Base64 string.  */
    @Throws(IOException::class)
    private fun serialize(o: Serializable): String {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(o)
        oos.close()
        return Base64.getEncoder().encodeToString(baos.toByteArray())
    }
}
