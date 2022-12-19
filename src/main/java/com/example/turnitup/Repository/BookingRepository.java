package com.example.turnitup.Repository;

import com.example.turnitup.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    //Query om de dj en organisatie te koppelen
}
