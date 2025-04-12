package org.example.com.esii.eat.booking.core;

public class Restaurant {
  private String name;
  private Table[] tables;
  private int totalTables;
  private int availableTables;
  private final int additionalTables = 2;
  private int currentTableIndex;

  public Restaurant(String name, int totalTables) {
    this.name = name;
    this.totalTables = totalTables;
    this.availableTables = totalTables + additionalTables;
    this.tables = new Table[totalTables + additionalTables];
    for (int i = 0; i < tables.length; i++) {
      tables[i] = new Table();
    }
    this.currentTableIndex = 0;
  }

  public boolean reserveTables(int numberOfPeople, String reservationName) {
    int tablesNeeded = (int) Math.ceil((double) numberOfPeople / Table.CAPACITY);
    if (tablesNeeded <= 0 || tablesNeeded > availableTables) {
      return false;
    }

    int startIndex = currentTableIndex;
    for (int i = 0; i < tablesNeeded; i++) {
      int index = (startIndex + i) % tables.length;
      if (tables[index].isOccupied()) {
        return false;
      }
    }

    for (int i = 0; i < tablesNeeded; i++) {
      int index = (currentTableIndex + i) % tables.length;
      tables[index].reserve(Table.CAPACITY, reservationName);
    }

    currentTableIndex = (currentTableIndex + tablesNeeded) % tables.length;
    availableTables -= tablesNeeded;
    return true;
  }

  public String getName() {
    return name;
  }

  public boolean hasAvailableTables(int numberOfPeople) {
    int tablesNeeded = (int) Math.ceil((double) numberOfPeople / Table.CAPACITY);
    return availableTables >= tablesNeeded;
  }

  public String availableTablesInfo(int numberOfPeople) {
    int tablesNeeded = (int) Math.ceil((double) numberOfPeople / Table.CAPACITY);
    StringBuilder info = new StringBuilder();
    int count = 0;
    int index = currentTableIndex;

    for (int i = 0; i < tables.length && count < tablesNeeded; i++) {
      Table table = tables[index];
      if (!table.isOccupied()) {
        info.append("Table ").append(index + 1).append(": ").append(table).append("\n");
        count++;
      }
      index = (index + 1) % tables.length;
    }

    return count >= tablesNeeded ? info.toString().trim() : "Not enough tables available.";
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Restaurant: ").append(name).append("\n");
    for (int i = 0; i < tables.length; i++) {
      sb.append("Table ").append(i + 1).append(": ").append(tables[i]).append("\n");
    }
    return sb.toString().trim();
  }
}