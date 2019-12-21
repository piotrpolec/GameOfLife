package Lib;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Genes {
  private Integer[] genes = new Integer[32];
  private final Random generator = new Random();

  public Genes(){
    for(int i = 0; i < 32; i++){
      this.genes[i] = generator.nextInt(7);
    }
    if(this.correct());
    else this.recalculate();
  }

  public Genes(Integer [] genes){
    this.genes = genes;
  }

  public Genes(int x, int y, Genes fatherGenes, Genes motherGenes){
    int breakpoint = Math.max(x, y);
    if (breakpoint >= 0) System.arraycopy(fatherGenes.genes, 0, this.genes, 0, breakpoint);
    if (32 - breakpoint >= 0) System.arraycopy(motherGenes.genes, breakpoint, this.genes, breakpoint, 32 - breakpoint);

    if(this.correct());
    else this.recalculate();
  }

  public Integer rotation(){
    return this.genes[generator.nextInt(32)]*45;
  }

  private boolean correct(){
    boolean correct = true;
    Integer[] t = new Integer[8];
    for(int i = 0; i < 8; i++) t[i] = 0;
    for(int i = 0; i < 32; i++)  t[genes[i]]++;

    for(int i = 0; i < 8; i++){
      if (t[i] == 0) {
        correct = false;
        break;
      }
    }
    if(correct) {
      Arrays.sort(genes);
      return true;
    }
    else return false;
  }

  private void recalculate(){
    Map<Integer, Integer> geneOccurrence = new HashMap<>();
    for(int i = 0; i < 8; i++)  geneOccurrence.put(i, 0);
    for(int i = 0; i < 32; i++) geneOccurrence.put(genes[i], geneOccurrence.get(genes[i])+1);

    if(geneOccurrence.containsValue(0)){
      for(int missingGene = 0; missingGene < 8; missingGene++){
        if(geneOccurrence.get(missingGene) == 0){
          Integer index = generator.nextInt(7);
          while(index == missingGene || geneOccurrence.get(index) <= 1){
            index = generator.nextInt(7);
          }
          geneOccurrence.put(genes[missingGene], geneOccurrence.get(genes[missingGene])+1);
          geneOccurrence.put(genes[index], geneOccurrence.get(genes[index])-1);
          for(int j = 0; j < 32; j++){
            if(genes[j] == index) genes[j] = missingGene;
            break;
          }
        }
      }
    }
    Arrays.sort(genes);
  }
}
