package org.facengineer.Plugins;

import org.apache.zookeeper.*;
import org.facengineer.PublicTools.PublicUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

public class ServiceRegister implements Watcher {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceRegister.class);
    private String hostport;
    private ZooKeeper zk;
    private volatile boolean connected = false;
    private volatile boolean RegistCheck = false;

    boolean isConnected() {
        return connected;
    }

    public void shutdown() throws Exception {
        zk.close();
    }

    @Override
    public void process(WatchedEvent e) {
        LOG.info("Processing event: " + e.toString());
        if (e.getType() == Event.EventType.None) {
            switch (e.getState()) {
                case SyncConnected:
                    connected = true;
                    break;
                case Disconnected:
                    connected = false;
                    break;
                case Expired:
                    connected = false;
                    LOG.error("Session expiration");
                default:
                    break;
            }
        }
    }

    public ServiceRegister() throws Exception {
        zk = new ZooKeeper(PluginsConfig.ZkServer, 1000, this);
        while (!isConnected()) {
            Thread.sleep(100);
        }
//        bootstrap("/servertree", new byte[0]);
        runForEver();
    }

    private void bootstrap(String node, byte[] data) {
        LOG.info("Running for bootstrap");
        createParent(node, data);
    }

    void createParent(String path, byte[] data) {
        zk.create(path,
                data,
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT,
                createParentCallback,
                data);
    }

    AsyncCallback.StringCallback createParentCallback = new AsyncCallback.StringCallback() {
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    createParent(path, (byte[]) ctx);
                    break;
                case OK:
                    LOG.info("Parent created");
                    break;
                case NODEEXISTS:
                    LOG.warn("Parent already registered: " + path);
                    break;
                default:
                    LOG.error("Something went wrong: ",
                            KeeperException.create(KeeperException.Code.get(rc), path));
            }
        }
    };

    private void runForEver() {
        try {
            String host = PublicUtils.getHostIp();
            LOG.info("Running for ServiceRegistMaster");
            zk.create("/servertree/worker" + Integer.toHexString((new Random()).nextInt()),
                    host.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL,
                    ServiceCreateCallback,
                    null);
        } catch (Exception e) {
            LOG.error("Error");
        }
    }

    AsyncCallback.StringCallback ServiceCreateCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    runForEver();
                    break;
                case OK:
                    LOG.info("Service Registed");
                    RegistCheck = true;
                    break;
                case NODEEXISTS:
                    RegistCheck = false;
                    LOG.error("NODE has been exist");
                    break;
                default:
                    LOG.error("Something went wrong when running for master.",
                            KeeperException.create(KeeperException.Code.get(rc), path));
            }
            LOG.info("Service " + (RegistCheck ? "is " : "not  ") + "Registed");
        }
    };
}
