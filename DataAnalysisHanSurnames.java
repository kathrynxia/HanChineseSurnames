import java.io.*;
import java.lang.*;
import java.util.*;

public class DataAnalysisHanSurnames {

  public static void main(String[] args)
    throws FileNotFoundException, NumberFormatException { //need to throw these exceptions because a file might not exist, or a number might not be able to be parsed.
    File givenName = new File(
      "/Users/kxia/CSSeminar/Unit1/HanChineseSurnames/ChineseNames-main/data-csv/givenname.csv"
    ); //This is the dataset I will be using, and I am saving it as a file object

    String[] nameTraits = { "name.valence", "name.competence", "name.warmth" }; //categories that I will be analyzing for both of my questions

    //name valence: positivity of character meaning), name warmth/morality: warmness, name competence: assertiveness

    printAverageNameValues(givenName, nameTraits); //finds the average name.valence, name.competence, and name.warmth for boys, girls, and gender neutral characters
    compareCommonAverageCharacterRatings(givenName, 3, nameTraits, "character"); //compares the 3 most popular characters to the 3 highest rated characters in terms of nameTraits
  }

  public static void printAverageNameValues(File f, String[] nameTraits)
    throws FileNotFoundException {//throwing the exception in case the file does not exist

    ArrayList<String> boyCharacters = new ArrayList<>();
    ArrayList<String> girlCharacters = new ArrayList<>();
    ArrayList<String> genderNeutralCharacters = new ArrayList<>();
    ArrayList<String> categories = getColumns(f);//saving the first line of the file, which are the categories of the data, into an ArrayList
    int ind = 0;//declaring and initializing and index value that we will use to make parallel arrays

    fillGenderArrays(
      f,
      girlCharacters,
      boyCharacters,
      genderNeutralCharacters,
      categories,
      0.250
      //if name.gender is negative, the moajority of people with it are girls, if it is positive, then boys, if 0, then it is gender neutral.
      //I chose 0.250 as the minimum value needed for a character to be classified as male or female
    );

    ArrayList<Double> girlValues = new ArrayList<>();
    ArrayList<Double> boyValues = new ArrayList<>();
    ArrayList<Double> genderNeutralValues = new ArrayList<>();

    for (int i = 0; i < nameTraits.length; i++) {
      ind = categories.indexOf(nameTraits[i]);//the index of the name of the column of the data set we are isolating for the parallel array

      //creating parallel ArraysLists with the trait at the index i of nameTraits, and then taking the average of all those ArrayLists and saving the values in another ArrayList
      girlValues.add(
        getAverage(
          createParallelArrayListDouble(
            f,
            categories,
            girlCharacters,
            nameTraits[i],
            ind
          )
        )
      );
      boyValues.add(
        getAverage(
          createParallelArrayListDouble(
            f,
            categories,
            boyCharacters,
            nameTraits[i],
            ind
          )
        )
      );
      genderNeutralValues.add(
        getAverage(
          createParallelArrayListDouble(
            f,
            categories,
            genderNeutralCharacters,
            nameTraits[i],
            ind
          )
        )
      );
    }

    //printing out the arrays so the format is easier to read
    for (int i = 0; i < nameTraits.length; i++) {
      System.out.println("index " + i + ": " + nameTraits[i]);
    }
    System.out.println();
    System.out.println("Girls: " + girlValues);
    System.out.println("Boys: " + boyValues);
    System.out.println("Gender Neutral: " + genderNeutralValues);
    System.out.println();
  }

  public static void compareCommonAverageCharacterRatings(//compares the most common characters ratings with the highest rated characters
    File givenName,
    int numItems,
    String[] nameTraits,
    String indName
  ) throws FileNotFoundException { 

    ArrayList<String> categories = getColumns(givenName);//getting the categories

    ArrayList<String> mostPopular = findMostPopular(
      givenName,
      categories,
      numItems
    );//getting the most popular characters, length is numItems

    ArrayList<String> highestRanked = new ArrayList<>();//empty ArrayList that will be added to
    int ind = 0;//index variable

    for (int i = 0; i < nameTraits.length; i++) {//printing for each of the strings in the nameTraits array
      ind = categories.indexOf(nameTraits[i]);
      System.out.println(nameTraits[i] + ": ");
      System.out.println("Most popular: " + mostPopular);//prints the most popular characters 

      //prints the trait value for each of the most popular characters
      System.out.println(
        createParallelArrayListDouble(
          givenName,
          categories,
          mostPopular,
          nameTraits[i],
          ind
        )
      );
      System.out.println();


      highestRanked =
        findHighestRanked(givenName, categories, numItems, nameTraits[i], ind);//getting the #numItems highest ranking characters
      System.out.println("Highest Ranked: " + highestRanked);
      //printing the values for the #numItems highest ranking characters for each trait
      System.out.println(
        createParallelArrayListDouble(
          givenName,
          categories,
          highestRanked,
          nameTraits[i],
          ind
        )
      );
      System.out.println();
      System.out.println();
      System.out.println();
    }
  }

  public static ArrayList<Integer> getTotalPeople(
    //gets the total amount of people that a specific character in their name for each given name character 
    File f,
    ArrayList<String> categories
  ) throws FileNotFoundException, NumberFormatException {
    ArrayList<Integer> totalPeople = new ArrayList<>();

    // gets the indexs of n.male and n.female in the categories ArrayList passed in. Pains me because it's not gender inclusive, but that's unfortunately how it is. 
    int numMale = categories.indexOf("n.male");
    int numFemale = categories.indexOf("n.female");
    Scanner fileScan = new Scanner(f);//new Scanner object to scan the file
    fileScan.nextLine();//skipping the first line because the first line are the categories, not the actual values

    while (fileScan.hasNextLine()) {//until there are no more lines in the file
      ArrayList<String> rows = new ArrayList<String>(
        Arrays.asList(fileScan.nextLine().split(","))//take the first row of the array (a string value) and passing in a delimiter (","), 
        //that splits it into seperate indexes in the ArrayList
      );

      totalPeople.add(//add the totalPeople array the total amount of people who have a specific character in their given name
        Integer.parseInt(rows.get(numMale)) + Integer.parseInt(rows.get(numFemale))
      );
    }
    fileScan.close();

    return totalPeople;
  }


  public static void fillGenderArrays(//sorting characters into passed in ArrayLists based on whether it is considered a girl, boy, or gender neutral character
    File f,
    ArrayList<String> girlCharacters,
    ArrayList<String> boyCharacters,
    ArrayList<String> genderNeutralCharacters,
    ArrayList<String> categories,
    double limit//min name.gender absolute value needed for it to the character to not be considered gender neutral

  ) throws FileNotFoundException, NumberFormatException {
    Scanner fileScan = new Scanner(f);//creating a scanner object for the file

    //originally these values were not hard coded, but I had to because of the indexOf issues talked about in the README
    int charIndex = 0; //hardcoding the index of the characters to 0
    int genderIndex = 5; //hardcoding the index of the characters to 0

    double curGenderVal = 0.0;//declaring and initializing a current gender value holder for the current character being examined
    fileScan.nextLine();

    while (fileScan.hasNextLine()) {//until there are no more lines in the file
      String[] row = fileScan.nextLine().split(",");//take the first row of the array (a string value) and passing in a delimiter (","), 
        //that splits it into seperate indexes in the ArrayList

      curGenderVal = Double.parseDouble(row[genderIndex]); //the current gender value is the value at the index of the genderIndex 
      //(or in other words, the column of the name.gender column in the file), but parsed as a double

      if (curGenderVal > Math.abs(limit)) {//if the gender value is greater than the absolute value of limit
        boyCharacters.add(row[charIndex]);//it's a predominately male character and is added toe the boyCharacters ArrayList 
      } 
      
      else if (curGenderVal < (-1 * Math.abs(limit))) {//if the gender value is less than the (absolute value of limit * -1)
        girlCharacters.add(row[charIndex]);//it's a predominately female character and is added toe the girlCharacters ArrayList 
      } 
      
      else {
        genderNeutralCharacters.add(row[charIndex]);//if the value is neither big nor small enough to fit into a gendered category
      }
    }
    fileScan.close();
  }

  public static ArrayList<Double> createParallelArrayListDouble(
    File f,
    ArrayList<String> categories,
    ArrayList<String> characters,
    String category,
    int index
  ) throws FileNotFoundException, NumberFormatException {
    ArrayList<Double> values2 = new ArrayList<>(); //an array list to add the categories to
    double[] values = new double[characters.size()];
    String curString;
    String curValue;
    //int charIndex = categories.indexOf(charIndexName);
    int charIndex = 0;

    Scanner fileScan = new Scanner(f);
    fileScan.nextLine();

    while (fileScan.hasNextLine()) {//until there are no more lines in the file
      ArrayList<String> rows = new ArrayList<String>(
        Arrays.asList(fileScan.nextLine().split(","))
      );

      for (int i = 0; i < characters.size(); i++) {
        curString = characters.get(i);

        if ((rows.get(charIndex).equals(curString))) { //This was a problem line
          curValue = rows.get(index);
          values[characters.indexOf(curString)] = Double.parseDouble(curValue);
        }
      }
    }

    fileScan.close();

    for (int j = 0; j < values.length; j++) {
      values2.add(values[j]);
    }

    return values2;
  }

  public static ArrayList<Integer> createParallelArrayListInt(
    File f,
    ArrayList<String> categories,
    ArrayList<String> characters,
    String category,
    int index
  ) throws FileNotFoundException, NumberFormatException {
    ArrayList<Integer> values = new ArrayList<>();

    Scanner fileScan = new Scanner(f);

    int count = 0;

    while (fileScan.hasNextLine()) {
      if (count == characters.size()) {
        break;
      }

      ArrayList<String> rows = new ArrayList<String>(
        Arrays.asList(fileScan.nextLine().split(","))
      );

      if ((rows.indexOf(characters.get(count))) != -1) {
        values.add(Integer.parseInt((rows.get(index))));

        count++;
      }
    }

    fileScan.close();

    return values;
  }

  public static ArrayList<String> createParallelArrayListString( //returns a arraylist of all the characters in the dataset
    File f,
    ArrayList<String> categories,
    int index
  ) throws FileNotFoundException {
    int charIndex = 0;

    ArrayList<String> values = new ArrayList<>();

    Scanner fileScan = new Scanner(f);
    fileScan.nextLine();

    while (fileScan.hasNextLine()) {
      ArrayList<String> rows = new ArrayList<String>(
        Arrays.asList(fileScan.nextLine().split(","))
      );

      values.add(rows.get(index));
    }

    fileScan.close();

    return values;
  }

  public static ArrayList<String> findMostPopular(
    File f,
    ArrayList<String> categories,
    int length
  ) throws FileNotFoundException {
    ArrayList<String> allCharacters = createParallelArrayListString(
      f,
      categories,
      0
    );

    ArrayList<Integer> totalNum = getTotalPeople(f, categories);

    ArrayList<String> mostPopularChars = new ArrayList<>();

    for (int i = 0; i < length; i++) {
      mostPopularChars.add(allCharacters.get(findMax(totalNum)));
      allCharacters.remove(findMax(totalNum));
      totalNum.remove(findMax(totalNum));
    }

    return mostPopularChars;
  }

  public static ArrayList<String> findHighestRanked(
    File f,
    ArrayList<String> categories,
    int length,
    String category,
    int charIndex
  ) throws FileNotFoundException {
    Double current;
    ArrayList<String> allCharacters = createParallelArrayListString(
      f,
      categories,
      0
    );

    int max = 0;
    ArrayList<String> highestRanked = new ArrayList<>();

    ArrayList<Double> charValues = createParallelArrayListDouble(
      f,
      categories,
      allCharacters,
      category,
      charIndex
    );

    for (int i = length - 1; i >= 0; i--) {
      max = findMaxDouble(charValues);
      highestRanked.add(allCharacters.get(max));
      allCharacters.remove(max);
      charValues.remove(max);
    }

    return highestRanked;
  }

  public static int findMax(ArrayList<Integer> arr) {
    int index = 0; //
    int largest = arr.get(0);
    for (int i = 0; i < arr.size(); i++) {
      if (arr.get(i) >= largest) { //checking if element is smaller
        largest = arr.get(i);
        //updates each time a smaller number is found
        index = i; //records index of smallest number
      }
    }

    return index;
  }

  public static int findMaxDouble(ArrayList<Double> arr) {
    int index = 0; //
    double largest = arr.get(0);
    for (int i = 0; i < arr.size(); i++) {
      if (arr.get(i) > largest) { //checking if element is smaller
        largest = arr.get(i);
        //updates each time a smaller number is found
        index = i; //records index of smallest number
      }
    }

    return index;
  }


  public static double getAverage(ArrayList<Double> values) {
    double total = values.get(0); //in case we have negative numbers

    for (int i = 1; i < values.size(); i++) {
      total += values.get(i);
    }
    return total / values.size();
  }

  public static ArrayList<String> getColumns(File f)
    throws FileNotFoundException {
    Scanner fileScan = new Scanner(f); //anonymous object

    String[] firstLineArr = fileScan.nextLine().split(","); //assumes first line is header, grabs it and splits it by comma

    ArrayList<String> headers = new ArrayList<>(Arrays.asList(firstLineArr));

    fileScan.close();

    return headers;
  }
}
