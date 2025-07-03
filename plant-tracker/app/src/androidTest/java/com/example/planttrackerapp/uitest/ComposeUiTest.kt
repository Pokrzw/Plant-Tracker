package com.example.planttrackerapp.uitest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.model.Species
import com.example.planttrackerapp.ui.PlantList
import org.junit.Rule
import org.junit.Test
import java.util.Calendar
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.example.planttrackerapp.ui.SinglePlantView
import com.example.planttrackerapp.ui.SpeciesList
import com.example.planttrackerapp.ui.ActivityJournal


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
    val species = listOf(mockSpecies1, mockSpecies2)

    @JvmField
    @Rule
    val composeRule = createComposeRule()

    @Test
    fun testIfPlantNamesAreDisplayed() {

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
    fun testSIfSearchWorks() {

        composeRule.setContent {
            PlantList(
                plantList = plants,
                onClickDetails = {},
                setPlantOnClick = {}
            )
        }

        composeRule.onNodeWithContentDescription("Search by name")
            .performTextInput("2")

        composeRule.onNodeWithText("mockPlant2").assertIsDisplayed()
        composeRule.onNodeWithText("mockPlant1").assertDoesNotExist()
        composeRule.onNodeWithText("mockPlant3").assertDoesNotExist()
    }

    @Test
    fun testIfPlantNameAndSpeciesAreDisplayed() {

        composeRule.setContent {
            SinglePlantView(
                plant = mockPlant1,
                onClickYes = {},
                onWater = {},
                onGoToActivityJournal = {},
                onGoToForm = {},
                onGoBack = {},
                onGoToRepot = {},
                onGoToDisease = {},
                onGoToOther = {}
            )
        }

        composeRule.onNodeWithText("mockPlant1").assertIsDisplayed()
        composeRule.onNodeWithText("mockSpecies1").assertIsDisplayed()
    }

    @Test
    fun testIfWateringPlantShowsConfirmationMessage() {
        composeRule.setContent {
            SinglePlantView(
                plant = mockPlant1,
                onClickYes = {},
                onWater = {},
                onGoToActivityJournal = {},
                onGoToForm = {},
                onGoBack = {},
                onGoToRepot = {},
                onGoToDisease = {},
                onGoToOther = {}
            )
        }

        composeRule.onNodeWithText("Water plant").performClick()

        composeRule.onNodeWithText("Plant watered!").assertIsDisplayed()
    }

    @Test
    fun testIfDeleteConfirmationDialogAppears() {
        composeRule.setContent {
            SinglePlantView(
                plant = mockPlant1,
                onClickYes = {},
                onWater = {},
                onGoToActivityJournal = {},
                onGoToForm = {},
                onGoBack = {},
                onGoToRepot = {},
                onGoToDisease = {},
                onGoToOther = {}
            )
        }

        composeRule.onNodeWithText("Delete plant").performClick()

        composeRule.onNodeWithText("Delete plant?").assertIsDisplayed()
        composeRule.onNodeWithText("Yes").assertIsDisplayed()
        composeRule.onNodeWithText("No").assertIsDisplayed()
    }

    @Test
    fun testIfSpeciesNamesAreDisplayed() {
        composeRule.setContent {
            SpeciesList(
                speciesList = species,
                setSpeciesOnClick = {}
            )
        }

        composeRule.onNodeWithText("mockSpecies1").assertIsDisplayed()
        composeRule.onNodeWithText("mockSpecies2").assertIsDisplayed()
    }

    @Test
    fun testIfSpeciesSearchWorks() {
        composeRule.setContent {
            SpeciesList(
                speciesList = species,
                setSpeciesOnClick = {}
            )
        }

        composeRule.onNodeWithText("Search by name").performTextInput("2")

        composeRule.onNodeWithText("mockSpecies2").assertIsDisplayed()
        composeRule.onNodeWithText("mockSpecies1").assertDoesNotExist()
    }

    @Test
    fun testIfSpeciesCardExpandsOnClickAndShowsEditButton() {
        var selectedSpecies: Species? = null

        composeRule.setContent {
            SpeciesList(
                speciesList = listOf(mockSpecies1),
                setSpeciesOnClick = { selectedSpecies = it }
            )
        }

        val node = composeRule.onNodeWithText("mockSpecies1")
        node.assertIsDisplayed()
        node.performClick()

        composeRule.onNodeWithText("Soil Moisture: 1").assertIsDisplayed()
        composeRule.onNodeWithText("Edit").assertIsDisplayed()
    }

    @Test
    fun testIfSectionsAreDisplayedAndScrollable() {
        composeRule.setContent {
            ActivityJournal(
                currentPlant = null,
                onGoBack = {}
            )
        }

        composeRule.onNodeWithText("Watering").assertIsDisplayed()
        composeRule.onNodeWithText("Repotting").assertIsDisplayed()
        composeRule.onNodeWithText("Diseases").assertIsDisplayed()

        composeRule
            .onNodeWithText("Other", useUnmergedTree = true)
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    fun testIfJournalShowsWateringHistoryByDefault() {
        composeRule.setContent {
            ActivityJournal(
                currentPlant = mockPlant1,
                onGoBack = {}
            )
        }

        composeRule.onNodeWithText("Watering").assertIsDisplayed()
        composeRule.onNodeWithText("Fertilizer").assertIsDisplayed()
    }

    @Test
    fun testIfClickingRepottingSwitchesSection() {
        composeRule.setContent {
            ActivityJournal(
                currentPlant = mockPlant1,
                onGoBack = {}
            )
        }

        composeRule.onNodeWithText("Repotting").performClick()

        composeRule.onNodeWithText("Date").assertIsDisplayed()
        composeRule.onNodeWithText("Pot size").assertIsDisplayed()
        composeRule.onNodeWithText("Fertilizer").assertDoesNotExist()
    }
}