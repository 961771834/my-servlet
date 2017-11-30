package HttpServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyResponse {
    private static final int BUFFER_SIZE = 1024;
    MyRequest request;
    OutputStream output;

    public MyResponse(OutputStream output) {
        this.output = output;
    }

    public void setRequest(MyRequest request) {
        this.request = request;
    }
    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        File file = new File(MyHttpServerDemo.WEB_ROOT, request.getUri());
        System.out.println(file);
        try{
            if(file.exists()){
                fis = new FileInputStream(file);
                int ch = fis.read(bytes,0,BUFFER_SIZE);
                while (ch != -1){
                    output.write(bytes,0,ch);
                    System.out.println(new String(bytes));
                    ch = fis.read(bytes,0,BUFFER_SIZE);
                }
            } else {
                // file not fount
                String errorMssage =  "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";

                output.write(errorMssage.getBytes());
            }
        } catch (Exception e){
            // thrown if cannot instantiate a File Object
            System.out.println(e.toString());
        }
        finally {
            if(fis!=null){
                fis.close();
            }
        }
    }
}
