package main;

import java.util.LinkedList;

public class Fragment {
    public char[] list;

    /**
     * Empty fragment
     */
    public Fragment(){
    }

    /**
     * Generate fragment based on a String sequence
     * @param s the sequence
     */
    public Fragment(String s){
                list = new char[s.length()];
                for (int i = 0; i < s.length(); i++){
                    list[i] = (s.charAt(i));
                }
    }
    public Fragment(char[] l){
        list = l;
    }

    /**
     * Make a correspondance between a char and an int
     * @param c the given char
     * @return the corresponding int
     */
    public byte getCharValue(char c) {
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
    public char getCharFromValue(byte i){
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
        byte comp = (byte) -getCharValue(c);
        return getCharFromValue(comp);
    }

    /**
     * return an inverted complemenrary Fragment acgg -> ccgt
     * @return an inverted complemenrary Fragment
     */
    public Fragment getComplementary(){
        char[] comp = new char[list.length];
        for(int i = list.length-1; i > -1; i--){
            comp[i] = (getCharComplementary(list[i]));
        }
        Fragment complement = new Fragment(comp);
        complement.invert();
        return complement;
    }

    /**
     * Invert a fragment acgt -> tgca
     */
    public void invert(){
        char[] invert = new char[list.length];
        int invIndex = list.length-1;
        for(int i = 0; i < list.length; i++){
            invert[invIndex-i] = list[i];
        }
        list = invert;
    }

    /**
     * return the char at the index i of the fragment
     * @param i index in the fragment
     * @return the char at index i
     */
    public char getCharAtIndex(int i){
        return list[i];
    }

    /**
     * return the size of the fragment
     * @return the size of the fragment
     */
    public int getSize(){
        return list.length;
    }

    /**
     * String representation of a fragment
     * @return a string representation of a fragment
     */
    public String toString(){
        String frag = "";
        for(int i = 0; i < list.length; i++){
            frag += list[i];
        }
        return frag;
    }
}
