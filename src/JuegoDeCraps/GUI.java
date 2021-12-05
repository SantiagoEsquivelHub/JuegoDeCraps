package JuegoDeCraps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is used for as view Craps Class
 * @autor Paola-J Rodriguez-C paola.rodriguez@correounivalle.edu.co
 * @version v.1.0.0 date:21/11/2021
 */
public class GUI extends JFrame {
    public static final String MENSAJE_INICIO =
            "Bienvenidos a Craps \n"
                    + "Oprime el botón lanzar para iniciar el juego"
                    + "\nSi tu tiro de salida es 7 u 11, ganas con Natural"
                    + "\nSi tu tiro de salida es 2, 3 o 12, pierdes con Craps"
                    + "\nSi sacas cualquier otro valor establecerás el Punto"
                    + "\nEstado en punto podrás seguir lanzando los dados"
                    + "\npero ahora ganarás si sacas nuevamente el valor del Punto"
                    + "\nsin que previamente hayas sacado 7"
            ;

    private Header headerProject;
    private JLabel dado1, dado2;
    private JButton lanzar;
    private JPanel panelDatos, panelResultatos;
    private ImageIcon imageDado;
    private JTextArea resultadosDados, mensajeSalida;
    private JSeparator separator;
    private Escucha escucha;
    private ModelCraps modelCraps;

    /**
     * Constructor of GUI class
     */
    public GUI(){
        initGUI();

        //Default JFrame configuration
        this.setTitle("Juego Craps");
        this.setSize(700,250);
        //this.pack();
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method is used to set up the default JComponent Configuration,
     * create Listener and control Objects used for the GUI class
     */
    private void initGUI() {
        //Set up JFrame Container's Layout
        //Create Listener Object and Control Object
        //Set up JComponents
        escucha = new Escucha();
        modelCraps = new ModelCraps();
        headerProject = new Header("Mesa Juego Craps", Color.BLACK);

        this.add(headerProject,BorderLayout.NORTH);

        imageDado = new ImageIcon(getClass().getResource("/assets/dado.png"));
        dado1 = new JLabel(imageDado);
        dado2 = new JLabel(imageDado);

        lanzar = new JButton("Lanzar");
        lanzar.addActionListener(escucha);

        panelDatos = new JPanel();
        panelDatos.setPreferredSize(new Dimension(300,180));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Tus Dados"));
        panelDatos.add(dado1);
        panelDatos.add(dado2);
        panelDatos.add(lanzar);

        this.add(panelDatos, BorderLayout.CENTER);

        mensajeSalida = new JTextArea(7, 31);
        mensajeSalida.setText(MENSAJE_INICIO);
        //mensajeSalida.setBorder(BorderFactory.createTitledBorder("Qué debes hacer"));
        JScrollPane scroll = new JScrollPane(mensajeSalida);

        panelResultatos = new JPanel();
        panelResultatos.setBorder(BorderFactory.createTitledBorder("Qué debes hacer"));
        panelResultatos.add(scroll);
        panelResultatos.setPreferredSize(new Dimension(370, 180));


        this.add(panelResultatos, BorderLayout.EAST);

        resultadosDados = new JTextArea(4,31);
        resultadosDados.setEditable(false);
        separator = new JSeparator();
        separator.setPreferredSize(new Dimension(320, 7));
        separator.setBackground(Color.BLUE);
    }

    /**
     * Main process of the Java program
     * @param args Object used in order to send input data from command line when
     *             the program is execute by console.
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            GUI miProjectGUI = new GUI();
        });
    }

    /**
     * inner class that extends an Adapter Class or implements Listeners used by GUI class
     */
    private class Escucha implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            modelCraps.calcularTiro();
            int[] caras = modelCraps.getCaras();
            imageDado = new ImageIcon(getClass().getResource("/assets/"+caras[0]+".png"));
            dado1.setIcon(imageDado);
            imageDado = new ImageIcon(getClass().getResource("/assets/"+caras[1]+".png"));
            dado2.setIcon(imageDado);
            modelCraps.determinarJuego();

            panelResultatos.removeAll();
            panelResultatos.setBorder(BorderFactory.createTitledBorder("Resultados"));
            panelResultatos.add(resultadosDados);
            panelResultatos.add(separator);
            panelResultatos.add(mensajeSalida);
            resultadosDados.setText(modelCraps.getEstadoToString()[0]);
            mensajeSalida.setRows(4);
            mensajeSalida.setText(modelCraps.getEstadoToString()[1]);
            revalidate();
            repaint();
        }
    }
}
