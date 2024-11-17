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
    private Map<File, String> zipStudentIds; // Store student IDs for each zip

    public ZipIterator(String mainZipPath) {
        this.mainZipPath = mainZipPath;
        this.zipStudentIds = new HashMap<>();
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
                    
                    // Store the student ID for this zip file
                    String studentId = DirectoryUtils.getStudentId(entry.getName());
                    if (studentId != null) {
                        zipStudentIds.put(subZipFile, studentId);
                    }
                    
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
        // Use student ID in the extraction folder name if available
        String studentId = zipStudentIds.get(subZip);
        String folderName = studentId != null ? 
            subZip.getName().replace(".zip", "") + "_" + studentId :
            subZip.getName().replace(".zip", "");
            
        File tempDir = new File(subZip.getParent(), folderName);

        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(subZip))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().endsWith(".java")) {
                    File javaFile = new File(tempDir, entry.getName());
                    // Create parent directories if they don't exist
                    javaFile.getParentFile().mkdirs();
                    
                    try (FileOutputStream fos = new FileOutputStream(javaFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                    String className = entry.getName().substring(
                        entry.getName().lastIndexOf('/') + 1,
                        entry.getName().lastIndexOf('.')
                    );
                    javaFiles.put(className, javaFile);
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

    public String getCurrentStudentId(File subZip) {
        return zipStudentIds.get(subZip);
    }
}