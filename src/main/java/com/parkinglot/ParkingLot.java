package com.parkinglot;

import java.util.HashMap;

public class ParkingLot {
  static final int DEFAULT_CAPACITY = 10;
  private final int capacity;
  HashMap<Ticket, Car> parkingLotMap = new HashMap<>();

  public ParkingLot() {
    this.capacity = DEFAULT_CAPACITY;
  }

  public ParkingLot(int capacity) {
    this.capacity = capacity;
  }

  public Ticket park(Car car) {
    if (parkingLotMap.size() < capacity) {
      Ticket ticketToBeReturn = new Ticket();
      parkingLotMap.put(ticketToBeReturn, car);
      return ticketToBeReturn;
    }
    return null;
  }

  public Car fetch(Ticket ticket) {
    if (ticket != null) {
      return parkingLotMap.remove(ticket);
    }
    return null;
  }
}
