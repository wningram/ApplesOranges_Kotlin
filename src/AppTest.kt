import org.junit.Test
import org.junit.Assert

@Test
fun test_parse_input(): Unit {
	// Setup
	val mockInput = "Apple, Orange, Aple"
	
	// Execution
	val result = parseInput(mockInput)
	
	// Assertions
	Assert.assertEquals(result, listOf("Apple", "Orange", "Aple"))
}