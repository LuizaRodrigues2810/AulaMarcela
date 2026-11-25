import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroCliente extends JFrame {

    private JLabel labelNome;
    private JTextField fieldNome;
    private JButton buttonCadastrar;

    public TelaCadastroCliente(){
        initComponents();
    }
    public void initComponents(){
        labelNome = new JLabel("Nome: ");
        fieldNome = new JTextField();
        buttonCadastrar = new JButton("Cadastrar");

        this.setTitle("Cadastrar Cliente");
        this.setSize(400, 300);

        this.setResizable(false);
        this.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE);

        this.getContentPane().setLayout(null);

        //30 pixels à direita da origem horizontal
        //80 pixels abaixo da origem
        // largura de 70 pixels
        //altura de 20 pixels

        labelNome.setBounds(30,80,70,20);
        this.add(labelNome);

        fieldNome.setBounds(90,80,250,20);
        this.add(fieldNome);

        buttonCadastrar.setBounds(90,180,120,30);
        this.add(buttonCadastrar);
        buttonCadastrar.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nome = fieldNome.getText();
                        Cliente cliente = new Cliente(nome);
                        JOptionPane.showMessageDialog(null, " Cliente cadastrado com sucesso!" +
                                " Nome: " + cliente.getNome());
                    }
                }
        );
        this.setVisible(true);
    }

    void main(){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new TelaCadastroCliente();
                    }
                }
        );
    }

}
