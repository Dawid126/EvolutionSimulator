package main.java.utils;

public class RandomInt {


    public int generate(int max)
    {
        int min = 0;
        int range = max - min + 1;
        int rand = (int) (Math.random() * range) + min;
        return rand;
    }

}
