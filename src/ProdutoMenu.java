public class ProdutoMenu {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    public void show() {
        int opcao;
        do {
            System.out.println("\n===== MENU PRODUTOS =====");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Remover Produto");
            System.out.println("0. Voltar");
            opcao = ConsoleUtil.readInt("Escolha uma opção: ");

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: listar(); break;
                case 3: atualizar(); break;
                case 4: remover(); break;
                case 0: System.out.println("Voltando ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\nCadastro de Produto");
        String nome = ConsoleUtil.readLine("Nome: ");
        String categoria = ConsoleUtil.readLine("Categoria: ");
        double preco = ConsoleUtil.readDouble("Preço: R$");
        int estoque = ConsoleUtil.readInt("Quantidade em estoque: ");

        if (preco < 0) { System.out.println("Erro: O preço não pode ser negativo!"); return; }
        if (estoque < 0) { System.out.println("Erro: A quantidade em estoque não pode ser negativa!"); return; }

        Produto p = new Produto(nome, categoria, preco, estoque);
        try {
            boolean ok = produtoDAO.create(p);
            if (ok) System.out.println("Produto cadastrado com sucesso! ID: " + p.getId());
            else System.out.println("Nenhum dado foi inserido.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void listar() {
        System.out.println("\nLista de Produtos");
        try {
            java.util.List<Produto> lista = produtoDAO.listAll();
            if (lista.isEmpty()) { System.out.println("Nenhum produto cadastrado"); return; }
            for (Produto p : lista) {
                System.out.println("[" + p.getId() + "] -> " + p);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void atualizar() {
        listar();
        int id = ConsoleUtil.readInt("\nInforme o ID do produto que deseja atualizar: ");
        try {
            Produto existente = produtoDAO.findById(id);
            if (existente == null) { System.out.println("Produto com ID " + id + " não encontrado."); return; }

            String nome = ConsoleUtil.readLine("Novo nome (" + existente.getNome() + "): "); if (nome.trim().isEmpty()) nome = existente.getNome();
            String categoria = ConsoleUtil.readLine("Nova categoria (" + existente.getCategoria() + "): "); if (categoria.trim().isEmpty()) categoria = existente.getCategoria();
            double preco = ConsoleUtil.readDoubleAllowEmpty("Novo preço (R$" + existente.getPreco() + "): ", existente.getPreco());
            int estoque = ConsoleUtil.readIntAllowEmpty("Nova quantidade (" + existente.getQuantDeEstoque() + "): ", existente.getQuantDeEstoque());

            if (preco < 0) { System.out.println("Erro: O preço não pode ser negativo!"); return; }
            if (estoque < 0) { System.out.println("Erro: A quantidade em estoque não pode ser negativa!"); return; }

            Produto p = new Produto(nome, categoria, preco, estoque);
            boolean ok = produtoDAO.update(id, p);
            if (ok) System.out.println("Produto atualizado com sucesso!"); else System.out.println("Falha ao atualizar o produto.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void remover() {
        listar();
        int id = ConsoleUtil.readInt("\nInforme o ID do produto que deseja remover: ");
        try {
            Produto p = produtoDAO.findById(id);
            if (p == null) { System.out.println("Produto com ID " + id + " não encontrado."); return; }
            boolean ok = produtoDAO.delete(id);
            if (ok) System.out.println("Produto removido: " + p.getNome()); else System.out.println("Falha ao remover o produto.");
        } catch (Exception e) {
            System.out.println("Erro ao remover produto: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
