/*
    Copyright of Ed.Co Enterprises
*/
package sync.service;

import org.springframework.beans.factory.annotation.Autowired;
import sync.dto.device.DeviceItem;
import sync.dto.device.DeviceList;
import sync.persistence.DataStoreManager;

import java.util.List;

/**
 * Service used for The endpoints of the ShoppingList controller
 *
 * @author tedward603@gmail.com
 */
public class ShoppingListService {

    @Autowired
    DataStoreManager dataStoreManager;

    /**
     * Used to all for synchronizing up the list for a specific user , with their input that they send in.
     * @param list the list that device has sent
     * @param user the user that that list belongs to
     * @return the modified server list converted to a device list
     */
    public List<DeviceList> syncDeviceLists(List<DeviceList> list, String user){
        dataStoreManager.init();
        resolveLists(list,user);
        return dataStoreManager.findAllForUser(user);
    }

    /**
     * Used to resolve the device list objects into a server list,
     * using the sync information on each object it decides what to do with each item,
     * either to leave it alone,
     * add it to the users lists,
     * modify some bit of information about it.
     * of delete it on request from the device
     *
     * @param newList the list sent by the device
     * @param user the user that sent the list
     */
    private void resolveLists(List<DeviceList> newList, String user){
        for(DeviceList deviceList : newList){
            //add a list
            if(!dataStoreManager.findAllForUser(user).contains(deviceList)){
                dataStoreManager.addShoppingList(deviceList);
            }
            //modify an already existing list
            else if(!deviceList.getServerSyncInfo().isServerCopy() && deviceList.getServerSyncInfo().isLocallyModified()){
                dataStoreManager.updateShoppingList(deviceList);
            }
            //check items
            resolveItems(deviceList);

            //delete a list
            if(deviceList.getServerSyncInfo().isLocallyDeleted()){
                dataStoreManager.removeShoppingList(deviceList);
            }

        }
    }

    /**
     * Resolves what to do with each item that comes through in each list
     * if it needs to be added to an already existing list
     * if it needs to be modified
     * if it needs to be deleted from a list
     * @param incomingDeviceList the list with a ll the items.
     */
    private void resolveItems(DeviceList incomingDeviceList) {
        for(DeviceItem itemDevice : incomingDeviceList.getShoppingListItemDeviceListItem()) {
            //add
            if (dataStoreManager.findItemById(itemDevice.getParentListId(), itemDevice.getItemId()) == null) {
                dataStoreManager.addItemToList(itemDevice);
            }
            //modify
            else if(!itemDevice.getInfo().isServerCopy() && itemDevice.getInfo().isLocallyModified()){
                dataStoreManager.updateItem(itemDevice);
            }
            //delete
            else if(itemDevice.getInfo().isLocallyDeleted()){
                dataStoreManager.removeItemFromList(itemDevice);
            }
        }
    }
}
