import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public boolean create(Produto produto) throws SQLException {
        String sql = "INSERT INTO Produto (nome, categoria, preco, quantDeEstoque) VALUES (?, ?, ?, ?)";
        try (Connection conexao = DBUtil.getConnection(); java.sql.PreparedStatement stmt = conexao.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getCategoria());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantDeEstoque());
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        produto.setId(keys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    public List<Produto> listAll() throws SQLException {
        String sql = "SELECT * FROM Produto";
        List<Produto> lista = new ArrayList<>();
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String categoria = rs.getString("categoria");
                double preco = rs.getDouble("preco");
                int qtd = rs.getInt("quantDeEstoque");
                Produto p = new Produto(id, nome, categoria, preco, qtd);
                lista.add(p);
            }
        }
        return lista;
    }

    public Produto findById(int id) throws SQLException {
        String sql = "SELECT * FROM Produto WHERE id = ?";
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int pid = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String categoria = rs.getString("categoria");
                    double preco = rs.getDouble("preco");
                    int qtd = rs.getInt("quantDeEstoque");
                    return new Produto(pid, nome, categoria, preco, qtd);
                }
            }
        }
        return null;
    }

    public boolean update(int id, Produto produto) throws SQLException {
        String sql = "UPDATE Produto SET nome = ?, categoria = ?, preco = ?, quantDeEstoque = ? WHERE id = ?";
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getCategoria());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantDeEstoque());
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Produto WHERE id = ?";
        try (Connection conexao = DBUtil.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
