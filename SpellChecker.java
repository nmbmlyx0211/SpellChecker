
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;


public class SpellChecker {

    public static final String PUNCTUATION_MARKS = ".;?";
    public static final int IGNORE_RULE_OPTION = 1;
    public static final int VALID_WORD_OPTION = 2;
    public static final int CORRECT_WORD_OPTION = 3;
    public static final int IGNORE_WORD_OPTION = 4;
    private Dictionary dict;
    
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
    		this.dict  = new Dictionary(dictionaryFile);
    }

    /**
     * Update dictionary file
     */
    public void updateDictionary() throws IOException{
		dict.update();
    }


    /**
     * Check words in the file targetFile and output the word in outputFile
     * @param targetFile input file
     * @param outputFile output file
     */
    public void checkWords(String targetFile, String outputFile) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(targetFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        
        String line = null;
        while ((line=reader.readLine()) != null) {
        	if(line.length()==0){
        		line=reader.readLine();
        	}
        	StringBuffer sb = new StringBuffer();
            String [] words = line.split(" ");
            for (String word : words){
				if (dict.isValid(word) == true){
					sb.append(word + " "); 
				}
				else{
					StringBuffer sb1 = new StringBuffer();
					Character punc = null;
					for (char ch : word.toCharArray()){
						if(PUNCTUATION_MARKS.indexOf(ch) !=-1){
							punc = ch;
						}else{
							sb1.append(ch);
						}
					}
					word = sb1.toString();
					punc = (punc == null? Character.MIN_VALUE:punc);
					int num = 0;
					do{               			
						System.out.println("\nPlease choose one of the following :");
						System.out.println("1:Ignore Rule\n2:Valid Word\n3:Correct Word\n4:Ignore word");
						Scanner input = new Scanner (System.in);
						num = input.nextInt();            				
						
						switch(num){
							case 1:
								sb.append(word + punc + " ");  
								break;
							case 2:
								sb.append(word + punc + " "); 
								dict.addWord(word);
								break;
							case 3:
								String newWord;
								do{
									System.out.println("Please input correct word: ");
					    				Scanner sc = new Scanner (System.in);
					    				newWord = sc.next(); 
						    			if (newWord.chars().allMatch(Character::isLetter)){
						    				sb.append(newWord + punc + " "); 
						    				dict.addWord(newWord);
						    			}
						    			else{
						    				System.out.println("Invalid word: ["+ newWord + "]. please input a word with only alphabetic characters.");
						    			}
								}while (!(newWord.chars().allMatch(Character::isLetter)));
								break;
							case 4:
								sb.setLength(sb.length()-1);
								sb.append(punc + " ");
								break;
							default:
								System.out.println("Error: please input a valid value!");   
						}
					}while(num != 1 && num != 2 && num != 3 && num !=4);
					if(punc == '.' || punc == '?'){
						sb.append(System.getProperty("line.separator"));
					}
				}
            }
            writer.write(sb.toString());
        }
		
		writer.flush();
		writer.close();
		reader.close();
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
