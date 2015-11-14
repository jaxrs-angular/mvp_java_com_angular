package br.com.azi.pesquisa.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class ResponseFilter {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private HashSet<Object> circularDetection = new HashSet<Object>();

    private FilterArgs args = new FilterArgs();

    public ResponseFilter() {}

    public ResponseFilter(Class<?> clazz, String expression) {
        this.add(clazz, expression);
    }

    public void add(Class<?> clazz, String expression) {
        args.add(clazz, expression);
    }

    public Object apply(Object obj){
        if(ReflectionUtil.isCollection(obj)) return processCollection(obj);
        else if(ReflectionUtil.isObject(obj)) return processObject(obj);
        return null;
    }

    private Map<String, Object> processObject(Object obj) {
        if(obj==null) return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String,Object> ret = new HashMap<String, Object>();
        for (Field field : fields) {
            try {
                if(args.processField(obj.getClass(),field.getName())) {

                    Method getter = ReflectionUtil.getter(obj.getClass(), field);
                    if(getter==null) continue;

                    Object val = getter.invoke(obj);
                    Object res = null;
                    if (ReflectionUtil.isCollectionField(field)) {
                        List<Object> list = new ArrayList<Object>();
                        if(val!=null) {
                            for (Object item : (Collection) val) {
                                if (ReflectionUtil.isNative(item)) {
                                    list.add(processNative(item));
                                } else if (ReflectionUtil.isCollection(item)) {
                                    list.add(processCollection(item));
                                } else if (ReflectionUtil.isObject(item)) {
                                    list.add(processObject(item));
                                } else {
                                    throw new Exception("Tipo não suportado: " + item.getClass().getCanonicalName());
                                }
                            }
                        }
                        res = list;
                    } else if (ReflectionUtil.isNativeField(field)) {
                        res = processNative(val);
                    } else if (ReflectionUtil.isObjectField(field)) {
                        res = processObject(val);
                    } else {
                        throw new Exception("Tipo não suportado: " + field.getType().getCanonicalName());
                    }
                    ret.put(field.getName(), res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return ret;
    }


    private Object processNative(Object val) {
        if(val==null) return null;
        if(ReflectionUtil.descends(val.getClass(),Calendar.class)){
            return format.format(((Calendar)val).getTime());
        }
        if(ReflectionUtil.descends(val.getClass(),Date.class)){
            return format.format((Date)val);
        }
        return val;
    }

    private List<Object> processCollection(Object obj) {
        List<Object> ret = new ArrayList<Object>();
        Collection coll = (Collection) obj;
        for (Object item : coll) {
            Object val = null;
            if (item == null) val = item;
            else if (ReflectionUtil.isCollection(item)) val = processCollection(item);
            else if (ReflectionUtil.isNative(item)) val = processNative(item);
            else if (ReflectionUtil.isObject(item)) val = processObject(item);
            ret.add(val);
        }


        return ret;
    }



}
