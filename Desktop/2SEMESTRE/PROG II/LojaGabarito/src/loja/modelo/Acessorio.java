package loja.modelo;

public class Acessorio extends Produto implements Promocional {

    private double percentualDesconto;

    public Acessorio(String codigo, String nome, double precoBase, int estoque,
                     double percentualDesconto) {
        super(codigo, nome, precoBase, estoque);
        if (percentualDesconto < 0 || percentualDesconto > 100) {
            throw new IllegalArgumentException("O desconto deve estar entre 0 e 100.");
        }
        this.percentualDesconto = percentualDesconto;
    }

    @Override
    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    @Override
    public double calcularValorDesconto(double valor) {
        return valor * percentualDesconto / 100;
    }

    @Override
    public double calcularPrecoFinal() {
        return getPrecoBase() - calcularValorDesconto(getPrecoBase());
    }

    @Override
    public String getCategoria() {
        return "Acessório";
    }
}
