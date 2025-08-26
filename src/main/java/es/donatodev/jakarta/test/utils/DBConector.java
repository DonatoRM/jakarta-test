package es.donatodev.jakarta.test.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.*;

import org.apache.commons.dbcp2.BasicDataSource;
import static es.donatodev.jakarta.test.utils.Messages.*;

public class DBConector {
    private static BasicDataSource pool;
    private static final Logger logger = Logger.getLogger(DBConector.class.getName());

    private static BasicDataSource getInstance() {
        if (pool == null) {
            Properties props = new Properties();
            // Cargar propiedades desde application.properties
            try (InputStream input = DBConector.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (input != null) {
                    props.load(input);
                } else {
                    logger.log(Level.WARNING, "No se encontró application.properties, se intentarán variables de entorno.");
                }
            } catch (Exception ex) {
                logger.log(Level.WARNING, "Error cargando application.properties, se intentarán variables de entorno.", ex);
            }

            // Leer credenciales, primero de properties, luego de entorno
            String url = getValue(props, "db.url", "DB_URL");
            String user = getValue(props, "db.user", "DB_USER");
            String password = getValue(props, "db.password", "DB_PASSWORD");

            // Validar credenciales
            if (isNullOrEmpty(url) || isNullOrEmpty(user) || isNullOrEmpty(password)) {
                logger.log(Level.SEVERE, MISSING_CREDENTIALS);
                throw new RuntimeException(MISSING_CREDENTIALS);
            }

            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(user);
            pool.setPassword(password);

            // Configuración flexible del pool
            pool.setInitialSize(getIntValue(props, "db.initialSize", 5));
            pool.setMinIdle(getIntValue(props, "db.minIdle", 5));
            pool.setMaxIdle(getIntValue(props, "db.maxIdle", 10));
            pool.setMaxTotal(getIntValue(props, "db.maxTotal", 25));
        }
        return pool;
    }

    /**
     * Obtiene una conexión del pool.
     */
    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }

    /**
     * Obtiene un valor de properties o variable de entorno.
     */
    private static String getValue(Properties props, String propKey, String envKey) {
        String value = props.getProperty(propKey);
        if (isNullOrEmpty(value)) {
            value = System.getenv(envKey);
        }
        return value;
    }

    /**
     * Obtiene un valor entero de properties, o usa el valor por defecto.
     */
    private static int getIntValue(Properties props, String propKey, int defaultValue) {
        String value = props.getProperty(propKey);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Verifica si un string es nulo o vacío.
     */
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
