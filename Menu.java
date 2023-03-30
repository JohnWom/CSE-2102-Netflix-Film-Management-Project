import java.util.HashMap;
import java.util.Scanner;

import DataManagement.*;

public class Menu {
    TitleContainer tc = new TitleContainer();
    static Scanner sc;
    public static void main(String[] args) {
        Menu menu = new Menu();

        sc = new Scanner(System.in);
        int exit = 0;
        while (exit == 0) {
            try {
                System.out.print("Please enter the name of the input file: ");
                String title = sc.nextLine();
                menu.tc = new TitleContainer(title);
                break;
            } catch (RuntimeException e) {
                System.out.println("File Not Found");
                e.printStackTrace();
                continue;
            }
        }
        

        String option;
       exit = 0;
        while(exit == 0) {
            System.out.println("=====================================================");
            System.out.println("1. Add Title");
            System.out.println("2. Delete Title");
            System.out.println("3. Search Titles");
            System.out.println("4. Modify Title");
            System.out.println("Type Exit to end program");
            System.out.println("=====================================================");
            option = sc.nextLine();

            switch (option) {
                case "1":
                    menu.addTitle();
                    break;
                case "2":
                    menu.deleteTitle();
                    break;
                case "3":
                    menu.search();
                    break;
                case "4":
                    menu.modifyTitle();
                    break;
                case "Exit":
                    exit = 1;
                    break;
                default:
                    break;
            }



        }

        sc.close();
    }

    public void addTitle() {
        System.out.println("\nWould you like to add a Movie or a TV Show?");
        String type = sc.nextLine();
        
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
    
        tc.addTitle(title, director, rating, country, year, gen, duration, type);

        tc.addToFile(title, director, rating, country, year, gen, duration, type);
    }

    public void deleteTitle() {
        System.out.println("Select a Title to remove, use space bar to see more options:");

        Title t = selectTitle();

        tc.removeTitle(t.getTitle());

        System.out.println(t.getTitle() + " removed");
        
    }

    // This function handles gathering user input as to what they would like to search by, actual searching takes place in TitleContainer class
    public void search() {
        System.out.println("\nAre you looking for a Movie or TV Show?");
        String titleType = sc.nextLine();

        while (titleType.compareTo("Movie") != 0 && titleType.compareTo("TV Show") != 0) {
            System.out.println(titleType);
            System.out.println("\nPlease Enter Movie or TV Show");
            titleType = sc.nextLine();
            
        }

        String attr = chooseAttribute(titleType);

        String value = chooseValue(attr);

        tc.searchTitles(titleType, attr, value);
    }

    public void modifyTitle() {
        System.out.println("Select a Title to remove, use space bar to see more options:");

        Title t = selectTitle();

        System.out.println("Enter the new rating for the movie you chose");
        String new_rating = sc.nextLine();

        tc.removeTitle(t.getTitle());
        tc.addTitle(t.getTitle(), t.getDirector(), new_rating, t.getCountry(), t.getYear(), t.getGenres(), t.getDuration(), t.getType());

        tc.addToFile(t.getTitle(), t.getDirector(), new_rating, t.getCountry(), t.getYear(), t.getGenres(), t.getDuration(), t.getType());
        System.out.println(" ");    // Just for formatting
    }

    // Helper function for search
    private String chooseAttribute(String type) {
        String attr;

        // Menu
        System.out.println("\nWhich attribute are you searching based on?");
        System.out.println("1. Rating");
        System.out.println("2. Director");
        System.out.println("3. Genre");
        System.out.println("4. Duration");
        System.out.println("5. Country");
        System.out.println("6. Year");

        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                attr = "Rating";
                break;
            case "2":
                attr = "Director";
                break;
            case "3":
                attr = "Genre";
                break;
            case "4":
                // Titles have 2 different types of duration
                if (type.compareTo("Movie") == 0) {
                    attr = "lenMinutes";
                }
                else {
                    attr = "numSeasons";
                }
                break;
            case "5":
                attr = "Country";
                break;
            case "6":
                attr = "Year";
                break;
            default:
                attr = "Rating";
                break;
        }

        return attr;
    }

    // Helper method for search
    private String chooseValue(String option){
        HashMap<Integer, String> attr;
        System.out.println(" "); // Spacing to make menu a little friendlier

        switch (option) {
            case "Rating":
                attr = tc.getRatings();
                break;
            case "Director":
                attr = tc.getDirectors();
                break;
            case "Genre":
                attr = tc.getGenres();
                break;
            case "lenMinutes":
                // This Menu is hard coded because movies have finite lengths
                attr = new HashMap<Integer, String>();
                attr.put(1, "0-30 Minutes");
                attr.put(2, "31-60 Minutes");
                attr.put(3, "61-90 Minutes");
                attr.put(4, "91-120 Minutes");
                attr.put(5, "121-150 Minutes");
                attr.put(5, "151-180 Minutes");
                break;
            case "numSeasons":
                attr = tc.getNumSeasons();
                break;
            case "Country":
                attr = tc.getCountries();
                break;
            case "Year":
                attr = tc.getYears();
                break;
            default:
                attr = tc.getRatings();
                break;
        }
        System.out.println(" "); // For formatting
        for (int key : attr.keySet()) {
            System.out.printf("%d. %s\n", key, attr.get(key));
        }
        int choice = Integer.parseInt(sc.nextLine());
        String val = "None";
        for (int i = 1; i <= attr.size(); i++) {
            if (choice == i) {
                val = attr.get(choice);
            }
        }
        return val;
    }

    private Title selectTitle() {
        HashMap<Integer, Title> display = new HashMap<Integer, Title>();
        Title selected;

        int count = 1;
        for (Title t : tc.getTitles()) {
            display.put(count++, t);
        }

        count = 1;
        String ans = "None";
        while (count <= display.size()) {
            for (int i = 0; i < 10; i++) {
                if (display.containsKey(count)) {       //Here in case last group is not perfect 10
                    System.out.printf("%d: %s\n", count, display.get(count).getTitle());
                }
                else {
                    break;
                }
                count++;
            }
            ans = sc.nextLine();
            if (ans.compareTo(" ") == 0) {
                continue;
            }
            else {
                break;
            }
            
        }

        selected = display.get(Integer.parseInt(ans));
        return selected;
    }

}
