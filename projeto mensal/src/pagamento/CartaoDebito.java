package pagamento;

public class CartaoDebito extends pagamento.Pagamento {

    @Override
    public void realizarPagamento(double valor) {

        valor = valor * 0.3;

        System.out.printf("Total com cartao de debito:%.2f",valor);
    }
}
