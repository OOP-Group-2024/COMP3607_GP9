package comp3607;

import java.io.File;
import java.util.Map;

public interface IteratorInterface {
    public boolean hasNext();

    public Map<String, File> next();

}
