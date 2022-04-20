package com.muryllo.ia.tsp.interfaces;

import java.util.ArrayList;

public interface IPopulation<N, C extends IChromosome<N> & IAdaptable> {
  public ArrayList<C> getChromosomes();
  public void sortRoutesByFitness();
}
