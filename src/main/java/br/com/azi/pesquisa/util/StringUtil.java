package br.com.azi.pesquisa.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DMartinez on 06/11/2015.
 */
public class StringUtil {
    public static String captalize(String original){
        return original.trim().substring(0,1).toUpperCase()+original.trim().substring(1);
    }

    public static List<String> explode(String original,Character separator, Character ignoreStart, Character ignoreEnd){
        List<String> list = new ArrayList<String>();
        int ini = 0,pos =0;
        boolean ignore = false;
        int ignoreIndex = 0;

        for(pos = 0 ; pos<original.length();pos++){
            Character letra = original.charAt(pos);
            if(letra==separator && !ignore){
               if(ini<pos){
                   list.add(original.substring(ini, pos - 1));
                   ini = pos+1;
               }

            }else if(letra==ignoreStart){
                if(!ignore)ignore = true;
                else ignoreIndex++;
            }else if(letra==ignoreEnd){
                if(ignore){
                    if(ignoreIndex>0) ignoreIndex--;
                    else ignore=false;
                }
            }
        }
        if(ini<pos){
            list.add(original.substring(ini,pos-1));
        }
        return list;
    }
}
