import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class diseño_base extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JTextField nom;
    private JTextField eda;
    private JButton VERButton;
     private JTextField id_busqueda;


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
    String edad= eda.getText();

    Connection conecta = conexion();
    String sql ="INSERT INTO estudiante(nombre,edad)values(?,?)";

    PreparedStatement pstmt=conecta.prepareStatement(sql);
    pstmt.setString(1,nombre);
    pstmt.setInt(2,Integer.parseInt(edad));

    int rowsAffected=pstmt.executeUpdate();
    if ( (rowsAffected >0)){
        JOptionPane.showMessageDialog(null, "REGISTRO INSERTADO CORRECTAMENTE");
    }
    pstmt.close();
    conecta.close();

}

// BUSQUEDA POR ID - CAMPO PRINCIPAL DE LA TABLA ESTUDIANTE
public void mostrar() throws SQLException {
       int id= Integer.parseInt(id_busqueda.getText());

        Connection conectado=conexion();
       String sql = "SELECT * FROM estudiante WHERE idestudiante = ?";

        PreparedStatement pstmt=conectado.prepareStatement(sql);
        pstmt.setInt(1,id);

    ResultSet rs=pstmt.executeQuery();
    if(rs.next()){
        String nombre=rs.getString("nombre");
        JOptionPane.showMessageDialog(null, "codigo"+  " " +id +"nombre"+ " "  +nombre);
    }
rs.close();
    pstmt.close();
    conectado.close();
}


//CONEXION A LA BASE DE DATOS
    public Connection conexion() throws SQLException {
        String url= "jdbc:mysql://localhost:3306/esfotventa";
        String user="root";
        String password="1234";

        return DriverManager.getConnection(url,user,password);
        }
    }




