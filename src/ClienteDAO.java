import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean create(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Cliente (nome, cpf) VALUES (?, ?)";
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Cliente> listAll() throws SQLException {
        String sql = "SELECT * FROM Cliente";
        List<Cliente> lista = new ArrayList<>();
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                lista.add(new Cliente(id, nome, cpf));
            }
        }
        return lista;
    }

    public Cliente findById(int id) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    return new Cliente(id, nome, cpf);
                }
            }
        }
        return null;
    }

    public boolean update(int id, Cliente cliente) throws SQLException {
        String sql = "UPDATE Cliente SET nome = ?, cpf = ? WHERE id = ?";
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setInt(3, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
