package unsw.archaic_fs.exceptions;

import java.util.List;

public class UNSWFileNotFoundException extends java.io.FileNotFoundException {

    public UNSWFileNotFoundException() {
    }
 
    public UNSWFileNotFoundException(String s) {
       super(s);
    }
 
    private UNSWFileNotFoundException(String path, String reason) {
       super(path + (reason == null ? "" : " (" + reason + ")"));
    }
}
