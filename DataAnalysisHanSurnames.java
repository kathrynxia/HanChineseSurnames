import java.io.*;
import java.util.*;
import java.lang.*;

public class DataAnalysisHanSurnames {

  public static void main(String[] args) throws FileNotFoundException {

    ArrayList<String> boyCharacters = new ArrayList<>();
    ArrayList<String> girlCharacters = new ArrayList<>();
    ArrayList<String> genderNeutralCharacters = new ArrayList<>();


  //testing with a baby array
    File test = new File("/Users/kxia/CSSeminar/Unit1/HanChineseSurnames/BabyDataSet.csv"); //change to relative path but I don't know how
    ArrayList<String> categories = getColumns(test);
    fillGenderArrays(test, girlCharacters, boyCharacters, genderNeutralCharacters, categories, 0.250);
    System.out.println(boyCharacters);
    System.out.println();
    // System.out.println(girlCharacters);
    // System.out.println();
    // System.out.println(genderNeutralCharacters);
    ArrayList<Double> nCBoys = createParallelArray(test, categories, boyCharacters, "name.competence");
    System.out.println(nCBoys);
    System.out.println(getAverage(nCBoys));

  }



  public static void fillGenderArrays(File f, ArrayList<String> girlCharacters, ArrayList<String> boyCharacters, ArrayList<String> genderNeutralCharacters, ArrayList<String> categories, double limit) throws FileNotFoundException{
    Scanner fileScan = new Scanner(f);
    int charIndex = 0;//change to indexOf
    int genderIndex = 5;//change to indexOf
    double curGenderVal = 0.0; 


    while (fileScan.hasNextLine()) {
      // String line = fileScan.nextLine();
      // Scanner lineScan = new Scanner(line);

      String[] row = fileScan.nextLine().split(",");

       try {
      curGenderVal = Double.parseDouble(row[genderIndex]);

      if (curGenderVal > Math.abs(limit)){
        boyCharacters.add(row[charIndex]);
      }

      else if (curGenderVal < (-1 * Math.abs(limit))){

        girlCharacters.add(row[charIndex]);

      }

      else{

         genderNeutralCharacters.add(row[charIndex]);

      }
       }

       catch (Exception e) {

        System.out.println("Exception: " + e);

       }

    }
    fileScan.close();


  }


public static ArrayList<Double> createParallelArray(File f, ArrayList<String> categories, ArrayList<String> characters, String category) throws FileNotFoundException{

int targetIndex = categories.indexOf(category);

ArrayList<Double> values = new ArrayList<>();

//ArrayList<String> rows = new ArrayList<>();

Scanner fileScan = new Scanner(f);

int count = 0; 

 while (fileScan.hasNextLine()) {

  if (count == characters.size()){
    break;
  }

  ArrayList<String> rows = new ArrayList<String>(Arrays.asList(fileScan.nextLine().split(",")));

  if ((rows.indexOf(characters.get(count))) != -1){

    try {
    values.add(Double.parseDouble((rows.get(targetIndex))));
    }

    catch (Exception e) {

    System.out.println("Exception: " + e);

    }

    count++;
  }



}

fileScan.close();

return values;

}

public static ArrayList<String> findTop (File f, int numItems){//if two names have the same number of peoplw with it, it will be added to the list and the user can exclude it once the ArrayList is returned

ArrayList<String> mostPopular = new ArrayList<>();
int count = 0; 

Scanner fileScan = new Scanner(f);

  while (f.hasNextLine()){

    if (count < numItems){
      mostPopular.add()

    }

  }


}


public static int findSmallest(ArrayList<Int> arr) { //returns index of the smallest number
    int index = 0; //
    int smallest = arr.get(0);
    for (int i = 0; i < arr.size(); i++) {
      if (arr.get(i) <= smallest) { //checking if element is smaller
        smallest = arr.get(i); //updates each time a smaller number is found
        index = i; //records index of smallest number
      }
    }

    return index;
  }


public static double getAverage(ArrayList<Double> values){
double total = values.get(0);//in case we have negative numbers

for (int i = 1; i < values.size(); i++){

  total += values.get(i);


}
return total/values.size();

}




//   public static double getAverage

// }




  public static ArrayList<String> getColumns(File f) throws FileNotFoundException {
    Scanner fileScan = new Scanner(f); //anonymous object

    String[] firstLineArr = fileScan.nextLine().split(","); //assumes first line is header, grabs it and splits it by comma

    ArrayList<String> headers = new ArrayList<>(Arrays.asList(firstLineArr));

    fileScan.close();

    return headers;
  }
}
