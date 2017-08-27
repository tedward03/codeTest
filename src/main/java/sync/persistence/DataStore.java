/*
    Copyright of Ed.Co Enterprises
*/
package sync.persistence;

import sync.dto.server.ServerItem;
import sync.dto.server.ServerList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the mock of the Datastore
 *
 * @author tedward603@gmail.com
 */
public class DataStore {

    /**
     * Constructs some server device data
     * @return the mocked data of two list
     */
    public List<ServerList> getMockedData(){

        List<ServerList> returnedList = new ArrayList<ServerList>();

        ServerList shoppingList1 = new ServerList();
        shoppingList1.setIdOnDevice(1L);
        shoppingList1.setOwner("Jim");
        shoppingList1.setListName("Groceries");
        ServerItem item1 = new ServerItem(
                1L,
                1L,
                "shampoo",
                false,
                "2");
        ServerItem item2 = new ServerItem(
                1L,
                2L,
                "conditioner",
                false,
                "2");
        shoppingList1.setShoppingListItemServerListItem(new ArrayList<ServerItem>(Arrays.asList(item1,item2)));

        ServerList shoppingList2 = new ServerList();
        shoppingList2.setIdOnDevice(2L);
        shoppingList2.setOwner("Joe");
        shoppingList2.setListName("Fruit");
        ServerItem item3 = new ServerItem(
                2L,
                3L,
                "apples",
                false,
                "25");
        ServerItem item4= new ServerItem(
                2L,
                4L,
                "oranges",
                false,
                "30");
        shoppingList2.setShoppingListItemServerListItem(new ArrayList<ServerItem>(Arrays.asList(item3,item4)));

        returnedList.add(shoppingList1);
        returnedList.add(shoppingList2);
        return returnedList;
    }



}
