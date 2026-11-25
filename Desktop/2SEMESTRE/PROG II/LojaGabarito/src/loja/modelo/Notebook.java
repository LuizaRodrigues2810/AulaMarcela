package loja.modelo;

public class Notebook extends Produto {

    private int garantiaMeses;

    public Notebook(String codigo, String nome, double precoBase, int estoque, int garantiaMeses) {
        super(codigo, nome, precoBase, estoque);
        this.garantiaMeses = garantiaMeses;
    }

    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    // Preço final = preço base + 50 reais por mês de garantia
    @Override
    public double calcularPrecoFinal() {
        return getPrecoBase() + (garantiaMeses * 50);
    }

    @Override
    public String getCategoria() {
        return "Notebook";
    }
}
