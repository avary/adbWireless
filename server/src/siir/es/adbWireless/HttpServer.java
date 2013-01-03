/**
 * siir.es.adbWireless.adbWireless.java
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/

package siir.es.adbWireless;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer extends Thread {

	private int port;
	private String client;
	volatile boolean finished = false;

	public HttpServer(int p) {
		this.port = p;
		this.client = "";
	}

	public void stopThread() {
		finished = true;
	}

	public void run() {

		ServerSocket serversocket = null;

		try {
			serversocket = new ServerSocket(this.port);
		} catch (Exception e) {
			return;
		}

		while (!finished) {
			try {
				Socket connectionsocket = serversocket.accept();
				InetAddress client = connectionsocket.getInetAddress();
				BufferedReader input = new BufferedReader(new InputStreamReader(connectionsocket.getInputStream()));
				DataOutputStream output = new DataOutputStream(connectionsocket.getOutputStream());
				this.client = client.getHostAddress();
				Server.o("run() accept from " + this.client);
				httpHandler(input, output);
			} catch (Exception e) {
				return;
			}
		}

	}

	private void httpHandler(BufferedReader in, DataOutputStream out) {
		
		Server.o("httpHandler()");
		
		boolean ok = false;

		try {
			/*
			if(!in.ready())
				throw new Exception("BufferedReader no preparado");
			*/
			String tmp = in.readLine();
			if (tmp != null && tmp.length() > 10 && tmp.toUpperCase().startsWith("GET")) {
				try {
					int start = 0;
					int end = 0;
					for (int a = 0; a < tmp.length(); a++) {
						if (tmp.charAt(a) == ' ' && start != 0) {
							end = a;
							break;
						}
						if (tmp.charAt(a) == ' ' && start == 0) {
							start = a;
						}
					}
					String op = tmp.substring(start + 2, start + 3);
					String path = tmp.substring(start + 4, end);
					if(path.equals(this.client)) {
						if(op.equals("c")) {
							Process p = Runtime.getRuntime().exec("adb connect " + path);
							p.waitFor();
							ok = true;
						} else if(op.equals("d")) {
							Process p = Runtime.getRuntime().exec("adb disconnect " + path);
							p.waitFor();
							ok = true;
						}
					}
				} catch (Exception e) {
					Server.o("httpHandler() ERROR[0]: " + e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			Server.o("httpHandler() ERROR[1]: " + e.getLocalizedMessage());
		}
		
		try {
			if(ok) {
				Server.o("OK");
				//out.writeBytes(httpHeaders(200));
			} else {
				Server.o("ERROR");
				//out.writeBytes(httpHeaders(501));
			}
		} catch (Exception e) {
			Server.o("httpHandler() ERROR[2]: " + e.getLocalizedMessage());
		}
		
		try {
			out.close();
		} catch(Exception e) {
		}

	}
/*
	private String httpHeaders(int code) {

		StringBuffer out = new StringBuffer("HTTP/1.0 ");

		switch (code) {
		case 200:
			out.append("200 OK");
			break;
		case 400:
			out.append("400 Bad Request");
			break;
		case 403:
			out.append("403 Forbidden");
			break;
		case 404:
			out.append("404 Not Found");
			break;
		case 500:
			out.append("500 Internal Server Error");
			break;
		case 501:
			out.append("501 Not Implemented");
			break;
		}

		out.append("\r\nConnection: close\r\nServer: adbWireless Server\r\n\r\n");
		return out.toString();

	}
*/
}