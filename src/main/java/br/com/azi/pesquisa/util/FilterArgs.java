package br.com.azi.pesquisa.util;

import java.util.*;

public class FilterArgs {

    public class FilterArg {
        public Class<?> clazz;
        public List<String> includes;
        public List<String> excludes;
        public Map<String,List<String>> sintetics;
    }

    private Map<Class<?>,FilterArg> args = new HashMap<Class<?>, FilterArg>();

    public FilterArgs() {}



    public boolean processField(Class<?> clazz, String fieldName) {
        if(args.containsKey(clazz)){
            FilterArg arg = args.get(clazz);
            if(arg.excludes.contains(fieldName) || (arg.includes.size()>0 && !arg.includes.contains(fieldName))){
                return (arg.sintetics.containsKey(fieldName));
            }
        }
        return true;
    }

    public void add(Class<?> clazz, String exp) {
        args.put(clazz, this.parseExp(clazz, exp));
    }

    private FilterArg parseExp(Class<?> clazz, String exp) {
        FilterArg arg = new FilterArg();
        arg.clazz = clazz;
        List<String> fields = StringUtil.explode(exp, ',', '(', ')' );
        for (String field : fields) {
            if(field.trim().indexOf('-')==0){
                arg.excludes.add(field.trim().substring(1));
            }else if(field.indexOf('(')>0){
                String fieldName = field.trim().substring(0,field.indexOf('(')-1);
                if(fieldName.indexOf('+')==0) fieldName = fieldName.trim().substring(1);
                String content = field.substring(field.indexOf('(')+1,field.indexOf(')')-1);
                arg.sintetics.put(fieldName,Arrays.asList(content.split(content, ',')));
            }else if(field.trim().indexOf('+')==0){
                arg.includes.add(field.trim().substring(1));
            } else{
                arg.includes.add(field);
            }
        }
        return arg;
    }

}


