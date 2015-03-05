import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.armedbear.lisp.*;


public class ScytheServlet extends HttpServlet {
        Interpreter i;
        private ServletContext scontext;

        @Override
        public void init(ServletConfig config) {
                scontext = config.getServletContext();
                i = Interpreter.createInstance();
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

                if( i == null ) {
                        scontext.log( "interpreter is null ");
                        i = Interpreter.createInstance();
                        scontext.log( "interpreter recreated " + i);
                }
                else {
                        i = Interpreter.getInstance();
                        scontext.log( "interpreter got" + i);
                }

                String code = req.getParameter("code");
                if( code == null ) {
                        scontext.log( "code is null ");
                        out.println("code is null");
                        return ;
                }
                LispObject a = i.eval(code);
                scontext.log( a.princToString() );
                out.println( a.princToString());

                } catch (Exception e) {
                        scontext.log( "Exception : " + e.getMessage() );
                        e.printStackTrace();
                }
        }
}

