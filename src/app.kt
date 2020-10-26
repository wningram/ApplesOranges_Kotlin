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
 	DEPRECATED(Use Order.calculateGrossCost). Calculates the cost of a list of products.
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

/**
 	DEPRECATED(Use Order.calculateDiscount). Determines the discount to apply to the overall order. Returns a negative value.
 */
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
	val helpText = "Enter a comma separated list of items. Items can be: Apple or Orange." +
			"\nTo restock enter restock apples/oranges ENTER. Then type the number of stock to add."
	val notifications = NotificationService()
	var run = true
	
	println(helpText)
	
	while (run) {
		print("Input -> ")
		val input = readLine()
		
		try {
			when (input?.toLowerCase()) {
				"quit" -> run = false
				"help" -> println(helpText)
				"debug" -> {
					println(notifications.orders)
					println("Apples in Stock: ${notifications.stockCountApples}")
					println("Oranges in Stock: ${notifications.stockCountOranges}")
				}
				"order" -> {
					try {
						val order_num = readLine()?.toInt()
						if (order_num != null) {
							val order = notifications.getOrder(order_num)
							println(order)
							println("Order Cost: ${order.calculateGrossCost() + order.calculateDiscount()}")
						}
					} catch (e: Exception) {
						println("Invalid input.")
					}
				}
				"restock apples" -> {
					val restock_count = readLine()?.toInt()
					if (restock_count != null && restock_count > 0) {
						val processed_orders = notifications.restockApples(restock_count)
						for (order in processed_orders) {
							val discount = order.calculateDiscount()
							println("Order ${order.id} reprocessed.")
							println("Discount $discount")
							println("Total Cost is ${order.calculateGrossCost() + discount}")
							println("Estimated Delivery Time: ${notifications.getDeliveryTime(order.id)}s (when in stock)")
						}
					}
				}
				"restock oranges" -> {
					val restock_count = readLine()?.toInt()
					if (restock_count != null && restock_count > 0) {
						val processed_orders = notifications.restockOranges(restock_count)
						for (order in processed_orders) {
							val discount = order.calculateDiscount()
							println("Order ${order.id} reprocessed.")
							println("Discount $discount")
							println("Total Cost is ${order.calculateGrossCost() + discount}")
							println("Estimated Delivery Time: ${notifications.getDeliveryTime(order.id)}s (when in stock)")
						}
					}
				}
				else -> {
					val new_order = notifications.registerOrder(Order(parse_input(input)))
					val discount = new_order.calculateDiscount()
					var cost = new_order.calculateGrossCost() + discount
					
					try {
						notifications.processOrder(new_order.id)						
					} catch (e: OutOfStockException) {
						println("Could not process order, the following item is out of stock: ${e.message}")
					}
					
					if (new_order.status) {
						println("Order ${new_order.id} processed.")
						println("Discount $discount")
						println("Total Cost is $cost")
						println("Estimated Delivery Time: ${notifications.getDeliveryTime(new_order.id)}s (when in stock)")
					} else {
						println("Order ${new_order.id} not yet processed.")
						println("Estimated Delivery Time: ${notifications.getDeliveryTime(new_order.id)}s (when in stock)")
					}
					
				}
			}
		} catch (e: Exception) {
			println("Error: " + e.message)
		}
	}
	
	println("Program successfully terminated.")
}