package com.parkinglot;

import com.parkinglot.exception.NoAvailablePositionException;
import com.parkinglot.exception.UnrecognizedTicketException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuperSmartParkingBoyTest {
  @Test
  public void should_park_to_first_lot_when_parking_given_super_smart_park_boy_manage_2lots_and_both_same_available_parking_rate() {
    ParkingLot firstParkingLot = new ParkingLot();
    ParkingLot secondParkingLot = new ParkingLot();
    List<ParkingLot> parkingLotList = Arrays.asList(firstParkingLot, secondParkingLot);

    SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLotList);
    Ticket ticket = superSmartParkingBoy.park(new Car());

    assertNotNull(ticket);
    assertEquals(9, firstParkingLot.getAvailablePosition());
    assertEquals(10, secondParkingLot.getAvailablePosition());
  }

  @Test
  public void should_park_to_second_lot_when_parking_given_super_smart_park_boy_manage_2lots_and_2nd_larger_available_rate() {
    ParkingLot firstParkingLot = new ParkingLot();
    ParkingLot secondParkingLot = new ParkingLot(6);
    List<ParkingLot> parkingLotList = Arrays.asList(firstParkingLot, secondParkingLot);
    firstParkingLot.park(new Car());

    SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLotList);
    Ticket ticket = superSmartParkingBoy.park(new Car());

    assertThat(secondParkingLot.contains(ticket)).isEqualTo(true);
  }

  @Test
  public void should_return_right_when_parking_given_super_parking_boy_manage_two_parking_lots_and_both_have_1car() {
    ParkingLot firstParkingLot = new ParkingLot();
    ParkingLot secondParkingLot = new ParkingLot();
    List<ParkingLot> parkingLotList = Arrays.asList(firstParkingLot, secondParkingLot);

    SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLotList);
    Car firstParkedCar = new Car();
    Ticket firstTicket = firstParkingLot.park(firstParkedCar);
    Car secondParkedCar = new Car();
    Ticket secondTicket = secondParkingLot.park(secondParkedCar);

    Car carFetchFromFirstTicket = superSmartParkingBoy.fetch(firstTicket);
    Car carFetchFromSecondTicket = superSmartParkingBoy.fetch(secondTicket);

    assertThat(carFetchFromFirstTicket).isEqualTo(firstParkedCar);
    assertThat(carFetchFromSecondTicket).isEqualTo(secondParkedCar);
  }

  @Test
  public void should_return_nothing_when_fetching_given_smart_parking_boy_manage_two_parking_lots_and_unrecognized_ticket() {
    ParkingLot firstParkingLot = new ParkingLot();
    ParkingLot secondParkingLot = new ParkingLot();
    List<ParkingLot> parkingLotList = Arrays.asList(firstParkingLot, secondParkingLot);

    SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLotList);

    UnrecognizedTicketException unrecognizedWrongTicket = assertThrows(UnrecognizedTicketException.class, () -> {
      superSmartParkingBoy.fetch(new Ticket());
    });
    assertEquals("Unrecognized parking ticket.", unrecognizedWrongTicket.getMessage());
  }

  @Test
  public void should_return_nothing_when_fetching_given_smart_parking_boy_manage_two_parking_lots_and_used_ticket() {
    ParkingLot firstParkingLot = new ParkingLot();
    ParkingLot secondParkingLot = new ParkingLot();
    List<ParkingLot> parkingLotList = Arrays.asList(firstParkingLot, secondParkingLot);

    SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLotList);

    Ticket ticket = superSmartParkingBoy.park(new Car());
    superSmartParkingBoy.fetch(ticket);
    UnrecognizedTicketException unrecognizedUsedTicket = assertThrows(UnrecognizedTicketException.class, () -> {
      superSmartParkingBoy.fetch(ticket);
    });
    assertEquals("Unrecognized parking ticket.", unrecognizedUsedTicket.getMessage());
  }

  @Test
  public void should_return_nothing_when_parking_given_smart_parking_boy_manage_two_parking_lots_and_both_unavailable() {
    ParkingLot firstParkingLot = new ParkingLot(1);
    ParkingLot secondParkingLot = new ParkingLot(1);
    List<ParkingLot> parkingLotList = Arrays.asList(firstParkingLot, secondParkingLot);

    SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLotList);

    firstParkingLot.park(new Car());
    secondParkingLot.park(new Car());

    NoAvailablePositionException noAvailablePositionException = assertThrows(NoAvailablePositionException.class, () -> {
      superSmartParkingBoy.park(new Car());
    });
    assertEquals("No available position.", noAvailablePositionException.getMessage());
  }
}
