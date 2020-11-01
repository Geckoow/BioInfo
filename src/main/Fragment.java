package main;

import java.util.LinkedList;

public class Fragment {
    public LinkedList<Character> list;
    public Fragment(){
        list = new LinkedList<>();
    }
    public Fragment(String s){
                list = new LinkedList();
                for (int i = 0; i < s.length(); i++){
                    list.add(s.charAt(i));
                }
    }
    public Fragment(LinkedList l){
        list = l;
    }
    public int getCharValue(char c) {
        switch (c) {
            case '-':
                return 0;
            case 'a':
                return 1;
            case 't':
                return -1;
            case 'c':
                return 2;
            case 'g':
                return -2;
            default:
                throw new IllegalArgumentException();
        }
    }

    public char getCharFromValue(int i){
        switch (i) {
            case 0:
                return '-';
            case 1:
                return 'a';
            case -1:
                return 't';
            case 2:
                return 'c';
            case -2:
                return 'g';
            default:
                throw new IllegalArgumentException();
        }
    }
    public char getCharComplementary(char c){
        int comp = -getCharValue(c);
        return getCharFromValue(comp);
    }

    public Fragment getComplementary(){
        LinkedList comp = new LinkedList();
        for(int i = list.size()-1; i > -1; i--){
            comp.add(getCharComplementary(list.get(i)));
        }
        return new Fragment(comp);
    }

    public void invert(){
        LinkedList invert = new LinkedList();
        for(int i = list.size()-1; i > -1; i--){
            invert.add(list.get(i));
        }
        list = invert;
    }
    public char getCharAtIndex(int i){
        return list.get(i);
    }
    public int getSize(){
        return list.size();
    }
    public String toString(){
        String frag = "";
        for(int i = 0; i < list.size(); i++){
            frag += list.get(i);
        }
        return frag;
    }
}
