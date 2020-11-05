package main;

import java.util.LinkedList;

public class Fragment {
    public LinkedList<Character> list;

    /**
     * Empty fragment
     */
    public Fragment(){
        list = new LinkedList<>();
    }

    /**
     * Generate fragment based on a String sequence
     * @param s the sequence
     */
    public Fragment(String s){
                list = new LinkedList();
                for (int i = 0; i < s.length(); i++){
                    list.add(s.charAt(i));
                }
    }
    public Fragment(LinkedList l){
        list = l;
    }

    /**
     * Make a correspondance between a char and an int
     * @param c the given char
     * @return the corresponding int
     */
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
    /**
     * Make a correspondance between a char and an int
     * @param i the given int
     * @return the corresponding char
     */
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

    /**
     * return the complementary of a char
     * @param c a char
     * @return the complement of c
     */
    public char getCharComplementary(char c){
        int comp = -getCharValue(c);
        return getCharFromValue(comp);
    }

    /**
     * return an inverted complemenrary Fragment acgg -> ccgt
     * @return an inverted complemenrary Fragment
     */
    public Fragment getComplementary(){
        LinkedList comp = new LinkedList();
        for(int i = list.size()-1; i > -1; i--){
            comp.add(getCharComplementary(list.get(i)));
        }
        return new Fragment(comp);
    }

    /**
     * Invert a fragment acgt -> tgca
     */
    public void invert(){
        LinkedList invert = new LinkedList();
        for(int i = list.size()-1; i > -1; i--){
            invert.add(list.get(i));
        }
        list = invert;
    }

    /**
     * return the char at the index i of the fragment
     * @param i index in the fragment
     * @return the char at index i
     */
    public char getCharAtIndex(int i){
        return list.get(i);
    }

    /**
     * return the size of the fragment
     * @return the size of the fragment
     */
    public int getSize(){
        return list.size();
    }

    /**
     * String representation of a fragment
     * @return a string representation of a fragment
     */
    public String toString(){
        String frag = "";
        for(int i = 0; i < list.size(); i++){
            frag += list.get(i);
        }
        return frag;
    }
}
