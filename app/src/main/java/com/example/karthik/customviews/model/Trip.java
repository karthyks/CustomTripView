package com.example.karthik.customviews.model;

public class Trip {

  private int id;
  private int type;

  private String tripId1;
  private String tripId2;

  public Trip(int id, int type) {
    this.id = id;
    this.type = type;
    this.tripId1 = "1st";
    this.tripId2 = "2nd";
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getTripId1() {
    return tripId1;
  }

  public void setTripId1(String tripId1) {
    this.tripId1 = tripId1;
  }

  public String getTripId2() {
    return tripId2;
  }

  public void setTripId2(String tripId2) {
    this.tripId2 = tripId2;
  }
}
