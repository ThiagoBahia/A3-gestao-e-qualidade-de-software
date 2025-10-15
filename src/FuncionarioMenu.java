public class FuncionarioMenu {
    private final FuncionarioDAO dao = new FuncionarioDAO();

    public void show() {
        int opcao;
        do {
            System.out.println("\n===== MENU FUNCIONÁRIOS =====");
            System.out.println("1. Cadastrar Funcionário");
            System.out.println("2. Listar Funcionários");
            System.out.println("3. Atualizar Funcionário");
            System.out.println("4. Remover Funcionário");
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
        System.out.println("\nCadastro de Funcionário");
        String nome = ConsoleUtil.readLine("Nome: ");
        String cpf = ConsoleUtil.readLine("CPF (11 dígitos): ");
        if (cpf.length() != 11 || !cpf.matches("\\d{11}")) { System.out.println("Erro: CPF inválido"); return; }
        try {
            boolean ok = dao.create(new Funcionario(nome, cpf));
            if (ok) System.out.println("Funcionário cadastrado com sucesso!"); else System.out.println("Nenhum dado foi inserido.");
        } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); e.printStackTrace(); }
    }

    private void listar() {
        System.out.println("\nLista de Funcionários");
        try {
            java.util.List<Funcionario> lista = dao.listAll();
            if (lista.isEmpty()) { System.out.println("Nenhum funcionário cadastrado"); return; }
            for (Funcionario f : lista) { System.out.println("\n[" + f.getId() + "]"); f.exibirDetalhes(); }
        } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); e.printStackTrace(); }
    }

    private void atualizar() {
        listar();
        int id = ConsoleUtil.readInt("\nInforme o ID do funcionário que deseja atualizar: ");
        try {
            Funcionario atual = dao.findById(id);
            if (atual == null) { System.out.println("Funcionário com ID " + id + " não encontrado."); return; }
            String nome = ConsoleUtil.readLine("Novo nome (" + atual.getNome() + "): "); if (nome.trim().isEmpty()) nome = atual.getNome();
            String cpf = ConsoleUtil.readLine("Novo CPF (" + atual.getCpf() + "): "); if (cpf.trim().isEmpty()) cpf = atual.getCpf();
            if (cpf.length() != 11 || !cpf.matches("\\d{11}")) { System.out.println("Erro: CPF inválido"); return; }
            boolean ok = dao.update(id, new Funcionario(nome, cpf));
            if (ok) System.out.println("Funcionário atualizado com sucesso!"); else System.out.println("Falha ao atualizar o funcionário.");
        } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); e.printStackTrace(); }
    }

    private void remover() {
        listar();
        int id = ConsoleUtil.readInt("\nInforme o ID do funcionário que deseja remover: ");
        try {
            Funcionario f = dao.findById(id);
            if (f == null) { System.out.println("Funcionário com ID " + id + " não encontrado."); return; }
            boolean ok = dao.delete(id);
            if (ok) System.out.println("Funcionário removido: " + f.getNome()); else System.out.println("Falha ao remover o funcionário.");
        } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); e.printStackTrace(); }
    }
}
