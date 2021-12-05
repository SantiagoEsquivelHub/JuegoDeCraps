package JuegoDeCraps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIGridBagLayout extends JFrame {
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
    private JButton lanzar, ayuda, salir;
    private JPanel panelDatos;
    private ImageIcon imageDado;
    private JTextArea resultadosDados, mensajeSalida;
    private Escucha escucha;
    private ModelCraps modelCraps;

    public GUIGridBagLayout(){
        initGUI();
        //Default JFrame configuration
        this.setTitle("Juego Craps");
        this.setUndecorated(true);
        this.setBackground(new Color(255,255,255,0));
        this.pack();
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initGUI() {
        //Set up JFrame Container's Layout
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //Create Listener Object and Control Object
        //Set up JComponents
        escucha = new Escucha();
        modelCraps = new ModelCraps();
        headerProject = new Header("Mesa Juego Craps", Color.BLACK);
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.gridwidth=2;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        this.add(headerProject,constraints);


        ayuda = new JButton(" ? ");
        ayuda.addActionListener(escucha);
        constraints.gridx=0;
        constraints.gridy=1;
        constraints.gridwidth=1;
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.LINE_START;
        this.add(ayuda,constraints);

        salir = new JButton("Salir");
        salir.addActionListener(escucha);
        constraints.gridx=1;
        constraints.gridy=1;
        constraints.gridwidth=1;
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.LINE_END;
        this.add(salir,constraints);

        imageDado = new ImageIcon(getClass().getResource("/assets/dado.png"));
        dado1 = new JLabel(imageDado);
        dado2 = new JLabel(imageDado);


        panelDatos = new JPanel();
        panelDatos.setPreferredSize(new Dimension(300,180));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Tus Dados"));
        panelDatos.add(dado1);
        panelDatos.add(dado2);
        constraints.gridx=0;
        constraints.gridy=2;
        constraints.gridwidth=1;
        constraints.fill=GridBagConstraints.BOTH;
        constraints.anchor=GridBagConstraints.CENTER;
        this.add(panelDatos,constraints);

        resultadosDados = new JTextArea(4,31);
        resultadosDados.setBorder(BorderFactory.createTitledBorder("Resultados"));
        resultadosDados.setText("Debes lanzar los dados");
        resultadosDados.setEditable(false);
        constraints.gridx=1;
        constraints.gridy=2;
        constraints.gridwidth=1;
        constraints.fill=GridBagConstraints.BOTH;
        constraints.anchor=GridBagConstraints.CENTER;
        this.add(resultadosDados,constraints);

        lanzar = new JButton("Lanzar");
        lanzar.addActionListener(escucha);
        constraints.gridx=0;
        constraints.gridy=3;
        constraints.gridwidth=2;
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.CENTER;
        this.add(lanzar,constraints);

        mensajeSalida = new JTextArea(4, 31);
        mensajeSalida.setText("Usa el botón (?) de ayuda para ver las reglas del juego :)");
        mensajeSalida.setBorder(BorderFactory.createTitledBorder("Mensajes"));

        constraints.gridx=0;
        constraints.gridy=4;
        constraints.gridwidth=2;
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.CENTER;
        this.add(mensajeSalida,constraints);

    }

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            GUIGridBagLayout miProjectGUI = new GUIGridBagLayout();
        });
    }


    private class Escucha implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
           if(e.getSource() == lanzar){
               modelCraps.calcularTiro();
               int[] caras = modelCraps.getCaras();
               imageDado = new ImageIcon(getClass().getResource("/assets/"+caras[0]+".png"));
               dado1.setIcon(imageDado);
               imageDado = new ImageIcon(getClass().getResource("/assets/"+caras[1]+".png"));
               dado2.setIcon(imageDado);
               modelCraps.determinarJuego();
               resultadosDados.setText(modelCraps.getEstadoToString()[0]);
               mensajeSalida.setText(modelCraps.getEstadoToString()[1]);
           }else{
               if(e.getSource() == ayuda){
                    JOptionPane.showMessageDialog(null,MENSAJE_INICIO);
               }else{
                   System.exit(0);
               }
           }

        }
    }

}
