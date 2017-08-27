/*
    Copyright of Ed.Co Enterprises
*/
package sync.dto.server;

import sync.dto.base.AbstractList;
import sync.dto.base.ShoppingList;

import java.util.ArrayList;
import java.util.List;

/**
 * This is used as the persistence side of the program for the shopping List,
 * i.e. this object would be the representation of the Table on the server side
 *
 * @author tedward603@gmail.com
 */
public class ServerList extends AbstractList implements ShoppingList {

    private List<ServerItem> shoppingListItemServerListItem;

    public ServerList() {
    }

    public ServerList(Long idOnDevice, String listName, String owner, List<ServerItem> shoppingListItemServerListItem) {
        super(idOnDevice, listName,owner);
        this.shoppingListItemServerListItem = shoppingListItemServerListItem;
    }

    public ServerList(Long idOnDevice, String listName, String owner) {
        super(idOnDevice, listName,owner);
        this.shoppingListItemServerListItem = new ArrayList<ServerItem>();
    }

    public List<ServerItem> getShoppingListItemServerListItem() {
        return shoppingListItemServerListItem;
    }

    public void setShoppingListItemServerListItem(List<ServerItem> shoppingListItemServerListItem) {
        this.shoppingListItemServerListItem = shoppingListItemServerListItem;
    }

}
