import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RhinoServlet extends HttpServlet {
        private ServletContext scontext;
		ScriptEngine i ;


        @Override
        public void init(ServletConfig config) {
                scontext = config.getServletContext();
                ScriptEngineManager mgr = new javax.script.ScriptEngineManager();
        		i = mgr.getEngineByName("javascript");
                
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
                        scontext.log( "interpreter recreated " + i);
                        return;
                }

                String code = req.getParameter("code");
                if( code == null ) {
                        scontext.log( "code is null ");
                        out.println("code is null");
                        return ;
                }
                Object a = i.eval(code);
                scontext.log( a.toString() );
                out.println( a );

                } catch (Exception e) {
                        scontext.log( "Exception : " + e.getMessage() );
                        e.printStackTrace();
                }
        }
}
