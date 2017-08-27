/*
    Copyright of Ed.Co Enterprises
*/
package sync.dto.server;

import sync.dto.base.AbstractItem;
import sync.dto.base.ShoppingList;

/**
 * This is used as the persistence side of the program for the shopping item,
 * i.e. this object would be the representation of the Table on the server side
 *
 * @author tedward603@gmail.com
 */
public class ServerItem extends AbstractItem implements ShoppingList{

    public ServerItem() {
    }

    public ServerItem(Long parentListId, Long itemId, String itemName, boolean isMarkedAsChecked, String quantityDescription) {
        super(parentListId,itemId, itemName, isMarkedAsChecked, quantityDescription);
    }

}
