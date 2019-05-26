package order

import exception.EmptyOrderException
import exception.NoValidateOrderException
import statesOrder.StateOrder
import statesOrder.StateOrder.*
import paymentMethod.*
import java.util.*
import user.User
import restaurant.Restaurant
import productAndMenu.Menu
import user.Client

data class Order(val code : Int, private val user : Client,
                 private val restaurant : Restaurant, private var payment : PaymentMethod,
                 private val menus : MutableCollection<Menu>){

    private var state : StateOrder = PENDING
    private var date : Date? = null

    fun processOrder() {
        if (menus.isEmpty()) {
            throw EmptyOrderException("")
        }
        restaurant.addOrder(this)
    }

    fun addMenu(_new_menu : Menu) {
        if(!canProcessOrder(_new_menu)) {
            throw NoValidateOrderException("")
        }
        menus.add(_new_menu)
    }

    fun removeMenu(_menu : Menu) {
        menus.remove(_menu)
    }

    fun price() : Double = menus.map{ menu -> menu.totalPrice() }.sum()

    fun setState(_state : StateOrder) {
        state = _state
    }

    fun menus() : MutableCollection<Menu> = menus

    fun getState() : StateOrder = state

    fun delivered() {
        user.addOrder(this)
        setState(DELIVERED)
    }

    fun pending() {
        setState(PENDING)
    }

    fun onMyWay() {
        setState(ONMYWAY)
    }

    fun cancelled() {
        setState(CANCELLED)
    }

    fun getPaymentMethod(): PaymentMethod = payment

    fun setPaymentMethod(_payment: PaymentMethod) {
        if (canChange()) {
            payment = _payment
        }
    }

    fun canChange(): Boolean = state.canChange()

    fun getUser() : Client = user

    fun getRestaurant() : Restaurant = restaurant

    fun getMenu() : MutableCollection<Menu> = menus

    private fun canProcessOrder(_menu : Menu) : Boolean = user.canDoOrder(_menu.restaurant)
}