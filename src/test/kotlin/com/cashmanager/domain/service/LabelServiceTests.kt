package com.cashmanager.domain.service

import com.cashmanager.entity.Label
import com.cashmanager.repository.LabelRepository
import com.cashmanager.service.LabelService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.stream.Stream

@ExtendWith(MockitoExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LabelServiceTests {

    @Mock
    private lateinit var labelRepository: LabelRepository

    @InjectMocks
    private lateinit var labelService: LabelService

    @ParameterizedTest
    @MethodSource("testGetLabelsArguments")
    fun testGetLabels(repositoryList: List<Label>, expectedSize: Int) {
        given(labelRepository.findAll()).willReturn(repositoryList)
        val labelList = labelService.getLabels()
        assertEquals(labelList.size, expectedSize)
    }

    fun testGetLabelsArguments(): Stream<Arguments> = Stream.of(
        arguments(listOf<Label>(), 0),
        arguments(listOf(Label(1, "Courses"), Label(2, "Restaurant")), 2),
        arguments(listOf(Label(1, "Courses")), 1)
    )
}
