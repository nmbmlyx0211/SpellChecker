package edu.njit.cs602.s2018.assignments;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class SpellChecker {

    public static final String PUNCTUATION_MARKS = ".;?";
    public static final int IGNORE_RULE_OPTION = 1;
    public static final int VALID_WORD_OPTION = 2;
    public static final int CORRECT_WORD_OPTION = 3;
    public static final int IGNORE_WORD_OPTION = 4;

    private class Dictionary {
        private final String dictionaryFile;
        private final Set<String> wordList = new HashSet<>();

        /**
         * Add word to dictionary
         * @param word
         */
        public void addWord(String word) {
            wordList.add(word);
        }

        /**
         * Is it a valid word ?
         * @param word
         * @return
         */
        public boolean isValid(String word) {
            return wordList.contains(word);
        }

        /**
         * Update the dictionary file
         */
        public void update() throws IOException {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(dictionaryFile));
                for (String word : wordList) {
                    writer.write(word);
                    writer.newLine();
                }
                writer.close();
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        /**
         * Construct dictionary from a dictionary file, one word per line
         * @param dictionaryFile
         * @throws IOException
         */
        public Dictionary(String dictionaryFile) throws IOException {
            this.dictionaryFile = dictionaryFile;
            BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile));
            String line = null;
            while ((line=reader.readLine()) != null) {
                addWord(line.trim());
            }
            reader.close();
        }
    }

    /**
     * Constructs WordChecker with a dictionary given in dictionary file
     * @param dictionaryFile
     */
    public SpellChecker(String dictionaryFile) throws IOException {
    }

    /**
     * Update dictionary file
     */
    public void updateDictionary() {
    }


    /**
     * Check words in the file targetFile and output the word in outputFile
     * @param targetFile input file
     * @param outputFile output file
     */
    public void checkWords(String targetFile, String outputFile) {
    }


    public static void main(String [] args) throws Exception {
        String dictionaryFile = args[0];
        SpellChecker checker = new SpellChecker(dictionaryFile);
        String inputFile = args[1];
        String outputFile = args[2];
        checker.checkWords(inputFile, outputFile);
        checker.updateDictionary();
    }
}
