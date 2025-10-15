import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilitário simples para obter conexões com o banco de dados.
 * As credenciais podem ser obtidas das variáveis de ambiente DB_URL, DB_USER, DB_PASS
 * ou, se não definidas, usam valores padrão compatíveis com o projeto atual.
 */
public class DBUtil {

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/PROJETOA3";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASS = "root";

    public static Connection getConnection() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASS");

        if (url == null || url.isBlank()) url = DEFAULT_URL;
        if (user == null || user.isBlank()) user = DEFAULT_USER;
        if (pass == null) pass = DEFAULT_PASS;

        return DriverManager.getConnection(url, user, pass);
    }
}
