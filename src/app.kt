
fun parse_input(input: String): List<String> {
	val data = input.replace("\\s".toRegex(), "")
	val words = data.split(",")
	
	return words
}

fun cost_calculator(input: List<String>): Double {
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
		
		when (input?.toLowerCase()) {
			"quit" -> run = false
			"help" -> println(helpText)
			else -> println("Unrecognized input.")
		}
	}
	
	println("Program successfully terminated.")
}