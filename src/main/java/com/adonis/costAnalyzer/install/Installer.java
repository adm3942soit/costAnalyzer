package com.adonis.costAnalyzer.install;

import com.adonis.costAnalyzer.utils.FileReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import static com.adonis.costAnalyzer.utils.FileReader.getCurrentDirectory;

/**
 * Created by oksdud on 21.04.2017.
 */
public class Installer {

    public static String rootDir;
    private static File output = new File(getCurrentDirectory() + System.getProperty("file.separator") + "install"
            + System.getProperty("file.separator") + "install.zip");

    public static void installJdk() {
        File jdkFile = new File(getCurrentDirectory() + File.separator + "install/jdk/jdk-8u131-windows-x64.exe");
        if (jdkFile.exists()) {
            String[] command = new String[7];
            command[0] = "MSIEXEC";
            command[1] = " /I";
            command[2] = jdkFile + "";
            command[3] = " /passive";
            command[4] = " /norestart";
            command[5] = " /L*V ";
            command[6] = " Setup.log";

            ProcessBuilder pb = new ProcessBuilder(command[0], command[1], command[2], command[3], command[4], command[5], command[6]);
            pb.directory(new File(getCurrentDirectory()));
            try {
                Process process = pb.start();
                //process.waitFor();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    public static void createShortcat() {

        String nameRunBat = "run.bat";
        String pathFuture = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "carmanager.lnk";
        String pathToIcon = getCurrentDirectory() + File.separator + "install";
        File runFile = new File(getCurrentDirectory() + File.separator + nameRunBat);

        try {
            if (!runFile.exists()) runFile.createNewFile();
            FileWriter fileWriter = new FileWriter(runFile);
            fileWriter.write(
                    "#!/bin/sh\n" +
                            "#\n" +
                            "# ---------------------------------------------------------------------\n" +
                            "# VehiclyManager startup script.\n" +
                            "# ---------------------------------------------------------------------\n" +
                            "#\n" +
                            "cd " + getCurrentDirectory() + "\n" +
                            "java -jar VehiclyManager-1.0.0.jar");//build/libs/
            fileWriter.flush();
            fileWriter.close();
//            Filer.setRights(runFile, "777", true, false);
//            Filer.setRights(new File(getCurrentDirectory() + System.getProperty("file.separator") + "VehyclyManager-0.0.1.jar"), "777", true, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileReader.createShortcutVehiclyManager(nameRunBat, getCurrentDirectory(), pathToIcon);
        try {
            Files.copy(
                    Paths.get(getCurrentDirectory() + File.separator + "carmanager.lnk"),
                    Paths.get(pathFuture),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createInstallZip() {
        System.err.println("createRootDirectory");
        if (FileReader.createRootDirectory("C://VehiclyManager"))
            rootDir = getCurrentDirectory() + System.getProperty("file.separator") + rootDir;

        File root = new File(rootDir);

        if (!FileReader.isEmptyDirectory(rootDir)) {

            List<File> listDir = new ArrayList<File>();
            listDir.add(root);


            try {
                System.out.println("packZipDir");

                try {

                    System.out.println("zipFile" + output.getAbsolutePath());
                    ZipFile zipFile = new ZipFile(output.getAbsolutePath());

                    // Folder to add
                    String folderToAdd = root.getAbsolutePath();
                    System.out.println("folderToAdd" + folderToAdd);

                    // Add folder to the zip file
                    zipFile.getEntry(folderToAdd);
                    System.out.println("Create zip!");


                } catch (ZipException e) {
                    e.printStackTrace();
                }

            } catch (Exception ex) {
                ex.printStackTrace();

                System.err.println(ex.getMessage());
            }

        }

    }

}
