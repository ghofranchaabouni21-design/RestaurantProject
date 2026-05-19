package view;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;          // ← import pour GridLayout, Dimension, etc.

public class LoginFrame extends JFrame {
    JTextField txtUser = new JTextField();
    JPasswordField txtPass = new JPasswordField();

    public LoginFrame() {
        setTitle("Login - Restaurant Manager");
        setSize(350, 200);
        setLayout(new GridLayout(3, 2, 10, 10));  // ← maintenant GridLayout est trouvé
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("Username:");
        JLabel l2 = new JLabel("Password:");
        JButton btnLogin = new JButton("Login");

        add(l1);
        add(txtUser);
        add(l2);
        add(txtPass);
        add(new JLabel());      // cellule vide
        add(btnLogin);

        btnLogin.addActionListener(e -> login());
    }

    public void login() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());

        UserDAO dao = new UserDAO();
        User u = dao.login(user, pass);

        if (u != null) {
            JOptionPane.showMessageDialog(this, "Bienvenue " + user);
            if (u.getRole().equals("admin")) {
                new PlatFrame().setVisible(true);
                new DashboardFrame().setVisible(true); // si tu as créé DashboardFrame
            } else {
                new CommandeFrame().setVisible(true);
            }
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Login incorrect");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}