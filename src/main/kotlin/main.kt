data class Item(val name: String, val price: Double)

data class ItemEntry(val item: Item, val count: Int)

sealed class DeliveryMethod {
    object SelfPickup : DeliveryMethod()
    data class CourierDelivery(val address: String, val price: Double) : DeliveryMethod()
}

data class Order(val items:List<ItemEntry>, val delivery: DeliveryMethod, val discount: Double) {
    fun getTotalPrice(): Double {
        var price = 0.0
        for (i in items){
            price += i.item.price * i.count
        }

        price *= ((100 - discount) / 100)

        when(delivery){
            is DeliveryMethod.CourierDelivery -> price += delivery.price
            is DeliveryMethod.SelfPickup -> price = price
        }
        return price
    }
}

fun main() {
    val milk = Item("Milk",0.99)
    val eggs = Item("Eggs", 1.49)
    val water = Item("Water", 0.59)
    val chocolate = Item("Chocolate", 1.99)

    val bottelOfMilk =  ItemEntry(milk, 2)
    val bottelOfWater =  ItemEntry(water, 3)
    val dozenEggs = ItemEntry(eggs, 1)
    val chocolateBars = ItemEntry(chocolate, 1)

    val shoppingList1: List<ItemEntry> = listOf(bottelOfMilk,bottelOfWater, dozenEggs)
    val shoppingList2: List<ItemEntry> = listOf(bottelOfMilk,bottelOfWater,chocolateBars, dozenEggs)

    val order1 = Order(shoppingList1, DeliveryMethod.SelfPickup, 50.0)
    val order2 = Order(shoppingList2, DeliveryMethod.CourierDelivery("A", 2.0), 20.0)

    println(order1.getTotalPrice())
    println(order2.getTotalPrice())
}
