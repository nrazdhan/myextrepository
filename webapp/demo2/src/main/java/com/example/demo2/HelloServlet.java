package com.example.demo2;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/hello")
public class HelloServlet extends jakarta.servlet.http.HttpServlet {
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException {
        response.setContentType("text/plain");
        response.getWriter().write("Hello, World!");
    }
}
