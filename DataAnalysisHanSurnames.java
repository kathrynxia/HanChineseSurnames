import java.io.*;
import java.lang.*;
import java.util.*;

public class DataAnalysisHanSurnames {

  public static void main(String[] args)
    throws FileNotFoundException, NumberFormatException {


    File test = new File(
      "/Users/kxia/CSSeminar/Unit1/HanChineseSurnames/BabyDataSet.csv"
    );


      printAverageNameValues(test);
   

  }

  public static void printAverageNameValues(File f) throws FileNotFoundException {
    ArrayList<String> boyCharacters = new ArrayList<>();
    ArrayList<String> girlCharacters = new ArrayList<>();
    ArrayList<String> genderNeutralCharacters = new ArrayList<>();

    ArrayList<String> categories = getColumns(f);

     fillGenderArrays(
      f,
      girlCharacters,
      boyCharacters,
      genderNeutralCharacters,
      categories,
      0.250
    ); 

    double avgGirlValence = getAverage(createParallelArrayDouble(f, categories, girlCharacters, "name.valence"));

    System.out.println(avgGirlValence);

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
      // String line = fileScan.nextLine();
      // Scanner lineScan = new Scanner(line);

      String[] row = fileScan.nextLine().split(",");

      //  try {
      curGenderVal = Double.parseDouble(row[genderIndex]);

      if (curGenderVal > Math.abs(limit)) {
        boyCharacters.add(row[charIndex]);
      } else if (curGenderVal < (-1 * Math.abs(limit))) {
        girlCharacters.add(row[charIndex]);
      } else {
        genderNeutralCharacters.add(row[charIndex]);
      }
      //  }

      //  catch (Exception e) {

      //   System.out.println("Exception: " + e);

      //  }

    }
    fileScan.close();
  }

  //public static

  public static ArrayList<Double> createParallelArrayDouble(
    File f,
    ArrayList<String> categories,
    ArrayList<String> characters,
    String category
  ) throws FileNotFoundException, NumberFormatException {
    int targetIndex = categories.indexOf(category);

    ArrayList<Double> values = new ArrayList<>();

    //ArrayList<String> rows = new ArrayList<>();

    Scanner fileScan = new Scanner(f);

    int count = 0;
    fileScan.nextLine();

    while (fileScan.hasNextLine()) {
      if (count == characters.size()) {
        break;
      }

      ArrayList<String> rows = new ArrayList<String>(
        Arrays.asList(fileScan.nextLine().split(","))
      );

      if ((rows.indexOf(characters.get(count))) != -1) {
        // try {
        values.add(Double.parseDouble((rows.get(targetIndex))));
        // }

        // catch (Exception e) {

        // System.out.println("Exception: " + e);

        // }

        count++;
      }
    }

    fileScan.close();

    return values;
  }

  public static ArrayList<Integer> createParallelArrayInt(
    File f,
    ArrayList<String> categories,
    ArrayList<String> characters,
    String category
  ) throws FileNotFoundException {
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
        try {
          values.add(Integer.parseInt((rows.get(targetIndex))));
        } catch (Exception e) {
          System.out.println("Exception: " + e);
        }

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
