package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Carlin on 11/19/2014.
 */

public class driver {

    static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String args[]) throws IOException {
        String docsFile = "docs.txt";
        String noiseWords = "noisewords.txt";

        LittleSearchEngine google = new LittleSearchEngine();

        google.makeIndex(docsFile, noiseWords);

        String kw1 = "deep";
        String kw2 = "world";

        google.top5search(kw1, kw2);


    }

}