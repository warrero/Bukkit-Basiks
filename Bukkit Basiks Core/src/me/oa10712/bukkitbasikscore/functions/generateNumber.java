package me.oa10712.bukkitbasikscore.functions;

public class generateNumber {

    public int generateNumber(Integer min, Integer max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
