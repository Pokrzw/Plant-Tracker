package com.example.planttrackerapp.uitest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.planttrackerapp.backend.repositories.SpeciesRepository
import com.example.planttrackerapp.backend.repositories.UserPlantRepository
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.model.Species
import com.example.planttrackerapp.ui.PlantList
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import java.util.Calendar


private fun getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): Calendar {
    return Calendar.getInstance().apply {
        set(year, month - 1, day, hour, minute, 0)
        set(Calendar.MILLISECOND, 0)
    }
}


class ComposeUiTest {

    private val mockSpecies1: Species = Species(
        name = "mockSpecies1",
        soilMoisture = 1
    )

    private val mockSpecies2: Species = Species(
        name = "mockSpecies2",
        soilMoisture = 2
    )

    private var mockPlant1: Plant = Plant(
        id = "1",
        name = "mockPlant1",
        speciesId = mockSpecies1.id,
        species = mockSpecies1,
        waterHistory = listOf(),
        imageUri = null,
        created = getDate(2024, 10, 15, 10, 30) ,
        diseaseHistory = listOf(),
        repotHistory = listOf(),
        otherActivitiesHistory = listOf(),
        qrCodeImage = null,
    )

    private var mockPlant2: Plant = Plant(
        id = "2",
        name = "mockPlant2",
        speciesId = mockSpecies1.id,
        species = mockSpecies1,
        waterHistory = listOf(),
        imageUri = null,
        created = getDate(2024, 10, 15, 10, 30) ,
        diseaseHistory = listOf(),
        repotHistory = listOf(),
        otherActivitiesHistory = listOf(),
        qrCodeImage = null,
    )

    private var mockPlant3: Plant = Plant(
        id = "3",
        name = "mockPlant3",
        speciesId = mockSpecies2.id,
        species = mockSpecies2,
        waterHistory = listOf(),
        imageUri = null,
        created = getDate(2024, 10, 15, 10, 30) ,
        diseaseHistory = listOf(),
        repotHistory = listOf(),
        otherActivitiesHistory = listOf(),
        qrCodeImage = null,
    )

    val plants = listOf(mockPlant1, mockPlant2, mockPlant3)

    @JvmField
    @Rule
    val composeRule = createComposeRule()

    @Test
    fun testPlantNamesAreDisplayed() {

        composeRule.setContent {
            PlantList(
                plantList = plants,
                onClickDetails = {},
                setPlantOnClick = {}
            )
        }

        composeRule.onNodeWithText("mockPlant1").assertIsDisplayed()
        composeRule.onNodeWithText("mockPlant2").assertIsDisplayed()
        composeRule.onNodeWithText("mockPlant3").assertIsDisplayed()
    }

    @Test
    fun testSearchFiltersPlantList() {
        val plants = listOf(mockPlant1, mockPlant2, mockPlant3)

        composeRule.setContent {
            PlantList(
                plantList = plants,
                onClickDetails = {},
                setPlantOnClick = {}
            )
        }

        composeRule.onNodeWithContentDescription("Search by name")
            .performTextInput("mockPlant2")

        composeRule.onNodeWithText("mockPlant2").assertIsDisplayed()
        composeRule.onNodeWithText("mockPlant1").assertDoesNotExist()
        composeRule.onNodeWithText("mockPlant3").assertDoesNotExist()
    }

}