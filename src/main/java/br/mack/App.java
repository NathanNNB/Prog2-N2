package br.mack;

import br.mack.dao.TrendsDao;
import br.mack.resource.TrendsResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Hello world!
 *
 */
public class App extends Application<Configuration>
{
    public static void main( String[] args )
    {
        try{
            (new App()).run(args);
        } catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        AssetsBundle assetsBundle=new AssetsBundle("/site", "/", "index.html");
        bootstrap.addBundle(assetsBundle);
        // nothing to do yet
    }

    @Override
    public void run(Configuration configuration, Environment environment) {

        //Enable CORS heders
        final FilterRegistration.Dynamic cors=
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        //Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type, Accept, Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS, GET, PUT, POST, DELETE, HEAD");

        //Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        //Registrando Recursos
        TrendsDao dao = new TrendsDao();
        TrendsResource trendsResource = new TrendsResource(dao);
        environment.jersey().register(trendsResource);

        //Mudar rotas dos recursos
        environment.jersey().setUrlPattern("/api/*");
    }

}
