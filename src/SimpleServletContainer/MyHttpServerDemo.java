package SimpleServletContainer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The type My http server demo.
 */
public class MyHttpServerDemo {

    // WEB_ROOT is the directory where  our HTML and other files reside;
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";


    // shutdown command
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    // pulic shotdown command received
    private boolean shotdown = false;
    public static void main(String[] args) {
        MyHttpServerDemo server = new MyHttpServerDemo();
        server.await();
    }

    public void await() {
        System.out.println(WEB_ROOT);
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // loop watting for request
        while (!shotdown){
            Socket socket = null;
            InputStream input =  null;
            OutputStream output = null;
            try {
                socket = serverSocket.accept();
                input  = socket.getInputStream();
                output = socket.getOutputStream();

                // create Request object and parse
                MyRequest request = new MyRequest(input);
                request.parse();

                // create response object
                MyResponse response = new MyResponse(output);
                response.setRequest(request);


                // check if this is a request for a servlet or a static resource
                // a request for a servlet begins with "/servlet"

                if(request.getUri().startsWith("/servlet/")){
                    ServletProcessor1 processor = new ServletProcessor1();
                    processor.process(request,response);
                }else{
                    StaticResourceProcessor processor = new StaticResourceProcessor();
                    processor.process(request,response);
                }


                // close the socket
                socket.close();

                // check if the previous URI is a shutdown command
                shotdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

        }

    }
}
