package pagamento;

public class Pix extends Pagamento {

    public void realizarPagamento(double valor) {

        valor = valor - 10;

        System.out.printf("Total no pix:%.2f",valor);

    }
}
