package comp3607;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class RunAssignmentTest {

    private static String tempDirectory = " ";
    
        public static void runTests() throws IOException {
            AssignmentTest test = new AssignmentTest();
            
    
            try {
                String zipFilePath = ZipHandler.getZipFileFromInputFolder();
                System.out.println("Zip file found at: " + zipFilePath);

                String tempDirPath = Files.createTempDirectory("unzipped_main_").toString();
                System.out.println("Unzipping main zip to: " + tempDirPath);
                System.out.println("");

        
                ZipHandler.unzipFile(zipFilePath, tempDirPath);
                File mainUnzipDir = new File(tempDirPath);

                System.out.println("Contents of mainUnzipDir:");
                for (File file : mainUnzipDir.listFiles()) {
                    System.out.println(file.getName() + (file.isDirectory() ? " (directory)" : " (file)"));
                }
                System.out.println("");

                for (File innerZipFile : mainUnzipDir.listFiles((dir, name) -> name.endsWith(".zip"))) {
               
                    System.out.println("Processing inner zip file: " + innerZipFile.getName());

                    File innerUnzipDir = new File(mainUnzipDir, innerZipFile.getName().replace(".zip", ""));
                    innerUnzipDir.mkdirs();
    
                    ZipHandler.unzipFile(innerZipFile.getAbsolutePath(), innerUnzipDir.getAbsolutePath());
                    System.out.println("Unzipping inner zip to: " + innerUnzipDir);

                    System.out.println("Contents of innerUnzipDir:");
                    for (File file : innerUnzipDir.listFiles()) {
                        System.out.println("    "+file.getName() + (file.isDirectory() ? " (directory)" : " (file)"));
                    }
                    System.out.println("");
    
                    //for (File projectDir : innerUnzipDir.listFiles(File::isDirectory)) {
                        System.out.println("Processing files");

                        File chatbotFile = new File(innerUnzipDir, "ChatBot.java");
                        File chatbotPlatformFile = new File(innerUnzipDir, "ChatBotPlatform.java");

                        if (chatbotFile.exists() && chatbotPlatformFile.exists()) {
                            // Compile the Java files
                            ClassCompiler.compileJavaFiles(chatbotFile, chatbotPlatformFile);

                            // Load the compiled classes
                            Class<?> chatbotClass = ClassLoader.loadClass(innerUnzipDir, "ChatBot");
                            Class<?> chatbotPlatformClass = ClassLoader.loadClass(innerUnzipDir, "ChatBotPlatform");

                            if (chatbotClass != null && chatbotPlatformClass != null) {
                                // Set up and execute the test
                                test.setUp(chatbotClass, chatbotPlatformClass);
                                test.printReport();
                            }

                        } else {
                            System.out.println("Required files not found in " + innerUnzipDir.getName());
                        }
                    //}
                System.out.println("");
            }

        } catch (URISyntaxException e) {//for debugging
            System.err.println("Failed to locate the input zip folder: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("An error occurred while unzipping the file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        } catch (NoClassDefFoundError e) {
            System.err.println("A required class dependency is missing: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("Security restriction prevented class loading: " + e.getMessage());
        } catch (Exception e){
            System.err.println("Generic Error: " + e.getMessage());
        } finally {
            deleteDirectory(new File(tempDirectory));
        }
    }

    private static void deleteDirectory(File dir) throws IOException {
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                deleteDirectory(file);
            }
        }
        dir.delete();
    }
}

