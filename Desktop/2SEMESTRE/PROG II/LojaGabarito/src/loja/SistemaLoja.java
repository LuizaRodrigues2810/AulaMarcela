package loja;

import loja.excecoes.EstoqueInsuficienteException;
import loja.excecoes.ProdutoNaoEncontradoException;
import loja.modelo.*;

import java.util.ArrayList;

public class SistemaLoja {

    public static void main(String[] args) {

        // ===== PASSO 1: criar a loja e os produtos (AGREGAÇÃO: criados fora da loja) =====
        Loja loja = new Loja("Loja Tech");

        Notebook notebook = new Notebook("N001", "Notebook Gamer", 4000.0, 5, 12);
        Celular celular = new Celular("C001", "Smartphone X", 2000.0, 10, 128, 10.0);
        Acessorio fone = new Acessorio("A001", "Fone Bluetooth", 200.0, 20, 20.0);
        Acessorio mouse = new Acessorio("A002", "Mouse", 100.0, 0, 0.0);

        loja.cadastrarProduto(notebook);
        loja.cadastrarProduto(celular);
        loja.cadastrarProduto(fone);
        loja.cadastrarProduto(mouse);

        // ===== PASSO 2: POLIMORFISMO =====
        // A lista é de Produto, mas cada objeto executa o SEU calcularPrecoFinal().
        System.out.println("--- Passo 2: Polimorfismo ---");
        ArrayList<Produto> lista = loja.getProdutos();
        for (Produto p : lista) {
            System.out.printf("%s: R$ %.2f%n", p.getCategoria(), p.calcularPrecoFinal());
        }

        // ===== PASSO 3: médias e totais =====
        System.out.println("\n--- Passo 3: Preço médio e estoque ---");
        System.out.printf("Preço médio do catálogo: R$ %.2f%n", loja.calcularPrecoMedioCatalogo());
        System.out.printf("Valor total do estoque: R$ %.2f%n", loja.calcularValorTotalEstoque());

        // ===== PASSO 4: INTERFACE =====
        System.out.println("\n--- Passo 4: Interface Promocional ---");
        for (Produto p : lista) {
            if (p instanceof Promocional) {                 // este produto cumpre o contrato?
                Promocional promo = (Promocional) p;        // trata como Promocional
                System.out.println(p.getNome() + " tem desconto de " + promo.getPercentualDesconto() + "%");
            }
        }
        Promocional promoCelular = celular;   // Celular pode ser guardado numa variável Promocional
        System.out.println("Desconto do celular sobre R$ 1000: R$ "
                + promoCelular.calcularValorDesconto(1000));

        // ===== PASSO 5: avaliações (composição: criadas dentro do produto) =====
        notebook.avaliar("Ana", 5, "Excelente");
        notebook.avaliar("Bia", 4, "Muito bom");
        notebook.avaliar("Carla", 5, "Perfeito");

        celular.avaliar("Duda", 3, "Regular");
        celular.avaliar("Eva", 4, "Bom");

        fone.avaliar("Fabi", 5, "Ótimo");
        fone.avaliar("Gabi", 5, "Ótimo");
        fone.avaliar("Helo", 4, "Bom");
        fone.avaliar("Isa", 4, "Bom");

        // ===== PASSO 6: médias das avaliações =====
        System.out.println("\n--- Passo 6: Avaliações ---");
        for (Produto p : lista) {
            System.out.printf("Média de %s: %.2f%n", p.getNome(), p.calcularMediaAvaliacoes());
        }
        System.out.printf("Média geral das avaliações: %.2f%n", loja.calcularMediaGeralAvaliacoes());
        Produto melhor = loja.buscarMelhorAvaliado();
        System.out.println("Melhor avaliado: " + melhor.getNome());

        // ===== PASSO 7: pedido da Marina =====
        System.out.println("\n--- Passo 7: Pedido da Marina ---");
        Pedido pedido = new Pedido("Marina");
        try {
            pedido.adicionarItem(celular, 2);
            pedido.adicionarItem(fone, 3);
            pedido.adicionarItem(notebook, 1);
        } catch (EstoqueInsuficienteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.printf("Total: R$ %.2f%n", pedido.calcularTotal());
        System.out.println("Unidades: " + pedido.contarUnidades());
        System.out.printf("Preço médio por unidade: R$ %.2f%n", pedido.calcularPrecoMedioPorUnidade());

        // ===== PASSO 8: estoque depois do pedido =====
        System.out.println("\n--- Passo 8: Estoque depois do pedido ---");
        System.out.printf("Valor total do estoque: R$ %.2f%n", loja.calcularValorTotalEstoque());
        System.out.println("Produtos sem estoque:");
        for (Produto p : loja.listarSemEstoque()) {
            System.out.println(" - " + p.getNome());
        }

        // ===== TESTES DE EXCEÇÃO =====
        System.out.println("\n===== TESTES DE EXCEÇÃO =====");

        // 9: estoque insuficiente (só restam 4 notebooks)
        try {
            pedido.adicionarItem(notebook, 10);
        } catch (EstoqueInsuficienteException e) {
            System.out.println("9) " + e.getMessage());
            System.out.println("   Solicitado: " + e.getQuantidadeSolicitada()
                    + " | Disponível: " + e.getQuantidadeDisponivel());
        }

        // 10: produto sem estoque
        try {
            pedido.adicionarItem(mouse, 1);
        } catch (EstoqueInsuficienteException e) {
            System.out.println("10) " + e.getMessage());
        }

        // 11: quantidade negativa -> IllegalArgumentException (não verificada)
        try {
            pedido.adicionarItem(fone, -1);
        } catch (EstoqueInsuficienteException e) {
            System.out.println("11) Estoque: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("11) Dado inválido: " + e.getMessage());
        }

        // 12: produto não encontrado
        try {
            loja.buscarPorCodigo("X999");
        } catch (ProdutoNaoEncontradoException e) {
            System.out.println("12) " + e.getMessage());
        }

        // 13: nota inválida
        try {
            notebook.avaliar("Joana", 7, "Nota alta demais");
        } catch (IllegalArgumentException e) {
            System.out.println("13) " + e.getMessage());
        }

        // 14: código repetido
        try {
            loja.cadastrarProduto(new Acessorio("A001", "Fone Repetido", 50.0, 1, 0.0));
        } catch (IllegalArgumentException e) {
            System.out.println("14) " + e.getMessage());
        }

        // 15: preço negativo
        try {
            Acessorio ruim = new Acessorio("A099", "Cabo", -10.0, 1, 0.0);
        } catch (IllegalArgumentException e) {
            System.out.println("15) " + e.getMessage());
        }

        // 16: o pedido NÃO mudou depois das exceções
        System.out.printf("16) Total do pedido continua: R$ %.2f%n", pedido.calcularTotal());

        // 17: multi-catch + finally
        try {
            Produto p = loja.buscarPorCodigo("N001");
            pedido.adicionarItem(p, 100);
        } catch (EstoqueInsuficienteException | ProdutoNaoEncontradoException e) {
            System.out.println("17) Erro tratado: " + e.getMessage());
        } finally {
            System.out.println("17) Operação finalizada");   // roda SEMPRE
        }

        // 18: exceções do próprio Java
        try {
            int numero = Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("18a) Não é número: " + e.getMessage());
        }

        try {
            loja.getProdutos().get(99);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("18b) Posição inexistente na lista: " + e.getMessage());
        }

        try {
            int a = 10;
            int b = 0;
            int resultado = a / b;
        } catch (ArithmeticException e) {
            System.out.println("18c) Divisão por zero: " + e.getMessage());
        }

        // 19: com double NÃO lança exceção. O resultado é Infinity (infinito).
        // int / 0  -> ArithmeticException.   double / 0 -> Infinity (e 0.0/0 -> NaN).
        double infinito = 10.0 / 0;
        System.out.println("19) 10.0 / 0 = " + infinito);

        // 20: Produto é abstrata, então a linha abaixo NÃO compila:
        // Produto p = new Produto("X", "Y", 10.0, 1);
        // Erro: 'Produto' is abstract; cannot be instantiated
    }
}
