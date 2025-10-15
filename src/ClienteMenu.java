public class ClienteMenu {
    private final ClienteDAO dao = new ClienteDAO();

    public void show() {
        int opcao;
        do {
            System.out.println("\n===== MENU CLIENTES =====");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Remover Cliente");
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
        System.out.println("\nCadastro de Cliente");
        String nome = ConsoleUtil.readLine("Nome: ");
        String cpf = ConsoleUtil.readLine("CPF (11 dígitos): ");
        if (cpf.length() != 11 || !cpf.matches("\\d{11}")) { System.out.println("Erro: CPF inválido"); return; }
        try {
            boolean ok = dao.create(new Cliente(nome, cpf));
            if (ok) System.out.println("Cliente cadastrado com sucesso!"); else System.out.println("Nenhum dado foi inserido.");
        } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); e.printStackTrace(); }
    }

    private void listar() {
        System.out.println("\nLista de Clientes");
        try {
            java.util.List<Cliente> lista = dao.listAll();
            if (lista.isEmpty()) { System.out.println("Nenhum cliente cadastrado"); return; }
            for (Cliente c : lista) { System.out.println("\n[" + c.getId() + "]"); c.exibirDetalhes(); }
        } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); e.printStackTrace(); }
    }

    private void atualizar() {
        listar();
        int id = ConsoleUtil.readInt("\nInforme o ID do cliente que deseja atualizar: ");
        try {
            Cliente atual = dao.findById(id);
            if (atual == null) { System.out.println("Cliente com ID " + id + " não encontrado."); return; }
            String nome = ConsoleUtil.readLine("Novo nome (" + atual.getNome() + "): "); if (nome.trim().isEmpty()) nome = atual.getNome();
            String cpf = ConsoleUtil.readLine("Novo CPF (" + atual.getCpf() + "): "); if (cpf.trim().isEmpty()) cpf = atual.getCpf();
            if (cpf.length() != 11 || !cpf.matches("\\d{11}")) { System.out.println("Erro: CPF inválido"); return; }
            boolean ok = dao.update(id, new Cliente(nome, cpf));
            if (ok) System.out.println("Cliente atualizado com sucesso!"); else System.out.println("Falha ao atualizar o cliente.");
        } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); e.printStackTrace(); }
    }

    private void remover() {
        listar();
        int id = ConsoleUtil.readInt("\nInforme o ID do cliente que deseja remover: ");
        try {
            Cliente c = dao.findById(id);
            if (c == null) { System.out.println("Cliente com ID " + id + " não encontrado."); return; }
            boolean ok = dao.delete(id);
            if (ok) System.out.println("Cliente removido: " + c.getNome()); else System.out.println("Falha ao remover o cliente.");
        } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); e.printStackTrace(); }
    }
}
