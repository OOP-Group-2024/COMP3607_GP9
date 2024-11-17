package comp3607;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class DirectoryUtils {

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

    public static Boolean hasItems(){
        String baseDir = System.getProperty("user.dir");
        File inputZipFolder = new File(baseDir, "input_zip_here");
        
        if (inputZipFolder.exists() && inputZipFolder.isDirectory()) {
            String[] items = inputZipFolder.list();
            return items != null && items.length > 0;
        }
        return false;
    }

    public static Class<?> compileFile(File file) {
        // Use Java Compiler API to compile the file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        
        // Prepare the file to be compiled
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Collections.singletonList(file));
        
        // Prepare a diagnostic listener to capture compile errors
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        
        // Create a compilation task
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
        
        // Perform the compilation
        boolean success = task.call();
        
        // Handle compilation errors or success
        if (success) {
            try {
                // Assuming the file is compiled into the default classpath (can adjust if needed)
                String className = file.getName().replace(".java", "");
                File classFile = new File(file.getParent(), className + ".class");
                
                // Dynamically load the compiled class
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { file.getParentFile().toURI().toURL() });
                return Class.forName(className, true, classLoader);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.err.println(diagnostic.getMessage(null));
            }
            return null;
        }
    }

    public static String getDependenciesPath() throws IOException {
        Path resourceUrl = Paths.get("src", "main", "resources", "dependencies");
        if (resourceUrl == null) {
            createDependenciesFolder();
            throw new IOException("Resource folder 'dependencies' not found.");
        }
        
        return resourceUrl.toString();
    }


    public static void createDependenciesFolder() throws IOException {
        Path dependenciesPath = Paths.get("src", "main", "resources", "dependencies");
        
        if (!Files.exists(dependenciesPath)) {
            Files.createDirectories(dependenciesPath);
            System.out.println("Created 'dependencies' folder at: " + dependenciesPath.toAbsolutePath()+" re-launch this application");
        } else {
            System.out.println("'dependencies' folder already exists at: " + dependenciesPath.toAbsolutePath());
        }
    }

    //EXPERIMENT
     public static Class<?> loadClass(String className, String mainDirPath, String backupDirPath) {
        try {
            // List of all required Java files
            List<String> requiredFiles = Arrays.asList(
                "ChatBotGenerator.java", 
                "ChatBot.java", 
                "ChatBotPlatform.java", 
                "ChatBotSimulation.java"
            );

            // Prepare to compile all Java files
            List<File> filesToCompile = new ArrayList<>();

            // First, check if all files exist in the main directory
            File mainDir = new File(mainDirPath);
            for (String fileName : requiredFiles) {
                File file = new File(mainDir, fileName);
                if (file.exists()) {
                    filesToCompile.add(file);
                } else {
                    // If a file is missing, fetch it from the backup directory
                    File backupDir = new File(backupDirPath);
                    file = new File(backupDir, fileName);
                    if (file.exists()) {
                        filesToCompile.add(file);
                    } else {
                        // If the file is missing from both, return null
                        System.err.println("Missing required file: " + fileName);
                        return null;
                    }
                }
            }

            // Compile the Java files found in both directories
            boolean success = compileFiles(filesToCompile, mainDir);
            if (!success) {
                return null;
            }

            // Load the requested class dynamically
            return loadCompiledClass(className, mainDir);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static boolean compileFiles(List<File> filesToCompile, File dir) {
        try {
            // Use Java Compiler API to compile the files
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(filesToCompile);
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

            // Create the compilation task
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
            boolean success = task.call();

            // If compilation failed, print the errors
            if (!success) {
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    System.err.println(diagnostic.getMessage(null));
                }
            }
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to load the compiled class dynamically
    private static Class<?> loadCompiledClass(String className, File dir) {
        try {
            // Use URLClassLoader to load the class from the directory where it was compiled
            URLClassLoader classLoader = new URLClassLoader(new URL[] { dir.toURI().toURL() });
            return Class.forName(className, true, classLoader);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


