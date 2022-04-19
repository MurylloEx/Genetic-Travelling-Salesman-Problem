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

  public <N, I extends IChromosome<N> & IAdaptable, P extends IPopulation<N, I>> P mutatePopulation(P population) {
    population.getChromosomes().stream().filter((x) -> {
      return population.getChromosomes().indexOf(x) >= AlgorithmService.NumberOfEliteRoutes;
    }).forEach((x) -> {
      mutateChromosome(x);
    });
    return population;
  }

  public <N, I extends IChromosome<N> & IAdaptable> I mutateChromosome(I route) {
    route.getNucleotides().stream().filter((x) -> {
      return Math.random() < AlgorithmService.MutationRate;
    }).forEach((cityX) -> {
      int y = (int) ((double) route.getNucleotides().size() * Math.random());
      N cityY = route.getNucleotides().get(y);
      route.getNucleotides().set(route.getNucleotides().indexOf(cityX), cityY);
      route.getNucleotides().set(y, cityX);
    });
    return route;
  }

}
