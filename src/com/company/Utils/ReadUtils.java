package com.company.Utils;

import com.company.Domain.Post;
import com.company.Domain.Sarcina;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class ReadUtils {

    /**
     * Reads a post type from the standard input. Exits only when a correct type has been read.
     * @param message - The message to display before requesting input
     * @return - The {@link com.company.Domain.Post.Type}
     */
    public static Post.Type readPostType(Scanner scanner, String message) {
        Post.Type type = null;
        do {
            System.out.print(message + " ");
            String line = scanner.nextLine();

            if(line.equals(Post.FULL_TIME_STRING)) {
                type = Post.Type.FULLTIME;
            } else if(line.equals(Post.PART_TIME_STRING)) {
                type = Post.Type.PARTTIME;
            }

            if(type == null) {
                System.out.println("Invalid input");
            }

        }while(type == null);

        return type;
    }


    /**
     * Reads a {@link String} from the standard input
     * @param message - The message to display before the string request
     * @return - The string
     */
    public static String readString(Scanner scanner, String message) {
        System.out.print(message + " ");
        return scanner.nextLine();
    }

    /**
     * Reads a {@link Sarcina} from the standard input
     * @return - The read {@link Sarcina}
     */
    public static Sarcina readSarcina(Scanner scanner) {
        String description = readString(scanner, "Description: ");
        int id = readInt(scanner, "Input id: ");
        return new Sarcina(id, description);
    }

    /**
     * Reads a {@link Sarcina} from the standard input
     * @param scanner The scanner
     * @param getId If true, we ask for the id as well
     * @return The read sarcina
     */
    public static Sarcina readSarcina(Scanner scanner, boolean getId) {

        String description = readString(scanner, "Description: ");

        if(getId) {
            int id = readInt(scanner, "Input id: ");
            return new Sarcina(id, description);
        }

        return new Sarcina(-1, description);
    }

    /**
     * Reads a {@link Post} from the standard input
     * @return - The read post
     */
    public static Post readPost(Scanner scanner) {
        String name = readString(scanner, "Input name: ");
        Post.Type type = readPostType(scanner, "Input type(FullTime/ PartTime): ");
        int id = readInt(scanner, "Input id: ");

        return new Post(id, name, type);
    }

    /**
     * Reads a {@link Post} from the standard input
     * @param scanner The scanner
     * @param getId If true, we ask for the id as well.
     * @return The read post
     */
    public static Post readPost(Scanner scanner, boolean getId) {

        String name = readString(scanner, "Input name: ");
        Post.Type type = readPostType(scanner, "Input type(FullTime / PartTime): ");

        if(getId) {
            int id = readInt(scanner, "Input id: ");
            return new Post(id, name, type);
        }

        return new Post(-1, name, type);
    }



    /**
     * Reads an {@link Integer} from the standard input. Does not exit until a valid integer is inputted.
     * @param message - The message to display before the integer request
     * @return - The integer
     */
    public static int readInt(Scanner scanner, String message) {
        Integer res = null;
        do {
            System.out.print(message + " ");
            try {
                res = scanner.nextInt();
                scanner.nextLine();
            }catch(InputMismatchException e) {
                System.out.println("Invalid value");
                scanner.nextLine();
            }
        }while(res == null);
        return res;
    }

}
