/** State variables */
var swiping = false;
var waiting = false;

/** Text box element */
var wordbox; 

/** Words found textarea */
var wordsFound;

/** Last highlighted td. */
var lastHighlit;

/** Words Found submit button. */
var wordsFoundButton;

/**
 * Set event listeners, initialize some variables.
 */
function init() {
  wordbox = document.getElementById("wordbox");
  wordsFound = document.getElementById("wordsFound");
  var tbody = document.getElementsByTagName("TBODY")[0];
  tbody.addEventListener("mousedown", startSwipe, true);
  tbody.addEventListener("mouseout", waitNext, true);
  tbody.addEventListener("mouseover", gotNext, true);
  document.addEventListener("mouseup", endSwipe, true);
  var form = document.getElementsByTagName("FORM")[0];
  form.addEventListener("submit", checkWord, true);
  var scoreMe = document.getElementById("scoreMe");
  scoreMe.addEventListener("click", score, false);
  var newGame = document.getElementById("newGame");
  newGame.addEventListener("click", startGame, false);
  wordsFoundButton = document.getElementById("wordsFoundButton");
}

/**
 * Click inside the table body.  If it is on a span, then
 * capture this letter and initiate swiping.
 */
function startSwipe(event) {
  if (isElement(event.target, "span")) {
    swiping = true;
    waiting = false;
    highlight(event.target);
    wordbox.value = event.target.childNodes[0].data;
    event.stopPropagation();
  }
}

/** 
 * Mouseout has occured.  If we are in the process of swiping
 * and the mouseout occurred from a td and we are not
 * moving back into a span (the one from which we came into the td,
 * presumably), then go to the state of waiting for the next span 
 * to be entered.
 */
function waitNext(event) {

  if (swiping && isElement(event.target, "td") && 
      !isElement(event.relatedTarget, "span")) {
//  if (swiping && isElement(event.target, "span")) {
    waiting = true;
    event.stopPropagation();
  }
}

/**
 * Mouseover has occurred.  If we are swiping and waiting for
 * the next character and we entered a span, capture this letter.
 */
function gotNext(event) {
  if (swiping && waiting && isElement(event.target, "span")) {
    waiting = false;
    wordbox.value = wordbox.value + event.target.childNodes[0].data;
    highlight(event.target);
    event.stopPropagation();
  }
}


/**
 * Mouseup has occured on the document; we're done swiping.
 */
function endSwipe(event) {
  if (swiping) {
    swiping = false;
    if (lastHighlit) {
      lastHighlit.style.setProperty("background-color", "inherit", "");
      lastHighlit = null;
    }
    checkWord();
    // event.stopPropagation();
  }
}

function isElement(node, elementName) {
  return ((node.nodeType == Node.ELEMENT_NODE)
          && (node.nodeName.toLowerCase() == elementName));
}

function highlight(elt) {
    if (lastHighlit) {
      lastHighlit.style.setProperty("background-color", "inherit", "");
    }
    lastHighlit = elt.parentNode;
    lastHighlit.style.setProperty("background-color", "yellow", "");
}  

/**
 * Misnamed function.  It simply records the word entered by the user.
 */
function checkWord(event) {

  var word = wordbox.value.toUpperCase();
  if (!wordsFound.hasChildNodes()) {
    wordsFound.appendChild(document.createTextNode(""));
  }
  wordsFound.childNodes[0].data = word + "\n" + 
                                  wordsFound.childNodes[0].data;
  msgArea.childNodes[0].data = "Recorded " + word;
  wordbox.value = "";
  if (event) {
    event.preventDefault();
  }
}

/** Pass words to server for it to score. */
function score(event) {
  wordsFoundButton.click();
}

/** Navigate to the server using GET method (preserves URL rewriting). */
function startGame(event) {
  window.location.href = window.location.href;
}