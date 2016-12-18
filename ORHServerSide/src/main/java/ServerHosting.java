import NetworkClasses.WebSocketServerHandler;

import java.net.InetSocketAddress;

/**
 * Created by Darko on 19.11.2016.
 */
public class ServerHosting {

    public static void main(String[] args) {

        InetSocketAddress address = new InetSocketAddress(25565);

        WebSocketServerHandler server = new WebSocketServerHandler(address);
        server.start();

    }

}
