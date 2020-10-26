class NotificationService {
	var orders: List<Order> = listOf()
	var stockCountApples: Int = 3
	var stockCountOranges: Int = 3
	
	/**
	 	Gets the order with the specified Id from the list of registered orders.
	 */
	fun getOrder(id: Int): Order {
		val result = this.orders.firstOrNull({it.id == id})
		if (result != null)
			return result
		else
			throw Exception("Order with specified Id does not exist: $id")
	}
	
	/**
	 	Gets the status of an order in the orders list.
	 */
	fun getOrderStatus(id: Int): Boolean {
		return this.getOrder(id).status
	}
	
	/**
	 	Adds a new order to the list of orders. Returns the newly registered order.
	 */
	fun registerOrder(order: Order): Order {
		this.orders = this.orders + order
		return this.getOrder(order.id)
	}
	
	/**
	 	 Runs processing logic on the order with the specified Id.
	 */
	fun processOrder(id: Int): Unit {
		val order = this.orders.firstOrNull({it.id == id})
		var apples_available = this.stockCountApples
		var oranges_available = this.stockCountOranges
		if (order != null) {
			// Determine if order can be fulfilled with current stock
			for (item in order.orderInput) {
				when (item.toLowerCase()) {
					"apple" -> {
						if (--apples_available == -1)
							throw OutOfStockException("Apple")
					}
					"orange" -> {
						if (--oranges_available == -1)
							throw OutOfStockException("Orange")
					}
				}
			}
			
			// Update stock count for this order, if order can be fulfilled
			this.stockCountApples -= this.stockCountApples - apples_available
			this.stockCountOranges -= this.stockCountOranges - oranges_available
			// Indicate that this order has been processed
			order.status = true
		} else {
			throw Exception("Order with specified Id does not exist: $id")
		}
	}
	
	/**
	 	Gets the estimated delivery time for an order based on its
	 	position in the orders list and how many orders have already
	 	been processed.
	 */
	fun getDeliveryTime(order_id: Int): Int {
		var delivery_time = 0
		val current_order = this.getOrder(order_id)
		if (current_order.status)
			return 0
		else {
			for (order in this.orders) {
				// Add 10 minutes to delivery time for each order that is not processed
				// whose Id preceeds the current
				if (!order.status && order.id < order_id)
					delivery_time += 10 * 60
			}
			
			return delivery_time
		}
	}
	
	fun reprocessUnfulfilledOrders(): List<Order> {
		var processed_orders: List<Order> = listOf()
		
		// Get list of unfulfilled orders
		for (order in this.orders.filter { !it.status }) {
			try {
				this.processOrder(order.id)
				processed_orders += order
			} catch (e: OutOfStockException) {}
		}
		
		return processed_orders
	}
	
	/**
	 	Increments apple stock by specified amount and attempts
		to reprocess unfulfilled orders. Returns a list of fulfilled
	 	orders.
	 */
	fun restockApples(count: Int): List<Order> {
		var processed_orders: List<Order>
		// Increment stock count for apples
		this.stockCountApples += count
		
		processed_orders = reprocessUnfulfilledOrders()
		
		return processed_orders
	}
	
	/**
	 	Increments orange stock by specified amount and attempts
		to reprocess unfulfilled orders. Returns a list of fulfilled
	 	orders.
	 */
	fun restockOranges(count: Int): List<Order> {
		var processed_orders: List<Order>
		// Increment sotck coutn for oranges
		this.stockCountOranges += count
		
		processed_orders = reprocessUnfulfilledOrders()
		
		return processed_orders
	}
}