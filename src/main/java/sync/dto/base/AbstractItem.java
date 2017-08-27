/*
    Copyright of Ed.Co Enterprises
*/
package sync.dto.base;

/**
 * Abstract class for inheritance by the Server item and device Item
 *
 * @author tedward603@gmail.com
 */
public class AbstractItem {
    private Long parentListId;
    private Long itemId;
    private String itemName;
    private boolean isMarkedAsChecked;
    private String quantityDescription;

    public AbstractItem() {
    }

    public AbstractItem(Long parentListId, Long itemId, String itemName, boolean isMarkedAsChecked, String quantityDescription) {
        this.parentListId = parentListId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.isMarkedAsChecked = isMarkedAsChecked;
        this.quantityDescription = quantityDescription;
    }

    public Long getParentListId() {
        return parentListId;
    }

    public void setParentListId(Long parentListId) {
        this.parentListId = parentListId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean getMarkedAsChecked() {
        return isMarkedAsChecked;
    }

    public void setMarkedAsChecked(boolean markedAsChecked) {
        isMarkedAsChecked = markedAsChecked;
    }

    public String getQuantityDescription() {
        return quantityDescription;
    }

    public void setQuantityDescription(String quantityDescription) {
        this.quantityDescription = quantityDescription;
    }
}
