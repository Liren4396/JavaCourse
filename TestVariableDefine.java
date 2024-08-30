import java.util.Scanner;

public class TestVariableDefine {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter name:");
        String name = input.next();
        System.out.println("Please enter age:");
        int age = input.nextInt();
        System.out.println("Please enter gender:");
        char gender = input.next().charAt(0);
        System.out.println("name is "+ name);
        System.out.println("age is "+ age);
        
        System.out.println("gender is "+ gender);

    }
}