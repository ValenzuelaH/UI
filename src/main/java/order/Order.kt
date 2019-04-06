package order

import statesOrder.StateOrder
import statesOrder.StateOrder.*
import paymentMethod.*

data class Order(private val code : Int, private val user : User,
                 private val restaurant : Restaurant, private var payment : PaymentMethod
                 private val menus : MutableList<Menu>){

    private var state : StateOrder = PENDING
    private var date : Date? = null

    fun addMenu(_new_menu : Menu): Unit {
        menus.add(_new_menu)
    }

    private fun setState(_state : StateOrder) : Unit{
        state = _state
    }

    fun delivered() : Unit{
        setState(DELIVERED)
    }

    fun pending() : Unit{
        setState(PENDING)
    }

    fun onMyWay() : Unit{
        setState(ONMYWAY)
    }

    fun cancelled() : Unit{
        setState(CANCELLED)
    }

}