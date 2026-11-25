package loja.modelo;

// INTERFACE: é um "contrato". Quem implementar PROMETE ter estes dois métodos.
// Na interface só escrevemos a assinatura, sem o corpo.
public interface Promocional {

    double getPercentualDesconto();

    double calcularValorDesconto(double valor);
}
