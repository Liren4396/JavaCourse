package unsw.archaic_fs.exceptions;

public class UNSWFileAlreadyExistsException extends java.nio.file.FileAlreadyExistsException {
    public UNSWFileAlreadyExistsException(String file) {
        super(file);
    }
  
    public UNSWFileAlreadyExistsException(String file, String other, String reason) {
        super(file, other, reason);
    }
}
