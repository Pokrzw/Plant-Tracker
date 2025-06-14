package com.example.planttrackerapp.backend

import android.util.Log
import com.example.planttrackerapp.backend.dao.SpeciesDao
import com.example.planttrackerapp.backend.dao.UserPlantDao
import com.example.planttrackerapp.backend.repositories.UserPlantRepository
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.model.Species
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.Calendar


@RunWith(MockitoJUnitRunner::class)
class UserPlantTest {
    @Mock lateinit var userPlantDao: UserPlantDao
    @Mock lateinit var speciesDao: SpeciesDao

    private lateinit var userPlantRepository: UserPlantRepository
    private lateinit var plant: Plant
    private lateinit var afterUpdatePlant: Plant
    private lateinit var ficus: Species
    private lateinit var afterUpdateSpecies: Species
    private lateinit var date: Calendar
    private lateinit var waterHistory: List<Map<String?, Calendar>>
    private lateinit var diseaseHistory: List<Map<String, Calendar>>
    private lateinit var repotHistory: List<Map<String, Calendar>>
    private lateinit var otherActivitiesHistory: List<Map<String, Calendar>>


    private lateinit var mockedLog: MockedStatic<Log>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userPlantRepository = UserPlantRepository(userPlantDao, speciesDao)
        mockedLog = mockStatic(Log::class.java)
        mockedLog.`when`<Int> {
            Log.e(Mockito.anyString(), Mockito.anyString())
        }.thenReturn(0)

        ficus = Species("Ficus", 3)
        afterUpdateSpecies = Species("Pilea", 3)
        date = Calendar.getInstance()
        date.set(2025, Calendar.MAY, 25)

        waterHistory = listOf(
            mapOf("fertilizer" to Calendar.getInstance().apply {
                set(2000, 10, 10, 10, 10, 0) }
        ))
        diseaseHistory = listOf(
            mapOf("disease" to Calendar.getInstance().apply {
                set(2001, 11, 11, 11, 11, 0) }
            ))
        repotHistory = listOf(
            mapOf("repot" to Calendar.getInstance().apply {
                set(2001, 11, 11, 11, 11, 0) }
            ))
        otherActivitiesHistory = listOf(
            mapOf("accident" to Calendar.getInstance().apply {
                set(2001, 11, 11, 11, 11, 0) }
            ))


        plant = Plant(
            id = "1234567890",
            name = "Bartus",
            species = ficus,
            speciesName = "Ficus",
            created = date,
            waterHistory = waterHistory,
            diseaseHistory = diseaseHistory,
            repotHistory = repotHistory,
            otherActivitiesHistory = otherActivitiesHistory
        )
    }

    @Test
    fun getPlantByIdTest() {
        runBlocking {
            `when`(userPlantDao.getUserPlantById(plant.id)).thenReturn(plant)

            val result: Plant = userPlantRepository.getPlantById(plant.id)
            assertEquals(result.id, plant.id)
        }
    }

    @Test
    fun insertPlantTest() {
        runBlocking {
            `when`(userPlantDao.getUserPlantById(plant.id)).thenReturn(null).thenReturn(plant)
            `when`(userPlantDao.insert(plant)).thenReturn(Unit)

            val result: Plant? = userPlantRepository.insert(plant)

            assertThat(result).isNotNull()
            assertThat(result).isEqualTo(plant)
        }
    }

    @Test
    fun updateByIdTest() {
        runBlocking {
            afterUpdatePlant = Plant(
                id = "1234567890",
                name = "Ficus",
                species = afterUpdateSpecies,
                speciesName = "Pilea",
                created = date,
                waterHistory = waterHistory,
                diseaseHistory = diseaseHistory,
                repotHistory = repotHistory,
                otherActivitiesHistory = otherActivitiesHistory
            )
            `when`(speciesDao.getSpeciesByName(afterUpdatePlant.speciesName)).thenReturn(afterUpdateSpecies)

            userPlantRepository.updateById(
                plant.id,
                afterUpdatePlant.name,
                afterUpdatePlant.speciesName,
                null
            )
            `when`(userPlantDao.getUserPlantById(plant.id)).thenReturn(afterUpdatePlant)

            val result = userPlantRepository.getPlantById(plant.id)
            assertThat(result).isEqualTo(afterUpdatePlant)
            verify(userPlantDao).updateById(
                plant.id,
                afterUpdatePlant.name,
                afterUpdatePlant.speciesName,
                afterUpdateSpecies,
                null)
        }
    }

    @Test
    fun updateWateringHistoryTest() {
        runBlocking {
            val waterHistoryAfterUpdate: List<Map<String?, Calendar>> = listOf(
                mapOf("fertilizer" to Calendar.getInstance().apply {
                    set(2002, 10, 10, 10, 10, 0) }
                ))
            val afterUpdatePlant = Plant(
                id = "1234567890",
                name = "Bartus",
                species = ficus,
                speciesName = "Ficus",
                created = date,
                waterHistory = waterHistoryAfterUpdate,
                diseaseHistory = diseaseHistory,
                repotHistory = repotHistory,
                otherActivitiesHistory = otherActivitiesHistory
            )

            userPlantRepository.updateWateringHistory(
                plant.id,
                waterHistoryAfterUpdate,
            )
            `when`(userPlantDao.getUserPlantById(plant.id)).thenReturn(afterUpdatePlant)
            val result: Plant = userPlantRepository.getPlantById(plant.id)

            assertThat(result).isEqualTo(afterUpdatePlant)
        }
    }

    @Test
    fun updateDiseaseHistoryTest() {
        runBlocking {
            val diseaseHistoryAfterUpdate: List<Map<String, Calendar>> = listOf(
                mapOf("disease" to Calendar.getInstance().apply {
                    set(2003, 11, 11, 11, 11, 0) }
                ))
            val afterUpdatePlant = Plant(
                id = "1234567890",
                name = "Bartus",
                species = ficus,
                speciesName = "Ficus",
                created = date,
                waterHistory = waterHistory,
                diseaseHistory = diseaseHistoryAfterUpdate,
                repotHistory = repotHistory,
                otherActivitiesHistory = otherActivitiesHistory
            )
            userPlantRepository.updateDiseaseHistory(
                plant.id,
                diseaseHistoryAfterUpdate,
            )
            `when`(userPlantDao.getUserPlantById(plant.id)).thenReturn(afterUpdatePlant)
            val result: Plant = userPlantRepository.getPlantById(plant.id)

            assertThat(result).isEqualTo(afterUpdatePlant)
        }
    }

    @Test
    fun updateRepotHistoryTest() {
        runBlocking {
            val repotHistoryAfterUpdate: List<Map<String, Calendar>> = listOf(
                mapOf("repot" to Calendar.getInstance().apply {
                    set(2004, 11, 11, 11, 11, 0) }
                ))
            val afterUpdatePlant = Plant(
                id = "1234567890",
                name = "Bartus",
                species = ficus,
                speciesName = "Ficus",
                created = date,
                waterHistory = waterHistory,
                diseaseHistory = diseaseHistory,
                repotHistory = repotHistoryAfterUpdate,
                otherActivitiesHistory = otherActivitiesHistory
            )
            userPlantRepository.updateRepotHistory(
                plant.id,
                repotHistoryAfterUpdate,
            )
            `when`(userPlantDao.getUserPlantById(plant.id)).thenReturn(afterUpdatePlant)
            val result: Plant = userPlantRepository.getPlantById(plant.id)

            assertThat(result).isEqualTo(afterUpdatePlant)
        }
    }

    @Test
    fun updateOtherActivitiesHistoryTest() {
        runBlocking {
            val otherActivitiesHistoryAfterUpdate: List<Map<String, Calendar>> = listOf(
                mapOf("accident" to Calendar.getInstance().apply {
                    set(2005, 11, 11, 11, 11, 0) }
                ))
            val afterUpdatePlant = Plant(
                id = "1234567890",
                name = "Bartus",
                species = ficus,
                speciesName = "Ficus",
                created = date,
                waterHistory = waterHistory,
                diseaseHistory = diseaseHistory,
                repotHistory = repotHistory,
                otherActivitiesHistory = otherActivitiesHistoryAfterUpdate
            )

            userPlantRepository.updateOtherActivitiesHistory(
                plant.id,
                otherActivitiesHistoryAfterUpdate,
            )
            `when`(userPlantDao.getUserPlantById(plant.id)).thenReturn(afterUpdatePlant)
            val result: Plant = userPlantRepository.getPlantById(plant.id)

            assertThat(result).isEqualTo(afterUpdatePlant)
        }
    }

    @Test
    fun deletePlantTest() {

    }

    @After
    fun tearDown() {
        mockedLog.close()
    }

}