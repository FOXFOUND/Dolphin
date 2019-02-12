package org.facengineer.Plugins;

import java.util.concurrent.TimeUnit;

public class PluginsConfig {
    public static final int PERMANENT = 0;
    public static final int ONE_MINUTE = 60;
    public static final int FIFTEN_MINUTE = 15 * 60;

    public static String FilePageHandler_SqlCmd = "SELECT * FROM Dolphin.FILE";
    public static String PersonPageHandler_SqlCmd = "SELECT name,email FROM Dolphin.USER";
    public static int PageMos = 5;

}