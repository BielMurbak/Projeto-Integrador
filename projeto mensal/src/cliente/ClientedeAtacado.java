package cliente;


import java.util.Scanner;

public class ClientedeAtacado extends Cliente {
    private double descontoEspecial;
    private String cnpj;

    public double getDescontoEspecial() {
        return descontoEspecial;
    }

    public void setDescontoEspecial(double descontoEspecial) {
        this.descontoEspecial = descontoEspecial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void cadastrarCliente(Scanner scanner){
        super.cadastrarCliente(scanner); //aqui chamo aquele metodo dos clientes
        scanner.nextLine(); ///limpa o campo

        System.out.print("Digite o seu CNPJ: ");
        this.cnpj = scanner.nextLine();

        System.out.print("Digite o desconto especial: ");
        this.descontoEspecial = scanner.nextDouble();

        System.out.println("Cadastro de Cliente Atacado realizado com sucesso");
    }

}
