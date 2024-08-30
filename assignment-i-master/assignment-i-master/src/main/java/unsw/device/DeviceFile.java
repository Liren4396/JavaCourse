package unsw.device;

public class DeviceFile {
    private String content;
    private String filename;
    private int size;
    private boolean isComplete;
    public DeviceFile(String content, int size, String filename, boolean isComplete) {
        this.content = content;
        this.filename = filename;
        this.size = size;
        this.isComplete = isComplete;
    }
    public DeviceFile(String content, String filename) {
        this.content = content;
        this.filename = filename;
        this.size = content.length();
        this.isComplete = false;
    }
    public String getContent() {
        return content;
    }
    public String getFilename() {
        return filename;
    }
    public int getSize() {
        return size;
    }
    public boolean getIsComplete() {
        return isComplete;
    }
    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String toString() {
        return filename + ":" + content + ":" + size + ":" + isComplete;
    }
}
