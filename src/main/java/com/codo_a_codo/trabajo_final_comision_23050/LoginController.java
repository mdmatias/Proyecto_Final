package com.codo_a_codo.trabajo_final_comision_23050;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "login", value = "/login")
public class LoginController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String umail = request.getParameter("email");
        String upwd = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher disp = null;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_usuarios?useSSL=false", "root", "r00t");
            final String QUERY = "select * from usuarios where email = ? and contraseña = ?";
            PreparedStatement ps = con.prepareStatement(QUERY);
            ps.setString(1, umail);
            ps.setString(2, upwd);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                session.setAttribute("email", rs.getString("email"));
                session.setAttribute("nombre", rs.getString("nombre"));
                disp = request.getRequestDispatcher("index.jsp");
            } else {
                request.setAttribute("status", "failed");
                disp = request.getRequestDispatcher("login.jsp");
            }
            disp.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
