package search;

import java.io.*;
import java.util.*;

/**
 * This class encapsulates an occurrence of a keyword in a document. It stores the
 * document name, and the frequency of occurrence in that document. Occurrences are
 * associated with keywords in an index hash table.
 */
class Occurrence {
    /**
     * Document in which a keyword occurs.
     */
    String document;

    /**
     * The frequency (number of times) the keyword occurs in the above document.
     */
    int frequency;

    /**
     * Initializes this occurrence with the given document,frequency pair.
     *
     * @param doc Document name
     * @param freq Frequency
     */
    public Occurrence(String doc, int freq) {
        document = doc;
        frequency = freq;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "(" + document + "," + frequency + ")";
    }
}

/**
 * This class builds an index of keywords. Each keyword maps to a set of documents in
 * which it occurs, with frequency of occurrence in each document. Once the index is built,
 * the documents can searched on for keywords.
 *
 */

    public class LittleSearchEngine {

    /**
     * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
     * an array list of all occurrences of the keyword in documents. The array list is maintained in descending
     * order of occurrence frequencies.
     */
    HashMap<String,ArrayList<Occurrence>> keywordsIndex;

    /**
     * The hash table of all noise words - mapping is from word to itself.
     */
    HashMap<String,String> noiseWords;

    /**
     * Creates the keyWordsIndex and noiseWords hash tables.
     */
    public LittleSearchEngine() {
        keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
        noiseWords = new HashMap<String,String>(100,2.0f);
    }

    /**
     * This method indexes all keywords found in all the input documents. When this
     * method is done, the keywordsIndex hash table will be filled with all keywords,
     * each of which is associated with an array list of Occurrence objects, arranged
     * in decreasing frequencies of occurrence.
     *
     * @param docsFile Name of file that has a list of all the document file names, one name per line
     * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
     * @throws FileNotFoundException If there is a problem locating any of the input files on disk
     */
    public void makeIndex(String docsFile, String noiseWordsFile) throws FileNotFoundException
    {
        // load noise words to hash table
        Scanner sc = new Scanner(new File(noiseWordsFile));
        while (sc.hasNext()) {
            String word = sc.next();
            noiseWords.put(word,word);
        }

        // index all keywords
        sc = new Scanner(new File(docsFile));
        while (sc.hasNext()) {
            String docFile = sc.next();
            HashMap<String,Occurrence> kws = loadKeyWords(docFile);
            mergeKeyWords(kws);
        }
    }

    /**
     * Scans a document, and loads all keywords found into a hash table of keyword occurrences
     * in the document. Uses the getKeyWord method to separate keywords from other words.
     *
     * @param docFile Name of the document file to be scanned and loaded
     * @return Hash table of keywords in the given document, each associated with an Occurrence object
     * @throws FileNotFoundException If the document file is not found on disk
     */
    public HashMap<String,Occurrence> loadKeyWords(String docFile) throws FileNotFoundException
    {
        HashMap<String,Occurrence> keywords = new HashMap<String,Occurrence>();
        File docs = new File(docFile);
        try
        {
            Scanner words = new Scanner(docs);
        }
        catch(FileNotFoundException e)
        {
            return keywords;
        }
        Scanner words = new Scanner(docs);
        int freq = 1;

        boolean next = words.hasNext();
        if(next)
        {
            do
            {
                String nextWord = words.next();

                if(getKeyWord(nextWord) != null)
                {
                    nextWord = getKeyWord(nextWord);
                    boolean containsNextWord = keywords.containsKey(nextWord);
                    if(!containsNextWord)
                    {
                        Occurrence occ = new Occurrence(docFile,freq);
                        keywords.put(nextWord, occ);
                    }
                    else if(containsNextWord)
                    {
                        int keyFreq = keywords.get(nextWord).frequency;
                        keyFreq++;
                        keywords.get(nextWord).frequency = keyFreq;
                    }
                }
                next = words.hasNext();
            }while(next);
        }
        return keywords;
    }

    /**
     * Merges the keywords for a single document into the master keywordsIndex
     * hash table. For each keyword, its Occurrence in the current document
     * must be inserted in the correct place (according to descending order of
     * frequency) in the same keyword's Occurrence list in the master hash table.
     * This is done by calling the insertLastOccurrence method.
     *
     * @param kws Keywords hash table for a document
     */
    public void mergeKeyWords(HashMap<String,Occurrence> kws)
    {
        ArrayList<Occurrence> occList1;
        for(String occKey: kws.keySet())
        {
            Occurrence occ = kws.get(occKey);
            boolean containsOccKey = keywordsIndex.containsKey(occKey);
            ArrayList<Occurrence> occList2 = new ArrayList<Occurrence>();
            if(!containsOccKey)
            {
                occList2.add(occ);
                keywordsIndex.put(occKey, occList2);
            }
            else if(containsOccKey)
            {
                occList1 = keywordsIndex.get(occKey);
                occList1.add(occ);
                insertLastOccurrence(occList1);
                keywordsIndex.put(occKey, occList1);
            }
        }
    }

    /**
     * Given a word, returns it as a keyword if it passes the keyword test,
     * otherwise returns null. A keyword is any word that, after being stripped of any
     * trailing punctuation, consists only of alphabetic letters, and is not
     * a noise word. All words are treated in a case-INsensitive manner.
     *
     * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
     *
     * @param word Candidate word
     * @return Keyword (word without trailing punctuation, LOWER CASE)
     */
    public String getKeyWord(String word)
    {
        word = word.trim();
        char end = word.charAt(word.length()-1);
        while(endCharCheck(end))
        {
            word = word.substring(0, word.length()-1);
            if(word.length() > 1)
                end = word.charAt(word.length()-1);
            else
                break;
        }
        word = word.toLowerCase();
        for(String noiseWord: noiseWords.keySet())
        {
            if(word.equalsIgnoreCase(noiseWord))
                return null;
        }
        for(int count = 0; count < word.length(); count++)
        {
            if(!Character.isLetter(word.charAt(count)))
                return null;
        }
        return word;
    }
    private static boolean endCharCheck(char end)
    {
        if(end == '.' || end == ',' || end == '?' || end == ':' || end == ';' || end == '!')
            return true;
        else
            return false;
    }

    /**
     * Inserts the last occurrence in the parameter list in the correct position in the
     * same list, based on ordering occurrences on descending frequencies. The elements
     * 0..n-2 in the list are already in the correct order. Insertion is done by
     * first finding the correct spot using binary search, then inserting at that spot.
     *
     * @param occs List of Occurrences
     * @return Sequence of mid point indexes in the input list checked by the binary search process,
     *         null if the size of the input list is 1. This returned array list is only used to test
     *         your code - it is not used elsewhere in the program.
     */
    public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs)
    {
        int lastFreq, low, middle, high;
        if(occs.size() == 1)
            return null;
        ArrayList<Integer> midIndex = new ArrayList<Integer>();
        Occurrence temp = occs.get(occs.size()-1);
        lastFreq = occs.get(occs.size()-1).frequency;
        low = 0;
        high = occs.size()-1;
        if(high>=low)
        {
            do
            {
                int tempMid=low+high;
                middle=tempMid/2;
                midIndex.add(middle);
                tempMid=middle;
                if( lastFreq > occs.get(middle).frequency )
                    high = tempMid-1;
                else if(lastFreq < occs.get(middle).frequency)
                    low = tempMid+1;
                else
                    break;
            }while(high>=low);
        }
        if(midIndex.get(midIndex.size()-1) == 0)
        {
            if(temp.frequency < occs.get(0).frequency)
            {
                occs.add(1, temp);
                occs.remove(occs.size()-1);
                return midIndex;
            }
        }
        occs.add(midIndex.get(midIndex.size()-1), temp);
        occs.remove(occs.size()-1);
        return midIndex;
    }

    /**
     * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
     * document. Result set is arranged in descending order of occurrence frequencies. (Note that a
     * matching document will only appear once in the result.) Ties in frequency values are broken
     * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
     * also with the same frequency f1, then doc1 will appear before doc2 in the result.
     * The result set is limited to 5 entries. If there are no matching documents, the result is null.
     *
     * @param kw1 First keyword
     * @param kw1 Second keyword
     * @return List of NAMES of documents in which either kw1 or kw2 occurs, arranged in descending order of
     *         frequencies. The result size is limited to 5 documents. If there are no matching documents,
     *         the result is null.
     */
    public ArrayList<String> top5search(String kw1, String kw2)
    {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Occurrence> list1 = new ArrayList<Occurrence>();
        ArrayList<Occurrence> list2 = new ArrayList<Occurrence>();
        String keyWord1 = kw1.toLowerCase();
        String keyWord2 = kw2.toLowerCase();
        if(keywordsIndex.get(keyWord1) != null)
            list1 = keywordsIndex.get(keyWord1);
        if(keywordsIndex.get(keyWord2) != null)
            list2 = keywordsIndex.get(keyWord2);
        int iter1=0;
        while(iter1<list1.size())
        {
            if(result.size()<=4)
            {
                int iter2=0;
                int list1Freq = list1.get(iter1).frequency;
                String docName1 = list1.get(iter1).document;
                while(iter2<list2.size())
                {
                    String docName2 = list2.get(iter2).document;
                    int list2Freq = list2.get(iter2).frequency;
                    if(list2Freq <= list1Freq)
                    {
                        if(!result.contains(docName1) && result.size() <= 4)
                            result.add(docName1);
                    }
                    else if(list2Freq > list1Freq)
                    {
                        if(!result.contains(docName2) && result.size() <= 4)
                            result.add(docName2);
                    }
                    iter2++;
                }
            }
            iter1++;
        }
        System.out.println();
        int count=0;
        while(count<result.size())
        {
            String res = result.get(count);
            if(count + 1 == result.size())
                System.out.print(res);
            else
                System.out.print(res + ", ");
            count++;
        }
        if(result.size() == 0)
            return null;
        else
            return result;
    }
}