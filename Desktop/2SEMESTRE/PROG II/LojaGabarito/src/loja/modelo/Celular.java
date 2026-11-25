package loja.modelo;

// HERANÇA + INTERFACE: Celular é um Produto E cumpre o contrato Promocional.
public class Celular extends Produto implements Promocional {

    private int armazenamentoGb;
    private double percentualDesconto;

    public Celular(String codigo, String nome, double precoBase, int estoque,
                   int armazenamentoGb, double percentualDesconto) {
        super(codigo, nome, precoBase, estoque);
        if (percentualDesconto < 0 || percentualDesconto > 100) {
            throw new IllegalArgumentException("O desconto deve estar entre 0 e 100.");
        }
        this.armazenamentoGb = armazenamentoGb;
        this.percentualDesconto = percentualDesconto;
    }

    public int getArmazenamentoGb() {
        return armazenamentoGb;
    }

    @Override
    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    @Override
    public double calcularValorDesconto(double valor) {
        return valor * percentualDesconto / 100;
    }

    // Passo 1: soma 5% de taxa.  Passo 2: tira o desconto.
    @Override
    public double calcularPrecoFinal() {
        double precoComTaxa = getPrecoBase() * 1.05;
        double desconto = calcularValorDesconto(precoComTaxa);
        return precoComTaxa - desconto;
    }

    @Override
    public String getCategoria() {
        return "Celular";
    }
}
