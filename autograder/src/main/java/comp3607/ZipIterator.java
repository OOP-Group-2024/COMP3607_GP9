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

    private Map<String, File> extractJavaFilesFromSubZip(File subZip) {
        Map<String, File> javaFiles = new HashMap<>();
        File tempDir = new File(subZip.getParent(), subZip.getName().replace(".zip", ""));

        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        // Unzip the sub-zip to a temporary directory
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(subZip))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().endsWith(".java")) {
                    // Extract Java file
                    //String javaFileName = entry.getName().substring(0, entry.getName().lastIndexOf(".java"));
                    File javaFile = new File(tempDir, entry.getName().replace(".java", ""));
                    try (FileOutputStream fos = new FileOutputStream(javaFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                    javaFiles.put(entry.getName().replace(".java", ""), javaFile);
                }
                zipInputStream.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return javaFiles;
    }

    @Override
    public boolean hasNext() {
        return subZipIterator != null && subZipIterator.hasNext();
    }

    @Override
    public Map<String, File> next() {
        File subZip = subZipIterator.next();
        return extractJavaFilesFromSubZip(subZip);
    }
}
