package sync.dto.device;

import sync.dto.base.AbstractShoppingListItem;
import sync.dto.base.ServerSyncInfo;
import sync.dto.base.ShoppingList;
import sync.dto.server.ShoppingListServer;

public class ShoppingListItemDevice extends AbstractShoppingListItem {

    private ServerSyncInfo serverSyncInfo;

    public ShoppingListItemDevice() {
    }

    public ShoppingListItemDevice(Long parentListId,Long itemId, String itemName, boolean isMarkedAsChecked, String quantityDescription, ServerSyncInfo info) {
        super(parentListId,itemId, itemName, isMarkedAsChecked, quantityDescription);
        this.serverSyncInfo = info;
    }

    public ShoppingListItemDevice(Long parentListId,Long itemId, String itemName, boolean isMarkedAsChecked, String quantityDescription) {
        super(parentListId,itemId, itemName, isMarkedAsChecked, quantityDescription);
    }

    public ServerSyncInfo getInfo() {
        return serverSyncInfo;
    }

    public void setInfo(ServerSyncInfo serverSyncInfo) {
        this.serverSyncInfo = serverSyncInfo;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if (!(obj instanceof ShoppingListItemDevice)){
            return false;
        }
        ShoppingListItemDevice item = (ShoppingListItemDevice) obj;
        return item.getItemId().equals(this.getItemId());
    }
}
