/*
    Copyright of Ed.Co Enterprises
*/
package sync.dto.device;

import sync.dto.base.AbstractItem;
import sync.dto.base.ServerSyncInfo;

/**
 * this is a DTO used for the device side of the program ofr the shopping item, this would be stored on the device,
 * i.e. this object should never be persisted with its current information
 *
 *@author tedward603@gmail.com
 */
public class DeviceItem extends AbstractItem {

    private ServerSyncInfo serverSyncInfo;

    public DeviceItem() {
    }

    public DeviceItem(Long parentListId, Long itemId, String itemName, boolean isMarkedAsChecked, String quantityDescription, ServerSyncInfo info) {
        super(parentListId,itemId, itemName, isMarkedAsChecked, quantityDescription);
        this.serverSyncInfo = info;
    }

    public DeviceItem(Long parentListId, Long itemId, String itemName, boolean isMarkedAsChecked, String quantityDescription) {
        super(parentListId,itemId, itemName, isMarkedAsChecked, quantityDescription);
    }

    public ServerSyncInfo getInfo() {
        return serverSyncInfo;
    }

    public void setInfo(ServerSyncInfo serverSyncInfo) {
        this.serverSyncInfo = serverSyncInfo;
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
        if (!(obj instanceof DeviceItem)){
            return false;
        }
        DeviceItem item = (DeviceItem) obj;
        return item.getItemId().equals(this.getItemId());
    }
}
