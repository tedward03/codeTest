package sync.dto.device;

import sync.dto.base.AbstractShoppingList;
import sync.dto.base.ServerSyncInfo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDevice extends AbstractShoppingList {

    private ServerSyncInfo serverSyncInfo;
    private List<ShoppingListItemDevice> shoppingListItemDeviceList;

    public ShoppingListDevice() {
    }

    public ShoppingListDevice(Long idOnDevice, String listName,String owner, ServerSyncInfo serverSyncInfo, List<ShoppingListItemDevice> shoppingListItemDeviceList) {
        super(idOnDevice, listName,owner);
        this.serverSyncInfo = serverSyncInfo;
        this.shoppingListItemDeviceList = shoppingListItemDeviceList;
    }

    public ShoppingListDevice(Long idOnDevice, String listName,String owner) {
        super(idOnDevice, listName, owner);
        this.shoppingListItemDeviceList = new ArrayList<ShoppingListItemDevice>();
    }


    public ServerSyncInfo getServerSyncInfo() {
        return serverSyncInfo;
    }

    public void setServerSyncInfo(ServerSyncInfo serverSyncInfo) {
        this.serverSyncInfo = serverSyncInfo;
    }

    public List<ShoppingListItemDevice> getShoppingListItemDeviceList() {
        return shoppingListItemDeviceList;
    }

    public void setShoppingListItemDeviceList(List<ShoppingListItemDevice> shoppingListItemDeviceList) {
        this.shoppingListItemDeviceList = shoppingListItemDeviceList;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if (!(obj instanceof ShoppingListDevice)){
            return false;
        }
        ShoppingListDevice list = (ShoppingListDevice) obj;
        return list.getIdOnDevice().equals(this.getIdOnDevice());
    }
}
