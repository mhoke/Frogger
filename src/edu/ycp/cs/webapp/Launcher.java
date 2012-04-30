package edu.ycp.cs.webapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

// See: http://brandontilley.com/2010/03/27/serving-a-gwt-application-with-an-embedded-jetty-server.html
public class Launcher {
	private static final String WEB_APP_DIR_NAME = "../Frogger/jsp/";

	public static void main(String[] args) throws Exception {
		// Configure logging
		configureLogging();
		
		// Create an embedded Jetty server
		Server server = new Server();
		
		// Create a connector
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(8081);
		server.addConnector(connector);
		
		File appDir = new File(WEB_APP_DIR_NAME);
		if (!appDir.exists()) {
			System.out.println("webapp dir does not exist");
			System.exit(1);
		}
		if (!appDir.isDirectory()) {
			System.out.println("webapp dir is not a directory");
			System.exit(1);
		}

		// Create WebAppContext
		WebAppContext handler = new WebAppContext();
		handler.setResourceBase(WEB_APP_DIR_NAME);
		handler.setDescriptor(WEB_APP_DIR_NAME + "/WEB-INF/web.xml");
		handler.setContextPath("/frogger");
		handler.setParentLoaderPriority(true);

		// Add it to the server
		server.setHandler(handler);

		// Other misc. options
		server.setThreadPool(new QueuedThreadPool(20));

		// And start it up
		System.out.println("Starting up the server...type 'quit' to shut down");
		try {
			server.start();
		} catch (Exception e) {
			System.err.println("Could not start server: " + e.getMessage());
		}
		
		// Wait until "quit" is written to the FIFO
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			try {
				while (true) {
					String line = reader.readLine();
					if (line == null) {
						System.err.println("Reached EOF on FIFO?");
						break;
					}
					if (line.trim().toLowerCase().equals("quit")) {
						System.out.println("Quit command read from FIFO");
						break;
					}
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			System.err.println("IOException reading from FIFO: " + e.getMessage());
			e.printStackTrace(System.err);
		}
		
		try {
			System.out.println("Stopping the server...");
			server.stop();
			System.out.println("Waiting for server to finish...");
			server.join();
			System.out.println("Server is finished");
		} catch (Exception e) {
			// Should not happen
			System.err.println("Exception shutting down the server");
			e.printStackTrace(System.err);
		}
	}

	private static void configureLogging() throws IOException {
//		Properties log4jProperties = new Properties();
//		InputStream in = new FileInputStream("./apps/cloudCoder/WEB-INF/classes/log4j.properties"); 
//		try {
//			log4jProperties.load(in);
//		} finally {
//			in.close();
//		}
//		
//		// Configure log4j
//		PropertyConfigurator.configure(log4jProperties);
	}
}
