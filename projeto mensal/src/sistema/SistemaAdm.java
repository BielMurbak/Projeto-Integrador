package sistema;

import produtos.Tenis;

import java.util.ArrayList;
import java.util.Scanner;

public class SistemaAdm {
    public void sistemaAdm() {

        Scanner scanner = new Scanner(System.in);
        int auxI = 0;

        do {
            System.out.println("Sistema E-commerce");
            System.out.println("1 - Cadastrar Produtos");
            System.out.println("2 - Remover Produtos");
            System.out.println("3 - Remover Clientes");
            System.out.println("4 - Adicionar Administrador");
            System.out.println("5 - Sair");

            auxI = scanner.nextInt();

            switch (auxI) {
                case 1:
                    cadastrarProduto(scanner);
                    break;
                case 2:
                    removerProduto(scanner);
                    break;
                case 3:
                    removerCliente(scanner);
                    break;
                case 4:
                  adicionarAdm(scanner);
                case 5:
                    SistemaUsuario sU = new SistemaUsuario();
                    sU.sistemaUsuario();
                default:
                    System.out.println("Erro! numero digitado é invalido");
            }
        } while (auxI != 5);
    }

    public void cadastrarProduto(Scanner scanner) {
        int continuarCadastro =0;

        do {
            System.out.println("\n--- Cadastro de Produtos ---");
            System.out.print("Nome do tênis: ");
            scanner.nextLine(); // Limpar buffer
            String nome = scanner.nextLine();

            System.out.print("Preço: R$ ");
            double preco = scanner.nextDouble();

            System.out.print("Código: ");
            int codigo = scanner.nextInt();

            System.out.print("Quantidade em estoque: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            Tenis novoTenis = new Tenis(nome, codigo, quantidade, preco);
            Tenis.catalogo.add(novoTenis);

            System.out.println("✅ Tênis cadastrado com sucesso!");

            System.out.println("Deseja cadastrar outro tênis? 1 - Sim / 2 - Não");
            continuarCadastro = scanner.nextInt();

        } while (continuarCadastro == 1);
    }

    public void removerProduto(Scanner scanner){

        /// precisa trocar pelo codigo int

        System.out.println("\n--- Remover Produto ---");
        System.out.print("Digite codigo do tenis: ");
        String  codigoRemover = scanner.nextLine();
        removerProduto(scanner);

    }

    public void removerCliente(Scanner scanner){

        System.out.println("\n--- Remover Cliente---");
        System.out.print("Nome do cliente: ");
        String nomeClienteRemover = scanner.nextLine();
        System.out.print("Senha do cliente: ");
        int senhaClienteRemover = scanner.nextInt();
        removerCliente(scanner);
    }

    public void adicionarAdm(Scanner scanner){
         ArrayList<Administrador> listaAdm = new ArrayList<>();

        System.out.print("Digite seu nome: ");
        String nomeAdm = scanner.nextLine();

        System.out.print("Digite sua idade: ");
        int idadeAdm = scanner.nextInt();

        System.out.print("Digite seu CEP: ");
        int cepAdm = scanner.nextInt();

        System.out.print("Digite sua senha (número): ");
        int senhaAdm = scanner.nextInt();

        System.out.println("Cadastro do Administrador realizado com sucesso");

        Administrador novoAdm = new Administrador();

        novoAdm.setNome(nomeAdm);
        novoAdm.setIdade(idadeAdm);
        novoAdm.setCep(cepAdm);
        novoAdm.setSenha(senhaAdm);

        listaAdm.add(novoAdm);

    }

}
