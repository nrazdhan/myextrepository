package com.example.demo2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String param = request.getParameter("imgName");
        OutputStream out = response.getOutputStream();
        ServletContext context = getServletContext();
        String realPath = context.getRealPath("/images/"+param);
        FileInputStream stream = new FileInputStream(realPath);
        BufferedInputStream buf = new BufferedInputStream(stream);
        int readBytes = 0;
        byte[] bytes = new byte[1024];
        response.setContentType("image/png");
        while((readBytes = buf.read(bytes)) != -1) {
            out.write(bytes, 0, readBytes);
        }
        out.flush();
        // buf.close();
        stream.close();
    }
}
