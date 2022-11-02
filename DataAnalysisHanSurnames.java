import java.io.*;
import java.lang.*;
import java.util.*;

public class DataAnalysisHanSurnames {

  public static void main(String[] args)
    throws FileNotFoundException, NumberFormatException {
    File givenName = new File(
      "/Users/kxia/CSSeminar/Unit1/HanChineseSurnames/ChineseNames-main/data-csv/givenname.csv"
    );

    String[] nameTraits = { "name.valence", "name.competence", "name.warmth" };

    //name valence: positivity of character meaning), name warmth/morality: warmness, name competence: assertiveness

    //printAverageNameValues(givenName, nameTraits);
    File baby = new File(
      "/Users/kxia/CSSeminar/Unit1/HanChineseSurnames/BabyDataSet.csv"
    );


  //printAverageNameValues(givenName, nameTraits);

  //compareCommonAverageCharacterRatings (givenName, 4, nameTraits, "c");

   ArrayList<String> boyCharacters = new ArrayList<>();
    ArrayList<String> girlCharacters = new ArrayList<>();
    ArrayList<String> genderNeutralCharacters = new ArrayList<>();

    ArrayList<String> categories = getColumns(givenName);
    //System.out.println(categories.size());
    //System.out.println(categories);

    //not 4, 10, 11, 12

    fillGenderArrays(
      givenName,
      girlCharacters,
      boyCharacters,
      genderNeutralCharacters,
      categories,
      0.250
    );


  //System.out.println(boyCharacters);
  //System.out.println(createParallelArrayListDouble(baby, categories, boyCharacters, "name.valence", "character"));
   //System.out.println(categories.indexOf("character"));

  // printAverageNameValues(baby, nameTraits);
  // compareCommonAverageCharacterRatings(baby, 3, nameTraits, "character");


 printAverageNameValues(givenName, nameTraits);
  compareCommonAverageCharacterRatings(givenName, 3, nameTraits, "character");
  //System.out.println(createParallelArrayListDouble(givenName, categories, boyCharacters, "name.valence", "character"));//index ends up at -1
  //System.out.println(createParallelArrayListDouble(givenName, categories, boyCharacters, "name.valence", "character"));
  //System.out.println(createParallelArrayListDouble(givenName, categories, boyCharacters, "name.valence",  "c"));

  //System.out.println(findMostPopular(givenName, categories, 3));

    
  // public static ArrayList<String> findMostPopular(
  //   File f,
  //   ArrayList<String> categories,
  //   int length

     File top1000name = new File(
      "/Users/kxia/CSSeminar/Unit1/HanChineseSurnames/ChineseNames-main/data-csv/top1000name.prov.csv"
    );

    File population = new File(
      "/Users/kxia/CSSeminar/Unit1/HanChineseSurnames/ChineseNames-main/data-csv/population.csv"
    );

  }




  public static void printAverageNameValues(File f, String[] nameTraits)
    throws FileNotFoundException {
    //ArrayList<ArrayList<Double>> allArrayLists = new ArrayList<>();
    ArrayList<String> boyCharacters = new ArrayList<>();
    ArrayList<String> girlCharacters = new ArrayList<>();
    ArrayList<String> genderNeutralCharacters = new ArrayList<>();
    ArrayList<String> categories = getColumns(f);
    int ind = 0;

    //not 4, 10, 11, 12

    fillGenderArrays(
      f,
      girlCharacters,
      boyCharacters,
      genderNeutralCharacters,
      categories,
      0.250
    );

    ArrayList<Double> girlValues = new ArrayList<>();
    ArrayList<Double> boyValues = new ArrayList<>();
    ArrayList<Double> genderNeutralValues = new ArrayList<>();

    for (int i = 0; i < nameTraits.length; i++) {
    ind = categories.indexOf(nameTraits[i]);
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
          createParallelArrayListDouble(f, categories, boyCharacters, nameTraits[i], ind)
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
    for (int i = 0; i < nameTraits.length; i++) {
      System.out.println("index " + i + ": " + nameTraits[i]);
    }
    System.out.println();
    System.out.println("Girls: " + girlValues);
    System.out.println("Boys: " + boyValues);
    System.out.println("Gender Neutral: " + genderNeutralValues);
    System.out.println();




  }



 

  public static void compareCommonAverageCharacterRatings (File givenName, int numItems, String[] nameTraits, String indName) throws FileNotFoundException{//compares the most common characters ratings with the highest rated characters
    ArrayList<String> categories = getColumns(givenName);
    ArrayList<String> mostPopular = findMostPopular(givenName, categories, numItems);
    ArrayList<String> highestRanked = new ArrayList<>();
    int ind = 0;


    for (int i = 0; i < nameTraits.length; i++){
    ind = categories.indexOf(nameTraits[i]);
    System.out.println(nameTraits[i] + ": ");
    System.out.println("Most popular: " + mostPopular);
    System.out.println(createParallelArrayListDouble (givenName, categories, mostPopular, nameTraits[i], ind));
    System.out.println();

    highestRanked = findHighestRanked(givenName, categories, numItems, nameTraits[i], ind);
    System.out.println("Highest Ranked: " + highestRanked);
    System.out.println(createParallelArrayListDouble (givenName, categories, highestRanked, nameTraits[i], ind));
    System.out.println();
    System.out.println();
    System.out.println();

    }
  }






  public static ArrayList<Integer> getTotalPeople(
    File f,
    ArrayList<String> categories
  ) throws FileNotFoundException, NumberFormatException {
    ArrayList<Integer> totalPeople = new ArrayList<>();
    int numMale = categories.indexOf("n.male");
    int numFemale = categories.indexOf("n.female");
    Scanner fileScan = new Scanner(f);
    fileScan.nextLine();

    while (fileScan.hasNextLine()) {
      ArrayList<String> rows = new ArrayList<String>(
        Arrays.asList(fileScan.nextLine().split(","))
      );

      totalPeople.add(
        Integer.parseInt(rows.get(numMale)) +
        Integer.parseInt(rows.get(numFemale))
      );
    }

    return totalPeople;
  }



  public static void fillGenderArrays(
    File f,
    ArrayList<String> girlCharacters,
    ArrayList<String> boyCharacters,
    ArrayList<String> genderNeutralCharacters,
    ArrayList<String> categories,
    double limit
  ) throws FileNotFoundException, NumberFormatException{
    Scanner fileScan = new Scanner(f);
    int charIndex = 0; //change to indexOf
    int genderIndex = 5; //change to indexOf
    double curGenderVal = 0.0;
    fileScan.nextLine();

    while (fileScan.hasNextLine()) {

      String[] row = fileScan.nextLine().split(",");

      curGenderVal = Double.parseDouble(row[genderIndex]);

      if (curGenderVal > Math.abs(limit)) {
        boyCharacters.add(row[charIndex]);
      } else if (curGenderVal < (-1 * Math.abs(limit))) {
        girlCharacters.add(row[charIndex]);
      } else {
        genderNeutralCharacters.add(row[charIndex]);
      }
  

    }
    fileScan.close();
  }

 public static ArrayList<Double> createParallelArrayListDouble(
    File f, ArrayList<String> categories, ArrayList<String> characters, String category, int index
  ) throws FileNotFoundException, NumberFormatException{

    //System.out.println(index);

    //int targetIndex = categories.indexOf(category);//index of the category we want to isolate
    //int targetIndex = 23;
    ArrayList<Double> values2 = new ArrayList<>();//an array list to add the categories to
    double[] values = new double[characters.size()];
    String curString;
    String curValue;
    //int charIndex = categories.indexOf(charIndexName);
    int charIndex = 0;
    //System.out.println(charIndex);

    //ArrayList<String> rows = new ArrayList<>();

    Scanner fileScan = new Scanner(f);
    fileScan.nextLine();

    while (fileScan.hasNextLine()) {

      ArrayList<String> rows = new ArrayList<String>(Arrays.asList(fileScan.nextLine().split(",")));

      for(int i = 0; i < characters.size(); i++){
      curString = characters.get(i);

      // System.out.println("*");
      // System.out.println(curString);

      //System.out.println(charIndex);

      //System.out.println("*");
      //System.out.println(rows.get(charIndex));
      if ((rows.get(charIndex).equals(curString))) {//i think this is the problem
      //System.out.println("%");
      //System.out.println(rows.get(0));
      // System.out.println((rows.get(0).equals(curString)));
      
        curValue = rows.get(index);
        // try {
        values[characters.indexOf(curString)] = Double.parseDouble(curValue);

      }


      }

    }

    fileScan.close();

    for (int j = 0; j < values.length; j++){
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
    //int targetIndex = categories.indexOf(category);

    ArrayList<Integer> values = new ArrayList<>();

    //ArrayList<String> rows = new ArrayList<>();

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

  public static ArrayList<String> createParallelArrayListString(//returns a arraylist of all the characters in the dataset
    File f,
    ArrayList<String> categories,
    int index
  ) throws FileNotFoundException {
    //int targetIndex = categories.indexOf(category);
    int charIndex = 0;

    ArrayList<String> values = new ArrayList<>();

    //ArrayList<String> rows = new ArrayList<>();

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

    //System.out.println(allCharacters);

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

    //System.out.println(allCharacters);
    int max = 0;
    ArrayList<String> highestRanked = new ArrayList<>();

    //System.out.println("*");

    ArrayList<Double> charValues = createParallelArrayListDouble(f, categories, allCharacters, category, charIndex);
    //System.out.println(charValues);
    //System.out.println("*");

    for (int i = length-1; i >= 0; i--) {
      max = findMaxDouble(charValues);
      //current = charValues.get(max);
     // System.out.println(max);
      //System.out.println("$");

      highestRanked.add(allCharacters.get(max));
      //System.out.println(highestRanked);
      //System.out.println(highestRanked);
      //System.out.println(max);
      //System.out.println("%");

      allCharacters.remove(max);
      charValues.remove(max);
      //System.out.println(allCharacters);
      //System.out.println("*");
      //charValues.remove(max);
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



  public static int findSmallest(ArrayList<Integer> arr) { //returns index of the smallest number
    int index = 0; //
    int smallest = arr.get(0);
    for (int i = 0; i < arr.size(); i++) {
      if (arr.get(i) <= smallest) { //checking if element is smaller
        smallest = arr.get(i);
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
