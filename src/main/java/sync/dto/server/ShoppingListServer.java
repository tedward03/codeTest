package sync.dto.server;

import sync.dto.base.AbstractShoppingList;
import sync.dto.base.ShoppingList;
import sync.dto.device.ShoppingListDevice;
import sync.dto.device.ShoppingListItemDevice;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class ShoppingListServer extends AbstractShoppingList implements ShoppingList {

    private List<ShoppingListItemServer> shoppingListItemServerList;

    public ShoppingListServer() {
    }

    public ShoppingListServer(Long idOnDevice, String listName,String owner, List<ShoppingListItemServer> shoppingListItemServerList) {
        super(idOnDevice, listName,owner);
        this.shoppingListItemServerList = shoppingListItemServerList;
    }

    public ShoppingListServer(Long idOnDevice, String listName,String owner) {
        super(idOnDevice, listName,owner);
        this.shoppingListItemServerList = new ArrayList<ShoppingListItemServer>();
    }

    public List<ShoppingListItemServer> getShoppingListItemServerList() {
        return shoppingListItemServerList;
    }

    public void setShoppingListItemServerList(List<ShoppingListItemServer> shoppingListItemServerList) {
        this.shoppingListItemServerList = shoppingListItemServerList;
    }

}
