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
import java.sql.Statement;

import java.util.List;

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

        ArrayList<ProdutosDTO> produtos = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = new conectaDAO().connectDB();
            stmt = conn.createStatement();  // Atualizado aqui
            String sql = "SELECT * FROM produtos";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return produtos;
    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> produtos = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = new conectaDAO().connectDB();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // ...
            // feche as conexões como antes
            // ...
        }
        return produtos;
    }

    public boolean venderProduto(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = new conectaDAO().connectDB();
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
