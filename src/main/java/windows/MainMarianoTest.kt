package windows

import applicationModel.MorfApp
import discount.FixedDiscount
import discount.NoDiscount
import geoclaseui.Geo
import paymentMethod.Cash
import productAndMenu.Category
import productAndMenu.Menu
import productAndMenu.Product

fun main() {
    var morfap = MorfApp;
    var laConga = morfap.createRestaurant("La Conga",
            "Cocina Peruana",
            "Calle Falsa 123",
            Geo(1.5, 1.5),
            mutableListOf(Cash()));
    var clubMili = morfap.createRestaurant("El club de la milanesa",
                                        "Mili de plastico",
                                            "Rigoletto 245",
                                            Geo(1.7, 1.8),
                                            mutableListOf(Cash()));
    morfap.createSupervisor(clubMili, "pepe", "1234");
    morfap.createSupervisor(laConga, "...", "...");

    var unaHamburguesaSalvaje : Product = laConga.createProduct("Hamburguesa", "Al vapor", 100.00, Category.NONE)
    var unaCocaSalvaje : Product = laConga.createProduct("Coca Cola", "Azucar 200%", 60.00, Category.DRINK)
    var menu1: Menu = laConga.createMenu("Menu1",
                                         "Coca + Hambur",  mutableListOf<Product>(), laConga, NoDiscount(), false)
    menu1.addProductToMenu(unaHamburguesaSalvaje)
    menu1.addProductToMenu(unaCocaSalvaje)

    var porcionDePapas : Product = laConga.createProduct("Papas fritas", "papas", 80.00, Category.ADITIONAL)
    var rabas : Product = laConga.createProduct("Rabas", "rabas fritas", 160.00, Category.STARTER)

    var menu2: Menu = laConga.createMenu("Menu2",
            "Coca + Hambur",  mutableListOf<Product>(), laConga, FixedDiscount(20.00), true)

    menu2.addProductToMenu(rabas)
    menu2.addProductToMenu(porcionDePapas)
    menu2.addProductToMenu(unaHamburguesaSalvaje)
    //print(menu2.productsOfMenu)
    WelcomeWindow(UserModel()).startApplication();

}
