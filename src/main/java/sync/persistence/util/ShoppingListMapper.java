package sync.persistence.util;

import sync.dto.base.ServerSyncInfo;
import sync.dto.device.ShoppingListDevice;
import sync.dto.device.ShoppingListItemDevice;
import sync.dto.server.ShoppingListItemServer;
import sync.dto.server.ShoppingListServer;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListMapper {


    public List<ShoppingListDevice> mapServerToDeviceList(List<ShoppingListServer> serverShoppingListCollection){
        List<ShoppingListDevice> deviceShoppingListCollection = new ArrayList<ShoppingListDevice>();
        for (ShoppingListServer serverList: serverShoppingListCollection) {
            deviceShoppingListCollection.add(mapServerToDeviceListSingle(serverList));
        }
        return deviceShoppingListCollection;
    }

    public List<ShoppingListServer> mapDeviceToServerList(List<ShoppingListDevice> deviceShoppingListCollection){
        List<ShoppingListServer> serverShoppingListCollection = new ArrayList<ShoppingListServer>();
        for (ShoppingListDevice deviceList: deviceShoppingListCollection) {
            serverShoppingListCollection.add(mapDeviceToServerListSingle(deviceList));
        }
        return serverShoppingListCollection;
    }


    public ShoppingListDevice mapServerToDeviceListSingle(ShoppingListServer serverList){
        ShoppingListDevice shoppingListDevice =  new ShoppingListDevice(
                serverList.getIdOnDevice(),
                serverList.getListName(),
                serverList.getOwner());
        for (ShoppingListItemServer serverItem: serverList.getShoppingListItemServerList()) {
            shoppingListDevice.getShoppingListItemDeviceList().add(mapServerToDeviceItem(serverItem));
        }
        shoppingListDevice.setServerSyncInfo(new ServerSyncInfo(true,false,false));
        return shoppingListDevice;
    }

    public ShoppingListServer mapDeviceToServerListSingle(ShoppingListDevice deviceList){
        ShoppingListServer serverList = new ShoppingListServer(
                deviceList.getIdOnDevice(),
                deviceList.getListName(),
                deviceList.getOwner());
        for(ShoppingListItemDevice deviceItem: deviceList.getShoppingListItemDeviceList()){
            serverList.getShoppingListItemServerList().add(mapDeviceToServerItem(deviceItem));
        }
        return serverList;
    }

   public ShoppingListItemDevice mapServerToDeviceItem(ShoppingListItemServer serverItem){
       return new ShoppingListItemDevice(
               serverItem.getParentListId(),
               serverItem.getItemId(),
               serverItem.getItemName(),
               serverItem.getMarkedAsChecked(),
               serverItem.getQuantityDescription(),
               new ServerSyncInfo(true,false,false));
    }

    public ShoppingListItemServer mapDeviceToServerItem(ShoppingListItemDevice deviceItem){
        return new ShoppingListItemServer(
                deviceItem.getParentListId(),
                deviceItem.getItemId(),
                deviceItem.getItemName(),
                deviceItem.getMarkedAsChecked(),
                deviceItem.getQuantityDescription());
    }

}
