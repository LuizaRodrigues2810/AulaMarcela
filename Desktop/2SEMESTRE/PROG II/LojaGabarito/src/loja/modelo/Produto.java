package loja.modelo;

import loja.excecoes.EstoqueInsuficienteException;
import java.util.ArrayList;

// CLASSE ABSTRATA: não pode ser instanciada (new Produto(...) dá erro).
// Serve de molde para as filhas: Notebook, Celular e Acessorio.
public abstract class Produto {

    private String codigo;
    private String nome;
    private double precoBase;
    private int estoque;
    private ArrayList<Avaliacao> avaliacoes = new ArrayList<>();

    public Produto(String codigo, String nome, double precoBase, int estoque) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("O código não pode ser nulo nem vazio.");
        }
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo nem vazio.");
        }
        if (precoBase <= 0) {
            throw new IllegalArgumentException("O preço base deve ser maior que zero.");
        }
        if (estoque < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo.");
        }
        this.codigo = codigo;
        this.nome = nome;
        this.precoBase = precoBase;
        this.estoque = estoque;
    }

    public abstract double calcularPrecoFinal();

    public abstract String getCategoria();

    public void avaliar(String cliente, int nota, String comentario) {
        Avaliacao avaliacao = new Avaliacao(cliente, nota, comentario);
        avaliacoes.add(avaliacao);
    }

    public double calcularMediaAvaliacoes() {
        if (avaliacoes.isEmpty()) {
            return 0;
        }
        double soma = 0;
        for (Avaliacao a : avaliacoes) {
            soma += a.getNota();
        }
        return soma / avaliacoes.size();
    }
    public void retirarEstoque(int quantidade) throws EstoqueInsuficienteException {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        if (quantidade > estoque) {
            throw new EstoqueInsuficienteException(quantidade, estoque);
        }
        estoque = estoque - quantidade;
    }

    public void reporEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        estoque = estoque + quantidade;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public int getEstoque() {
        return estoque;
    }

    public ArrayList<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    @Override
    public String toString() {
        return codigo + " - " + nome + " (" + getCategoria() + ") - R$ "
                + String.format("%.2f", calcularPrecoFinal());
    }
}
