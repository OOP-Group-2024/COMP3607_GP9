package comp3607;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipIterator implements IteratorInterface {
    private final String mainZipPath;
    private Iterator<File> subZipIterator;
 

    public ZipIterator(String mainZipPath) {
        this.mainZipPath = mainZipPath;
        unzipMainFolder();
    }

    private void unzipMainFolder() {
        File mainZipFile = new File(mainZipPath);
        File tempDir = new File(mainZipFile.getParent(), "unzippedMainFolder");

        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(mainZipFile))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().endsWith(".zip")) {
                    // Extract the sub-zip files
                    File subZipFile = new File(tempDir, entry.getName());
                    try (FileOutputStream fos = new FileOutputStream(subZipFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create an iterator for the sub-zips in the extracted folder
        File[] subZips = tempDir.listFiles((dir, name) -> name.endsWith(".zip"));
        if (subZips != null) {
            subZipIterator = Arrays.asList(subZips).iterator();
        }
    }

    @Override
    public boolean hasNext() {
        return subZipIterator != null && subZipIterator.hasNext();
    }

    @Override
    public Map<String, Class<?>> next() {
        File subZip = subZipIterator.next();
        return loadClassesFromSubZip(subZip);
    }

    private Map<String, Class<?>> loadClassesFromSubZip(File subZip) {
        Map<String, Class<?>> classes = new HashMap<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(subZip))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().endsWith(".java")) {
                    // Extract Java file and load it as a Class
                    File tempJavaFile = new File(subZip.getParent(), entry.getName());
                    try (FileOutputStream fos = new FileOutputStream(tempJavaFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                    // Load the class from the Java file
                    try {
                        String className = tempJavaFile.getName().replace(".java", "");
                        Class<?> clazz = Class.forName(className);
                        classes.put(className, clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    tempJavaFile.delete(); // Clean up the temporary Java file
                }
                zipInputStream.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }
}

