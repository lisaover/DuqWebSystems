// The Boggle Bean 



 
import java.util.*;


public class BoggleBean{
/**
 * Class that "knows" about Boggle.
 * Specifically, this class contains the dictionary that will be used,
 * can generate random Boggle boards,
 * and can find all of the words contained in a given board.
 * 
 */

    /** 
     * The "mark" array will be used when recursively searching for a word
     * to make sure we don't double back on ourselves.
     */

// Bean Contents *******************************

// instance variables

String[][] gameboard;
String wordsFound;
String wordsMissed;
int numWordsFound;
int numWordsOnBoard;
int totWordsFound;
int totWordsOnBoards; 

String[] allWords; // an array of all words on the current board
HashMap<String, Boolean> gameMap = new HashMap<String, Boolean>();


public BoggleBean(){
totWordsFound = 0;
totWordsOnBoards = 0;
createBoard();
}

public String[][] getBoard(){
  
 return gameboard; 
 }

//split wordsFound String by "\\r?\\n" and place into an array
//create a hashmap of String keys (words on board) and Boolean values (found or not) set initially to false
//compare hashmap of userWords to allWords and mark true any matches
//call setWordsFound and setWordsMissed
public void setGameMap(String wordsFound) {
  
   // split the wordTally string into single words and store in an array
   String[] userWords = wordsFound.split("\\r?\\n");
     
  //create map of words on the board
  HashMap<String, Boolean> gameMap;
  gameMap = new HashMap<String,Boolean>();
  for (String word : allWords) {
    gameMap.put(word.toUpperCase(), false);
  }
  
  //check each word found by the user to see if it is in the map
  //set value to true if user found word is in the map
    for (String userWord : userWords) {
     //   System.out.println("inside for loop " + userWord);
        if (gameMap.containsKey(userWord)) {
           // System.out.println("found a match");
            gameMap.put(userWord, true);
        }
    }
  
    setWordsFound(gameMap);
    setWordsMissed(gameMap);
}

private void setWordsFound(HashMap gameMap){
  
  for (Iterator it = gameMap.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            if ((Boolean)gameMap.get(key)) {
              wordsFound += (String)key + "\n";
              numWordsFound++;
              totWordsFound++;
            } 
        }
  
  }

private void setWordsMissed(HashMap gameMap) {
 
  for (Iterator it = gameMap.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            if (!(Boolean)gameMap.get(key)) {
              this.wordsMissed += (String)key + "\n";
            } 
        }
  
}


public String getWordsFound(){

 return wordsFound;
 
}

public String getWordsMissed(){

 return wordsMissed;
 
}

public int getNumberFound(){

 return numWordsFound;
}

public int getNumberOnBoard(){
  
 return numWordsOnBoard; 
  
}

public int getTotalFound(){
  
 return totWordsFound; 
  
}

public int getTotalOnBoard(){
  
 return totWordsOnBoards;  
  
}


public boolean hasWord(String[] list, String word){
  
  for(String wd:list){
   
    if(wd.equals(word))
      return true;
    
    }
  return false; 
  
  
}

//**********************************************BOARD METHODS

    private Boolean mark[][] = { {false, false, false, false},
     {false, false, false, false},
     {false, false, false, false},
     {false, false, false, false} };

   

    /** Create a random Boggle board. */
    public void createBoard() {
 // This array effectively makes some letters more likely to appear
 // than others when it is sampled uniformly.
 String[] letters = {
     "A", "A", "A", "B", "C", "D", "E", "E", "E", "E", "E",
     "F", "G", "H", "I", "I", "J", "K", "L", "M", "N", "N", 
     "O", "O", "P", "Q", "R", "S", "T", "T", "T", "U", "U",
     "V", "W", "X", "Y", "Z"
 };

 gameboard = new String[4][];
 for (int i=0; i<4; i++) {
     gameboard[i] = new String[4];
     for (int j=0; j<4; j++) {
  gameboard[i][j] = 
      letters[(int)Math.floor(Math.random()*letters.length)];
     }
 }
 
 allWords = findAllWords(gameboard);
 numWordsOnBoard = allWords.length; 
 numWordsFound = 0; 
 totWordsOnBoards += numWordsOnBoard; 
 wordsFound = "";
 wordsMissed = "";
 
    }

    /** Is the given location (i,j) on the board? */
    private boolean isOnBoard(int i, int j) {
 return (i >= 0 && i < 4 && j >= 0 && j < 4);
    }

    /**
     * Is the given word on the board beginning at location (i,j)? 
     * This method is syncrhonized because it uses the static field 'mark'.
     */
    private synchronized boolean isWordAt
 (String str, int i, int j, String[][] board) {

 // Test for various stopping conditions
 if (str.length() == 0) return true;
 if (!isOnBoard(i,j)) return false;
 if (mark[i][j])      return false;
 if (!str.startsWith(board[i][j])) return false;
 
 // If no reason to stop, recursively search neighborhood.
 mark[i][j] = true;
 // chop off first letter; it's at (i,j)
 String newStr = str.substring(1); 
 boolean retVal = false;
 for (int ii = i-1; !retVal && (ii <= i+1); ii++) {
     for (int jj = j-1; !retVal && (jj <= j+1); jj++) {
  if (isWordAt(newStr, ii, jj, board)) {
      retVal = true;
  }
     }
 }
 mark[i][j] = false;
 return retVal;
    }

    /** 
     *Look for each word on the board and add those found
     * to the list of words.
     */
    public String[] findAllWords(String[][] board) {
 ArrayList<String> boardWords = new ArrayList<String>();
 for (String word : dict) {
     boolean found = false;
     String wordCaps = word.toUpperCase();
     for (int i=0; !found && (i <= 3); i++) {
  for (int j=0; !found && (j <= 3); j++) {
      if (isWordAt(wordCaps, i, j, board)) {
   found = true;
   boardWords.add(wordCaps);
      }
  }
     }
 }
 String[] allWords = boardWords.toArray(new String[0]);
 Arrays.sort(allWords);
 return allWords;
    }
    
   /** The dictionary array of all 3-letter words. */
 private static String[] dict = {
 "ace",
 "act",
 "add",
 "ado",
 "ads",
 "aft",
 "age",
 "ago",
 "aid",
 "ail",
 "aim",
 "air",
 "ale",
 "all",
 "and",
 "ant",
 "any",
 "ape",
 "apt",
 "arc",
 "are",
 "ark",
 "arm",
 "art",
 "ash",
 "ask",
 "asp",
 "ate",
 "awe",
 "awl",
 "aye",
 "bad",
 "bag",
 "bah",
 "ban",
 "bar",
 "bat",
 "bay",
 "bed",
 "bee",
 "beg",
 "bet",
 "bib",
 "bid",
 "big",
 "bin",
 "bit",
 "boa",
 "bob",
 "bog",
 "boo",
 "bow",
 "box",
 "boy",
 "bra",
 "bud",
 "bug",
 "bum",
 "bun",
 "bus",
 "but",
 "buy",
 "bye",
 "cab",
 "cad",
 "cam",
 "can",
 "cap",
 "car",
 "cat",
 "caw",
 "cod",
 "cog",
 "con",
 "coo",
 "cop",
 "cot",
 "cow",
 "cry",
 "cub",
 "cud",
 "cue",
 "cup",
 "cut",
 "dad",
 "dam",
 "day",
 "den",
 "dew",
 "did",
 "die",
 "dig",
 "dim",
 "din",
 "dip",
 "doe",
 "dog",
 "don",
 "dot",
 "dry",
 "dub",
 "dud",
 "due",
 "dug",
 "dye",
 "ear",
 "eat",
 "ebb",
 "eel",
 "egg",
 "ego",
 "eke",
 "elf",
 "elk",
 "ell",
 "elm",
 "end",
 "era",
 "ere",
 "erg",
 "err",
 "ewe",
 "eye",
 "fan",
 "far",
 "fat",
 "fed",
 "fee",
 "fen",
 "few",
 "fib",
 "fig",
 "fin",
 "fir",
 "fit",
 "fix",
 "flu",
 "fly",
 "fob",
 "foe",
 "fog",
 "for",
 "fox",
 "fro",
 "fry",
 "fun",
 "fur",
 "gab",
 "gad",
 "gag",
 "gap",
 "gas",
 "gay",
 "gel",
 "gem",
 "get",
 "gig",
 "gin",
 "gnu",
 "god",
 "got",
 "gum",
 "gun",
 "gut",
 "guy",
 "had",
 "hag",
 "ham",
 "hap",
 "has",
 "hat",
 "hay",
 "hem",
 "hen",
 "her",
 "hew",
 "hex",
 "hey",
 "hid",
 "him",
 "hip",
 "his",
 "hit",
 "hoe",
 "hog",
 "hop",
 "hot",
 "how",
 "hub",
 "hue",
 "hug",
 "huh",
 "hum",
 "hut",
 "ice",
 "icy",
 "ill",
 "imp",
 "ink",
 "inn",
 "ion",
 "ire",
 "irk",
 "its",
 "ivy",
 "jab",
 "jam",
 "jar",
 "jaw",
 "jay",
 "jet",
 "jig",
 "job",
 "jog",
 "jot",
 "joy",
 "jug",
 "jut",
 "ken",
 "key",
 "kid",
 "kin",
 "kit",
 "lab",
 "lad",
 "lag",
 "lap",
 "law",
 "lax",
 "lay",
 "led",
 "lee",
 "leg",
 "let",
 "lid",
 "lie",
 "lip",
 "lit",
 "lot",
 "low",
 "mad",
 "man",
 "map",
 "mat",
 "men",
 "met",
 "mew",
 "mid",
 "mix",
 "mob",
 "moo",
 "mop",
 "mow",
 "mud",
 "mug",
 "nab",
 "nag",
 "nap",
 "nay",
 "net",
 "new",
 "nil",
 "nip",
 "nod",
 "non",
 "nor",
 "not",
 "now",
 "nun",
 "nut",
 "oaf",
 "oak",
 "oar",
 "oat",
 "odd",
 "ode",
 "off",
 "oft",
 "ohm",
 "oil",
 "old",
 "one",
 "opt",
 "orb",
 "ore",
 "our",
 "out",
 "owe",
 "owl",
 "own",
 "pad",
 "pal",
 "pan",
 "par",
 "pat",
 "paw",
 "pay",
 "pea",
 "peg",
 "pen",
 "pep",
 "per",
 "pet",
 "pew",
 "phi",
 "pie",
 "pig",
 "pin",
 "pip",
 "pit",
 "ply",
 "pod",
 "pop",
 "pot",
 "pox",
 "pro",
 "pry",
 "pub",
 "pun",
 "pup",
 "pus",
 "put",
 "qua",
 "quo",
 "rag",
 "ram",
 "ran",
 "rap",
 "rat",
 "raw",
 "ray",
 "red",
 "rho",
 "rib",
 "rid",
 "rig",
 "rim",
 "rip",
 "rob",
 "rod",
 "roe",
 "rot",
 "row",
 "rub",
 "rue",
 "rug",
 "rum",
 "run",
 "rut",
 "rye",
 "sad",
 "sag",
 "sap",
 "sat",
 "saw",
 "sax",
 "say",
 "sea",
 "see",
 "set",
 "sew",
 "sex",
 "she",
 "shy",
 "sip",
 "sir",
 "sit",
 "six",
 "ski",
 "sky",
 "sly",
 "sob",
 "sod",
 "son",
 "sow",
 "soy",
 "spa",
 "spy",
 "sub",
 "sue",
 "sum",
 "sun",
 "tab",
 "tag",
 "tan",
 "tap",
 "tar",
 "tau",
 "tax",
 "tea",
 "tee",
 "ten",
 "the",
 "tie",
 "tin",
 "tip",
 "toe",
 "ton",
 "too",
 "top",
 "tow",
 "toy",
 "try",
 "tub",
 "tug",
 "two",
 "ugh",
 "urn",
 "use",
 "van",
 "vat",
 "vex",
 "via",
 "vie",
 "vow",
 "wag",
 "wan",
 "war",
 "was",
 "wax",
 "way",
 "web",
 "wed",
 "wee",
 "wet",
 "who",
 "why",
 "wig",
 "win",
 "wit",
 "woe",
 "won",
 "woo",
 "yea",
 "yes",
 "yet",
 "yon",
 "you",
 "zoo"
    };
}
  
  
  
  
