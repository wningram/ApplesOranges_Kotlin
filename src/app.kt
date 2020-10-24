/**
 	Validates that the parsed input is in terms of Apples and Oranges.
 */
fun validate_input(inputs: List<String>) {
	for (input in inputs) {
		when (input.toLowerCase()) {
			"apple" -> continue
			"orange" -> continue
			else -> throw Exception("Invalid input.")
		}
	}
}

/**
 	Parses input for a list of products. Throws exception is products not
 	given in expected format.
 */
fun parse_input(input: String?): List<String> {
	if (input != null) {
		val data = input.replace("\\s".toRegex(), "")
		val words = data.split(",")
		
		validate_input(words)
		return words
	} else {
		// If no value was supplied return an empty list
		return listOf()
	}
}

/**
 	Calculates the cost of a list of products.
 */
fun calculate_cost(input: List<String>): Double {
	var totalCost = 0.0
	for (item in input) {
		when (item.toLowerCase()) {
			"apple" -> totalCost += 0.60
			"orange" -> totalCost += 0.25
		}
	}
	
	return totalCost
}

fun main(args: Array<String>) {
	val helpText = "Enter a comma separated list of items. Items can be: Apple or Orange."
	var run = true
	
	println(helpText)
	
	while (run) {
		print("Input -> ")
		val input = readLine()
		
		try {
			when (input?.toLowerCase()) {
				"quit" -> run = false
				"help" -> println(helpText)
				else -> {
					val cost = calculate_cost(parse_input(input))
					println("Total Cost is $cost")
				}
			}
		} catch (e: Exception) {
			println(e.message)
		}
	}
	
	println("Program successfully terminated.")
}