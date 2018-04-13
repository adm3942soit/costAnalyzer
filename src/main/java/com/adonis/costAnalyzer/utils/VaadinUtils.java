package com.adonis.costAnalyzer.utils;

import com.vaadin.server.*;

import java.io.File;

/**
 * Created by oksdud on 08.05.2017.
 */
public class VaadinUtils {
    public static String getIpAddress() {
        WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
        return webBrowser.getAddress();
    }

    public static Page getPage() {
        return Page.getCurrent();
    }

    public static WrappedSession getSession() {
        return VaadinSession.getCurrent().getSession();
    }

    public static File getBaseDir() {
        // Load an initial image
        File baseDir = VaadinService.getCurrent().getBaseDirectory();
        return baseDir;
    }

    //imageName with "images/"
    public static String getInitialPath(String imageName) {
        String initialPath = VaadinUtils.getBaseDir().getAbsolutePath() +
                File.separator + "VAADIN" + File.separator + "themes" + File.separator + "demo" +
///                            File.separator+ "images"
                File.separator + imageName;
        return initialPath;
    }
    public static String getInitialPath() {
        String initialPath = VaadinUtils.getBaseDir().getAbsolutePath() +
                File.separator + "VAADIN" + File.separator + "themes" + File.separator + "demo" +
                            File.separator+ "images";

        return initialPath;
    }

    public static String getResourcePath(String imageName) {
        String initialPath = FileReader.getCurrentDirectory() +
                File.separator + "src" + File.separator + "main" + File.separator + "resources" +
                File.separator + "VAADIN" + File.separator + "themes" + File.separator + "demo" +
                File.separator + "images" + File.separator + imageName;
        return initialPath;
    }

    public static String getResourcePath() {
        String initialPath = FileReader.getCurrentDirectory() +
                File.separator + "src" + File.separator + "main" + File.separator + "resources" +
                File.separator + "VAADIN" + File.separator + "themes" + File.separator + "demo" +
                File.separator + "images";
        return initialPath;
    }

    public static String getPathToJar() {
        String initialPath = FileReader.getCurrentDirectory() +
                File.separator + "build" + File.separator + "libs" + File.separator +
                "costAnalyzer-1.0.0.jar";
        return initialPath;

    }

    public static String getPathToImgInJar(String imageName) {
        String initialPath =
                "VAADIN" + File.separator + "themes" + File.separator + "demo" +
                        File.separator + "images" + File.separator + imageName;
      return initialPath;
    }
}
