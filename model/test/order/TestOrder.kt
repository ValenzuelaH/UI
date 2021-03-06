package order

import applicationModel.MorfApp
import geoclase.Geo
import org.junit.Assert
import org.junit.Test
import paymentMethod.Cash
import paymentMethod.MercadoPago
import paymentMethod.PaymentMethod
import productAndMenu.Category
import productAndMenu.Menu
import productAndMenu.Product
import restaurant.Restaurant
import statesOrder.StateOrder
import user.Client
import java.util.*

class TestOrder {

    private var applicationModel = MorfApp
    private val cash = Cash()
    private var geoLocation = Geo(1.2,2.2);
    private var iceCream = Product(1, "Soda", "Made with milk from happy cows", 20.0, Category.POSTRE)
    private var pizza = Product(2, "HotDog", "Really italian pepperoni pizza", 40.0, Category.PLATOPRINCIPAL)
    private var restaurant = applicationModel.createRestaurant("un Nombre", "Una descripcion", "Ay no se", geoLocation, mutableListOf(cash))
    private var menu0 = Menu(1,"SodaMenu","with authentic sodas since 90's", mutableListOf<Product>(),restaurant)
    private var menu1 = Menu(1,"SodaMenu","with authentic sodas since 90's", mutableListOf<Product>(),restaurant)
    private var menu2 = Menu(1,"SodaMenu","with authentic sodas since 90's", mutableListOf<Product>(),restaurant)
    private var menu3 = Menu(1,"SodaMenu","with authentic sodas since 90's", mutableListOf<Product>(),restaurant)
    private var menu4 = Menu(2, "SweetMenu", "Muuuuuuu", mutableListOf<Product>(iceCream), restaurant)
    private var menu5 = Menu(2, "SaltyMenu", "Pizza Time", mutableListOf<Product>(pizza), restaurant)
    private var menu6 = Menu(2, "FullyMenu", "Good friends, good FOOD, good times", mutableListOf<Product>(iceCream, pizza), restaurant)
    private val menus = mutableListOf<Menu>()
    private  var client = Client(1,"Pepe","Pepe", "Roque saenz peña", geoLocation, "1212", "mail@asd.com")
    private var order = Order(2, client, restaurant, cash, menus)

    @Test
    fun test01_theCustomerWhoMadeTheOrderIsTheOneIndicated(){
        Assert.assertEquals(client, order.getUser())
    }

    @Test
    fun test02_thePaymentMethodOfTheOrderIsPending(){
        Assert.assertEquals(cash, order.getPaymentMethod())
    }

    @Test
    fun test03_theRestaurantOfTheOrderIsTheOneIndicated(){
        Assert.assertEquals(restaurant, order.getRestaurant())
    }

    @Test
    fun test04_thePaymentMethodOfOfTheOrderIsTheOneIndicated(){
        Assert.assertEquals(cash, order.getPaymentMethod())
    }

    @Test
    fun test05_theMenusAreTheIndicated(){
        Assert.assertEquals(menus, order.getMenu())
    }

   //Behavior
    @Test
    fun test06_addMenuAddsMenusInTheOrdersCollection() {
        Assert.assertEquals(0, menus.size)
        order.addMenu(menu0)
        Assert.assertEquals(1, menus.size)
        order.addMenu(menu1)
        Assert.assertEquals(2, menus.size)
        order.addMenu(menu2)
        Assert.assertEquals(3, menus.size)
        order.addMenu(menu3)
        Assert.assertEquals(4, menus.size)
        order.addMenu(menu4)
        Assert.assertEquals(5, menus.size)
        order.addMenu(menu5)
        Assert.assertEquals(6, menus.size)
        order.addMenu(menu6)
        Assert.assertEquals(7, menus.size)
    }

    @Test
    fun test07_removeMenuRemovesMenusInTheOrdersCollection() {
        order.addMenu(menu0)
        order.addMenu(menu1)
        order.addMenu(menu2)
        order.addMenu(menu3)
        order.addMenu(menu4)
        order.addMenu(menu5)
        order.addMenu(menu6)

        Assert.assertEquals(7, order.menus().size)
        order.removeMenu(menu6)
        Assert.assertEquals(6, order.menus().size)
        order.removeMenu(menu5)
        Assert.assertEquals(5, order.menus().size)
        order.removeMenu(menu4)
        Assert.assertEquals(4, order.menus().size)
        order.removeMenu(menu3)
        Assert.assertEquals(3, order.menus().size)
        order.removeMenu(menu2)
        Assert.assertEquals(2, order.menus().size)
        order.removeMenu(menu1)
        Assert.assertEquals(1, order.menus().size)
        order.removeMenu(menu0)
        Assert.assertEquals(0, order.menus().size)
    }

    @Test
    fun test08_theModificationOfThePaymentMenthodOfTheOrderDependOfItsState(){
        Assert.assertTrue(order.canChange())
        order.cancelled()
        Assert.assertFalse(order.canChange())
    }

    @Test
    fun test09_theOrderCanBeModificatedWhenItsStateIsCancelled(){
        order.cancelled()
        Assert.assertFalse(order.canChange())
    }

    @Test
    fun test10_theOrderCanBeModificatedWhenItsStateIsOnMyWay(){
        order.onMyWay()
        Assert.assertFalse(order.canChange())
    }

    @Test
    fun test11_theOrderCanBeModificatedWhenItsStateIsDelivered(){
        order.delivered()
        Assert.assertFalse(order.canChange())
    }

    @Test
    fun test12_theOnlyPaymentMenthodOfTheOrderThatPermitItsModificationOIsThePending(){
        order.pending()
        Assert.assertTrue(order.canChange())
        order.cancelled()
        Assert.assertFalse(order.canChange())
    }

    @Test
    fun test13_thePriceOfTheOrderWithoutMenusIsZero(){
        Assert.assertEquals(0.0, order.price(), 0.0)
    }

    @Test
    fun test14_thePriceOfTheOrderIsThePriceOfItsOnlyMenu(){
        order.addMenu(menu4)
        Assert.assertEquals(20.0, order.price(), 0.0)
    }

    @Test
    fun test15_thePriceOfTheOrderIsTheSumOfThePricesThatComposeIt(){
        order.addMenu(menu4)
        order.addMenu(menu5)
        Assert.assertEquals(60.0, order.price(), 0.0)
    }

    @Test
    fun test16_theOrderCanChangeItsPaymentMethod(){
        var mercadoPago = MercadoPago("", "")
        order.setPaymentMethod(mercadoPago)
        Assert.assertEquals(mercadoPago, order.getPaymentMethod())
    }


        @Test
    fun test17_thePriceOfTheOrderModifiedByTheMenusThatComposeIt(){
        Assert.assertEquals(0.0, order.price(), 0.0)
        order.addMenu(menu6)
        Assert.assertEquals(60.0, order.price(), 0.0)
        order.addMenu(menu5)
        Assert.assertEquals(100.0, order.price(), 0.0)
        order.addMenu(menu4)
        Assert.assertEquals(120.0, order.price(), 0.0)
    }

    @Test
    fun test18_theStateOfTheOrderIsDelivered() {
        order.delivered()
        Assert.assertEquals(StateOrder.DELIVERED,order.getState())
    }

    @Test
    fun test19_theStateOfTheOrderIsDeliveredPending() {
        order.pending()
        Assert.assertEquals(StateOrder.PENDING,order.getState())
    }

    @Test
    fun test20_theStateOfTheOrderIsDeliveredOnMyWay() {
        order.onMyWay()
        Assert.assertEquals(StateOrder.ONMYWAY,order.getState())
    }

    @Test
    fun test21_theStateOfTheOrderIsDeliveredCancelled() {
        order.cancelled()
        Assert.assertEquals(StateOrder.CANCELLED,order.getState())
    }


}