package comp3607;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipHandler {
    
    public static void unzipFile(String zipFilePath, String destDir) throws IOException {
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                Path entryPath = Paths.get(destDir, entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(entryPath);
                } else {
                    Files.createDirectories(entryPath.getParent());
                    Files.copy(zipFile.getInputStream(entry), entryPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    public static String getZipFolderPath() throws URISyntaxException {
        // Use getResource to load the input_zip_here directory path dynamically
        Path path = Paths.get(ZipHandler.class.getClassLoader()
                                .getResource("input_zip_here")
                                .toURI());
        return path.toString();
    }

    public static String extractZipFolderToTemp() throws IOException {
        
        URL resource = ZipHandler.class.getClassLoader().getResource("input_zip_here");
        if (resource != null) {

            Path tempDir = Files.createTempDirectory("unzipped_");
            System.out.println("Extracting files to: " + tempDir.toString());

            try (InputStream in = resource.openStream()) {
                Files.copy(in, tempDir, StandardCopyOption.REPLACE_EXISTING);
                
                return tempDir.toString(); 
            }
        } else {
            throw new IOException("Resource 'input_zip_here' not found in classpath");
        }
    }

    public static String getZipFileFromInputFolder() throws IOException, URISyntaxException {
        URL resource = ZipHandler.class.getClassLoader().getResource("input_zip_here");
        if (resource != null) {
            Path resourcePath = Paths.get(resource.toURI());
            if (Files.exists(resourcePath)) {
                try (Stream<Path> paths = Files.walk(resourcePath)) {
                    Optional<Path> zipFile = paths.filter(path -> path.toString().endsWith(".zip")).findFirst();
                    return zipFile.map(Path::toString).orElseThrow(() -> new IOException("No zip file found"));
                }
            } else {
                throw new IOException("Resource 'input_zip_here' not found in classpath");
            }
        } else {
            throw new IOException("Resource 'input_zip_here' not found in classpath");
        }
    }
}
