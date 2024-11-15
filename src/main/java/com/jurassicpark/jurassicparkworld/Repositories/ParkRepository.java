package com.jurassicpark.jurassicparkworld.Repositories;

import com.jurassicpark.jurassicparkworld.models.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ParkRepository extends JpaRepository<Park,Long> {
}
