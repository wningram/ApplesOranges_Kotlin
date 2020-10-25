import com.oracle.xmlns.internal.webservices.jaxws_databinding.ExistingAnnotationsType

class NotificationService {
	var orders: List<Order> = listOf()
	
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
		return order
	}
	
	/**
	 	 Runs processing logic on the order with the specified Id.
	 */
	fun processOrder(id: Int): Unit {
		val order = this.orders.firstOrNull({it.id == id})
		if (order != null) {
			order.status = true
		} else {
			throw Exception("Order with specified Id does not exist: $id")
		}
	}
}