package com.example.karthik.customviews.model;

import java.util.Locale;

public class HelpContactsModel {
  private String itemName;
  private String itemNumber;
  private String itemType;

  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public HelpContactsModel() {
  }

  public HelpContactsModel(String name, String phone, String type) {
    this.itemName = name;
    this.itemNumber = phone;
    this.itemType = type;
  }

  @Override
  public boolean equals(Object o) {
    return o.toString().equals(toString());
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public String toString() {
    return String.format(Locale.getDefault(), "number: %s, name: %s, type: %s",
        itemNumber, itemName, itemType);
  }

  public String getItemNumber() {
    return itemNumber;
  }

  public void setItemNumber(String itemNumber) {
    this.itemNumber = itemNumber;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }
}