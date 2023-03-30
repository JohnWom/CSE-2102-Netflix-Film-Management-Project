package DataManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class TitleContainer {
    ArrayList<Title> titles = new ArrayList<Title>();
    HashMap<Integer, String> ratings = new HashMap<Integer, String>();
    HashMap<Integer, String> genres = new HashMap<Integer, String>();
    HashMap<Integer, String> directors = new HashMap<Integer, String>();
    HashMap<Integer, String> countries = new HashMap<Integer, String>();
    HashMap<Integer, String> numSeasons = new HashMap<Integer, String>();  
    HashMap<Integer, String> years = new HashMap<Integer, String>();
    String dataFile;

    public TitleContainer(){}

    public TitleContainer(String dataFile){
            try {
                this.dataFile = dataFile;
                File file = new File(dataFile);
                Scanner reader = new Scanner(file);
                reader.nextLine();  // skip header
                while (reader.hasNextLine()) {
                    String s = reader.nextLine();

                    //                          0     1      2       3         4      5      6        7         8
                    // info looks like this: [index, type, title, director, country, year, rating, duration, genres]
                    String[] info = parseString(s);
                    this.addTitle(info[2], info[3], info[6], info[4], intFromString(info[5]), info[8], intFromString(info[7]), info[1]);
                }
                reader.close();
                
    
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Error in creating TitleContainer");
            }
    }



    // type must be either "movie" or "series"
    public void addTitle(String t, String dir, String rat, String ct, int yr, String genre, int len, String type){
        if (!ratings.containsValue(rat)) {
            ratings.put(ratings.size() + 1, rat);
        }
        if (!directors.containsValue(dir)) {
            directors.put(directors.size() + 1, dir);
        }
        if (!countries.containsValue(ct)) {
            countries.put(countries.size() + 1, ct);
        }
        if (!years.containsValue(Integer.toString(yr))) {
            years.put(years.size() + 1, Integer.toString(yr));
        }
        if (!genres.containsValue(genre)) {
            genres.put(genres.size() + 1, genre);
        }
        if (type.compareTo("Movie") == 0) {
            Title Movie = new Movie(type, t, dir, rat, ct, yr, genre, len);
            titles.add(Movie);
        }
        else if (type.compareTo("TV Show") == 0) {

            if (!numSeasons.containsValue(Integer.toString(len) + " Seasons")) {
                numSeasons.put(numSeasons.size() + 1, Integer.toString(len) + " Seasons");
            }

            Title Series = new Series(type, t, dir, rat, ct, yr, genre, len);
            titles.add(Series);
        }
        else {
            System.out.println("Please enter a valid title type. Either \"Movie\" or \"TV Show\"");
        }
    }

    public void printCollection() {
        for (Title t : titles) {
            t.print();
            System.out.println(" ");
        }
    }

    public void removeTitle(String title) {
        Title selected = new Title("None", "None", "None", "None", "None", 0, "None");
        for (Title t : this.titles) {
            if (t.getTitle().compareTo(title) == 0) {
                selected = t;
                break;
            }
        }

        titles.remove(selected);

        // Deletes Entry from CSV by copying CSV to temp without the entry, and then rewrites it
        File infile = new File(dataFile);
        File temp = new File("temp.csv");
        try {
            Scanner reader = new Scanner(infile);
            FileWriter fw = new FileWriter(temp);
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.contains(title)) {
                    continue;
                } else {
                    fw.write(line + "\n");
                }
            }

            reader.close();
            fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        temp.renameTo(infile);
       
    }

    public void searchTitles(String type, String attr, String value) {
        ArrayList<Title> results = new ArrayList<Title>();

        for (Title t : titles) {
            if (t.getType().compareTo(type) == 0) {
                switch (attr) {
                    case "Rating":
                        if (t.getRating().compareTo(value) == 0) {
                            results.add(t);
                        }
                        break;
                    case "Director":
                        if (t.getDirector().compareTo(value) == 0) {
                            results.add(t);
                        }
                        break;
                    case "Genre":
                        // Has loop because titles can have multiple genres
                        if (t.getGenres().compareTo(value) == 0) {
                            results.add(t);
                        }
                        break;
                    case "lenMinutes":
                        // Special unformating for numeric range string input
                        int lower = intFromString(value.split("-")[0]);        // Lower Minute Bound
                        int upper = intFromString(value.split("-")[1]);        // Upper Minute Bound

                        if (t.getDuration() > lower && t.getDuration() < upper) {
                            results.add(t);
                        }
                        break;
                    case "numSeasons":
                        if (t.getDuration() == intFromString(value)) {
                            results.add(t);
                        }
                        break;
                    case "Country":
                        if (t.getCountry().compareTo(value) == 0) {
                            results.add(t);
                        }
                        break;
                    case "Year":
                        if (t.getYear() == Integer.parseInt(value)) {
                            results.add(t);
                        }
                        break;
                    default:
                        continue;
                }
            }
        }
        
        if (results.size() == 0) {
            System.out.println("No Titles Found");
            return;
        }
        System.out.println("\nResults: \n");     
        for (Title t : results) {
            t.print();
            System.out.println(" ");
        }

    }


    // Helper for building container out of csv file
    // Possible Performance Improvement could be using StringBuilder to make strings instead of concatenation
    private static String[] parseString(String s) {
        String[] info = new String[9];
        int array_spot = 0;
        int count = 0;

        while(count < s.length()) {
            String cur = "";
            if (s.charAt(count) == '"') {
                count++;
                while (count < s.length() && s.charAt(count) != '"') {
                    cur += s.charAt(count);
                    count++;
                }
                count++;
            }
            else {
                while (count < s.length() && s.charAt(count) != ','){
                    cur += s.charAt(count);
                    count++;
                }
            }
            info[array_spot] = cur;
            array_spot++;
            count++;
            
        }
        return info;
    }

    // Used to take year, duration out of string
    private int intFromString(String s) {
        s = s.replaceAll("\\D+", "");
        int i = Integer.parseInt(s);
        return i;
    }

    // This is used to add new title to file, not in addTitle, bc that function is used in constructor
    public void addToFile(String t, String dir, String rat, String ct, int yr, String genre, int len, String type){
        File file = new File(dataFile);
        try {
            FileWriter fw = new FileWriter(file, true);

            // Movies and Series have different duration label
            String dur;
            if (type.compareTo("Movie") == 0) {
                dur = Integer.toString(len) + " min";
            } else {
                dur = Integer.toString(len) + " Seasons";
            }

            // Wrap strings with multiple items in quotes, per csv formatting
            if (t.contains(",")) {
                t = '"' + t + '"';
            }
            if (dir.contains(",")) {
                dir = '"' + dir + '"';
            }
            if (rat.contains(",")) {
                rat = '"' + rat + '"';
            }
            if (genre.contains(",")) {
                genre = '"' + genre + '"';
            }
            if (t.contains(",")) {
                t = '"' + t + '"';
            }
            if (ct.contains(",")) {
                ct = '"' + ct + '"';
            }

            // s-- is the index, not tracked in this program. ... yet
            fw.write("s--," + type + "," + t + "," + dir + "," + ct + "," + Integer.toString(yr) + "," + rat + "," + dur + "," + genre);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public ArrayList<Title> getTitles() {
        return titles;
    }

    public HashMap<Integer, String> getRatings() {
        return ratings;
    }

    public HashMap<Integer, String> getGenres() {
        return genres;
    }

    public HashMap<Integer, String> getDirectors() {
        return directors;
    }

    public HashMap<Integer, String> getCountries() {
        return countries;
    }

    public HashMap<Integer, String> getNumSeasons() {
        return numSeasons;
    }

    public HashMap<Integer, String> getYears() {
        return years;
    }
}
