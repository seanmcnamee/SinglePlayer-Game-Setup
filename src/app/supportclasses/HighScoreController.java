package app.supportclasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class HighScoreController {
    private BufferedReader fileIn;
    private String fileName;
    private ArrayList<DateScore> dateScores;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  

    private class DateScore {
        public Date date;
        public int score;

        public DateScore(Date date, int score) {
            this.date = date;
            this.score = score;
        }
        public String toString() {
            return formatter.format(date) + "     " + score;
        }
        public String fileString() {
            return formatter.format(date) + " " + score;
        }
    }

    public HighScoreController(String fileName) {
        this.fileIn = getFile(fileName);
        this.fileName = fileName;
        dateScores = new ArrayList<DateScore>();
        try {
            fillScores();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private BufferedReader getFile(String fileName) {
        BufferedReader fileIn = null;
        try {
            fileIn = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileIn;
    }


    private void fillScores() throws IOException, ParseException {
        while(fileIn.ready()) {
            StringTokenizer line = new StringTokenizer(fileIn.readLine());
            
            Date date = formatter.parse(line.nextToken());
            int score = Integer.parseInt(line.nextToken());
            dateScores.add(new DateScore(date, score));
        }
    }

    public void saveFile() throws IOException {
        Writer out = new BufferedWriter(new FileWriter(fileName));
        PrintWriter easyOut = new PrintWriter( out );
        for (int i = 0; i < dateScores.size(); i++) {
            easyOut.println(dateScores.get(i).fileString());
        }
        easyOut.close();
    }

    public ArrayList<DateScore> getScores() {
        return dateScores;
    }

    public void addScore(int score) {
        addScore(new DateScore(new Date(), score));
    }

    public void addScore(DateScore score) {
        int index;
        if (dateScores.size() == 0) {
            index = 0;
        } else {
            index = recursiveSearch(score.score, 0, dateScores.size()-1);
        }
        dateScores.add(index, score);
    }

    /**
     * Largest to smallest
     */
    private int recursiveSearch(int key, int low, int high) {
        if (high-low == 0) {
            if (high == dateScores.size()-1) {
                return dateScores.size();
            } else {
                return high;
            }
        }

        int mid = low + (high - low) / 2;
        if (key == dateScores.get(mid).score) {
            return mid;
        } else if (key > dateScores.get(mid).score) {
            return recursiveSearch(key, low, mid);
        } else {
            return recursiveSearch(key, mid+1, high);
        }
    }
}
