import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea; 
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Font;

public class Calculador extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField pantalla; 
    private double operando1 = 0, operando2 = 0, resultado = 0; 
    private String operador = ""; 
    private boolean nuevoOperando = true;
    private JButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private JButton btnPunto, btnIgual, btnSuma, btnResta, btnMultiplica, btnDivide, btnLimpiar;
    private JTextArea historial;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Calculador frame = new Calculador();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Calculador() {
        setTitle("Calculadora");
        setSize(400, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(null);

        historial = new JTextArea();
        historial.setBounds(40, 30, 300, 50);  
        historial.setEditable(false);
        historial.setBackground(Color.BLACK);
        historial.setForeground(Color.WHITE);
        historial.setFont(new Font("Arial", Font.PLAIN, 14));
        historial.setLineWrap(true);
        historial.setWrapStyleWord(true);
        getContentPane().add(historial);

        pantalla = new JTextField("0");
        pantalla.setBounds(40, 90, 300, 70);
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setFont(pantalla.getFont().deriveFont(30f));
        pantalla.setEditable(false);
        pantalla.setBackground(Color.BLACK);
        pantalla.setForeground(Color.WHITE);
        pantalla.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        getContentPane().add(pantalla);

        btn7 = crearBoton("7", 40, 160, 60, 60, Color.WHITE);
        btn8 = crearBoton("8", 120, 160, 60, 60, Color.WHITE);
        btn9 = crearBoton("9", 200, 160, 60, 60, Color.WHITE);
        btnDivide = crearBoton("÷", 280, 160, 60, 60, new Color(0xFF6000));

        btn4 = crearBoton("4", 40, 240, 60, 60, Color.WHITE);
        btn5 = crearBoton("5", 120, 240, 60, 60, Color.WHITE);
        btn6 = crearBoton("6", 200, 240, 60, 60, Color.WHITE);
        btnMultiplica = crearBoton("×", 280, 240, 60, 60, new Color(0xFF6000));

        btn1 = crearBoton("1", 40, 320, 60, 60, Color.WHITE);
        btn2 = crearBoton("2", 120, 320, 60, 60, Color.WHITE);
        btn3 = crearBoton("3", 200, 320, 60, 60, Color.WHITE);
        btnResta = crearBoton("-", 280, 320, 60, 60, new Color(0xFF6000));

        btn0 = crearBoton("0", 40, 400, 140, 60, Color.WHITE);
        btnPunto = crearBoton(".", 200, 400, 60, 60, Color.WHITE);
        btnSuma = crearBoton("+", 280, 400, 60, 60, new Color(0xFF6000));

        btnLimpiar = crearBoton("C", 40, 480, 140, 60, new Color(0xFF6000));
        btnIgual = new JButton("=");
        btnIgual.setBounds(200, 480, 140, 60);
        btnIgual.setFont(btnIgual.getFont().deriveFont(18f));
        btnIgual.setBackground(new Color(0xFF6000));
        btnIgual.setForeground(Color.WHITE);
        btnIgual.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btnIgual.setOpaque(true);
        btnIgual.setContentAreaFilled(true);
        getContentPane().add(btnIgual);

        agregarAccionABotones();
    }

    private JButton crearBoton(String texto, int x, int y, int ancho, int alto, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setFont(boton.getFont().deriveFont(25f));
        boton.setContentAreaFilled(true);
        boton.setBackground(new Color(0x2B2B2B));
        boton.setForeground(colorTexto);
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        boton.setOpaque(true);
        getContentPane().add(boton);
        return boton;
    }

    private void agregarAccionABotones() {
        btn0.addActionListener(this);
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);
        btn6.addActionListener(this);
        btn7.addActionListener(this);
        btn8.addActionListener(this);
        btn9.addActionListener(this);

        btnSuma.addActionListener(this);
        btnResta.addActionListener(this);
        btnMultiplica.addActionListener(this);
        btnDivide.addActionListener(this);

        btnIgual.addActionListener(this);
        btnLimpiar.addActionListener(e -> limpiarPantalla());
        btnPunto.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        try {
            if ("0123456789.".contains(comando)) {
                if (nuevoOperando) {
                    pantalla.setText(comando);
                    nuevoOperando = false;
                } else {
                    pantalla.setText(pantalla.getText() + comando);
                }
            } else if ("+-×÷".contains(comando)) {
                operando1 = Double.parseDouble(pantalla.getText());
                operador = comando;
                nuevoOperando = true;
            } else if ("=".equals(comando)) {
                operando2 = Double.parseDouble(pantalla.getText());
                resultado = calcular(operando1, operando2, operador);
                pantalla.setText(String.valueOf(resultado));

                historial.append(operando1 + " " + operador + " " + operando2 + " = " + resultado + "\n");

                nuevoOperando = true;
            }
        } catch (NumberFormatException ex) {
            pantalla.setText("Error");
            nuevoOperando = true;
        }
    }

    private void limpiarPantalla() {
        pantalla.setText("0");
        operando1 = operando2 = resultado = 0;
        operador = "";
        nuevoOperando = true;
    }

    private double calcular(double operando1, double operando2, String operador) {
        return switch (operador) {
            case "+" -> operando1 + operando2;
            case "-" -> operando1 - operando2;
            case "×" -> operando1 * operando2;
            case "÷" -> operando1 / operando2;
            default -> 0;
        };
    }
}
