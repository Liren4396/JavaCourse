package splitter;

import java.util.Scanner;

public class Splitter {
    public static void main(String[] args) {
        System.out.println("Enter a sentence specified by spaces only:");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine().toString();
        String[] ret = s.split(" ");
        for (String output : ret) {
            System.out.println(output);
        }
    }
}
