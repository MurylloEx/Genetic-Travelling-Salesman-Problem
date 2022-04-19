/* Decompiler 4ms, total 356ms, lines 39 */
package com.muryllo.ia.tsp.models;

public class City {

  private String name;
  private double latitude;
  private double longitude;
  private static final double EARTH_EQUATORIAL_RADIUS = 6378.137D;
  private static final double CONVERT_DEGREES_TO_RADIANS = 0.017453292519943295D;

  public City(String name, double latitude, double longitude) {
    this.name = name;
    this.longitude = longitude * CONVERT_DEGREES_TO_RADIANS;
    this.latitude = latitude * CONVERT_DEGREES_TO_RADIANS;
  }

  public String getName() {
    return this.name;
  }

  public double getLongitude() {
    return this.longitude;
  }

  public double getLatitude() {
    return this.latitude;
  }

  public double distance(City city) {
    double deltaLongitude = city.getLongitude() - this.getLongitude();
    double deltaLatitude = city.getLatitude() - this.getLatitude();
    
    double a = Math.pow(Math.sin(deltaLatitude / 2.0D), 2.0D) +
      Math.cos(this.getLatitude()) *
      Math.cos(city.getLatitude()) *
      Math.pow(Math.sin(deltaLongitude / 2.0D), 2.0D);

    return EARTH_EQUATORIAL_RADIUS * 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1.0D - a));
  }

  public String toString() {
    return this.getName();
  }
  
}
