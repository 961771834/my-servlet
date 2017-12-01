package SimpleServletContainer;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

public class MyResponse implements ServletResponse {
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
                    System.out.println("去掉下划线");
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

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        PrintWriter  writer = new PrintWriter(output,true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
