import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class diseño_base extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JTextField nom;
    private JTextField dir;
    private JButton VERButton;
     private JTextField id_busqueda;
    private JTextField eda;
    private JTextField tel;
    private JTextField cor;
    private JTextField nota1;
    private JTextField nota2;


    public diseño_base() {
        //INFORMACION DEL JPANEL
        super("");
        setTitle("NUEVA CONEXION");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setSize(400,400);
        setContentPane(panel1);


        // BOTON DE GUARDAR LOS DATOS
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    InsertarDatos();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // BOTON PARA BUSCAR LA INFORMACION POR ID
        VERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
          }


          //INSERTAR DATOS EN LA TABLA ESTUDIANTES
public void InsertarDatos()throws SQLException{
    String nombre= nom.getText();
    String direccion= dir.getText();
    String edad= eda.getText();
    String telefono= tel.getText();
    String correo= cor.getText();
    String nota_1= nota1.getText();
    String nota_2= nota2.getText();

    Connection conecta = conexion();
    String sql ="INSERT INTO estudiantes(nombre_apellido,direccion,edad,telefono,correo,nota1,nota2)values(?,?,?,?,?,?,?)";

    PreparedStatement pstmt=conecta.prepareStatement(sql);
    pstmt.setString(1,nombre);
    pstmt.setString(2,direccion);
    pstmt.setInt(3,Integer.parseInt(edad));
    pstmt.setString(4,telefono);
    pstmt.setString(5,correo);
    pstmt.setDouble(6,Double.parseDouble(nota_1));
    pstmt.setDouble(7,Double.parseDouble(nota_2));

    int rowsAffected=pstmt.executeUpdate();
    if ( (rowsAffected >0)){
        JOptionPane.showMessageDialog(null, "REGISTRO INSERTADO CORRECTAMENTE");
    }
    pstmt.close();
    conecta.close();

}

// BUSQUEDA POR ID - CAMPO PRINCIPAL DE LA TABLA ESTUDIANTE
public void mostrar() throws SQLException {
    int codigo_matricula = Integer.parseInt(id_busqueda.getText());

    Connection conectado = conexion();
    String sql = "SELECT * FROM estudiantes WHERE codigo_matricula = ?";

    PreparedStatement pstmt = conectado.prepareStatement(sql);
    pstmt.setInt(1, codigo_matricula);

    ResultSet rs = pstmt.executeQuery();
    if (rs.next()) {
        String nombre = rs.getString("nombre_apellido");
        String direccion = rs.getString("direccion");
        int edad = rs.getInt("edad");
        String telefono = rs.getString("telefono");
        String correo = rs.getString("correo");
        double nota1 = rs.getDouble("nota1");
        double nota2 = rs.getDouble("nota2");
        JOptionPane.showMessageDialog(null, "Nombre: " + nombre + "\nDireccion: " + direccion + "\nEdad: " + edad + "\nTelefono: " + telefono + "\nCorreo: " + correo + "\nNota 1: " + nota1 + "\nNota 2: " + nota2);
    } else {
        JOptionPane.showMessageDialog(null, "No se encontraron registros para el ID especificado.");
    }
    rs.close();
    pstmt.close();
    conectado.close();
}

//CONEXION A LA BASE DE DATOS
    public Connection conexion() throws SQLException {
        String url= "jdbc:mysql://127.0.0.1:3306/curso";
        String user="root";
        String password="123456";

        return DriverManager.getConnection(url,user,password);
        }

}




