/*
    Copyright of Ed.Co Enterprises
*/
package sync.persistence.util;

import sync.dto.base.ServerSyncInfo;
import sync.dto.device.DeviceItem;
import sync.dto.device.DeviceList;
import sync.dto.server.ServerItem;
import sync.dto.server.ServerList;

/**
 * This Utility mapper is used for mapping back and forth between device items/list and server item/list
 *
 * @author tedward603@gmail.com
 */
public class ShoppingListMapper {

    public DeviceList mapServerToDeviceListSingle(ServerList serverList){
        DeviceList deviceList =  new DeviceList(
                serverList.getIdOnDevice(),
                serverList.getListName(),
                serverList.getOwner());
        for (ServerItem serverItem: serverList.getShoppingListItemServerListItem()) {
            deviceList.getShoppingListItemDeviceListItem().add(mapServerToDeviceItem(serverItem));
        }
        deviceList.setServerSyncInfo(new ServerSyncInfo(true,false,false));
        return deviceList;
    }

    public ServerList mapDeviceToServerListSingle(DeviceList deviceList){
        ServerList serverList = new ServerList(
                deviceList.getIdOnDevice(),
                deviceList.getListName(),
                deviceList.getOwner());
        for(DeviceItem deviceItem: deviceList.getShoppingListItemDeviceListItem()){
            serverList.getShoppingListItemServerListItem().add(mapDeviceToServerItem(deviceItem));
        }
        return serverList;
    }

   public DeviceItem mapServerToDeviceItem(ServerItem serverItem){
       return new DeviceItem(
               serverItem.getParentListId(),
               serverItem.getItemId(),
               serverItem.getItemName(),
               serverItem.getMarkedAsChecked(),
               serverItem.getQuantityDescription(),
               new ServerSyncInfo(true,false,false));
    }

    public ServerItem mapDeviceToServerItem(DeviceItem deviceItem){
        return new ServerItem(
                deviceItem.getParentListId(),
                deviceItem.getItemId(),
                deviceItem.getItemName(),
                deviceItem.getMarkedAsChecked(),
                deviceItem.getQuantityDescription());
    }

}
