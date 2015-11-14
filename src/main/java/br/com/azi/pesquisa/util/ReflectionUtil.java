package br.com.azi.pesquisa.util;

import com.sun.deploy.util.BlackList;
import org.hibernate.mapping.Collection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class ReflectionUtil {
    public static Method getter(Class<?> clazz,Field field){
        Method[] metodos = clazz.getDeclaredMethods();
        for (Method metodo : metodos) {
            if(metodo.getName().equals("get" + StringUtil.captalize(field.getName()))) return metodo;
        }
        return null;
    }

    public static boolean isCollection(Class<?> clazz) {
        try{
            Object obj =  clazz.newInstance();
            return isCollection(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isCollection(Object obj) {
        if(obj==null) return false;
        return ((obj instanceof AbstractCollection) || (obj instanceof Collection));
    }

    public static boolean isMap(Object obj) {
        if(obj==null) return false;
        return (obj instanceof Map);
    }

    public static boolean isNative(Object obj) {
        if(obj==null) return false;
        if(obj instanceof String) return true;
        if(obj instanceof Character) return true;
        if(obj instanceof Boolean) return true;
        if(obj instanceof Number) return true;
        if(obj instanceof Calendar) return true;
        if(obj instanceof Date) return true;
        if(obj.getClass().isPrimitive()) return true;

        return false;
    }

    public static boolean isNative(Class<?> clazz){

        try {
            if(clazz.isPrimitive()) return true;
            return isNative(clazz.newInstance());
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }



    public static boolean isObject(Object obj) {
        if(obj==null) return false;
        return (!isCollection(obj) && !isMap(obj) && !isNative(obj));
    }

    public static boolean isCollectionField(Field field) {
        if(field.getType().equals(List.class)) return true;
        if(field.getType().equals(Set.class)) return true;
        if(field.getType().equals(ArrayList.class)) return true;
        if(field.getType().equals(LinkedList.class)) return true;
        if(field.getType().equals(HashSet.class)) return true;
        if(field.getType().equals(TreeSet.class)) return true;
        if(field.getType().equals(LinkedHashSet.class)) return true;
        return false;
    }

    public static boolean isNativeField(Field field) {
        try{
            if(field.getType().getPackage().getName().equals("java.lang")) return true;
            if(descends(field.getType(), Number.class)) return true;
            if(descends(field.getType(), Calendar.class)) return true;
            if(descends(field.getType(), Date.class)) return true;
            Object obj =  field.getType().newInstance();
            return isNative(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean descends(Class<?> type, Class<?> parent) {
        Class<?> clazz = type;
        while(!clazz.equals(Object.class))
            if(clazz.equals(parent)) return true;
            else clazz = clazz.getSuperclass();

        return false;
    }

    public static boolean isObjectField(Field field) {
        try {
            if(field.getType().equals(BigDecimal.class)) return false;
            Object obj =  field.getType().newInstance();
            return isObject(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }



}
