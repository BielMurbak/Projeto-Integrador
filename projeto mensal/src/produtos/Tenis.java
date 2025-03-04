package produtos;

import java.util.ArrayList;
import java.util.Comparator;

public class Tenis {
    private String nome;
    private int codigo;
    private int quantidade;
    private double preco;
    public static ArrayList<Tenis> catalogo = new ArrayList<>();


    public Tenis(String nome, int codigo, int quantidade, double preco) {
        this.nome = nome;
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        if (preco > 0) {
            this.preco = preco;
        } else {
            System.out.println("Preço inválido! Definindo como R$ 0.00.");
            this.preco = 0.00;
        }
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade >= 0) {
            this.quantidade = quantidade;
        } else {
            System.out.println("Quantidade inválida! Definindo como 0.");
            this.quantidade = 0;
        }
    }

    //metodo removerProduto

    /// precisa trocar pelo codigo int
    public static boolean removerProduto(String nomeRemover) {
        for (Tenis tenis : catalogo) {
            if (tenis.nome.equalsIgnoreCase(nomeRemover)) {
                catalogo.remove(tenis);
                System.out.println("✅ Tênis foi removido com sucesso!");
                return true;
            }
        }
        System.out.println("✅ Tênis nao foi encotrado!");
        return false;
    }

    public static void exibirCatalogo() {
        if (catalogo.isEmpty()) {
            System.out.println("O catálogo está vazio.");
        } else {
            catalogo.sort(Comparator.comparingInt(tenis -> tenis.codigo)); //codigo que ordena os codigos dos produtos em ordem crescente começando em cima
            System.out.println("Catálogo de Tênis:");
            for (Tenis tenis : catalogo) {
                System.out.printf("Nome: %-20s | Código: %-10s | Quantidade: %-5d | Preço: R$ %.2f\n", tenis.nome, tenis.codigo, tenis.quantidade, tenis.preco);
            }
        }

    }

    public void condicionaisDeCompra(int codigoProduto, int quantidadeProduto, double total) {

        for(Tenis tenis:catalogo){
            if(tenis.codigo==codigoProduto){
                if(tenis.quantidade>=quantidadeProduto){
                    total=tenis.preco*quantidadeProduto;
                    tenis.setQuantidade(tenis.quantidade - quantidadeProduto);
                    System.out.println("✅ Compra realizada com sucesso!");
                    System.out.println("Total:"+total);

                }else{
                    System.out.println("✅ Erro! Quantidade insuficiente em estoque!");

                }
            }
        }
        System.out.println("Produto não encontrado!");

    }
}