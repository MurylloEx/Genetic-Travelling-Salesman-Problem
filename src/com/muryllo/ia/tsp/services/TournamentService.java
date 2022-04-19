/* Decompiler 6ms, total 131ms, lines 33 */
package com.muryllo.ia.tsp.services;

import com.muryllo.ia.tsp.interfaces.IAdaptable;
import com.muryllo.ia.tsp.interfaces.IChromosome;
import com.muryllo.ia.tsp.interfaces.IPopulation;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class TournamentService {

  protected AlgorithmService AlgorithmService;

  public TournamentService(AlgorithmService algorithmService) {
    this.AlgorithmService = algorithmService;
  }

  public <N, I extends IChromosome<N> & IAdaptable, P extends IPopulation<N, I>> P selectPopulation(P population, Class<P> typeClass) throws Exception 
  {
    P tournamentPopulation;
    try {
      tournamentPopulation = typeClass.getDeclaredConstructor(ArrayList.class, Integer.TYPE)
        .newInstance(this.AlgorithmService.InitialRoute, this.AlgorithmService.TournamentSelectionSize);
    } catch (Exception var5) {
      throw new Exception("Couldn't select the specified Population.");
    }

    IntStream.range(0, this.AlgorithmService.TournamentSelectionSize).forEach((x) -> {
      int randomRouteOffset = (int) (Math.random() * (double) population.getChromosomes().size());
      I randomRoute = population.getChromosomes().get(randomRouteOffset);
      tournamentPopulation.getChromosomes().set(x, randomRoute);
    });
    
    tournamentPopulation.sortRoutesByFitness();

    return tournamentPopulation;
  }

}
