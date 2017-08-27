package sync.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import sync.dto.device.ShoppingListDevice;
import sync.dto.device.ShoppingListItemDevice;
import sync.dto.server.ShoppingListItemServer;
import sync.dto.server.ShoppingListServer;
import sync.persistence.util.ShoppingListMapper;

import java.util.ArrayList;
import java.util.List;

public class DataStoreManager {

    @Autowired
    ShoppingListMapper mapper;

    @Autowired
    DataStore dataStore;

    public List<ShoppingListServer> globalServerList;

    public void init(){
        globalServerList = dataStore.getMockedData();
    }

    /**
     * this is mock for dividing up the lists to different user,
     * in a real program this would look into the users table and divide the tables based on that
     * but for this mock this will find with a specific user one set of data and
     * for another the both sets.
     * @param user the static user that I am using
     */
    public List<ShoppingListDevice> findAllForUser(String user){
        List<ShoppingListDevice> deviceList = new ArrayList<ShoppingListDevice>();
        for(ShoppingListServer serverList : globalServerList){
            if(user.equals(serverList.getOwner())){
                deviceList.add(mapper.mapServerToDeviceListSingle(serverList));
            }
        }
        return deviceList;
    }

    /**
     * Adds a new Entry into the globalServerList
     * @param shoppingListDevice
     */
    public void addShoppingList(ShoppingListDevice shoppingListDevice) {
        detectAndResolveAddConflict(shoppingListDevice);
        globalServerList.add(mapper.mapDeviceToServerListSingle(shoppingListDevice));
    }

    /**
     * If I was using a real database I would put in some means of checking
     * for a conflicting id before it went back to saving the newly added
     * List, but for now this isn't in scope.
     * @param shoppingListDevice the newly added Item
     */
    private void detectAndResolveAddConflict(ShoppingListDevice shoppingListDevice) {
      //TODO create in future when in scope
    }

    public void updateShoppingList(ShoppingListDevice shoppingListDevice) {
        globalServerList.remove(findById(shoppingListDevice.getIdOnDevice()));
        globalServerList.add(mapper.mapDeviceToServerListSingle(shoppingListDevice));
    }

    public void removeShoppingList(ShoppingListDevice shoppingListDevice) {
        globalServerList.remove(findById(shoppingListDevice.getIdOnDevice()));
    }

    public void addItemToList(ShoppingListItemDevice itemDevice) {
        ///map item
        ShoppingListItemServer serverItem = mapper.mapDeviceToServerItem(itemDevice);
        //find server list with item
        ShoppingListServer serverList = findById(serverItem.getParentListId());
        //TODO resolve conflict

        List<ShoppingListItemServer> list = serverList.getShoppingListItemServerList();
        list.add(serverItem);
    }

    public void updateItem(ShoppingListItemDevice itemDevice) {
        //find serverList in global
        ShoppingListServer serverList = findById(itemDevice.getParentListId());

        // find item in global
        ShoppingListItemServer serverItem = findItemById(itemDevice.getItemId(), serverList.getIdOnDevice());

        if(serverItem!=null) {
            // remove old item
            serverList.getShoppingListItemServerList().remove(serverItem);

            // add new item
            serverList.getShoppingListItemServerList().add(mapper.mapDeviceToServerItem(itemDevice));
            //TODO fix ordering of items by ID
        }
    }

    public void removeItemFromList(ShoppingListItemDevice itemDevice) {
        //find serverList in global
        ShoppingListServer serverList = findById(itemDevice.getParentListId());

        // find item in global
        ShoppingListItemServer serverItem = findItemById(itemDevice.getItemId(), serverList.getIdOnDevice());
        if(serverItem!=null) {
            // remove old item
            serverList.getShoppingListItemServerList().remove(serverItem);
        }
    }


    /**
     * Both of these method would not need to exist if i was using an unmocked database,
     * i would use some query to find them instead
     * @param shoppingListDeviceID the id to find the list
     * @return
     */
    public ShoppingListServer findById(Long shoppingListDeviceID){
        for(ShoppingListServer serverList : globalServerList){
            if(serverList.getIdOnDevice().equals(shoppingListDeviceID)){
                return serverList;
            }
        }
        return null;
    }


    /**
     *
     * @param itemId
     * @param parentId
     * @return
     */
    public ShoppingListItemServer findItemById(Long parentId,Long itemId) {
        for (ShoppingListServer serverList : globalServerList) {
            if (serverList.getIdOnDevice().equals(parentId)) {
                for (ShoppingListItemServer serverItem : serverList.getShoppingListItemServerList()) {
                    if (serverItem.getItemId().equals(itemId)) {
                        return serverItem;
                    }
                }
            }
        }
        return null;
    }
}
