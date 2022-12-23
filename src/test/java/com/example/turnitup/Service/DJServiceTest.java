package com.example.turnitup.Service;

import com.example.turnitup.DTO.DJDto;
import com.example.turnitup.Exception.DJNotFoundException;
import com.example.turnitup.Model.DJ;
import com.example.turnitup.Repository.DJRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DJServiceTest {

    @Mock
    DJRepository djRepository;

    @InjectMocks
    DJService djService;

    DJ dj = new DJ();
    DJ dj2 = new DJ();

    DJDto djDto = new DJDto();
    DJDto djDto2 = new DJDto();

    List<DJ> djList = new ArrayList<>();
    List<DJDto> djDtoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        dj.setDjName("Deadly Guns");
        dj.setMusicSpecialty("Hardcore");
        dj.setPricePerHour(80.50);

        dj2.setDjName("Freddy Moreira");
        dj2.setMusicSpecialty("Moombahton");
        dj2.setPricePerHour(120.80);

        djDto.setDjName("Deadly Guns");
        djDto.setMusicSpecialty("Hardcore");
        djDto.setPricePerHour(80.50);

        djDto2.setDjName("Freddy Moreira");
        djDto2.setMusicSpecialty("Moombahton");
        djDto2.setPricePerHour(120.80);

        djDtoList.add(djDto);
        djDtoList.add(djDto2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllDJs() {
        // Arrange
        when(djRepository.findAll()).thenReturn(djList);

        lenient().when(djRepository.count()).thenReturn(1L);

        // Act
        List<DJDto> allDJs = djService.getAllDJs();

        // Assert
        Mockito
                .verify(djRepository, Mockito.times(1)).findAll();
        assertThat(allDJs).isEqualTo(djList);

    }

    @Test
    void getDJById() {
        // Act
        when(djRepository.findById(dj.getId()))
                .thenReturn(Optional.of(dj));

        DJDto theRealDj = djService.getDJById(dj.getId());

        // Assert
        assertEquals(theRealDj.getDjName(), djDto.getDjName());
        assertEquals(theRealDj.getPricePerHour(), djDto.getPricePerHour());
        assertEquals(theRealDj.getMusicSpecialty(), djDto.getMusicSpecialty());

    }

    @Test
    void createDJ() {

        DJ testDj = new DJ(null, "Deadly Guns", "Hardcore",
                80.50, null, null, null);

        DJ savedDj = dj;

        lenient().when(djRepository.findById(dj.getId()))
                .thenReturn(Optional.of(savedDj));

        assertEquals(testDj.getId(), dj.getId());
        assertEquals(testDj.getDjName(), dj.getDjName());
        assertEquals(testDj.getMusicSpecialty(), dj.getMusicSpecialty());
        assertEquals(testDj.getPricePerHour(), dj.getPricePerHour());
    }

    @Test
    void updateDJById() {
        when(djRepository.save(dj)).thenReturn(dj);
        when(djRepository.findById(1L)).thenReturn(Optional.of(dj));
        DJDto newDjInformation = new DJDto("Deadly Roses", "Hip-Hop", 65.30);

        DJDto updatedDj = djService.updateDJ( 1L, newDjInformation);

        assertEquals(updatedDj.getDjName(), "Deadly Roses");
        assertEquals(updatedDj.getMusicSpecialty(), "Hip-Hop");
        assertEquals(updatedDj.getPricePerHour(), 65.30);

        DJNotFoundException thrown = assertThrows(
                DJNotFoundException.class,
                ()-> djService.updateDJ(2L, djDto)
        );

        assertEquals("The dj with this id doesn't exist", thrown.getMessage());
    }

    @Test
    void deleteDJById() {
       Optional<DJ> optionalDJ = Optional.of(dj);
       lenient().when(djRepository.findById(1002L)).thenReturn(optionalDJ);

       DJNotFoundException thrown = assertThrows(
                DJNotFoundException.class,
                ()-> djService.deleteDJById(dj.getId())
        );

        assertEquals("The dj with this id doesn't exist", thrown.getMessage());



    }
}