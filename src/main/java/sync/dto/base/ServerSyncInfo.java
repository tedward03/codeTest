/*
    Copyright of Ed.Co Enterprises
*/
package sync.dto.base;

/**
 *  this DTO is used for storing the information taken in from the device ,and the default conditions sent out,
 * this object hold the state of each of the items and list in the program
 *
 * @author tedward603@gmail.com
 */
public class ServerSyncInfo {

    private boolean isServerCopy;
    private boolean isLocallyModified;
    private boolean isLocallyDeleted;

    public ServerSyncInfo() {
    }

    public ServerSyncInfo(boolean isServerCopy, boolean isLocallyModified, boolean isLocallyDeleted) {
        this.isServerCopy = isServerCopy;
        this.isLocallyModified = isLocallyModified;
        this.isLocallyDeleted = isLocallyDeleted;
    }

    public boolean isServerCopy() {
        return isServerCopy;
    }

    public void setServerCopy(boolean serverCopy) {
        isServerCopy = serverCopy;
    }

    public boolean isLocallyModified() {
        return isLocallyModified;
    }

    public void setLocallyModified(boolean locallyModified) {
        isLocallyModified = locallyModified;
    }

    public boolean isLocallyDeleted() {
        return isLocallyDeleted;
    }

    public void setLocallyDeleted(boolean locallyDeleted) {
        isLocallyDeleted = locallyDeleted;
    }

}
