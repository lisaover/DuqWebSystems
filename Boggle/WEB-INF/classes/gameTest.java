
import java.util.*;
  
public class gameTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
      /*
        BoggleBean bean = new BoggleBean();
        String[][] name = bean.getBoard();
        for(int i=0; i<4; i++) {
          for(int j=0; j<4; j++) {
            System.out.println(name[i][j]);
          }
        }
        
      */
      BoggleBean bean = new BoggleBean();
      
      String[] allWords = new String[16]; 
      allWords[0] = "ACE";
      allWords[1] = "SAT";
      allWords[2] = "ATE";
      allWords[3] = "SIT";
      allWords[4] = "EAT";
      allWords[5] = "TEA";
      allWords[6] = "BIN";
      allWords[7] = "PAT";
      allWords[8] = "CAT";
      allWords[9] = "RED";
      allWords[10] = "RUN";
      allWords[11] = "DEN";
      allWords[12] = "PEN";
      allWords[13] = "BAT";
      allWords[14] = "MAT";
      allWords[15] = "NOT";
   
      String wordsFound = ("ACE\nATE\nRUN\n");
                      
      setGameMap(wordsFound, allWords, bean);
      System.out.println("words found");
      System.out.println(bean.wordsFound);
      System.out.println("words missed");
      System.out.println(bean.wordsMissed);
        
   }  
    
    //split wordsFound String by "\\r?\\n" and place into an array
//create a hashmap of String keys (words on board) and Boolean values (found or not) set initially to false
//compare hashmap of userWords to allWords and mark true any matches
//call setWordsFound and setWordsMissed
public static void setGameMap(String wordsFound, String[] allWords, BoggleBean bean) {
  
   // split the wordTally string into single words and store in an array
   String[] userWords = wordsFound.split("\\r?\\n");
     
  //create map of words on the board
  HashMap<String, Boolean> wordMap;
  wordMap = new HashMap<String,Boolean>();
  for (String word : allWords) {
    wordMap.put(word.toUpperCase(), false);
  }
  
  //check each word found by the user to see if it is in the map
  //set value to true if user found word is in the map
    for (String userWord : userWords) {
     //   System.out.println("inside for loop " + userWord);
        if (wordMap.containsKey(userWord)) {
           // System.out.println("found a match");
            wordMap.put(userWord, true);
        }
    }
  
    //bean.setWordsFound(wordMap);
    //bean.setWordsMissed(wordMap);
}

}
