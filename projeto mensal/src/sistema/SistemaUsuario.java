package sistema;

import pagamento.*;
import produtos.Tenis;

import java.util.Scanner;

import static produtos.Tenis.catalogo;
import static produtos.Tenis.*;

public class SistemaUsuario {
    public void sistemaUsuario() {

        Scanner scanner = new Scanner(System.in);
        Tenis tenis = new Tenis(null, 0, 0, 0);
        Pagamento pagamentoCredito = new CartaoDeCredito();
        Pagamento pagamentoDebito = new CartaoDebito();
        Pagamento pagamentoPix = new Pix();
        Pagamento pagamentoDinheiro = new Dinheiro();

        Endereco Endereco = new Endereco();


        int auxI = 0;
        double total = 0;
        int continuarCadastro = 0;

        catalogo.add(new Tenis("Nike Air Max", 1, 10, 499.99));
        catalogo.add(new Tenis("Adidas Ultra Boost", 2, 5, 599.99));
        catalogo.add(new Tenis("Puma RS-X", 3, 8, 349.99));

        do {
            System.out.println("Sistema E-commerce");
            System.out.println("1-Nossos Produtos");
            System.out.println("2-Carrinho");
            System.out.println("3-Pedido");
            System.out.println("4-Suporte ao Cliente");
            System.out.println("5-Suguraça e Privacidade");
            System.out.println("6-Sair");
            auxI = scanner.nextInt();

            switch (auxI) {
                case 1:
                    do {
                        exibirCatalogo();
                        System.out.println("Digite codigo do produto:");
                        int codigoProduto = scanner.nextInt();
                        System.out.println("Digite a quantidade:");
                        int quantidadeProduto = scanner.nextInt();
                        tenis.condicionaisDeCompra(codigoProduto, quantidadeProduto, total);
                        System.out.println("Deseja comprar outro tênis? 1 - Sim / 2 - Não");
                        continuarCadastro = scanner.nextInt();
                    } while (continuarCadastro == 1);
                    break;
                case 2:
                    for (Tenis t : catalogo) {
                        System.out.printf("Nome: %-20s | Código: %-10s | Quantidade: %-5d | Preço: R$ %.2f\n", t.getNome(), t.getCodigo(), t.getQuantidade(), t.getPreco());
                        System.out.println("Total:" + total);
                        System.out.println("---- Pagamento ----");
                        System.out.println("1-Cartao de credito");
                        System.out.println("2-Cartao de debito");
                        System.out.println("3-Pix");
                        System.out.println("4-Dinheiro");
                        System.out.print("Digite sua opcao:");
                        int opcaoPagamento = scanner.nextInt();
                        if (opcaoPagamento == 1) {
                            pagamentoCredito.realizarPagamento(total);
                        } else if (opcaoPagamento == 2) {
                            pagamentoDebito.realizarPagamento(total);
                        } else if (opcaoPagamento == 3) {
                            pagamentoPix.realizarPagamento(total);
                        } else if (opcaoPagamento == 4) {
                            pagamentoDinheiro.realizarPagamento(total);

                        } else {
                            System.out.println("Erro! Número digitado é inválido.");
                        }
                    }
                    break;
                case 3:
                    /// pedido
                    break;
                case 4:
                    mostrarSuporteAoCliente(scanner);
                case 5:
                    mostrarSegurancaPrivacidade(scanner);
                case 6:
                    System.out.println("Programa foi encerrado com sucesso!");
                    System.exit(0);
                default:
                    System.out.println("Erro! numero digitado é invalido");
            }
        } while (auxI >= 7 || auxI <= 0); //ver se a logica ta certa
    }


    private void mostrarSegurancaPrivacidade(Scanner scanner) {
        int escolhaSegurancaPrivacidade = 0;

        do {
            System.out.println("\n--- Segurança e Privacidade ---");
            System.out.println("1 - Exibir política de privacidade");
            System.out.println("2 - Alterar senha");
            System.out.println("3 - Configurações de segurança");
            System.out.println("4 - Logout");
            System.out.println("5 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            escolhaSegurancaPrivacidade = scanner.nextInt();

            switch (escolhaSegurancaPrivacidade) {
                case 1:
                    System.out.println("\nPolítica de Privacidade: \n- Nós respeitamos a sua privacidade e não compartilhamos seus dados com terceiros...");
                    break;
                case 2:
                    //
                    break;
                case 3:
                    //configuracoes de seguranca
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Erro! digite uma opcao valida!");

            }

        } while (escolhaSegurancaPrivacidade < 1 || escolhaSegurancaPrivacidade > 5);
    }

    private void mostrarSuporteAoCliente(Scanner scanner) {
        int escolhaSuporte = 0;

        do {
            System.out.println("\n--- Suporte ao Cliente ---");
            System.out.println("1 - FAQ (Perguntas Frequentes)");
            System.out.println("2 - Abrir um ticket de suporte");
            System.out.println("3 - Informações de contato");
            System.out.println("4 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            escolhaSuporte = scanner.nextInt();
/// //
            switch (escolhaSuporte) {
                case 1:
                    System.out.println("\nFAQ - Perguntas Frequentes:");
                    System.out.println("1-Como posso realizar um pedido?");
                    System.out.println("Entre em contato conosco para obter mais informações sobre este pedido.");
                    System.out.println("2-Como faço para devolver um produto?");
                    System.out.println("Entre em contato conosco para obter mais informações sobre sua situaçao.");
                    System.out.println("Telefone: 0800-123-456");
                    System.out.println("E-mail: suporte@ecommerce.com");
                    break;
                case 2:
                    System.out.println("\nAbrindo um ticket de suporte...");
                    System.out.println("\nDigite seu nome inteiro:");
                    String nometicket = scanner.nextLine();
                    System.out.println("\nDigite sua duvida ou problema:");
                    String duvida = scanner.nextLine();
                    System.out.println("\nTicket foi enviado com sucesso.");
                    break;
                case 3:
                    System.out.println("\nInformações de Contato:");
                    System.out.println("Telefone: 0800-123-456");
                    System.out.println("E-mail: suporte@ecommerce.com");
                    break;
                case 4:
                    System.out.println("\nVoltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolhaSuporte < 1 || escolhaSuporte > 4);
    }
}

    /*private void pedidoEntregaRetirada(Scanner scanner) {
        System.out.println("---- Pedido ----");
        System.out.println("1-Entrega");
        System.out.println("2-Retirada");
        System.out.print("Digite sua opcao:");
        int op = scanner.nextInt();
        if (op == 1) {
            System.out.print("Digite seu cep");
            int pedidoCep = scanner.nextInt();

            String urlString = "https://viacep.com.br/ws/" + pedidoCep + "/json/";

            try {
                // Cria a URL
                URL url = new URL(urlString);

                // Abre a conexão
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1000);
                connection.setReadTimeout(1000);

                // Obtém o código de resposta
                int status = connection.getResponseCode();
                if (status == 200) {
                    // Lê a resposta
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Usa o ObjectMapper para deserializar o JSON em um objeto Endereco
                    ObjectMapper objectMapper = new ObjectMapper();
                    Endereco endereco = objectMapper.readValue(response.toString(), Endereco.class);

                    // Exibe os dados do endereço
                    System.out.println("Dados do Endereço: ");
                    System.out.println("CEP: " + endereco.getCep());
                    System.out.println("Logradouro: " + endereco.getLogradouro());
                    System.out.println("Bairro: " + endereco.getBairro());
                    System.out.println("Localidade: " + endereco.getLocalidade());
                    System.out.println("UF: " + endereco.getUf());
                } else {
                    System.out.println("Erro na requisição: " + status);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }//acaba aqui

        } else if (op == 2) {
            /// nao sei
        } else {
            System.out.println("Erro! numero digitado é invalido");
        */

