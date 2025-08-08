package HelloWorld;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.InetSocketAddress;

import java.time.Instant;

public class hello{
	public static void main(String[] args) throws IOException {
		System.out.println("Hello World!");
		HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

		server.createContext("/hello", new MyHttpHandler());

		server.setExecutor(null);
		server.start();
		System.out.println("Java HttpHandler Server Has been started @" + Instant.now());
	}

	static class MyHttpHandler implements HttpHandler{
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String r = "Hello World from Java HttpServer!";
			exchange.sendResponseHeaders(200, r.length());
			exchange.getResponseBody().write(r.getBytes());
			exchange.getResponseBody().close();
		}
	}
}