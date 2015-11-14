package br.com.azi.pesquisa.bo;

import br.com.azi.pesquisa.entity.Agenda;
import br.com.azi.pesquisa.entity.Tipo;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DMartinez on 13/11/2015.
 */
@Dependent
public class TipoBO {
    private static List<Tipo> tipos = new ArrayList<Tipo>();
    private static Map<Integer, Tipo> mapa = new HashMap<Integer, Tipo>();


    static{

        Tipo tp = new Tipo();
        tp.setId(1);
        tp.setNome("Pessoa Física");
        tipos.add(tp);
        mapa.put(1, tp);

        tp = new Tipo();
        tp.setId(2);
        tp.setNome("Pessoa Jurídica");
        tipos.add(tp);
        mapa.put(2, tp);

        Tipo._id=2;

    }

    public Tipo save(Tipo tipo){
        if(tipo.getId()==null){
            Tipo._id++;
            tipo.setId(Tipo._id);
            tipos.add(tipo);
            mapa.put(tipo.getId(),tipo);
            return tipo;
        }else{
            if(mapa.containsKey(tipo.getId())){
                Tipo antigo = mapa.get(tipo.getId());
                antigo.setNome(tipo.getNome());
                return antigo;
            }
        }
        return null;
    }

    public Tipo remove(Tipo tipo){
        if(mapa.containsKey(tipo.getId())){
            Tipo antigo = mapa.get(tipo.getId());
            mapa.remove(antigo);
            tipos.remove(antigo);
            return antigo;
        }else{
            return null;
        }
    }

    public List<Tipo> listAll(){
        return tipos;
    }

    public List<Tipo> listBy(String nome){
        List<Tipo> lista = new ArrayList<Tipo>();
        Boolean ret=null;
        for (Tipo tipo: tipos) {
            ret=true;
            if(nome!=null && tipo.getNome().toLowerCase().indexOf(nome.toLowerCase())!=0) ret = false;
            if(ret) lista.add(tipo);
        }
        return lista;
    }

    public Tipo findById(Integer id){
        return mapa.containsKey(id)?mapa.get(id):null;
    }

    public static Tipo get(Integer id){
        return mapa.containsKey(id)?mapa.get(id):null;
    }

}
