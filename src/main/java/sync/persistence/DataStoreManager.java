/*
    Copyright of Ed.Co Enterprises
*/
package sync.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import sync.dto.device.DeviceItem;
import sync.dto.device.DeviceList;
import sync.dto.server.ServerItem;
import sync.dto.server.ServerList;
import sync.persistence.util.ShoppingListMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the the datastore and crud actions there in.
 *
 * @author tedward603@gmail.com
 */
public class DataStoreManager {

    @Autowired
    ShoppingListMapper mapper;

    @Autowired
    DataStore dataStore;

    public List<ServerList> globalServerList;

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
    public List<DeviceList> findAllForUser(String user){
        List<DeviceList> deviceList = new ArrayList<DeviceList>();
        for(ServerList serverList : globalServerList){
            if(user.equals(serverList.getOwner())){
                deviceList.add(mapper.mapServerToDeviceListSingle(serverList));
            }
        }
        return deviceList;
    }

    /**
     * Adds a new Entry into the globalServerList
     * @param deviceList
     */
    public void addShoppingList(DeviceList deviceList) {
        detectAndResolveAddConflict(deviceList);
        globalServerList.add(mapper.mapDeviceToServerListSingle(deviceList));
    }

    /**
     * If I was using a real database I would put in some means of checking
     * for a conflicting id before it went back to saving the newly added
     * List, but for now this isn't in scope.
     * @param deviceList the newly added Item
     */
    private void detectAndResolveAddConflict(DeviceList deviceList) {
      //TODO create in future when in scope
    }

    /**
     * Updates the globalServerList with the new device incoming
     * @param deviceList the new incoming Device List
     */
    public void updateShoppingList(DeviceList deviceList) {
        globalServerList.remove(findById(deviceList.getIdOnDevice()));
        globalServerList.add(mapper.mapDeviceToServerListSingle(deviceList));
    }

    /**
     * Removes the shopping list from global server list
     * @param deviceList the new incoming device list
     */
    public void removeShoppingList(DeviceList deviceList) {
        globalServerList.remove(findById(deviceList.getIdOnDevice()));
    }

    /**
     * Adds a new item to the list in the server
     * @param itemDevice the new incoming item
     */
    public void addItemToList(DeviceItem itemDevice) {
        ///map item
        ServerItem serverItem = mapper.mapDeviceToServerItem(itemDevice);
        //find server list with item
        ServerList serverList = findById(serverItem.getParentListId());
        //TODO resolve conflict

        List<ServerItem> list = serverList.getShoppingListItemServerListItem();
        list.add(serverItem);
    }

    /**
     * updates an already existing item in the list.
     * @param itemDevice the updated item from the device
     */
    public void updateItem(DeviceItem itemDevice) {
        //find serverList in global
        ServerList serverList = findById(itemDevice.getParentListId());
        // find item in global
        ServerItem serverItem = findItemById(itemDevice.getItemId(), serverList.getIdOnDevice());
        if(serverItem!=null) {
            // remove old item
            serverList.getShoppingListItemServerListItem().remove(serverItem);
            // add new item
            serverList.getShoppingListItemServerListItem().add(mapper.mapDeviceToServerItem(itemDevice));
            //TODO fix ordering of items by ID
        }
    }

    /**
     * remove an item from an already existing list
     * @param itemDevice
     */
    public void removeItemFromList(DeviceItem itemDevice) {
        //find serverList in global
        ServerList serverList = findById(itemDevice.getParentListId());
        // find item in global
        ServerItem serverItem = findItemById(itemDevice.getItemId(), serverList.getIdOnDevice());
        if(serverItem!=null) {
            // remove old item
            serverList.getShoppingListItemServerListItem().remove(serverItem);
        }
    }


    /**
     * Both of these method would not need to exist if i was using an unmocked database,
     * i would use some query to find them instead
     * @param shoppingListDeviceID the id to find the list
     * @return the List found from the server objects
     */
    //TODO DELETE MOCK
    public ServerList findById(Long shoppingListDeviceID){
        for(ServerList serverList : globalServerList){
            if(serverList.getIdOnDevice().equals(shoppingListDeviceID)){
                return serverList;
            }
        }
        return null;
    }


    /**
     * Used to find an item in a list in the server items
     * @param itemId the item id from the needed item
     * @param parentId the parent id of the needed item
     * @return the server item found from the parameters
     */
    //TODO DELETE MOCK
    public ServerItem findItemById(Long parentId, Long itemId) {
        for (ServerList serverList : globalServerList) {
            if (serverList.getIdOnDevice().equals(parentId)) {
                for (ServerItem serverItem : serverList.getShoppingListItemServerListItem()) {
                    if (serverItem.getItemId().equals(itemId)) {
                        return serverItem;
                    }
                }
            }
        }
        return null;
    }
}
