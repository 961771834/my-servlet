package SimpleServletContainer;


import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor2 {
    public void process (MyRequest request,MyResponse response){
        String  uri = request.getUri();
        String  servletName = uri.substring(uri.lastIndexOf("/")+1);
        URLClassLoader loader = null;



        // create a URLClassLoader
        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        File classPath = new File(Constants.WEB_ROOT);


        try {
            System.out.println("ServletProcessor2 创建 classLoader");
            String repository = (new URL("File",null,classPath.getCanonicalPath()+File.separator).toString());
            System.out.println("=======");
            System.out.println(repository);
            urls[0] = new URL(null,repository,streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class myClass = null;
        try {
            myClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Servlet  servlet = null;
        RequestFacade requestFacade = new RequestFacade(request);
        ResponseFacade responseFacade = new ResponseFacade(response);
        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.service(requestFacade,responseFacade);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
