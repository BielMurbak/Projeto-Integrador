package pagamento;

public class Dinheiro extends Pagamento{

    @Override
    public void realizarPagamento(double valor) {

        valor = valor - 15;

        System.out.printf("Total com dinheiro:%.2f",valor);
    }
}
