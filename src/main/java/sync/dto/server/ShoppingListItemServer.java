package sync.dto.server;

import sync.dto.base.AbstractShoppingListItem;
import sync.dto.base.ShoppingList;
import sync.dto.device.ShoppingListDevice;

public class ShoppingListItemServer extends AbstractShoppingListItem implements ShoppingList{

    public ShoppingListItemServer() {
    }

    public ShoppingListItemServer(Long parentListId,Long itemId, String itemName, boolean isMarkedAsChecked, String quantityDescription) {
        super(parentListId,itemId, itemName, isMarkedAsChecked, quantityDescription);
    }

}
