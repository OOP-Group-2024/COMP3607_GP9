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
    
                // Create a temp directory to unzip the contents
                String tempDirPath = Files.createTempDirectory("unzipped_").toString();
                System.out.println("Unzipping to: " + tempDirPath);
    
                // Unzip the zip file into the temporary directory
                ZipHandler.unzipFile(zipFilePath, tempDirPath);
                tempDirectory = tempDirPath;
            File tempDir = new File(tempDirPath);

            for (File projectDir : tempDir.listFiles(File::isDirectory)) {
                File chatbotFile = new File(projectDir, "ChatBot.java");
                File chatbotPlatformFile = new File(projectDir, "ChatBotPlatform.java");

                if (chatbotFile.exists() && chatbotPlatformFile.exists()) {

                    ClassCompiler.compileJavaFiles(chatbotFile, chatbotPlatformFile);


                    Class<?> chatbotClass = ClassLoader.loadClass(projectDir, "ChatBot");
                    Class<?> chatbotPlatformClass = ClassLoader.loadClass(projectDir, "ChatBotPlatform");


                    if (chatbotClass != null && chatbotPlatformClass != null) {
                        test.setUp(chatbotClass, chatbotPlatformClass);
                        test.printReport();
                    }
                } else {
                    System.out.println("Required files not found in " + projectDir.getName());
                }
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

