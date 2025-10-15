public class Produto {
    private int id;
    private String nome;
    private String categoria;
    private double preco;
    private int quantDeEstoque;

    public Produto(String nome, String categoria, double preco, int quantDeEstoque) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantDeEstoque = quantDeEstoque;
    }

    public Produto(int id, String nome, String categoria, double preco, int quantDeEstoque) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantDeEstoque = quantDeEstoque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantDeEstoque() {
        return quantDeEstoque;
    }

    public void setQuantDeEstoque(int quantDeEstoque) {
        this.quantDeEstoque = quantDeEstoque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Produto -> nome =" + nome + " , categoria =" + categoria + " , preco = R$" + preco + " , quantDeEstoque = " + quantDeEstoque + " .";
    }
}