package org.facengineer.PublicTools;

import org.facengineer.DaoMapper.PlatformModel;
import org.facengineer.Plugins.ServiceRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WebConfigListener implements ServletContextListener {
    private static final Logger LOG = LoggerFactory.getLogger(WebConfigListener.class);
    private PlatformModel platformModel;
    ServiceRegister _serviceRegister;

    public WebConfigListener(PlatformModel platformModel){
        this.platformModel = platformModel;
    }
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            org.facengineer.PublicTools.Configuration.SqlColumnsList = GenerateSqlColumnsList();
            _serviceRegister = new ServiceRegister();
        }catch(Exception e){
            LOG.error(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            LOG.info("Listener Destroyed Startup");
            _serviceRegister.shutdown();
        }catch (Exception e){
            LOG.error(e.getMessage());
        }
    }

    private Map<String,List<String>> GenerateSqlColumnsList(){
        Map<String,List<String>> Result = new HashMap<>();
        for(String TableName : this.platformModel.ShowTables()){
            Result.put(TableName.toUpperCase(),this.platformModel.DescribeTable(TableName));
        }
        return Result;
    }
}