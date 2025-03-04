package cliente;

import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
    private String nome;
    private int idade;
    private int cep;
    private int senha;
    static ArrayList<Cliente> catalogoCliente = new ArrayList<>();

    //getters and setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public int  getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    //metodo for cadastrar clientes
    public void cadastrarCliente(Scanner scanner){
        System.out.print("Digite seu nome: ");
        this.nome = scanner.nextLine();

        System.out.print("Digite sua idade: ");
        this.idade = scanner.nextInt();

        System.out.print("Digite seu CEP: ");
        this.cep = scanner.nextInt();

        System.out.print("Digite sua senha (número): ");
        this.senha = scanner.nextInt();

        System.out.println("Cadastro do cliente realizado com sucesso");

        // Cria um novo cliente com os dados informados
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome);
        novoCliente.setIdade(idade);
        novoCliente.setCep(cep);
        novoCliente.setSenha(senha);

        catalogoCliente.add(novoCliente);

    }//close the cadastrarCliente


    static boolean removerCliente(String nomeClienteRemover ,int senhaClienteRemover ){
        for (Cliente cliente : catalogoCliente) {
            if (cliente.nome.equalsIgnoreCase(nomeClienteRemover)) {
                if(cliente.senha == senhaClienteRemover) {
                    catalogoCliente.remove(cliente);
                    System.out.println("✅ Cliente removido com sucesso!");
                    return true;
                }
            }
        }
        System.out.println("✅ Cliente nao foi encotrado!");
        return false;
    }

}//close the class Cliente

