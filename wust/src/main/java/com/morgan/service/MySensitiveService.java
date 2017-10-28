package com.morgan.service;

import org.apache.commons.lang.CharUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/10/28 0028.
 */
@Service
public class MySensitiveService implements InitializingBean {

    private TreNode begin=new TreNode();

    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream is=Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("SensitiveWords.txt");
        InputStreamReader read=new InputStreamReader(is);
        BufferedReader bufferedReader=new BufferedReader(read);
        String lineTxt;
        while((lineTxt=bufferedReader.readLine())!=null){
            lineTxt=lineTxt.trim();
            add(lineTxt);
        }
        read.close();
    }

    private class  TreNode{
        private boolean end=false;
        private HashMap<Character,TreNode>map=new HashMap();

        public void setKeyWordEnd(boolean end){
            this.end=end;
        }
        public boolean getKeyWordEnd(){
            return end;
        }
        public void addSubNode(Character c,TreNode node){
            map.put(c,node);
        }
        private TreNode getSubNode(Character c){
            return map.get(c);
        }



    }

    private boolean isSymbol(char c){
        int ic=(int)c;
        return !CharUtils.isAsciiAlphanumeric(c)&&(ic<0x2E80||ic>0x9FFF);
    }


    public void add(String c){
        TreNode tempNode=begin;
        for(int i=0;i<c.length();i++){
            TreNode node=tempNode.getSubNode(c.charAt( i));
            if(node==null){
                node=new TreNode();
                tempNode.addSubNode(c.charAt(i),node);
            }
            tempNode=node;
            if(i==c.length()-1){
                tempNode.end=true;
            }
        }
  }

  public String filter(String text){
        String defaultString="***";
        TreNode tempNode=begin;
        int p1=0;
        int p2=0;
        StringBuilder sb=new StringBuilder("");

        while(p2<text.length()){
            char c=text.charAt(p2);
            if(isSymbol(c)){
                p2++;
                continue;
            }
            tempNode=tempNode.getSubNode(c);
            if(tempNode!=null){

                if(tempNode.getKeyWordEnd()==true){
                    sb.append(defaultString);
                    p2++;
                    p1=p2;
                    tempNode=begin;
                }else {
                    p2++;
                }
            }else {
                sb.append(text.charAt(p1));
                p1++;
                p2=p1;
                tempNode=begin;

            }
        }
        sb.append(text.substring(p1));
        return sb.toString();


  }

  public static void main(String []args){
      //sout
      MySensitiveService mySensitiveService=new MySensitiveService();
      mySensitiveService.add("色情");
      mySensitiveService.add("赌博");

      System.out.print(mySensitiveService.filter("你好色 情呵呵"));

  }






}
