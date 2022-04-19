/* Decompiler 4ms, total 132ms, lines 35 */
package com.muryllo.ia.tsp.services;

import com.muryllo.ia.tsp.interfaces.IAdaptable;
import com.muryllo.ia.tsp.interfaces.IChromosome;
import com.muryllo.ia.tsp.interfaces.IPopulation;

public class MutationService {

  protected AlgorithmService AlgorithmService;

  public MutationService(AlgorithmService algorithmService) {
    this.AlgorithmService = algorithmService;
  }

  public <N, C extends IChromosome<N> & IAdaptable, P extends IPopulation<N, C>> P mutatePopulation(P population) {
    population.getChromosomes().stream().filter((x) -> {
      return population.getChromosomes().indexOf(x) >= this.AlgorithmService.NumberOfEliteRoutes;
    }).forEach((x) -> {
      mutateChromosome(x);
    });
    return population;
  }

  public <N, C extends IChromosome<N> & IAdaptable> C mutateChromosome(C route) {
    route.getNucleotides().stream().filter((x) -> {
      return (Math.random() >= 0.5D) || (Math.random() <= this.AlgorithmService.MutationRate);
    }).forEach((nucleotideX) -> {
      int y = (int) ((double) route.getNucleotides().size() * Math.random());
      N nucleotideY = route.getNucleotides().get(y);
      route.getNucleotides().set(route.getNucleotides().indexOf(nucleotideX), nucleotideY);
      route.getNucleotides().set(y, nucleotideX);
    });
    return route;
  }

}
