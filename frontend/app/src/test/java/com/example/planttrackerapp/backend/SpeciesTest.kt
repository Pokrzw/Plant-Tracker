package com.example.planttrackerapp.backend

import android.util.Log
import com.example.planttrackerapp.backend.dao.SpeciesDao
import com.example.planttrackerapp.backend.repositories.SpeciesRepository
import org.mockito.Mockito.`when`
import com.example.planttrackerapp.model.Species
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Mockito.mockStatic
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SpeciesTest {
    @Mock
    lateinit var speciesDao: SpeciesDao

    private lateinit var speciesRepository: SpeciesRepository
    private lateinit var mockedLog: MockedStatic<Log>
    private lateinit var species: Species

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        speciesRepository = SpeciesRepository(speciesDao)
        mockedLog = mockStatic(Log::class.java)
        mockedLog.`when`<Int> {
            Log.e(Mockito.anyString(), Mockito.anyString())
        }.thenReturn(0)
        species = Species("Ficus", 3)
    }

    @Test
    fun insertSpeciesTest() {
        runBlocking {
            `when`(speciesDao.insert(species)).thenReturn(null)
            `when`(speciesDao.getSpeciesByName(species.name)).thenReturn(species)

            speciesRepository.insertSpecies(species)

            val result: Species? = speciesRepository.getSpeciesByName(species.name)
            assertThat(result).isEqualTo(species)
        }
    }

    @Test
    fun getAllSpeciesTest() {
        runBlocking {
            val expectedSpecies: List<Species> = listOf(species)
            `when`(speciesDao.getAll()).thenReturn(expectedSpecies)

            val result: List<Species> = speciesRepository.getAllSpecies()
            assertThat(result).isEqualTo(expectedSpecies)
        }
    }

    @Test
    fun deleteSpeciesTest() {
        runBlocking {
            `when`(speciesDao.delete(species)).thenReturn(Unit)
            `when`(speciesDao.getSpeciesByName(species.name)).thenReturn(null)

            speciesRepository.deleteSpecies(species)
            val result = speciesRepository.getSpeciesByName(species.name)
            assertThat(result).isNull()
        }
    }

    @After
    fun tearDown() {
        mockedLog.close()
    }
}