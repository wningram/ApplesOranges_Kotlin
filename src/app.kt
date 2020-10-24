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
	var total_cost = 0.0
	for (item in input) {
		when (item.toLowerCase()) {
			"apple" -> total_cost += 0.60
			"orange" -> total_cost += 0.25
		}
	}
	
	return total_cost
}

fun calculate_discount(input: List<String>): Double {
	var total_discount = 0.0
	var apple_count = 0
	var orange_count = 0
	for (item in input) {
		when (item.toLowerCase()) {
			"apple" -> apple_count++
			"orange" -> orange_count++
		}
	}
	
	total_discount += (apple_count / 2) * 0.60
	total_discount += (orange_count / 3) * 0.25
	
	return total_discount * -1
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
					val parsed_input = parse_input(input)
					val discount = calculate_discount(parsed_input)
					var cost = calculate_cost(parsed_input) + discount
					
					println("Discount $discount")
					println("Total Cost is $cost")
				}
			}
		} catch (e: Exception) {
			println(e.message)
		}
	}
	
	println("Program successfully terminated.")
}