package sistema;

import cliente.Cliente;
import cliente.ClientedeAtacado;
import cliente.Login;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Cliente cliente = new Cliente();
        ClientedeAtacado clienteAtacado = new ClientedeAtacado();
        Login login = new Login();
        sistema.Administrador adm = new sistema.Administrador();

        int aux = 0;
        String auxB;
        do {
            System.out.println("\n");
            System.out.println("---Sistema e-commerce---");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Cadastrar");
            System.out.println("3 - Encerrar programa");
            System.out.println("Escolha uma opção:");
            aux = scanner.nextInt();
            scanner.nextLine();

            switch (aux) {
                case 1:
                    System.out.println("\n");
                    System.out.println("Escolha o tipo de usuário para login:");
                    System.out.println("1 - Cliente Varejo");
                    System.out.println("2 - Cliente Atacado");
                    System.out.println("3 - Administrador");
                    System.out.print("Digite a opção desejada: ");
                    int tipoUsuario = scanner.nextInt();
                    scanner.nextLine();  // Clear the buffer after nextInt()

                    switch (tipoUsuario) {
                        case 1:  // Cliente Varejo login
                            System.out.println("Digite seu nome de login (Cliente Varejo): ");
                            String nomeLoginVarejo = scanner.nextLine();
                            System.out.println("Digite sua senha de login: ");
                            int senhaLoginVarejo = scanner.nextInt();
                            login.setNomeLogin(nomeLoginVarejo);
                            login.setSenhaLogin(senhaLoginVarejo);
                            login.realizarLoginCliente(cliente);
                            break;
                        case 2:  // Cliente Atacado login
                            System.out.println("Digite seu nome de login (Cliente Atacado): ");
                            String nomeLoginAtacado = scanner.nextLine();
                            System.out.println("Digite sua senha de login: ");
                            int senhaLoginAtacado = scanner.nextInt();
                            login.setNomeLogin(nomeLoginAtacado);
                            login.setSenhaLogin(senhaLoginAtacado);
                            login.realizarLoginClienteAtacado(clienteAtacado);
                            break;
                        case 3:  // Admin login
                            System.out.println("Digite seu nome de login (Administrador): ");
                            String nomeLoginAdmin = scanner.nextLine();
                            System.out.println("Digite sua senha de login: ");
                            int senhaLoginAdmin = scanner.nextInt();
                            login.setNomeLogin(nomeLoginAdmin);
                            login.setSenhaLogin(senhaLoginAdmin);
                            login.realizarloginAdm(adm);
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                    }
                    break;

                case 2:
                    System.out.println("Escolha o tipo de cadastro:");
                    System.out.println("1 - Cliente Varejo");
                    System.out.println("2 - Cliente Atacado");
                    System.out.print("Digite a opção desejada: ");
                    int tipoCadastro = scanner.nextInt();
                    scanner.nextLine();

                    switch (tipoCadastro) {
                        case 1:  // Cliente Varejo registration
                            cliente.cadastrarCliente(scanner);
                            System.out.println("Cadastro de Cliente Varejo concluído.");
                            break;
                        case 2:  // Cliente Atacado registration
                            clienteAtacado.cadastrarCliente(scanner);
                            System.out.println("Cadastro de Cliente Atacado concluído.");
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                    }
                    break;

                case 3:  // Exit the program
                    System.out.println("Programa Encerrado com sucesso.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Erro! Número digitado é inválido.");
            }

        } while (aux != 3);  // Keep looping until the user chooses to exit (option 3)


    }//fecha void main
}//fecha class main