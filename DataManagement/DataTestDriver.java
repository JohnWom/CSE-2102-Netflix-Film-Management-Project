package DataManagement;
import java.util.Scanner;

public class DataTestDriver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Hello, and welcome to Data Test Driver");
        System.out.println("Would you like to make a Movie or a TV Show");
        String type = sc.nextLine();

        while (type.compareTo("Movie") != 0 && type.compareTo("TV Show") != 0) {
            System.out.println("Please enter Movie or TV Show?");
            type = sc.nextLine();
        }
        System.out.printf("\n\n");

        Title t;

    //  Gather Info ================================================================================
        System.out.println("Please enter information for the following ...");

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Director: ");
        String director = sc.nextLine();

        System.out.print("Rating: ");
        String rating = sc.nextLine();

        System.out.print("Country: ");
        String country = sc.nextLine();

        System.out.print("Release Year: ");
        int year = Integer.parseInt(sc.nextLine());

        if (type.compareTo("Movie") == 0) {
            System.out.print("Length in minutes: ");
        }
        else {
            System.out.print("Number of seasons: ");
        }
        int duration = Integer.parseInt(sc.nextLine());

        System.out.print("Enter genres: ");
        String gen = sc.nextLine();
    
        if (type.compareTo("Movie") == 0) {
            t = new Movie(type, title, director, rating, country, year, gen, duration);
        }
        else { 
            t = new Series(type, title, director, rating, country, year, gen, duration);
        }

        System.out.printf("\n\n");
        System.out.println("Here is your title:");
        t.print();
        System.out.printf("\n\n");
    //==============================================================================================

        System.out.println("Would you like to change an attribute? (y/n)");
        String choice = sc.nextLine();

        if (choice.compareTo("y") == 0) {
            System.out.println("What attribute would you like to change?");
            System.out.println("1. Rating");
            System.out.println("2. Director");
            System.out.println("3. Genre");
            System.out.println("4. Duration");
            System.out.println("5. Country");
            System.out.println("6. Year");
            choice = sc.nextLine();

            System.out.print("Here is the old value: ");
            switch (choice) {
                case "1":
                    System.out.println(rating);
                    System.out.print("What would you like the new value to be: ");
                    t.setRating(sc.nextLine());
                    break;
                case "2":
                    System.out.println(director);
                    System.out.print("What would you like the new value to be: ");
                    t.setDirector(sc.nextLine());
                    break;
                case "3":
                    System.out.println(gen);
                    System.out.print("What would you like the new value to be: ");
                    t.setGenres(sc.nextLine());
                    break;
                case "4":
                    System.out.println(duration);
                    System.out.print("What would you like the new value to be: ");
                    t.setDuration(Integer.parseInt(sc.nextLine()));
                    break;
                case "5":
                    System.out.println(country);
                    System.out.print("What would you like the new value to be: ");
                    t.setCountry(sc.nextLine());;
                    break;
                case "6":
                    System.out.println(year);
                    System.out.print("What would you like the new value to be: ");
                    t.setYear(Integer.parseInt(sc.nextLine()));
                    break;
            }

            System.out.printf("\n\n");
            System.out.println("Here is your title with your change:");
            t.print();
        }

        sc.close();
    }
}