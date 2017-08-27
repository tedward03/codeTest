package sync.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sync.dto.base.ServerSyncInfo;
import sync.dto.device.DeviceItem;
import sync.dto.device.DeviceList;
import sync.persistence.DataStoreManagerTest;
import sync.persistence.DataStoreTest;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={
        ShoppingListServiceComponentTest.Config.class,
        DataStoreManagerTest.Config.class,
        DataStoreTest.Config.class})
public class ShoppingListServiceComponentTest {

    @Autowired
    ShoppingListService underTest;

    @Test
    public void syncDeviceListsCase0NoChanges() throws Exception {
        //when
        underTest.dataStoreManager.init();
        List<DeviceList> previousList = underTest.dataStoreManager.findAllForUser("Jim");
        Assert.assertEquals(1,previousList.size());
        List<DeviceList> returnedList = underTest.syncDeviceLists(previousList,"Jim");

        //then
        Assert.assertEquals(1,returnedList.size());
        Assert.assertEquals(2,returnedList.get(0).getShoppingListItemDeviceListItem().size());

        //also if i don't have one and I haven't explicitly deleted it it should still come back
        previousList.remove(0);
        returnedList = underTest.syncDeviceLists(previousList,"Jim");
        Assert.assertEquals(1,returnedList.size());
        Assert.assertEquals(2,returnedList.get(0).getShoppingListItemDeviceListItem().size());

    }

    @Test
    public void syncDeviceListsCase1AddListAndItem() throws Exception {
        //before
        underTest.dataStoreManager.init();
        List<DeviceList> previousList = underTest.dataStoreManager.findAllForUser("Jim");


        DeviceList newDeviceShoppingList = new DeviceList(33L,"newList","Jim",new ServerSyncInfo(false,true,false),new ArrayList<DeviceItem>());
        //with this
        previousList.add(newDeviceShoppingList);
        DeviceItem newDeviceItem = new DeviceItem(1L,134L,"soap",false,"34",new ServerSyncInfo(false,true,false));

        //and more
        previousList.get(0).getShoppingListItemDeviceListItem().add(newDeviceItem);
        List<DeviceList> changedList = underTest.syncDeviceLists(previousList,"Jim");

        Assert.assertEquals(2,changedList.size());
        Assert.assertEquals("soap",changedList.get(0).getShoppingListItemDeviceListItem().get(2).getItemName());
    }

    @Test
    public void syncDeviceListsCase3UpdateListAndItem() throws Exception {
        //first
        underTest.dataStoreManager.init();
        List<DeviceList> previousList = underTest.dataStoreManager.findAllForUser("Jim");
        String previousValue = previousList.get(0).getListName();

        //with just this
        previousList.get(0).setListName("Whateveriwant");
        // when
        List<DeviceList> returnedList = underTest.syncDeviceLists(previousList,"Jim");
        //then nothing
        Assert.assertEquals(previousValue,returnedList.get(0).getListName());

        //but with also this
        previousList.get(0).setServerSyncInfo(new ServerSyncInfo(false,true,false));
        //when
        returnedList = underTest.syncDeviceLists(previousList,"Jim");
        //then yes
        Assert.assertEquals("Whateveriwant",returnedList.get(0).getListName());

        //more

        // with just this
        returnedList.get(0).getShoppingListItemDeviceListItem().get(0).setItemName("thisDoesntMatter");
        // when
        returnedList = underTest.syncDeviceLists(returnedList,"Jim");
        //then nothing
        Assert.assertNotEquals("thisDoesntMatter",returnedList.get(0).getShoppingListItemDeviceListItem().get(0).getItemName());

        //with this
        returnedList.get(0).getShoppingListItemDeviceListItem().get(0).setItemName("thisDoesntMatter");
        //and this
        returnedList.get(0).getShoppingListItemDeviceListItem().get(0).setInfo(new ServerSyncInfo(false,true,false));
        //when
        returnedList = underTest.syncDeviceLists(returnedList,"Jim");
        //then this
        Assert.assertEquals("thisDoesntMatter",returnedList.get(0).getShoppingListItemDeviceListItem().get(1).getItemName());

    }

    @Test
    public void syncDeviceListsCase2removeListAndItem() throws Exception {
        //first
        underTest.dataStoreManager.init();
        List<DeviceList> returnedList = underTest.dataStoreManager.findAllForUser("Jim");
        Assert.assertEquals(1,returnedList.size());
        //if
        returnedList.remove(0);
        //when
        returnedList = underTest.syncDeviceLists(returnedList,"Jim");
        //then nothing
        Assert.assertEquals(1,returnedList.size());

        //but if
        returnedList.get(0).setServerSyncInfo(new ServerSyncInfo(false,false,true));
        //when
        returnedList = underTest.syncDeviceLists(returnedList,"Jim");
        //then
        Assert.assertEquals(0,returnedList.size());

        //but also
        //first
        underTest.dataStoreManager.init();
        returnedList = underTest.dataStoreManager.findAllForUser("Jim");
        Assert.assertEquals(1,returnedList.size());
        //if
        returnedList.get(0).getShoppingListItemDeviceListItem().remove(0);
        //when
        returnedList = underTest.syncDeviceLists(returnedList,"Jim");
        //then nothing
        Assert.assertEquals(2,returnedList.get(0).getShoppingListItemDeviceListItem().size());

        //but wait if
        returnedList.get(0).getShoppingListItemDeviceListItem().get(0).setInfo(new ServerSyncInfo(false,false,true));
        //when
        returnedList = underTest.syncDeviceLists(returnedList,"Jim");
        //then
        Assert.assertEquals(1,returnedList.get(0).getShoppingListItemDeviceListItem().size());

    }

    @Configuration
    public static class Config{

        @Bean
        public ShoppingListService shoppingListService(){
            return new ShoppingListService();
        }

    }
}