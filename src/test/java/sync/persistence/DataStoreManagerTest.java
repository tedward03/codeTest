package sync.persistence;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sync.dto.device.DeviceItem;
import sync.dto.device.DeviceList;
import sync.persistence.util.ShoppingListMapper;

import java.util.List;


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
        List<DeviceList> jimsList = underTest.findAllForUser(JIM);
        Assert.assertEquals(1,jimsList.size());
        List<DeviceList> joesList = underTest.findAllForUser(JOE);
        Assert.assertEquals(1,joesList.size());
        List<DeviceList> anyoneElseList = underTest.findAllForUser(ANYONEELSE);
        Assert.assertEquals(0,anyoneElseList.size());
    }

    @Test
    public void addShoppingList() throws Exception {
        underTest.init();
        DeviceList newList = new DeviceList(3L,"newList","Jim");

        underTest.addShoppingList(newList);

        List<DeviceList> returnedList = underTest.findAllForUser("Jim");
        Assert.assertEquals(2,returnedList.size());
        Assert.assertEquals(returnedList.get(1).getListName(),"newList");
        Assert.assertTrue(returnedList.get(1).getIdOnDevice().equals(3L));

    }

    @Test
    public void updateShoppingList() throws Exception {
        underTest.init();
        List<DeviceList> firstTimeList = underTest.findAllForUser("Jim");
        Assert.assertEquals(firstTimeList.get(0).getListName(),"Groceries");
        DeviceList updatedList = new DeviceList(1L,"BetterGroceries","Jim");
        underTest.updateShoppingList(updatedList);
        List<DeviceList> returnedList = underTest.findAllForUser("Jim");
        Assert.assertEquals(returnedList.get(0).getListName(),"BetterGroceries");
    }

    @Test
    public void removeShoppingList() throws Exception {
        underTest.init();
        List<DeviceList> firstTimeList = underTest.findAllForUser("Jim");
        Assert.assertEquals(1,firstTimeList.size());
        underTest.removeShoppingList(firstTimeList.get(0));
        List<DeviceList> secondTimeList = underTest.findAllForUser("Jim");
        Assert.assertEquals(0,secondTimeList.size());
    }

    @Test
    public void addItemToList() throws Exception {
        underTest.init();
        List<DeviceList> list = underTest.findAllForUser("Jim");
        Assert.assertEquals(2,list.get(0).getShoppingListItemDeviceListItem().size());

        DeviceList listToAddItemTo = underTest.findAllForUser("Jim").get(0);
        DeviceItem newItem = new DeviceItem(listToAddItemTo.getIdOnDevice(),45L,"ItemName",false,"2");
        underTest.addItemToList(newItem);
        list = underTest.findAllForUser("Jim");
        Assert.assertEquals(3,list.get(0).getShoppingListItemDeviceListItem().size());
    }

    @Test
    public void updateItem() throws Exception {
        underTest.init();
        List<DeviceList> list = underTest.findAllForUser("Jim");
        Assert.assertEquals("shampoo",list.get(0).getShoppingListItemDeviceListItem().get(0).getItemName());
        DeviceItem deviceItem = new DeviceItem(1L,1L,"BetterShampoo",false,"2");
        underTest.updateItem(deviceItem);
        list = underTest.findAllForUser("Jim");
        Assert.assertEquals("BetterShampoo",list.get(0).getShoppingListItemDeviceListItem().get(1).getItemName());
    }

    @Test
    public void removeItemFromList() throws Exception {

        underTest.init();
        List<DeviceList> list = underTest.findAllForUser("Jim");
        Assert.assertEquals(2,list.get(0).getShoppingListItemDeviceListItem().size());

        DeviceList listToAddItemTo = underTest.findAllForUser("Jim").get(0);
        DeviceItem newItem = new DeviceItem(listToAddItemTo.getIdOnDevice(),1L,"shampoo",false,"2");
        underTest.removeItemFromList(newItem);
        list = underTest.findAllForUser("Jim");
        Assert.assertEquals(1,list.get(0).getShoppingListItemDeviceListItem().size());
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