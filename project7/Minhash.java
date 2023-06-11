package project7;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class Minhash {

  public double jaccard(String fA, String fB) {

    int maxShId = (int)Math.pow(2,32)-1;
    long nextPrimeNum = 4294967311L;
    int hashesCount = 99;
    

    ArrayList<String> wordsInFile1  = fileRead(fA);
    ArrayList<String> wordsInFile2 =  fileRead(fB);

    HashSet<Integer> file1_shingles = new HashSet<>();
    HashSet<Integer> file2_shingles = new HashSet<>();

    List<Integer> RandomCoeffA = pickRandomCoeff(hashesCount, maxShId);
    List<Integer> RandomCoeffB = pickRandomCoeff(hashesCount, maxShId);
        
    List<Long> aArray = new ArrayList<>();
    List<Long> bArray = new ArrayList<>();
    
    for (int i = 0; i <  wordsInFile1.size(); i++)
    {
      String shingle =  wordsInFile1.get(i);
      file1_shingles.add(shingle.hashCode());
    }

    for (int i = 0; i < wordsInFile2.size(); i++)
    {
      String shingle = wordsInFile2.get(i);
      file2_shingles.add(shingle.hashCode());
    }

    List<Integer> file1_shingles_arr = new ArrayList<Integer>(file1_shingles);
    List<Integer> file2_shingles_arr = new ArrayList<Integer>(file2_shingles);

    for (int i = 0; i < hashesCount; i++) {
      long minHash = nextPrimeNum + 1;
      for (int j = 0; j < file1_shingles_arr.size(); j++) {
          long hash = (long)((long) (RandomCoeffA.get(i) * file1_shingles_arr.get(j) + RandomCoeffB.get(i)) % nextPrimeNum);
          if (hash < minHash) {
              minHash = hash;
          }
      }
      aArray.add(minHash);
    }

  for (int i = 0; i < hashesCount; i++) {
    long minHash = nextPrimeNum + 1;
    for (int j = 0; j < file2_shingles_arr.size(); j++) {
        long hash = (long)((long) (RandomCoeffA.get(i) * file2_shingles_arr.get(j) + RandomCoeffB.get(i)) % nextPrimeNum);
        if (hash < minHash) {
            minHash = hash;
        }
    }
    bArray.add(minHash);
  }


  int ptr = 0;
  for (int i = 0; i < hashesCount; i++)
  {
    if (aArray.get(i).equals(bArray.get(i)))
    {
      ptr++;
    }
  }

  float similarity = (float)(ptr) / (float)(hashesCount);
  return similarity;
  }

  public static List<Integer> pickRandomCoeff(int k, int maxShId) {
    Set<Integer> randList = new HashSet<>();
    Random rand = new Random();
    
    while (randList.size() < k) {
        int randIndex = rand.nextInt(maxShId);
        randList.add(randIndex);
    }
    
    return new ArrayList<>(randList);
  }

  public static ArrayList<String> fileRead(String fileName){
    ArrayList<String> file_words = new ArrayList<>();
    try {
      File file = new File(fileName);
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        file_words.add(sc.nextLine());        
      }
      sc.close();
    } catch (FileNotFoundException e) {
      System.out.println("File Not Found");
      e.printStackTrace();
    }
    return file_words;
  }

}
