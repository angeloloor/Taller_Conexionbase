import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class accion extends  JFrame{
    private JPanel jpanel2;
    private JTextField usuario;
    private JPasswordField password;
    private JButton button1;


public accion(){
    super("nuevo");
    setSize(400,400);
    setContentPane(jpanel2);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana


    button1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                verificarDatos();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    });
}

    public void verificarDatos() throws SQLException {

        String user = usuario.getText();
        String pass = new String(password.getPassword());

        // Llama al método de conexión
        Connection conecta=conexion();

        String sql = "SELECT * FROM acceso WHERE usuario = ? AND password = ?";

        Connection connection;
        PreparedStatement pstmt = conecta.prepareStatement(sql);
        pstmt.setString(1, user);
        pstmt.setString(2, pass);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(null,"La información ingresada es correcta.");

            // AHORA TOCA LLAMR AL FORMULARIO DE INGRESO.......
           diseño_base ventanopciones=new diseño_base();
            ventanopciones.setVisible(true);
            setVisible(false); // Ocultar la ventana de login


        } else {
            JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrectos.");

        }

        rs.close();
        pstmt.close();
        conecta.close();
    }

    public Connection conexion() throws SQLException {
        String url= "jdbc:mysql://localhost:3306/curso";
        String user="root";
        String password="123456";

        return DriverManager.getConnection(url,user,password);
    }

}