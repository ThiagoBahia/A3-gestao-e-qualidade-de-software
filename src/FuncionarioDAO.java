import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public boolean create(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO Funcionario (nome, cpf) VALUES (?, ?)";
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Funcionario> listAll() throws SQLException {
        String sql = "SELECT * FROM Funcionario";
        List<Funcionario> lista = new ArrayList<>();
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                lista.add(new Funcionario(id, nome, cpf));
            }
        }
        return lista;
    }

    public Funcionario findById(int id) throws SQLException {
        String sql = "SELECT * FROM Funcionario WHERE id = ?";
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    return new Funcionario(id, nome, cpf);
                }
            }
        }
        return null;
    }

    public boolean update(int id, Funcionario funcionario) throws SQLException {
        String sql = "UPDATE Funcionario SET nome = ?, cpf = ? WHERE id = ?";
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setInt(3, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Funcionario WHERE id = ?";
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
