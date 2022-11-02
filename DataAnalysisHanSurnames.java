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




  compareCommonAverageCharacterRatings (baby, 4, nameTraits);

  //  ArrayList<String> boyCharacters = new ArrayList<>();
  //   ArrayList<String> girlCharacters = new ArrayList<>();
  //   ArrayList<String> genderNeutralCharacters = new ArrayList<>();

  //   ArrayList<String> categories = getColumns(baby);

  //   //not 4, 10, 11, 12

  //   fillGenderArrays(
  //     baby,
  //     girlCharacters,
  //     boyCharacters,
  //     genderNeutralCharacters,
  //     categories,
  //     0.250
  //   );

  //   System.out.println(createParallelArrayListDouble(baby, categories, girlCharacters, "name.valence"));

    

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
      girlValues.add(
        getAverage(
          createParallelArrayListDouble(
            f,
            categories,
            girlCharacters,
            nameTraits[i]
          )
        )
      );
      boyValues.add(
        getAverage(
          createParallelArrayListDouble(f, categories, boyCharacters, nameTraits[i])
        )
      );
      genderNeutralValues.add(
        getAverage(
          createParallelArrayListDouble(
            f,
            categories,
            genderNeutralCharacters,
            nameTraits[i]
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




  }



  public static void compareCommonAverageCharacterRatings (File givenName, int numItems, String[] nameTraits) throws FileNotFoundException{//compares the most common characters ratings with the highest rated characters
    ArrayList<String> categories = getColumns(givenName);
    ArrayList<String> mostPopular = findMostPopular(givenName, categories, numItems);

    



    for (int i = 0; i < nameTraits.length; i++){
    System.out.println(nameTraits[i] + ": ");
    System.out.println("Most popular: " + mostPopular);
    System.out.println(createParallelArrayListDouble (givenName, categories, mostPopular, nameTraits[i]));
    System.out.println();

    ArrayList<String> highestRanked = findHighestRanked(givenName, categories, numItems, nameTraits[i]);

    System.out.println("Highest Ranked: " + highestRanked);

    System.out.println(createParallelArrayListDouble (givenName, categories, highestRanked, nameTraits[i]));
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
  ) throws FileNotFoundException {
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
    File f,
    ArrayList<String> categories,
    ArrayList<String> characters,
    String category
  ) throws FileNotFoundException, NumberFormatException {
    int targetIndex = categories.indexOf(category);
    ArrayList<Double> values2 = new ArrayList<>();
    double[] values = new double[characters.size()];

    //ArrayList<String> rows = new ArrayList<>();

    Scanner fileScan = new Scanner(f);

    int count = 0;
    fileScan.nextLine();

    while (fileScan.hasNextLine()) {

      if (count == characters.size()) {
        break;
      }

      ArrayList<String> rows = new ArrayList<String>(Arrays.asList(fileScan.nextLine().split(",")));

      for(int i = 0; i < characters.size(); i++){

      if ((rows.indexOf(characters.get(i))) != -1) {
        String char1 = characters.get(i);
        // try {
        values[characters.indexOf(char1)] = Double.parseDouble((rows.get(targetIndex)));
        // }

        // catch (Exception e) {

        // System.out.println("Exception: " + e);

        // }

        count++;
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
    String category
  ) throws FileNotFoundException, NumberFormatException {
    int targetIndex = categories.indexOf(category);

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
    
          values.add(Integer.parseInt((rows.get(targetIndex))));
        

        count++;
      }
    }

    fileScan.close();

    return values;
  }

  public static ArrayList<String> createParallelArrayListString(
    File f,
    ArrayList<String> categories,
    String category
  ) throws FileNotFoundException {
    int targetIndex = categories.indexOf(category);

    ArrayList<String> values = new ArrayList<>();

    //ArrayList<String> rows = new ArrayList<>();

    Scanner fileScan = new Scanner(f);
    fileScan.nextLine();

    while (fileScan.hasNextLine()) {
      ArrayList<String> rows = new ArrayList<String>(
        Arrays.asList(fileScan.nextLine().split(","))
      );

      values.add(rows.get(targetIndex));
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
      "character"
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
    String category
  ) throws FileNotFoundException {
    ArrayList<String> allCharacters = createParallelArrayListString(
      f,
      categories,
      "character"
    );

    ArrayList<String> highestRanked = new ArrayList<>();

    ArrayList<Double> charValues = createParallelArrayListDouble(f, categories, allCharacters, category);

    for (int i = 0; i < length; i++) {
      highestRanked.add(allCharacters.get(findMaxDouble(charValues)));
      allCharacters.remove(findMaxDouble(charValues));
      charValues.remove(findMaxDouble(charValues));
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
      if (arr.get(i) >= largest) { //checking if element is smaller
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
