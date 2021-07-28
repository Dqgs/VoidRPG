package net.voidmc.voidrpg.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class MySql {

    private final String host = "91.134.26.205";
    private final String port = "3306";
    private final String database = "s2472_VoidRPG";
    private final String username = "u2472_6v0l3PfYDd";
    private final String password = "yNIJc.0r!aY^GjdBqoSHP6D3";
    private  HikariDataSource DATA_SOURCE;
    private Connection connection;

    public boolean isConnected() {
        return (DATA_SOURCE != null);
    }

    public void initializeHikari() throws SQLException {
        if (DATA_SOURCE != null) {
            throw new IllegalArgumentException("Hikari data source has already been initialized!");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }

        final HikariConfig config = new HikariConfig();

        final String host = "91.134.26.205";
        final String port = "3306";
        final String database = "s2472_VoidRPG";
        final String username = "u2472_6v0l3PfYDd";
        final String password = "yNIJc.0r!aY^GjdBqoSHP6D3";

        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);

        if (username != null && password != null) {
            config.setUsername(username);
            config.setPassword(password);
        }

        config.setConnectionTimeout(TimeUnit.SECONDS.toMillis(15));
        config.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(60));
        config.setMaximumPoolSize(20);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setLeakDetectionThreshold(60 * 50000);

        DATA_SOURCE = new HikariDataSource(config);
        connection = DATA_SOURCE.getConnection();
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()){

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
            config.setUsername(username);
            config.setPassword(password);

            config.setConnectionTimeout(TimeUnit.SECONDS.toMillis(15));
            config.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(60));
            config.setMaximumPoolSize(20);

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");


            HikariDataSource dataSource = new HikariDataSource(config);
//            dataSource.setJdbcUrl("jdbc:mysql://" +
//                    host + ":" + port + "/" + database);
//            dataSource.setUsername(username);
//            dataSource.setPassword(password);
            connection = dataSource.getConnection();
//            connection = DriverManager.getConnection("jdbc:mysql://" +
//                            host + ":" + port + "/" + database + "",
//                    username, password);
        }
    }

    public void disconnect() throws SQLException {
        if (isConnected()){
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
