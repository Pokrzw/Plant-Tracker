package com.example.planttrackerapp

import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.graphics.createBitmap
import com.example.planttrackerapp.backend.repositories.SpeciesRepository
import com.example.planttrackerapp.backend.repositories.UserPlantRepository
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.model.Species
import org.junit.Assert.assertEquals

import com.example.planttrackerapp.ui.FormViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.Calendar

private fun getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): Calendar {
    return Calendar.getInstance().apply {
        set(year, month - 1, day, hour, minute, 0)
        set(Calendar.MILLISECOND, 0)
    }
}

class ViewModelTest {

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
        speciesName = "mockSpecies1",
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
        speciesName = "mockSpecies1",
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
        speciesName = "mockSpecies2",
        species = mockSpecies2,
        waterHistory = listOf(),
        imageUri = null,
        created = getDate(2024, 10, 15, 10, 30) ,
        diseaseHistory = listOf(),
        repotHistory = listOf(),
        otherActivitiesHistory = listOf(),
        qrCodeImage = null,
    )

    private val plantRepo = mockk<UserPlantRepository>(relaxed = true)
    private val speciesRepo = mockk<SpeciesRepository>(relaxed = true)
    private lateinit var formViewModel: FormViewModel
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        Dispatchers.setMain(dispatcher)
        formViewModel = FormViewModel(plantRepo, speciesRepo)

    }

    @Test
    fun formViewModel_selectedPlant_inFormState(){
        formViewModel.onSetPlant(mockPlant1)
        assertEquals(mockPlant1, formViewModel.plantUiState.value.currentlyEditedPlant)
    }

    @Test
    fun formViewModel_saveAction_repot(){
        formViewModel.onSetPlant(mockPlant1)
        formViewModel.saveActionForm("note", "repot")
        assertEquals(formViewModel.plantUiState.value.currentlyEditedPlant!!.repotHistory[0].keys, setOf("note"))
    }

    @Test
    fun formViewModel_saveAction_disease(){
        formViewModel.onSetPlant(mockPlant1)
        formViewModel.saveActionForm("note", "disease")
        assertEquals(formViewModel.plantUiState.value.currentlyEditedPlant!!.diseaseHistory[0].keys, setOf("note"))
    }

    @Test
    fun formViewModel_saveAction_else(){
        formViewModel.onSetPlant(mockPlant1)
        formViewModel.saveActionForm("note", "else")
        assertEquals(formViewModel.plantUiState.value.currentlyEditedPlant!!.otherActivitiesHistory[0].keys, setOf("note"))
    }

    @Test
    fun formViewModel_saveSelection_plantList(){
        val res = listOf<Plant>(mockPlant1, mockPlant2, mockPlant3)
        formViewModel.saveSelection(res)
        assertEquals(formViewModel.selectUiState.value.selectedPlantList, res)
    }

    @Test
    fun formViewModel_updateFromForm_name(){
        formViewModel.saveNameOnUpdate(mockPlant1.name)
        assertEquals(formViewModel.formUiState.value.name, mockPlant1.name)
    }

    @Test
    fun formViewModel_updateFromForm_species(){
        formViewModel.saveSpeciesOnUpdate(mockPlant1.species)
        assertEquals(formViewModel.formUiState.value.species, mockPlant1.species)
    }

    @Test
    fun formViewModel_updateFromForm_URI(){
        formViewModel.saveUriOnUpdate(mockPlant1.imageUri as Uri?)
        assertEquals(formViewModel.formUiState.value.imgUri, mockPlant1.imageUri)
    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun formViewModel_addPlant_listIncByOne() {
//        val testDispatcher = UnconfinedTestDispatcher()
//        Dispatchers.setMain(testDispatcher)
//
//        try {
//            formViewModel.saveNameOnUpdate(mockPlant1.name)
//            formViewModel.saveSpeciesOnUpdate(mockPlant1.species)
//            formViewModel.saveUriOnUpdate(mockPlant1.imageUri as Uri?)
//
//            val bitmapMock = mockkClass(Bitmap::class)
//            every {
//                bitmapMock.setPixel(any(), any(), any())
//            } returns Unit
//            every {
//                bitmapMock.compress(any(), any(), any())
//            } returns false
//
//            mockkStatic(Base64::class)
//            every { Base64.encodeToString(any(), any()) } returns ""
//
//            mockkStatic(Bitmap::class)
//            every { createBitmap(
//                width = 512,
//                height = 512,
//                config = Bitmap.Config.RGB_565
//            ) } returns bitmapMock
//
//            formViewModel.onClickAdd {  }
//            assertEquals(1, formViewModel.formUiState.value.plantsList.size)
//        } finally {
//            Dispatchers.resetMain()
//        }
//    }

    @Test
    fun formViewModel_resetForm_success(){
        formViewModel.saveNameOnUpdate(mockPlant1.name)
        formViewModel.saveSpeciesOnUpdate(mockPlant1.species)
        formViewModel.resetForm()
        assertEquals(formViewModel.formUiState.value.name, "")
        assertEquals(formViewModel.formUiState.value.species, null)
    }

    @Test
    fun formViewModel_addWateringDate_emptyString(){
        formViewModel.onSetPlant(mockPlant1)
        formViewModel.addWateringDate("")
        assert(formViewModel.plantUiState.value.currentlyEditedPlant!!.waterHistory.get(0).keys.contains(""))
    }

    @Test
    fun formViewModel_findById_exits(){
        
    }
}