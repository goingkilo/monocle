import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clojure.java.api.*;

public class ClojServ extends HttpServlet {
        private ServletContext scontext;

        @Override
        public void init(ServletConfig config) {
                scontext = config.getServletContext();

                try {
                        scontext.log( "(()) initialization successful" );
                }
                catch(Exception e) {
                        scontext.log( "(()) initialization failed :" + e.getMessage() );
                        e.printStackTrace();
                }
        }

        @Override
        public void doPost(HttpServletRequest req, HttpServletResponse res)
                        throws IOException {

                PrintWriter out = res.getWriter();
                try {


                String code = req.getParameter("code");
                if( code == null ) {
                        scontext.log( "code is null ");
                        out.println("code is null");
                        return ;
                }
		scontext.log( code );
		IFn goFunk = Clojure.var("clojure.core", "goFunk");
		goFunk.invoke();

                scontext.log( a.princToString() );
                out.println( a.princToString());

                } catch (Exception e) {
                        scontext.log( "Exception : " + e.getMessage() );
                        e.printStackTrace();
                }
        }
}

