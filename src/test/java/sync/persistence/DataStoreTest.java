package sync.persistence;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sync.dto.server.ShoppingListServer;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DataStoreTest.Config.class})
public class DataStoreTest {

   @Autowired
   private DataStore underTest;

    @Test
    public void testMockedDataIsRetrieved() throws Exception {

        List<ShoppingListServer> returnedList = underTest.getMockedData();
        Assert.assertEquals(2,
                returnedList.size());

        //assert list 1
        Assert.assertEquals("Groceries",
                returnedList.get(0).getListName());
        Assert.assertTrue(returnedList.get(0).getIdOnDevice().equals(1L));
        Assert.assertEquals(2,
                returnedList.get(0).getShoppingListItemServerList().size());

        //assert item 1
        Assert.assertEquals("shampoo",
                returnedList.get(0).getShoppingListItemServerList().get(0).getItemName());
        Assert.assertEquals(returnedList.get(0).getIdOnDevice(),
                returnedList.get(0).getShoppingListItemServerList().get(0).getParentListId());
        Assert.assertEquals(false,
                returnedList.get(0).getShoppingListItemServerList().get(0).getMarkedAsChecked());
        Assert.assertEquals("2",
                returnedList.get(0).getShoppingListItemServerList().get(0).getQuantityDescription());

        //assert item 2
        Assert.assertEquals("conditioner",
                returnedList.get(0).getShoppingListItemServerList().get(1).getItemName());
        Assert.assertEquals(returnedList.get(0).getIdOnDevice(),
                returnedList.get(0).getShoppingListItemServerList().get(1).getParentListId());
        Assert.assertEquals(false,
                returnedList.get(0).getShoppingListItemServerList().get(1).getMarkedAsChecked());
        Assert.assertEquals("2",
                returnedList.get(0).getShoppingListItemServerList().get(1).getQuantityDescription());

        //assert list 2
        Assert.assertEquals("Fruit",
                returnedList.get(1).getListName());
        Assert.assertTrue(returnedList.get(0).getIdOnDevice().equals(1L));
        Assert.assertEquals(2,
                returnedList.get(1).getShoppingListItemServerList().size());

        //assert item 1
        Assert.assertEquals("apples",
                returnedList.get(1).getShoppingListItemServerList().get(0).getItemName());
        Assert.assertEquals(returnedList.get(1).getIdOnDevice(),
                returnedList.get(1).getShoppingListItemServerList().get(0).getParentListId());
        Assert.assertEquals(false,
                returnedList.get(1).getShoppingListItemServerList().get(0).getMarkedAsChecked());
        Assert.assertEquals("25",
                returnedList.get(1).getShoppingListItemServerList().get(0).getQuantityDescription());

        //assert item 2
        Assert.assertEquals("oranges",
                returnedList.get(1).getShoppingListItemServerList().get(1).getItemName());
        Assert.assertEquals(returnedList.get(1).getIdOnDevice(),
                returnedList.get(1).getShoppingListItemServerList().get(1).getParentListId());
        Assert.assertEquals(false,
                returnedList.get(1).getShoppingListItemServerList().get(1).getMarkedAsChecked());
        Assert.assertEquals("30",
                returnedList.get(1).getShoppingListItemServerList().get(1).getQuantityDescription());

    }

    @Configuration
    public static class Config{

        @Bean
        public DataStore getDataStore(){
            return new DataStore();
        }

    }
}