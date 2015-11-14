package br.com.azi.pesquisa.common;

import br.com.azi.pesquisa.service.AgendaService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by DMartinez on 13/11/2015.
 */
@ApplicationPath("/ws")
public class HelloAngularApp extends Application{

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(AgendaService.class);
        return classes;
    }
}
