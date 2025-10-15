import java.sql.SQLException;

public class SistemaMercado {

    // Console input handled by ConsoleUtil

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("   SISTEMA DE GERENCIAMENTO     ");
        System.out.println("         DE MERCADO             ");
        System.out.println("=================================");

        int opcao;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Gerenciar Produtos");
            System.out.println("2. Gerenciar Funcionários");
            System.out.println("3. Gerenciar Clientes");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = ConsoleUtil.readInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    new ProdutoMenu().show();
                    break;
                case 2:
                    new FuncionarioMenu().show();
                    break;
                case 3:
                    new ClienteMenu().show();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
    } while (opcao != 0);
    }

    static void menuProdutos() {
        // agora delegado para ProdutoMenu
    }

    // agora delegado para ProdutoMenu

    static void menuFuncionarios() {
        int opcao;
        do {
            System.out.println("\n===== MENU FUNCIONÁRIOS =====");
            System.out.println("1. Cadastrar Funcionário");
            System.out.println("2. Listar Funcionários");
            System.out.println("3. Atualizar Funcionário"); // ADICIONADO
            System.out.println("4. Remover Funcionário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = ConsoleUtil.readInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    listarFuncionarios();
                    break;
                case 3:
                    atualizarFuncionario(); // ADICIONADO
                    break;
                case 4:
                    removerFuncionario();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static void cadastrarFuncionario() {
        System.out.println("\nCadastro de Funcionário");
        String nome = ConsoleUtil.readLine("Nome: ");
        String cpf = ConsoleUtil.readLine("CPF (11 dígitos): ");

        if (cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            System.out.println("Erro: CPF deve ter exatamente 11 dígitos numéricos!");
            return;
        }

        FuncionarioDAO dao = new FuncionarioDAO();
        try {
            Funcionario f = new Funcionario(nome, cpf);
            boolean ok = dao.create(f);
            if (ok) System.out.println("Funcionário cadastrado com sucesso!");
            else System.out.println("Nenhum dado foi inserido.");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static void listarFuncionarios() {
        System.out.println("\nLista de Funcionários");
        FuncionarioDAO dao = new FuncionarioDAO();
        try {
            java.util.List<Funcionario> lista = dao.listAll();
            if (lista.isEmpty()) {
                System.out.println("Nenhum funcionário cadastrado");
                return;
            }
            for (Funcionario f : lista) {
                System.out.println("\n[" + f.getId() + "]");
                f.exibirDetalhes();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static void atualizarFuncionario() {
        listarFuncionarios();

        int id = ConsoleUtil.readInt("\nInforme o ID do funcionário que deseja atualizar: ");
        FuncionarioDAO dao = new FuncionarioDAO();
        try {
            Funcionario atual = dao.findById(id);
            if (atual == null) {
                System.out.println("Funcionário com ID " + id + " não encontrado.");
                return;
            }

            String nomeAtual = atual.getNome();
            String cpfAtual = atual.getCpf();

            String nome = ConsoleUtil.readLine("Novo nome (" + nomeAtual + "): ");
            if (nome.trim().isEmpty()) nome = nomeAtual;

            String cpf = ConsoleUtil.readLine("Novo CPF (" + cpfAtual + "): ");
            if (cpf.trim().isEmpty()) cpf = cpfAtual;

            if (cpf.length() != 11 || !cpf.matches("\\d{11}")) {
                System.out.println("Erro: CPF deve ter exatamente 11 dígitos numéricos!");
                return;
            }

            Funcionario novo = new Funcionario(nome, cpf);
            boolean ok = dao.update(id, novo);
            if (ok) System.out.println("Funcionário atualizado com sucesso!");
            else System.out.println("Falha ao atualizar o funcionário.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar funcionário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static void removerFuncionario() {
        listarFuncionarios();

        int id = ConsoleUtil.readInt("\nInforme o ID do funcionário que deseja remover: ");
        FuncionarioDAO dao = new FuncionarioDAO();
        try {
            Funcionario f = dao.findById(id);
            if (f == null) {
                System.out.println("Funcionário com ID " + id + " não encontrado.");
                return;
            }
            boolean ok = dao.delete(id);
            if (ok) System.out.println("Funcionário removido: " + f.getNome());
            else System.out.println("Falha ao remover o funcionário.");
        } catch (SQLException e) {
            System.out.println("Erro ao remover funcionário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static void menuClientes() {
        int opcao;
        do {
            System.out.println("\n===== MENU CLIENTES =====");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente"); // ADICIONADO
            System.out.println("4. Remover Cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = ConsoleUtil.readInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    atualizarCliente(); // ADICIONADO
                    break;
                case 4:
                    removerCliente();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static void cadastrarCliente() {
        System.out.println("\nCadastro de Cliente");
        String nome = ConsoleUtil.readLine("Nome: ");
        String cpf = ConsoleUtil.readLine("CPF (11 dígitos): ");

        if (cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            System.out.println("Erro: CPF deve ter exatamente 11 dígitos numéricos!");
            return;
        }

        ClienteDAO dao = new ClienteDAO();
        try {
            Cliente c = new Cliente(nome, cpf);
            boolean ok = dao.create(c);
            if (ok) System.out.println("Cliente cadastrado com sucesso!");
            else System.out.println("Nenhum dado foi inserido.");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static void listarClientes() {
        System.out.println("\nLista de Clientes");
        ClienteDAO dao = new ClienteDAO();
        try {
            java.util.List<Cliente> lista = dao.listAll();
            if (lista.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado");
                return;
            }
            for (Cliente c : lista) {
                System.out.println("\n[" + c.getId() + "]");
                c.exibirDetalhes();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ADICIONADO: Método para atualizar cliente
    static void atualizarCliente() {
        listarClientes();

        int id = ConsoleUtil.readInt("\nInforme o ID do cliente que deseja atualizar: ");
        ClienteDAO dao = new ClienteDAO();
        try {
            Cliente atual = dao.findById(id);
            if (atual == null) {
                System.out.println("Cliente com ID " + id + " não encontrado.");
                return;
            }

            String nomeAtual = atual.getNome();
            String cpfAtual = atual.getCpf();

            String nome = ConsoleUtil.readLine("Novo nome (" + nomeAtual + "): ");
            if (nome.trim().isEmpty()) nome = nomeAtual;

            String cpf = ConsoleUtil.readLine("Novo CPF (" + cpfAtual + "): ");
            if (cpf.trim().isEmpty()) cpf = cpfAtual;

            if (cpf.length() != 11 || !cpf.matches("\\d{11}")) {
                System.out.println("Erro: CPF deve ter exatamente 11 dígitos numéricos!");
                return;
            }

            Cliente novo = new Cliente(nome, cpf);
            boolean ok = dao.update(id, novo);
            if (ok) System.out.println("Cliente atualizado com sucesso!");
            else System.out.println("Falha ao atualizar o cliente.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static void removerCliente() {
        listarClientes();

        int id = ConsoleUtil.readInt("\nInforme o ID do cliente que deseja remover: ");
        ClienteDAO dao = new ClienteDAO();
        try {
            Cliente c = dao.findById(id);
            if (c == null) {
                System.out.println("Cliente com ID " + id + " não encontrado.");
                return;
            }
            boolean ok = dao.delete(id);
            if (ok) System.out.println("Cliente removido: " + c.getNome());
            else System.out.println("Falha ao remover o cliente.");
        } catch (SQLException e) {
            System.out.println("Erro ao remover cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}