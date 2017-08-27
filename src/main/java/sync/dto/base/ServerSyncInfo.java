package sync.dto.base;

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
