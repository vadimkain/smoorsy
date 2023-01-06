package com.smoorsy.utils;

import com.smoorsy.utils.exception.ConnectionManagerException;
import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@UtilityClass
public class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final Integer DEFAULT_POOL_SIZE = 5;

    // Объявляем потокобезопасную очередь
    private static BlockingQueue<Connection> pool;
    // Объявляем список для закрытия соединений. Здесь храним исходные соединения
    private static List<Connection> sourceConnections;

    static {
        loadDriver();
        initConnectionPool();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ConnectionManagerException(e);
        }
    }

    private static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new ConnectionManagerException(e);
        }
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new ConnectionManagerException(e);
        }
    }

    // Инициализируем пул соединений
    private static void initConnectionPool() {
        // Устанавливаем размер пула
        int poolSize = PropertiesUtil.get(POOL_SIZE_KEY) == null ? DEFAULT_POOL_SIZE : Integer.parseInt(PropertiesUtil.get(POOL_SIZE_KEY));

        // Инициализируем массив потокобезопасной очереди
        pool = new ArrayBlockingQueue<>(poolSize);

        // Инициализируем массив для зарытия соединений
        sourceConnections = new ArrayList<>(poolSize);

        // Проходимся по пулу соединение и вставляем туда все наши соединения
        for (int i = 0; i < poolSize; i++) {
            Connection connection = open();

            // Реализовываем готовый Proxy паттерн из JDK
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(
                    ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            // Если вызывается close в Connection, то не закрываем соединение, а добавляем в пул соединений
                            if (method.getName().equals("close")) {
                                return pool.add((Connection) proxy);
                            }
                            // Иначе выполняем работу метода
                            return method.invoke(connection, args);
                        }
                    }
//                    ((proxy, method, args) -> method.getName().equals("close") ? pool.add((Connection) proxy) : method.invoke(connection, args))
            );

            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    private static void closePool() {
        try {
            for (Connection sourceConnection : sourceConnections) {
                sourceConnection.close();
            }
        } catch (SQLException e) {
            throw new ConnectionManagerException(e);
        }
    }
}
