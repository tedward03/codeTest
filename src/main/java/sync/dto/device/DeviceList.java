/*
    Copyright of Ed.Co Enterprises
*/
package sync.dto.device;

import sync.dto.base.AbstractList;
import sync.dto.base.ServerSyncInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * this is a DTO used for the device side of the program for the list, this object would be stored on the device,
 * i.e. this object should never be persisted with its current information
 *
 * @author tedward603@gmail.com
 */
public class DeviceList extends AbstractList {

    private ServerSyncInfo serverSyncInfo;
    private List<DeviceItem> shoppingListItemDeviceListItem;

    public DeviceList() {
    }

    public DeviceList(Long idOnDevice, String listName, String owner, ServerSyncInfo serverSyncInfo, List<DeviceItem> shoppingListItemDeviceListItem) {
        super(idOnDevice, listName,owner);
        this.serverSyncInfo = serverSyncInfo;
        this.shoppingListItemDeviceListItem = shoppingListItemDeviceListItem;
    }

    public DeviceList(Long idOnDevice, String listName, String owner) {
        super(idOnDevice, listName, owner);
        this.shoppingListItemDeviceListItem = new ArrayList<DeviceItem>();
    }


    public ServerSyncInfo getServerSyncInfo() {
        return serverSyncInfo;
    }

    public void setServerSyncInfo(ServerSyncInfo serverSyncInfo) {
        this.serverSyncInfo = serverSyncInfo;
    }

    public List<DeviceItem> getShoppingListItemDeviceListItem() {
        return shoppingListItemDeviceListItem;
    }

    public void setShoppingListItemDeviceListItem(List<DeviceItem> shoppingListItemDeviceListItem) {
        this.shoppingListItemDeviceListItem = shoppingListItemDeviceListItem;
    }

    /**
     * I modified this so that an object can equal another one when its id is the same.
     * i would not have to put this in is a was using a proper database
     * So Its marked as MOCK
     * @param obj the left side object of the equating
     * @return if they are "the same"
     */
    //TODO MOCK DELETE
    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if (!(obj instanceof DeviceList)){
            return false;
        }
        DeviceList list = (DeviceList) obj;
        return list.getIdOnDevice().equals(this.getIdOnDevice());
    }
}
