package com.company.Utils;

import java.util.Scanner;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class IOUtils {

    private static Scanner scanner = null;

    public static Scanner getScannerInstanceOnIn() {
        if(scanner != null) {
            return scanner;
        }

        scanner = new Scanner(System.in);
        return scanner;
    }

}
