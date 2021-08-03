package com.service.config;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @Auther: lihang
 * @Date: 2021-07-27 21:56
 * @Description:
 */
@Slf4j
public class KylinDataSource implements DataSource {
    private LinkedList<Connection> connectionPoolList = new LinkedList<>();

    private long maxWaitTime;

    public KylinDataSource(KylinProperties kylinProperties) {
        try {
            this.maxWaitTime = kylinProperties.getMaxWaitTime();
            Driver driverManager = (Driver) Class.forName(kylinProperties.getDriverClassName()).newInstance();
            Properties info = new Properties();
            info.put("user", kylinProperties.getUsername());
            info.put("password", kylinProperties.getPassword());
            for (int i = 0; i < kylinProperties.getPoolSize(); i++) {
                Connection connection = driverManager.connect(kylinProperties.getUrl(), info);
                connectionPoolList.add(ConnectionProxy.getProxy(connection, connectionPoolList));
            }
            log.info("PrestoDataSource has initialized {} size connection pool", connectionPoolList.size());
        } catch (Exception e) {
            log.error("kylinDataSource initialize error, ex: ", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        synchronized (connectionPoolList) {
            if (connectionPoolList.size() <= 0) {
                try {
                    connectionPoolList.wait(maxWaitTime);
                } catch (InterruptedException e) {
                    throw new SQLException("getConnection timeout..." + e.getMessage());
                }
            }

            return connectionPoolList.removeFirst();
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    static class ConnectionProxy implements InvocationHandler {

        private Object obj;
        private LinkedList<Connection> pool;
        private String DEFAULT_CLOSE_METHOD = "close";

        private ConnectionProxy(Object obj, LinkedList<Connection> pool) {
            this.obj = obj;
            this.pool = pool;
        }

        public static Connection getProxy(Object o, LinkedList<Connection> pool) {
            Object proxed = Proxy.newProxyInstance(o.getClass().getClassLoader(),
                    new Class[]{Connection.class}, new ConnectionProxy(o, pool));
            return (Connection) proxed;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals(DEFAULT_CLOSE_METHOD)) {
                synchronized (pool) {
                    pool.add((Connection) proxy);
                    pool.notify();
                }
                return null;
            } else {
                return method.invoke(obj, args);
            }
        }
    }
}
