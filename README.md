# Anagram Evaluator

This application takes a wordlist and detects anagrams held within the wordlist. In the event that anagrams exist for a 
given word, these are all grouped and printed on one line. In the event that an anagram does not exist then the word is 
printed on its own line.

## Extra assumptions made

In addition to the listed assumptions on the specification, I assume the following: 

- All words given should be processed as-is with no changes to casing. 
- Similarly, no extraneous whitespace characters should be removed from the start or end of a line/word.

## Chosen Language

The application is written in Java 17, which is the latest LTS version of the JDK available at the time of writing. 

The application also uses Spring Boot 2.7.0 as a framework - this *does not* provide support for solving anagrams as 
part of the solution.

## Startup and Running the application

The application is run through the command line and will require a build with Maven to get started. 

### Prerequisites 

You will need both Maven and JDK17 installed and present in the `PATH` variable on your machine.
To check these run the following within a terminal of your choosing: 
 - Java - `java -version`
 - Maven - `mvn -version`

1. From the root of the project run `mvn clean install` - this will build the executable `.jar` file which may be run.
2. Navigate to the `target` directory within the project - you should see a file named `anagram-evaluator.jar`. 
3. Use `java -jar anagram-evaluator.jar {WORDLIST_PATH}` replacing `{WORDLIST_PATH}` with the system file path of the
   wordlist that you intend to check. 
4. The startup of the application and result of checking will be displayed in the terminal. It is **recommended** to
   pipe this output to a text file to ensure that no data is lost. This is done by adding `> output.txt` to the command 
   run in step 3. This will place a file named `output.txt` within the `target` directory of the project.

## Big O Analysis

There are 3 main functions defined in the solution and each of these will be defined separately. These are: 

- **Parsing** the contents of the wordlist
- **Conversion** of each element in the wordlist to a `Word` model
- **Detection** of anagrams and non-anagram `Word` models

### Parsing

Each line in the word list holds a singular word. Each word is read in and converted to a `Word` model. It is reasonable
to conclude that the parsing of the wordlist will run in ***O*(N)** as the initial wordlist of size N scales up and down. 

### Conversion

Each `String` word goes through this conversion which will produce a `Word` model containing both the original `String` 
that was read in, and a `CharacterDistribution` object. The purpose of `CharacterDistribution` model is to denote the 
number of times that a character appeared within the `String`. The creation of this model requires one pass of the input
string. I believe it is therefore reasonable again to conclude that this will run in ***O*(N)** as the length of the 
word of size N scales up and down. 

### Detection

By the point of detection, a `List<Word>` has been populated complete with character distribution maps per-word.
Detection applies a similar form of logic to the identification of the character distribution map whereby the 
character distribution map acts as the key to a `Map<CharacterDistribution, List<String>>`. Given that two `Word` models
contain the same hashed key, the `String` representation of the `Word` will be added as an entry to the overall Map 
value. 

This again requires one pass of the `List<Word>` collection to deduce the presence of anagrams held in the wordlist. The
resulting values held in the `Map<CharacterDistribution, List<String>>` are then extracted and comma separated as per 
the requirements, requiring a single pass of the maps' values. 

Due to these factors, I would say that this portion of the application again runs in ***O*(N)** as the time taken to
iterate through the `List<Word>` and `List<String>` both are dependent on the quantity of words provided.

## Reasons behind data structures and code composition

TThere are a few key points to address surrounding how the application has been created.

## Usage of a `Word` model 

From the outset of the application I knew that my target goal was to base anagram detection on character distribution. 
A `Map<Character, Integer>` would naturally be insufficient to accomplish this task alone as the composition of the 
original word would be irretrievable beyond the invocation of the `WordConverterService` `String` conversion. 
Due to this I decided to include both the original `String` and the character distribution in a clear model. Length was
*not* included here and would only be a consideration in the instance that the provided wordlists were not sorted prior. 

## Usage of a `Map<CharacterDistribution, List<String>>`

At the point of creation of this collection, the focus shifts from processing "words" to processing entities which have
the same hashed key value - their character distribution. Usage of the `Map` interface allows for the following things: 
- *Cohesion with the `Optional` interface* - it is simple to check for a hashed character distribution `Map` entry and 
  act accordingly depending on its presence/absence. 
- *Automatic anagram detection* - given that a key already exists, this indicates that a matching word has already been 
  processed and thus an anagram exists. 
- *Zero word comparisons* - it is important to note that at no point does this application ever compare two words in
  their `String` form directly. While the usage of the `Map` interface does introduce complexity, I feel that this
  trade-off is fair with naming in the project.

## Argument evaluation in `AnagramEvaluatorApplication`

`AnagramEvaluatorApplication` is the entrypoint of the application and as such I have elected to check arguments here.
In the event that zero, or more than 1, arguments have been passed, the application will make no attempt to parse a 
wordlist. While it may be argued that this composes business logic and may be better suited in a `Service` annotated 
class - my local attempts to do so either polluted the entry layer of business logic, (now exposed through the
`AnagramService` interface), or did not provide clear separation of concerns.

Placing the argument check within the `run` method of the application entrypoint allows the application to "fail-fast"
when the conditions of use are not met resulting in broken functionality. 

## Given more time I would...

- Provide a benchmark performance test giving an idea of how long the application takes to process given wordlist sizes.
- Add integration tests with word lists of different sizes.
- Investigate if there are more optimal solutions for anagram detection.
- Use aspect orientated programming to benchmark performance, through time, of inner '@Service' annotated classes. 