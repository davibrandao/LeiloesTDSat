/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public boolean cadastrarProduto(ProdutosDTO produto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isCadastrado = false;
        try {
            // Obtém uma conexão com o banco de dados
            conn = new conectaDAO().connectDB();

            // Prepara a instrução SQL
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, produto.getNome());
            pstmt.setInt(2, produto.getValor());
            pstmt.setString(3, produto.getStatus());

            // Executa a instrução SQL
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isCadastrado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Fecha os recursos
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isCadastrado;
    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        return listagem;
    }

}
