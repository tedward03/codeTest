package sync.dto.base;

public class AbstractShoppingList implements ShoppingList {
    private Long idOnDevice;
    private String listName;
    private String owner;

    public AbstractShoppingList() {
    }

    public AbstractShoppingList(Long idOnDevice, String listName,String owner) {
        this.idOnDevice = idOnDevice;
        this.listName = listName;
        this.owner = owner;
    }

    public Long getIdOnDevice() {
        return idOnDevice;
    }

    public void setIdOnDevice(Long idOnDevice) {
        this.idOnDevice = idOnDevice;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
