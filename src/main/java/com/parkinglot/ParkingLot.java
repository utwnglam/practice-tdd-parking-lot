package com.parkinglot;

import com.parkinglot.exception.NoAvailablePositionException;
import com.parkinglot.exception.UnrecognizedTicketException;

import java.util.HashMap;

public class ParkingLot {
  private static final int DEFAULT_CAPACITY = 10;

  private final int capacity;
  private final HashMap<Ticket, Car> parkingLotMap = new HashMap<>();

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
    throw new NoAvailablePositionException("No available position.");
  }

  public Car fetch(Ticket ticket) {
    if (parkingLotMap.containsKey(ticket) && ticket != null) {
      return parkingLotMap.remove(ticket);
    }
    throw new UnrecognizedTicketException("Unrecognized parking ticket.");
  }

  public int getCapacity() {
    return capacity;
  }

  public int getAvailablePosition() {
    return capacity - parkingLotMap.size();
  }

  public boolean contains(Ticket ticket) {
    return parkingLotMap.containsKey(ticket);
  }
}
