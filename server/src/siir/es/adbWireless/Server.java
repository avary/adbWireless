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

class Server {

	private static HttpServer server;

	public static void start(int port) {
		try {
			o("start()");
			server = new HttpServer(port);
			server.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void stop() {
		try {
			o("stop()");
			server.stopThread();
			server = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		o("main()");
		
		if (args.length < 1) {
			System.out.println("incorrect arguments");
			return;
		}
		
		int port = 8555;
		
		if (args.length == 2) {
			try {
				port = Integer.parseInt(args[1]);
			} catch(Exception e) {
			}
		}
		
		o("port:" + port);
		
		if ("start".equals(args[0])) {
			start(port);
		} else if ("stop".equals(args[0])) {
			stop();
		}
	}
	
	public static void o(String s) {
		System.out.println(s);
	}
}