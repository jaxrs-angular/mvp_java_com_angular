package br.com.azi.pesquisa.service;

import br.com.azi.pesquisa.bo.AgendaBO;
import br.com.azi.pesquisa.bo.TipoBO;
import br.com.azi.pesquisa.entity.Agenda;
import br.com.azi.pesquisa.util.ResponseFilter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Path("/agenda")
public class AgendaService {

    @Inject
    private AgendaBO abo;

    @Inject
    private TipoBO tbo;


    @GET
    @Path("/init")
    @Produces(MediaType.APPLICATION_JSON)
    public Object inicializar(){
        Map<String, Object> ret = new TreeMap<String, Object>();
        ret.put("tipos",tbo.listAll());

        return ret;
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Object listar(@QueryParam("filtro")String filtro){
        return new ResponseFilter().apply(abo.listBy(filtro));
    }


    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Object salvar(Agenda agenda){
        return new ResponseFilter().apply(abo.save(agenda));
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Object remover(@QueryParam("id")Integer id){
        Agenda agenda;
        if((agenda=abo.findById(id))!=null)
            return new ResponseFilter().apply(abo.remove(agenda));
        else return null;
    }

}
