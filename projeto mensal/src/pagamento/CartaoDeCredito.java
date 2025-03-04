package pagamento;

import java.util.Scanner;

public class CartaoDeCredito extends Pagamento {

    Scanner scanner = new Scanner(System.in);

    @Override
    public void realizarPagamento(double valor) {
        System.out.println("Digite o numero de parcelas:");
        int parcelas = scanner.nextInt();
        if(parcelas<3){
            System.out.printf("Total com %d parcelas:%.2f",parcelas,valor);
        }
        if(parcelas==4){
            valor = valor * 0.10;
            System.out.printf("Total com %d parcelas:%.2f",parcelas,valor);
        }
        if(parcelas==5){
            valor = valor * 0.15;
            System.out.printf("Total com %d parcelas:%.2f",parcelas,valor);
        }
        if(parcelas==6){
            valor = valor * 0.20;
            System.out.printf("Total com %d parcelas:%.2f",parcelas,valor);
        }
        if(parcelas>6){
            valor = valor * 0.5;
            System.out.printf("Total com %d parcelas:%.2f",parcelas,valor);
        }
    }

}
