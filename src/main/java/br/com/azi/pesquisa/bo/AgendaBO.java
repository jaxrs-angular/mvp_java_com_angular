package br.com.azi.pesquisa.bo;

import br.com.azi.pesquisa.entity.Agenda;

import javax.enterprise.context.Dependent;
import java.math.BigDecimal;
import java.util.*;


@Dependent
public class AgendaBO {

    private static List<Agenda> agendas = new ArrayList<Agenda>();
    private static Map<Integer, Agenda> mapa = new HashMap<Integer, Agenda>();

    static{
        Agenda ag = new Agenda();
        ag.setId(1);
        ag.setNome("Alice");
        ag.setTelefone("1111-1111");
        ag.setTipo(TipoBO.get(1));
        ag.setSaldo(new BigDecimal(100));
        ag.setNascimento(new GregorianCalendar(1972, 1, 18));
        agendas.add(ag);
        mapa.put(ag.getId(), ag);

        ag = new Agenda();
        ag.setId(2);
        ag.setNome("Alec");
        ag.setTelefone("2222-2222");
        ag.setTipo(TipoBO.get(1));
        ag.setSaldo(new BigDecimal(101.12));
        ag.setNascimento(new GregorianCalendar(1936, 6, 11));
        agendas.add(ag);
        mapa.put(ag.getId(), ag);

        ag = new Agenda();
        ag.setId(3);
        ag.setNome("Alvin");
        ag.setTelefone("3333-3333");
        ag.setTipo(TipoBO.get(1));
        ag.setSaldo(new BigDecimal(1020.21));
        ag.setNascimento(new GregorianCalendar(1962, 3, 05));
        agendas.add(ag);
        mapa.put(ag.getId(), ag);

        ag = new Agenda();
        ag.setId(4);
        ag.setNome("Bubasauro");
        ag.setTelefone("4444-4444");
        ag.setTipo(TipoBO.get(1));
        ag.setSaldo(new BigDecimal(10.1));
        ag.setNascimento(new GregorianCalendar(1955, 10, 21));
        agendas.add(ag);
        mapa.put(ag.getId(), ag);

        ag = new Agenda();
        ag.setId(5);
        ag.setNome("Bufalo Bil Co");
        ag.setTelefone("5555-5555");
        ag.setTipo(TipoBO.get(2));
        ag.setSaldo(new BigDecimal(23100.11));
        ag.setNascimento(new GregorianCalendar(1962, 07, 01));
        agendas.add(ag);
        mapa.put(ag.getId(), ag);

        ag = new Agenda();
        ag.setId(6);
        ag.setNome("Bill Gates");
        ag.setTelefone("6666-6666");
        ag.setTipo(TipoBO.get(1));
        ag.setSaldo(new BigDecimal(12310.60));
        ag.setNascimento(new GregorianCalendar(1940, 10, 16));
        agendas.add(ag);
        mapa.put(ag.getId(), ag);

        ag = new Agenda();
        ag.setId(7);
        ag.setNome("Bruna Lombardi");
        ag.setTelefone("7777-7777");
        ag.setTipo(TipoBO.get(1));
        ag.setSaldo(new BigDecimal(1440.0));
        ag.setNascimento(new GregorianCalendar(1942, 9, 14));
        agendas.add(ag);
        mapa.put(ag.getId(), ag);

        ag = new Agenda();
        ag.setId(8);
        ag.setNome("Benedito Rui Barbosa");
        ag.setTelefone("8888-8888");
        ag.setTipo(TipoBO.get(1));
        ag.setSaldo(new BigDecimal(23423.66));
        ag.setNascimento(new GregorianCalendar(1945, 3, 26));
        agendas.add(ag);
        mapa.put(ag.getId(), ag);

        ag = new Agenda();
        ag.setId(9);
        ag.setNome("Catherine Zeta-Jones");
        ag.setTelefone("9999-9999");
        ag.setTipo(TipoBO.get(1));
        ag.setSaldo(new BigDecimal(673454.32));
        ag.setNascimento(new GregorianCalendar(1966, 9, 17));
        agendas.add(ag);
        mapa.put(ag.getId(), ag);

        ag = new Agenda();
        ag.setId(10);
        ag.setNome("Chistina Aguilera");
        ag.setTelefone("1010-1110");
        ag.setTipo(TipoBO.get(1));
        ag.setSaldo(new BigDecimal(96234.20));
        ag.setNascimento(new GregorianCalendar(1970, 1, 8));
        agendas.add(ag);
        mapa.put(ag.getId(), ag);

        ag = new Agenda();
        ag.setId(11);
        ag.setNome("Carlos Massa");
        ag.setTelefone("1011-1011");
        ag.setTipo(TipoBO.get(1));
        ag.setSaldo(new BigDecimal(45645.22));
        ag.setNascimento(new GregorianCalendar(1975, 1, 3));
        agendas.add(ag);
        mapa.put(ag.getId(), ag);

        Agenda._id = 11;
    }


    public Agenda save(Agenda agenda){
        if(agenda.getId()==null){
            Agenda._id++;
            agenda.setId(Agenda._id);
            agendas.add(agenda);
            mapa.put(agenda.getId(), agenda);
            return agenda;
        }else{
            if(mapa.containsKey(agenda.getId())){
                Agenda antiga = mapa.get(agenda.getId());
                int idx = agendas.indexOf(antiga);
                agendas.remove(antiga);
                mapa.remove(antiga.getId());

                agendas.add(idx,agenda);
                mapa.put(agenda.getId(),agenda);
                return agenda;
            }
        }
        return null;
    }

    public Agenda remove(Agenda agenda){
        if(mapa.containsKey(agenda.getId())){
            Agenda antiga = mapa.get(agenda.getId());
            mapa.remove(antiga);
            agendas.remove(antiga);
            return antiga;
        }else{
            return null;
        }
    }

    public List<Agenda> listAll(){
        return agendas;
    }

    public List<Agenda> listBy(String nome){
        if(nome==null)return agendas;
        List<Agenda> lista = new ArrayList<Agenda>();
        Boolean ret=null;
        for (Agenda agenda : agendas) {
            if(agenda.getNome().toLowerCase().indexOf(nome.toLowerCase())==0) {
                lista.add(agenda);
            }
        }
        return lista;
    }

    public Agenda findById(Integer id){
        return mapa.containsKey(id)? mapa.get(id):null;
    }

}
