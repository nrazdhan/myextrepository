package HelloWorld;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.InetSocketAddress;
<<<<<<< HEAD

import java.time.Instant;

=======
import java.security.KeyStore.Entry;
import java.sql.DriverManager;
import java.time.Instant;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Map;

>>>>>>> e6545b0 (terraform and webdemos)
public class hello{
	public static void main(String[] args) throws IOException {
		System.out.println("Hello World!");
		HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
<<<<<<< HEAD

		server.createContext("/hello", new MyHttpHandler());

		server.setExecutor(null);
=======
System.out.println("Server started at port 8081");
		server.createContext("/hello", new MyHttpHandler());

		server.setExecutor(null);

>>>>>>> e6545b0 (terraform and webdemos)
		server.start();
		System.out.println("Java HttpHandler Server Has been started @" + Instant.now());
	}

	static class MyHttpHandler implements HttpHandler{
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String r = "Hello World from Java HttpServer!";
<<<<<<< HEAD
=======

			String url = "jdbc:mysql://ec2-3-14-3-164.us-east-2.compute.amazonaws.com:3306/naveen";
			String user = "guest";
			String password = "Danzar12%#1";

			Map<String, String> map = System.getenv();
			for(Map.Entry<String, String> e : map.entrySet()){
				System.out.println(e.getKey() + " : " + e.getValue());
				if(e.getKey().equals("JDBC_URL")){
					url = e.getValue();
					r+= "\n DB URL from Env Variable: " + url;
				} else if(e.getKey().equals("JDBC_USER")){
					user = e.getValue();
					r+= "\n DB User from Env Variable: " + user;
				} else if(e.getKey().equals("JDBC_PASSWORD")){
					password = e.getValue();
					r+= "\n DB Password from Env Variable: " + password;
				}
			}

			
			try {
				// Load the MySQL JDBC driver
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection(url, user, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select name from chars");

				String names = "";
				while(rs.next()){
					names += rs.getString("name") + ", ";
				}
				r += "\n DB Results: " + names;

			} catch(SQLException se) {
				r = "SqlException Occusred: " + se.getMessage();
			} catch (ClassNotFoundException e) {
				r = "ClassNotFoundException Occusred: " + e.getMessage();
			} catch (Exception e) {
				r = "Exception Occusred: " + e.getMessage();
			}
			
>>>>>>> e6545b0 (terraform and webdemos)
			exchange.sendResponseHeaders(200, r.length());
			exchange.getResponseBody().write(r.getBytes());
			exchange.getResponseBody().close();
		}
	}
}