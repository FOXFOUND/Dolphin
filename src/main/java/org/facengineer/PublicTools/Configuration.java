package org.facengineer.PublicTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
    public static final long EXPIRE_TIME = 1*60*1000;
    public static final String TOKEN_SECRET = "1999.1979";
    public static Map<String,List<String>> SqlColumnsList;

    //For Windows-gs
    //public static final String FILEPATH = "F:\\pictureqq";
    //For Linux
    public static final String FILEPATH = "/home/tang/IdeaProjects/FileDao/";
}