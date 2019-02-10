package org.facengineer.PublicTools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LOG {
    private Logger _log;
    public LOG(Class type){
        _log = LoggerFactory.getLogger(type);
    }

    public void info(String value){
        _log.info(value);
    }

    public void error(String value){
        _log.error(value);
    }
}
