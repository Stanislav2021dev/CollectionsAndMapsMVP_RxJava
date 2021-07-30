package com.example.colmapsrxjavatask4.collectionsmodel;

import java.util.Random;

public class GenerateRandomElement {
    private static final Random element = new Random();

    public static int randomInRange(int numEl) {
        return (int) (Math.random() * numEl);
    }

    public static Integer randomInt() {
        return element.nextInt();
    }
}
