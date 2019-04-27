package applicationModel

import geoclaseui.Geo
import order.Order
import org.junit.Assert
import org.junit.Test
import paymentMethod.Cash
import paymentMethod.PaymentMethod
import productAndMenu.Category
import productAndMenu.Menu
import productAndMenu.Product
import restaurant.Restaurant
import user.Client
import user.Supervisor
import java.util.*

class TestFactory{

    private var orderFactory : OrderFactory = OrderFactory
    private var clientFactory : ClientFactory = ClientFactory

    private var restaurantFactory : RestaurantFactory = RestaurantFactory
    private var ProductFactory : ProductFactory = ProductFactory()
    private var MenuFactory : MenuFactory = MenuFactory()


    private var applicationModel : MorfApp = MorfApp ;
    private val cash : PaymentMethod = Cash()
    private val listOfPaymentMethod : MutableCollection<PaymentMethod> = mutableListOf(cash)
    private var geoLocation: Geo = Geo(1.2,2.2);
    private var iceCream = Product(1, "Soda", "Made with milk from happy cows", 20.0, Category.DISSERT);
    private var pizza = Product(2, "HotDog", "Really italian pepperoni pizza", 40.0, Category.MAINDISH);
    private var restaurant : Restaurant = Restaurant(1, "El Tano", "inserte descripcion", "por quilmes oeste", geoLocation, mutableListOf(cash));
    private var menu0 = Menu(1,"SodaMenu","with authentic sodas since 90's", mutableListOf<Product>(),restaurant);
    private var menu1 = Menu(1,"SodaMenu","with authentic sodas since 90's", mutableListOf<Product>(),restaurant);
    private var menu2 = Menu(1,"SodaMenu","with authentic sodas since 90's", mutableListOf<Product>(),restaurant);
    private var menu3 = Menu(1,"SodaMenu","with authentic sodas since 90's", mutableListOf<Product>(),restaurant);
    private var menu4 = Menu(2, "SweetMenu", "Muuuuuuu", mutableListOf<Product>(iceCream), restaurant)
    private var menu5 = Menu(2, "SaltyMenu", "Pizza Time", mutableListOf<Product>(pizza), restaurant)
    private var menu6 = Menu(2, "FullyMenu", "Good friends, good FOOD, good times", mutableListOf<Product>(iceCream, pizza), restaurant)
    private val menus : MutableCollection<Menu> = mutableListOf<Menu>()
    private var date : Date = Date()
    private  var client: Client = Client(1,"Pepe","Roque saenz peña", date, geoLocation, "1212", applicationModel)
    private var order : Order = Order(2, client, restaurant, cash, menus)

    //OrderFactory
    @Test
    fun testOrderFactoryHasIniciallyTheCounterInOne(){
        Assert.assertEquals(0,orderFactory.code)
    }

    @Test
    fun theOrderFactoryIncreasesItsCodeByOneWhenItCreatesAnOrder(){
        Assert.assertEquals(0,orderFactory.code)
        orderFactory.createOrder(client, restaurant, cash, menus)
        Assert.assertEquals(1,orderFactory.code)
        orderFactory.createOrder(client, restaurant, cash, menus)
        Assert.assertEquals(2,orderFactory.code)
    }

    @Test
    fun testOrderFactoryReturnsAConsistentOrder(){
        var product : Order = orderFactory.createOrder(client, restaurant, cash, menus)
        Assert.assertEquals(menus, order.menus())
        Assert.assertEquals(0, order.code)
        Assert.assertEquals(client, order.getUser())
        Assert.assertEquals(restaurant, order.getRestaurant())
        Assert.assertEquals(cash, order.getPaymentMethod())
    }

    //ClientFactory
    @Test
    fun testClientFactoryHasIniciallyTheCounterInOne(){
        Assert.assertEquals(0,clientFactory.code)
    }

    @Test
    fun theClientFactoryIncreasesItsCodeByOneWhenItCreatesAnOrder(){
        Assert.assertEquals(0,orderFactory.code)
        clientFactory.createClient("Algun lado", date, geoLocation, "Pepe", "Aloha", applicationModel)
        Assert.assertEquals(1,clientFactory.code)
        clientFactory.createClient("Algun lado", date, geoLocation, "Pepe", "Aloha", applicationModel)
        Assert.assertEquals(2,clientFactory.code)
    }

    @Test
    fun testClientFactoryReturnsAConsistentClient(){
        var product : Client = clientFactory.createClient("Algun lado", date, geoLocation, "Pepe", "Aloha", applicationModel)
        Assert.assertEquals("Algun lado",client.address)
        Assert.assertEquals(0, client.code)
        Assert.assertEquals(date, client.registrationDate)
        Assert.assertEquals(geoLocation, client.geoLocation)
        Assert.assertEquals("Pepe", client.name)
        Assert.assertEquals("Aloha", client.password)
        Assert.assertEquals(applicationModel, client.applicationModel)
    }

    @Test
    fun testClientFactoryReturnsAConsistentSupervisor(){
        var supervisor : Supervisor = clientFactory.createSupervisor(restaurant, "Pepe", "Aloha", applicationModel)
        Assert.assertEquals(0, supervisor.code)
        Assert.assertEquals("Pepe", supervisor.name)
        Assert.assertEquals("Aloha", supervisor.password)
        Assert.assertEquals(applicationModel, supervisor.applicationModel)
    }

    @Test
    fun theClientFactoryIncreasesItsCounterWhenItCreatesAnClientOrASupervisor(){
        Assert.assertEquals(0,orderFactory.code)
        clientFactory.createClient("Algun lado", date, geoLocation, "Pepe", "Aloha", applicationModel)
        Assert.assertEquals(1,clientFactory.code)
        clientFactory.createClient("Algun lado", date, geoLocation, "Pepe", "Aloha", applicationModel)
        Assert.assertEquals(2,clientFactory.code)
        clientFactory.createSupervisor(restaurant, "Pepe", "Aloha", applicationModel)
        Assert.assertEquals(3,clientFactory.code)
        clientFactory.createSupervisor(restaurant, "Pepe", "Aloha", applicationModel)
        Assert.assertEquals(4,clientFactory.code)
    }

    //RestaurantFactory
    @Test
    fun testRestaurantFactoryHasIniciallyTheCounterInOne(){
        Assert.assertEquals(0,restaurantFactory.code)
    }

    @Test
    fun theRestaurantFactoryIncreasesItsCodeByOneWhenItCreatesARestaurant(){
        Assert.assertEquals(0,restaurantFactory.code)
        restaurantFactory.createRestaurant("Los Maizales A", "ATR", "Por Caballito", geoLocation, listOfPaymentMethod)
        Assert.assertEquals(1,orderFactory.code)
        restaurantFactory.createRestaurant("Los Maizales B", "ATR", "Por Caballito", geoLocation, listOfPaymentMethod)
        Assert.assertEquals(2,orderFactory.code)
    }

    @Test
    fun testRestaurantFactoryReturnsAConsistentRestaurant(){
        var restaurant : Restaurant = restaurantFactory.createRestaurant("Los Maizales A", "ATR", "Por Caballito", geoLocation, listOfPaymentMethod)
        Assert.assertEquals(0, restaurant.code)
        Assert.assertEquals("Por Caballito", restaurant.direcction)
        Assert.assertEquals("Los Maizales A", restaurant.name)
        Assert.assertEquals(geoLocation, restaurant.geoLocation)
        Assert.assertEquals("ATR", restaurant.description)
        Assert.assertEquals(mutableListOf(cash), restaurant.availablePaymentMethods)
    }

}