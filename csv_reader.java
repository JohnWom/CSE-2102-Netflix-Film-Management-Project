import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class csv_reader {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the name of the csv file to be read: ");
        String fileName = sc.nextLine();
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String s = reader.nextLine();

                String[] info = parseString(s);
                System.out.println(Arrays.toString(info) + ", " + info.length);
            }
            reader.close();
            

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        sc.close();
    }

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
            info[array_spot] = cur.strip();
            array_spot++;
            count++;
            
        }
        System.out.println(" ");
        return info;
    }
}
