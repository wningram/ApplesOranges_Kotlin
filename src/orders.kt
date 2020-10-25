var total_orders = 0

class Order(var orderInput: List<String>) {
	val id: Int
	var status: Boolean = false
	
	init {
		id = total_orders++
	}
	
	/**
	 	Determines the discount to apply to the overall order. Returns a negative value.
	 */
	fun calculateDiscount(): Double {
		var total_discount = 0.0
		var apple_count = 0
		var orange_count = 0
		for (item in this.orderInput) {
			when (item.toLowerCase()) {
				"apple" -> apple_count++
				"orange" -> orange_count++
			}
		}
		
		total_discount += (apple_count / 2) * 0.60
		total_discount += (orange_count / 3) * 0.25
		
		return total_discount * -1
	}
}