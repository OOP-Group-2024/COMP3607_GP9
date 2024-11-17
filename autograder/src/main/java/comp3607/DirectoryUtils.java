package comp3607;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class DirectoryUtils {

    private static final Pattern STUDENT_ID_PATTERN = Pattern.compile("\\b\\d{9}\\b");
    private static final Pattern STUDENT_NAME_ID_PATTERN = Pattern.compile("(?:[A-Za-z]+_)+?(\\d{9})");
    private static final Pattern STUDENT_ID_COMMENT_PATTERN = 
    Pattern.compile("(?i)(?:student\\s*id|id)\\s*:?\\s*(\\d{9})");

    /**
     * Extracts the student ID from a file path or content.
     * Checks multiple locations in the following order:
     * 1. In the zip file name (e.g., "816000000.zip")
     * 2. In a parent directory name
     * 3. In the unzipped folder name
     * 4. In file content (for .java files)
     * 5. In any comment containing "Student ID: xxxxxxxx"
     *
     * @param path The file or directory path to extract the ID from
     * @return The student ID if found, or null if no valid ID is found
     */
    public static String getStudentId(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }

        File file = new File(path);
        
        // Try to find ID in the file path components
        String idFromPath = extractIdFromPath(file);
        if (idFromPath != null) {
            System.out.println("Found student ID: " + idFromPath + " in path: " + path);
            return idFromPath;
        }

        // If it's a Java file, try to find ID in the content
        if (path.endsWith(".java")) {
            String idFromContent = extractIdFromJavaFile(file);
            if (idFromContent != null) {
                return idFromContent;
            }
        }

        // If it's a directory, search through all Java files
        if (file.isDirectory()) {
            String idFromDirectory = searchDirectoryForStudentId(file);
            if (idFromDirectory != null) {
                return idFromDirectory;
            }
        }
        System.out.println("Warning: Could not find student ID in path: " + path);
        return null;
    }

    private static String extractIdFromPath(File file) {
        // Get the complete path
        String fullPath = file.getAbsolutePath();
        
        // Split the path into components
        String[] pathComponents = fullPath.split("[/\\\\]");
        
        for (String component : pathComponents) {
            // Clean the component (remove file extensions)
            String cleanComponent = component.replaceAll("\\.[^.]+$", "");
            
            // First try to match the Name_ID pattern
            Matcher nameMatcher = STUDENT_NAME_ID_PATTERN.matcher(cleanComponent);
            if (nameMatcher.find()) {
                return nameMatcher.group(1); // Return the ID part
            }
            
            // If no Name_ID pattern found, try to find a standalone 9-digit number
            Matcher matcher = STUDENT_ID_PATTERN.matcher(cleanComponent);
            if (matcher.find()) {
                return matcher.group();
            }
        }
        
        return null;
    }


    private static String extractIdFromJavaFile(File file) {
        try {
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            
            // First try to find ID in comments
            Matcher commentMatcher = STUDENT_ID_COMMENT_PATTERN.matcher(content);
            if (commentMatcher.find()) {
                return commentMatcher.group(1);
            }
            
          
            Matcher matcher = STUDENT_ID_PATTERN.matcher(content);
            if (matcher.find()) {
                return matcher.group();
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not read file " + file.getName() + ": " + e.getMessage());
        }
        return null;
    }

    private static String searchDirectoryForStudentId(File directory) {
        try (Stream<Path> paths = Files.walk(directory.toPath())) {
            return paths
                .filter(path -> path.toString().endsWith(".java"))
                .map(path -> extractIdFromJavaFile(path.toFile()))
                .filter(id -> id != null)
                .findFirst()
                .orElse(null);
        } catch (IOException e) {
            System.err.println("Warning: Could not search directory " + directory.getName() + ": " + e.getMessage());
            return null;
        }
    }

    public static void createInputZipFolder() {
        String baseDir = System.getProperty("user.dir");
        File inputZipFolder = new File(baseDir, "input_zip_here");
        
        if (!inputZipFolder.exists()) {
            boolean created = inputZipFolder.mkdir();
            if (created) {
                System.out.println("Folder 'input_zip_here' created successfully.");
            } else {
                System.err.println("Failed to create folder 'input_zip_here'.");
            }
        } else {
            System.out.println("Folder 'input_zip_here' already exists.");
        }
    }

    public static Boolean hasItems() {
        String baseDir = System.getProperty("user.dir");
        File inputZipFolder = new File(baseDir, "input_zip_here");
        
        if (inputZipFolder.exists() && inputZipFolder.isDirectory()) {
            String[] items = inputZipFolder.list();
            return items != null && items.length > 0;
        }
        return false;
    }

    public static Class<?> compileFile(File file) {
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                throw new IllegalStateException("Java compiler not available. Make sure you're using JDK, not JRE.");
            }

            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            
            Iterable<? extends JavaFileObject> compilationUnits = 
                fileManager.getJavaFileObjectsFromFiles(Collections.singletonList(file));
            
            List<String> options = new ArrayList<>();
            options.add("-classpath");
            options.add(System.getProperty("java.class.path"));
            
            JavaCompiler.CompilationTask task = compiler.getTask(
                null, fileManager, diagnostics, options, null, compilationUnits);
            
            boolean success = task.call();
            
            if (success) {
                String className = file.getName().replace(".java", "");
                File classFile = new File(file.getParent(), className + ".class");
                
                URLClassLoader classLoader = new URLClassLoader(
                    new URL[] { file.getParentFile().toURI().toURL() },
                    ClassLoader.getSystemClassLoader()
                );
                
                return Class.forName(className, true, classLoader);
            } else {
                System.err.println("Compilation failed for " + file.getName() + ":");
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    System.err.println(diagnostic.getMessage(null));
                }
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error compiling " + file.getName() + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static String getDependenciesPath() throws IOException {
        Path dependenciesPath = Paths.get("src", "main", "resources", "dependencies");
        if (!Files.exists(dependenciesPath)) {
            createDependenciesFolder();
            throw new IOException("Resource folder 'dependencies' not found. Created the folder, please re-launch the application.");
        }
        return dependenciesPath.toString();
    }

    public static void createDependenciesFolder() throws IOException {
        Path dependenciesPath = Paths.get("src", "main", "resources", "dependencies");
        
        if (!Files.exists(dependenciesPath)) {
            Files.createDirectories(dependenciesPath);
            System.out.println("Created 'dependencies' folder at: " + dependenciesPath.toAbsolutePath() + 
                             " - please re-launch this application");
        } else {
            System.out.println("'dependencies' folder already exists at: " + dependenciesPath.toAbsolutePath());
        }
    }

    public static Class<?> loadClass(String className, String mainDirPath, String backupDirPath) {
        boolean exist = true;
        try {
            List<String> requiredFiles = Arrays.asList(
                "ChatBotGenerator.java", 
                "ChatBot.java", 
                "ChatBotPlatform.java", 
                "ChatBotSimulation.java"
            );

            List<File> filesToCompile = new ArrayList<>();
            File mainDir = new File(mainDirPath);
            File backupDir = new File(backupDirPath);

            // Collect all required files
            for (String fileName : requiredFiles) {
                File mainFile = new File(mainDir, fileName);
                File backupFile = new File(backupDir, fileName);

                if (mainFile.exists()) {
                    filesToCompile.add(mainFile);
                    exist = true;
                } else if (backupFile.exists()) {
                    System.out.println("CLASS +"+mainFile.getName()+" is missing from student submission");
                    filesToCompile.add(backupFile);
                    exist = false;
                } else {
                    System.err.println("Missing required file: " + fileName);
                    System.err.println("Checked in:\n  " + mainDir.getAbsolutePath() + 
                                     "\n  " + backupDir.getAbsolutePath());
                    return null;
                }
            }

            // Compile all files
            if (!compileFiles(filesToCompile, mainDir)) {
                System.err.println("Compilation failed for one or more files");
                return null;
            }

            // Load the requested class
            if(exist){
                return loadCompiledClass(className, mainDir);
            }
            else{
                return null;
            }
            

        } catch (Exception e) {
            System.err.println("Error loading class " + className + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static boolean compileFiles(List<File> filesToCompile, File outputDir) {
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                throw new IllegalStateException("Java compiler not available. Make sure you're using JDK, not JRE.");
            }

            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

            List<String> options = new ArrayList<>();
            options.add("-d");
            options.add(outputDir.getAbsolutePath());
            options.add("-classpath");
            options.add(System.getProperty("java.class.path"));

            Iterable<? extends JavaFileObject> compilationUnits = 
                fileManager.getJavaFileObjectsFromFiles(filesToCompile);

            JavaCompiler.CompilationTask task = compiler.getTask(
                null, fileManager, diagnostics, options, null, compilationUnits);

            boolean success = task.call();

            if (!success) {
                System.err.println("Compilation errors:");
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    System.err.println(diagnostic.getMessage(null));
                }
            }

            return success;
        } catch (Exception e) {
            System.err.println("Error during compilation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static Class<?> loadCompiledClass(String className, File dir) {
        try {
            URLClassLoader classLoader = new URLClassLoader(
                new URL[] { dir.toURI().toURL() },
                ClassLoader.getSystemClassLoader()
            );
            return Class.forName(className, true, classLoader);
        } catch (Exception e) {
            System.err.println("Error loading compiled class " + className + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}