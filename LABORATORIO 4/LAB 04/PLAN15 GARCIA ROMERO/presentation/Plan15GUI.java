package presentation;
import domain.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 * @version ECI 2025
 */
public class Plan15GUI extends JFrame{


    private static final Dimension PREFERRED_DIMENSION =
                         new Dimension(700,700);

    private Plan15 plan;

    /*List*/
    private JButton buttonList;
    private JButton buttonRestartList;
    private JTextArea textDetails;
    
    /*Add*/
    private JTextField code;
    private JTextField name;   
    private JTextField credits;
    private JTextField inPerson;
    private JTextArea  basics;
    private JButton buttonAdd;
    private JButton buttonRestartAdd;
   
    /*Search*/
    private JTextField textSearch;
    private JTextArea textResults;
    
    /**
     * Constructor de la interfaz gráfica.
     * Inicializa el modelo Plan15 y prepara los elementos y acciones de la interfaz.
     */
    private Plan15GUI(){
        try{
            plan=new Plan15();
        }catch (Plan15Exception e){
            Log.record(e);
            e.printStackTrace();
        }
        prepareElements();
        prepareActions();
    }

    /**
     * Prepara todos los elementos visuales de la interfaz.
     * Configura la ventana principal, las pestañas y los componentes.
     */
    private void prepareElements(){
        setTitle("Plan 15");
        code = new JTextField(50);
        name = new JTextField(50);
        credits = new JTextField(50);
        inPerson = new JTextField(50);
        basics = new JTextArea(10, 50);
        basics.setLineWrap(true);
        basics.setWrapStyleWord(true);
        
        JTabbedPane etiquetas = new JTabbedPane();
        etiquetas.add("Listar",   prepareAreaList());
        etiquetas.add("Adicionar",  prepareAreaAdd());
        etiquetas.add("Buscar", prepareSearchArea());
        getContentPane().add(etiquetas);
        setSize(PREFERRED_DIMENSION);
        
    }


    /**
     * Prepara el área de listado con su área de texto y botones.
     * @return JPanel configurado con los componentes para listar elementos
     */
    private JPanel prepareAreaList(){

        textDetails = new JTextArea(10, 50);
        textDetails.setEditable(false);
        textDetails.setLineWrap(true);
        textDetails.setWrapStyleWord(true);
        JScrollPane scrollArea =
                new JScrollPane(textDetails,
                                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                                
        JPanel  botones = new JPanel();
        buttonList = new JButton("Listar");
        buttonRestartList = new JButton("Limpiar");
        botones.add(buttonList);
        botones.add(buttonRestartList);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollArea, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
     }
     
    /**
     * Prepara el área de adición con campos de entrada y botones.
     * @return JPanel configurado con los componentes para añadir elementos
     */
    private JPanel prepareAreaAdd(){

        JPanel fields = new JPanel(new GridLayout(9,1));
        fields.add(new JLabel("Sigla"));
        fields.add(code);
        fields.add(new JLabel("Nombre"));
        fields.add(name);
        fields.add(new JLabel("Creditos o porcentaje"));
        fields.add(credits);        
        fields.add(new JLabel("Horas presenciales (solo para cursos)"));
        fields.add(inPerson); 
        fields.add(new JLabel("Cursos (solo para nucleos)"));
       
        JPanel textDetailsPanel = new JPanel();
        textDetailsPanel.setLayout(new BorderLayout());
        textDetailsPanel.add(fields, BorderLayout.NORTH);
        textDetailsPanel.add(basics, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        buttonAdd = new JButton("Adicionar");
        buttonRestartAdd = new JButton("Limpiar");

        botones.add(buttonAdd);
        botones.add(buttonRestartAdd);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textDetailsPanel, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    



   /**
     * Prepara el área de búsqueda con campo de entrada y área de resultados.
     * @return JPanel configurado con los componentes para buscar elementos
     */
    private JPanel prepareSearchArea(){

        Box busquedaEtiquetaArea = Box.createHorizontalBox();
        busquedaEtiquetaArea.add(new JLabel("Buscar", JLabel.LEFT));
        busquedaEtiquetaArea.add(Box.createGlue());
        textSearch = new JTextField(50);
        Box busquedaArea = Box.createHorizontalBox();
        busquedaArea.add(busquedaEtiquetaArea);
        busquedaArea.add(textSearch);
        
        textResults = new JTextArea(10,50);
        textResults.setEditable(false);
        textResults.setLineWrap(true);
        textResults.setWrapStyleWord(true);
        JScrollPane scrollArea = new JScrollPane(textResults,
                                     JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                     JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel buttonListea = new JPanel();
        buttonListea.setLayout(new BorderLayout());
        buttonListea.add(busquedaArea, BorderLayout.NORTH);
        buttonListea.add(scrollArea, BorderLayout.CENTER);

        return buttonListea;
    }

    /**
     * Configura las acciones y listeners para todos los componentes interactivos.
     */
    private void prepareActions(){
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev){
                setVisible(false);
                System.exit(0);
            }
        });
        
        /*List*/
        buttonList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                actionList();
            }
        });

        buttonRestartList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                textDetails.setText("");
            }
        });
        
        /*Add*/
        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                actionAdd();                    
            }
        });
        
        buttonRestartAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                code.setText("");
                name.setText("");
                credits.setText("");
                inPerson.setText("");
                basics.setText("");
            }
        });
        
        /*Search*/
        textSearch.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent ev){
                actionSearch();
            }
           
            public void insertUpdate(DocumentEvent ev){
                actionSearch();
            }
            
            public void removeUpdate(DocumentEvent ev){
                actionSearch();
            }
        });
    }    

    /**
     * Acción para listar todos los elementos del plan.
     * Muestra en el área de texto el resultado del método toString() del modelo.
     */
    private void actionList(){
        textDetails.setText(plan.toString());
    }
    
    /**
     * Acción para agregar un nuevo curso o núcleo al plan.
     * Valida los campos de entrada y muestra mensajes de error o éxito según corresponda.
     * Maneja excepciones específicas de Plan15Exception y NumberFormatException.
     */
    private void actionAdd() {
        try {
            if (basics.getText().trim().equals("")) {
                // Validación para cursos
                if (code.getText().trim().isEmpty()) {
                    throw new Plan15Exception("El código no puede estar vacío");
                }
                if (code.getText().length() > 6) {
                    throw new Plan15Exception(Plan15Exception.INVALID_CODE_LENGTH);
                }
                if (name.getText().trim().isEmpty()) {
                    throw new Plan15Exception(Plan15Exception.INVALID_NAME);
                }
                if (credits.getText().trim().isEmpty()) {
                    throw new Plan15Exception("Recuerda que los creditos no pueden estar vacios");
                }
                if (inPerson.getText().trim().isEmpty()) {
                    throw new Plan15Exception("Recuerda que las horas presenciales no pueden estar vacias");
                }
    
                plan.addCourse(code.getText(), name.getText(), credits.getText(), inPerson.getText());
                JOptionPane.showMessageDialog(this, "Curso agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                // Validación para núcleos
                if (code.getText().trim().isEmpty()) {
                    throw new Plan15Exception("Recuerda que el codigo no puede estar vacio");
                }
                if (code.getText().length() > 6) {
                    throw new Plan15Exception(Plan15Exception.INVALID_CODE_LENGTH);
                }
                if (name.getText().trim().isEmpty()) {
                    throw new Plan15Exception(Plan15Exception.INVALID_NAME);
                }
                if (credits.getText().trim().isEmpty()) {
                    throw new Plan15Exception("Recuerda que el porcentaje no puede estar vacio");
                }
    
                plan.addCore(code.getText(), name.getText(), credits.getText(), basics.getText());
                JOptionPane.showMessageDialog(this, "Núcleo agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
    
            // Limpiar campos después de agregar exitosamente
            code.setText("");
            name.setText("");
            credits.setText("");
            inPerson.setText("");
            basics.setText("");
    
        } catch (Plan15Exception e) {
            // Manejo específico de errores
            Log.record(e);
            if (e.getMessage().equals(Plan15Exception.INVALID_NAME)) {
                name.setBackground(Color.PINK);
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error en nombre", JOptionPane.ERROR_MESSAGE);
            } 
            else if (e.getMessage().equals(Plan15Exception.INVALID_CODE_LENGTH)) {
                code.setBackground(Color.PINK);
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error en código", JOptionPane.ERROR_MESSAGE);
            }
            else if (e.getMessage().equals(Plan15Exception.INVALID_CREDITS_FORMAT)) {
                credits.setBackground(Color.PINK);
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error en créditos", JOptionPane.ERROR_MESSAGE);
            }
            else if (e.getMessage().equals(Plan15Exception.INVALID_HOURS_FORMAT)) {
                inPerson.setBackground(Color.PINK);
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error en horas", JOptionPane.ERROR_MESSAGE);
            }
            else if (e.getMessage().equals(Plan15Exception.CREDITS_HOURS_INCONSISTENT)) {
                credits.setBackground(Color.PINK);
                inPerson.setBackground(Color.PINK);
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error de consistencia", JOptionPane.ERROR_MESSAGE);
            }
            else if (e.getMessage().equals(Plan15Exception.INVALID_PERCENTAGE)) {
                credits.setBackground(Color.PINK);
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error en porcentaje", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // Mensaje genérico para otros errores de validación
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
            }
          
    
        } catch (NumberFormatException e) {
            Log.record(e);
            credits.setBackground(Color.PINK);
            inPerson.setBackground(Color.PINK);
            JOptionPane.showMessageDialog(this, "Los valores numéricos deben ser enteros válidos", "Error de formato", JOptionPane.ERROR_MESSAGE);
         
        } catch (Exception e) {
            Log.record(e);
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Acción para buscar elementos según un patrón de texto.
     * Actualiza el área de resultados en tiempo real mientras se escribe.
     * Maneja casos de búsqueda vacía y resultados no encontrados.
     */
    private void actionSearch() {
        try {
            String patronBusqueda = textSearch.getText().trim();
            if (patronBusqueda.isEmpty()) {
                textResults.setText("");
                return;
            }
            
            String resultado = plan.search(patronBusqueda);
            if (resultado == null ){
                textResults.setForeground(Color.RED);
            textResults.setText("No se han encontrado coincidencias, pruebe con otra materia");
                
                }
                else {
                textResults.setForeground(Color.BLACK);
                textResults.setText(resultado);
        }
        } catch (Plan15Exception e) {
            Log.record(e);
            if (e.getMessage().equals(Plan15Exception.NO_RESULTS_FOUND)) {
                textResults.setText("✖" + e.getMessage());
            }
            } catch (Exception e) {
            textResults.setForeground(Color.RED);
            textResults.setText("No se han encontrado coincidencias, pruebe con otra materia");
        }
    }
    
    /**
     * Punto de entrada principal para la aplicación.
     * Crea y muestra la interfaz gráfica.
     * @param args Argumentos de línea de comandos (no utilizados)
     */
   public static void main(String args[]){
       Plan15GUI gui=new Plan15GUI();
       gui.setVisible(true);
   }    
}