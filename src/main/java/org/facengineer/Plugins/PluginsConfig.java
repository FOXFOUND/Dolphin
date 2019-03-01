package org.facengineer.Plugins;

import java.util.concurrent.TimeUnit;

public class PluginsConfig {
    public static String FilePageHandler_SqlCmd = "SELECT * FROM Dolphin.FILE";
    public static String PersonPageHandler_SqlCmd = "SELECT name,email FROM Dolphin.USER";
    public static int PageMos = 5;

    public static String ZkServer = "localhost:2181";
}