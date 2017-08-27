package sync.persistence;

import org.springframework.stereotype.Component;
import sync.dto.server.ShoppingListItemServer;
import sync.dto.server.ShoppingListServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the mock of the datastore
 */
public class DataStore {

    /**
     * Constructs some server device data
     * @return the mocked data of two list
     */
    public List<ShoppingListServer> getMockedData(){

        List<ShoppingListServer> returnedList = new ArrayList<ShoppingListServer>();

        ShoppingListServer shoppingList1 = new ShoppingListServer();
        shoppingList1.setIdOnDevice(1L);
        shoppingList1.setOwner("Jim");
        shoppingList1.setListName("Groceries");
        ShoppingListItemServer item1 = new ShoppingListItemServer(
                1L,
                1L,
                "shampoo",
                false,
                "2");
        ShoppingListItemServer item2 = new ShoppingListItemServer(
                1L,
                2L,
                "conditioner",
                false,
                "2");
        shoppingList1.setShoppingListItemServerList(new ArrayList<ShoppingListItemServer>(Arrays.asList(item1,item2)));

        ShoppingListServer shoppingList2 = new ShoppingListServer();
        shoppingList2.setIdOnDevice(2L);
        shoppingList2.setOwner("Joe");
        shoppingList2.setListName("Fruit");
        ShoppingListItemServer item3 = new ShoppingListItemServer(
                2L,
                3L,
                "apples",
                false,
                "25");
        ShoppingListItemServer item4= new ShoppingListItemServer(
                2L,
                4L,
                "oranges",
                false,
                "30");
        shoppingList2.setShoppingListItemServerList(new ArrayList<ShoppingListItemServer>(Arrays.asList(item3,item4)));

        returnedList.add(shoppingList1);
        returnedList.add(shoppingList2);
        return returnedList;
}



}
