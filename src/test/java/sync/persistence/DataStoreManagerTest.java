package sync.persistence;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sync.dto.device.ShoppingListDevice;
import sync.dto.device.ShoppingListItemDevice;
import sync.dto.server.ShoppingListServer;
import sync.persistence.util.ShoppingListMapper;

import java.util.List;

import static org.junit.Assert.*;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DataStoreManagerTest.Config.class,DataStoreTest.Config.class})
public class DataStoreManagerTest {

    private static final String JIM = "Jim";
    private static final String JOE = "Joe";
    private static final String ANYONEELSE = "AnyoneElse";

    @Autowired
    private DataStoreManager underTest;

    @Test
    public void findAllForUser() throws Exception {
        underTest.init();
        List<ShoppingListDevice> jimsList = underTest.findAllForUser(JIM);
        Assert.assertEquals(1,jimsList.size());
        List<ShoppingListDevice> joesList = underTest.findAllForUser(JOE);
        Assert.assertEquals(1,joesList.size());
        List<ShoppingListDevice> anyoneElseList = underTest.findAllForUser(ANYONEELSE);
        Assert.assertEquals(0,anyoneElseList.size());
    }

    @Test
    public void addShoppingList() throws Exception {
        underTest.init();
        ShoppingListDevice newList = new ShoppingListDevice(3L,"newList","Jim");

        underTest.addShoppingList(newList);

        List<ShoppingListDevice> returnedList = underTest.findAllForUser("Jim");
        Assert.assertEquals(2,returnedList.size());
        Assert.assertEquals(returnedList.get(1).getListName(),"newList");
        Assert.assertTrue(returnedList.get(1).getIdOnDevice().equals(3L));

    }

    @Test
    public void updateShoppingList() throws Exception {
        underTest.init();
        List<ShoppingListDevice> firstTimeList = underTest.findAllForUser("Jim");
        Assert.assertEquals(firstTimeList.get(0).getListName(),"Groceries");
        ShoppingListDevice updatedList = new ShoppingListDevice(1L,"BetterGroceries","Jim");
        underTest.updateShoppingList(updatedList);
        List<ShoppingListDevice> returnedList = underTest.findAllForUser("Jim");
        Assert.assertEquals(returnedList.get(0).getListName(),"BetterGroceries");
    }

    @Test
    public void removeShoppingList() throws Exception {
        underTest.init();
        List<ShoppingListDevice> firstTimeList = underTest.findAllForUser("Jim");
        Assert.assertEquals(1,firstTimeList.size());
        underTest.removeShoppingList(firstTimeList.get(0));
        List<ShoppingListDevice> secondTimeList = underTest.findAllForUser("Jim");
        Assert.assertEquals(0,secondTimeList.size());
    }

    @Test
    public void addItemToList() throws Exception {
        underTest.init();
        List<ShoppingListDevice> list = underTest.findAllForUser("Jim");
        Assert.assertEquals(2,list.get(0).getShoppingListItemDeviceList().size());

        ShoppingListDevice listToAddItemTo = underTest.findAllForUser("Jim").get(0);
        ShoppingListItemDevice newItem = new ShoppingListItemDevice(listToAddItemTo.getIdOnDevice(),45L,"ItemName",false,"2");
        underTest.addItemToList(newItem);
        list = underTest.findAllForUser("Jim");
        Assert.assertEquals(3,list.get(0).getShoppingListItemDeviceList().size());
    }

    @Test
    public void updateItem() throws Exception {
        underTest.init();
        List<ShoppingListDevice> list = underTest.findAllForUser("Jim");
        Assert.assertEquals("shampoo",list.get(0).getShoppingListItemDeviceList().get(0).getItemName());
        ShoppingListItemDevice deviceItem = new ShoppingListItemDevice(1L,1L,"BetterShampoo",false,"2");
        underTest.updateItem(deviceItem);
        list = underTest.findAllForUser("Jim");
        Assert.assertEquals("BetterShampoo",list.get(0).getShoppingListItemDeviceList().get(1).getItemName());
    }

    @Test
    public void removeItemFromList() throws Exception {

        underTest.init();
        List<ShoppingListDevice> list = underTest.findAllForUser("Jim");
        Assert.assertEquals(2,list.get(0).getShoppingListItemDeviceList().size());

        ShoppingListDevice listToAddItemTo = underTest.findAllForUser("Jim").get(0);
        ShoppingListItemDevice newItem = new ShoppingListItemDevice(listToAddItemTo.getIdOnDevice(),1L,"shampoo",false,"2");
        underTest.removeItemFromList(newItem);
        list = underTest.findAllForUser("Jim");
        Assert.assertEquals(1,list.get(0).getShoppingListItemDeviceList().size());
    }

    @Configuration
    public static class Config{

        @Bean
        public DataStoreManager dataStoreManager(){
            return new DataStoreManager();
        }

        @Bean
        public ShoppingListMapper shoppingListMapper(){ return new ShoppingListMapper();}

    }
}