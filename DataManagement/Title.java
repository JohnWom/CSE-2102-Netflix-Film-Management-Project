package DataManagement;

public class Title
{
    private String type;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String title;
    private String director;
    private String rating;
    private String country;
    private int year;
    private String genres;




    protected Title(String ty, String t, String dir, String rat, String ct, int yr, String genre) {
        this.type = ty;
        this.title = t;
        this.director = dir;
        this.rating = rat;
        this.country = ct;
        this.year = yr;
        this.genres = genre;
    }

    public void print(){
        System.out.println("Title: " + title);
        System.out.println("Director: " + director);
        System.out.println("Rating: " + rating);
        System.out.println("Country: " + country);
        System.out.println("Year Released: " + year);
        System.out.print("Genre: " + genres);
        System.out.println(" ");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getDuration(){return 0;}

    public void setDuration(int i){}

}


class Movie extends Title 
{
    private int lenMinutes;

    public Movie(String ty, String t, String dir, String rat, String ct, int yr, String genre, int len) {
        super(ty, t, dir, rat, ct, yr, genre);

        this.lenMinutes = len;
    }

    public void print() {
        super.print();
        System.out.println("Length in Minutes: " + lenMinutes);
    }

    public int getDuration() {
        return lenMinutes;
    }

    public void setDuration(int lenMinutes) {
        this.lenMinutes = lenMinutes;
    }
}


class Series extends Title 
{
    private int numSeasons;

    public Series(String ty, String t, String dir, String rat, String ct, int yr, String genre, int len) {
        super(ty, t, dir, rat, ct, yr, genre);

        this.numSeasons = len;
    }

    public void print() {
        super.print();
        System.out.println("Number of Seasons: " + numSeasons);
    }

    public int getDuration() {
        return numSeasons;
    }

    public void setDuration(int numSeasons) {
        this.numSeasons = numSeasons;
    }

}