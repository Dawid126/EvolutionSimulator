package main.java.jungle_elements;

import main.java.utils.RandomInt;

import java.util.Arrays;

public class Genes {

    int[] genotype = new int[32];
    public Genes()
    {
        RandomInt randomInt = new RandomInt();
        for(int i = 0; i < 7; i++)
        {
            genotype[i] = i;
        }
        for(int i = 7; i < 32; i++)
        {
            genotype[i] = randomInt.generate(7);
        }
        Arrays.sort(genotype);
    }
    public Genes(Animal parent1, Animal parent2)
    {
        RandomInt randomInt = new RandomInt();
        int indexOfDivision1 = randomInt.generate(29);
        int indexOfDivision2 = randomInt.generate(29);
        while(indexOfDivision2 == indexOfDivision1) indexOfDivision2 = randomInt.generate(29) +1;

        for(int i = 0; i <= indexOfDivision1; i++) genotype[i] = parent2.getGenes().genotype[i];
        for(int i = indexOfDivision1 + 1; i < indexOfDivision2; i++) genotype[i] = parent1.getGenes().genotype[i];
        for(int i = indexOfDivision2; i < 32; i++) genotype[i] = parent2.getGenes().genotype[i];

        boolean[] genesStatus = checkGenotypeCompletness(genotype);
        while(genesStatus!= null)
        {
            for(int i = 0; i < 8; i++)
            {
                if(genesStatus[i] == false)
                {
                    genotype[randomInt.generate(31)] = i;
                    genesStatus = checkGenotypeCompletness(genotype);
                    break;
                }
            }
        }


        Arrays.sort(genotype);


    }
    private static boolean[] checkGenotypeCompletness(int[] genotype)
    {
        boolean[] genes = new boolean[8];
        for(int i = 0; i < 8; i++) genes[i] = false;
        for(int i = 0; i < 32; i++)
        {
            genes[genotype[i]] = true;
        }
        for(int i = 0; i < 8; i++)
        {
            if(genes[i] == false) return genes;
        }
        return null;
    }
}
