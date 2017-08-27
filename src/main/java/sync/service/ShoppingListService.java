package sync.service;

import org.springframework.beans.factory.annotation.Autowired;
import sync.dto.device.ShoppingListDevice;
import sync.dto.device.ShoppingListItemDevice;
import sync.persistence.DataStoreManager;

import java.util.List;

public class ShoppingListService {

    @Autowired
    DataStoreManager dataStoreManager;

    public List<ShoppingListDevice> syncDeviceLists(List<ShoppingListDevice> shoppingListDeviceList,String user){
        dataStoreManager.init();
        resolveLists(shoppingListDeviceList,user);
        return dataStoreManager.findAllForUser(user);
    }

    private void resolveLists(List<ShoppingListDevice> newShoppingListDeviceList,String user){
        for(ShoppingListDevice shoppingListDevice:newShoppingListDeviceList){
            if(!dataStoreManager.findAllForUser(user).contains(shoppingListDevice)){
                dataStoreManager.addShoppingList(shoppingListDevice);
            }else if(!shoppingListDevice.getServerSyncInfo().isServerCopy() && shoppingListDevice.getServerSyncInfo().isLocallyModified()){
                dataStoreManager.updateShoppingList(shoppingListDevice);
            }
            resolveItems(shoppingListDevice);
            if(shoppingListDevice.getServerSyncInfo().isLocallyDeleted()){
                dataStoreManager.removeShoppingList(shoppingListDevice);
            }

        }
    }

    private void resolveItems(ShoppingListDevice incomingShoppingListDevice) {
        for(ShoppingListItemDevice itemDevice : incomingShoppingListDevice.getShoppingListItemDeviceList()) {
            if (dataStoreManager.findItemById(itemDevice.getParentListId(), itemDevice.getItemId()) == null) {
                dataStoreManager.addItemToList(itemDevice);
            }else if(!itemDevice.getInfo().isServerCopy() && itemDevice.getInfo().isLocallyModified()){
                dataStoreManager.updateItem(itemDevice);
            }else if(itemDevice.getInfo().isLocallyDeleted()){
                dataStoreManager.removeItemFromList(itemDevice);
            }
        }
    }
}
