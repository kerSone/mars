package com.marsrover.rover.domain.service;

import com.marsrover.rover.cache.InputCache;
import com.marsrover.rover.domain.dao.RoverRepository;
import com.marsrover.rover.domain.service.impl.RoverServiceImpl;
import com.marsrover.rover.exception.BadRequestException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static com.marsrover.rover.domain.service.impl.RoverServiceImpl.MISSING_INPUT_INFORMATION;

@ExtendWith(MockitoExtension.class)
public class RoverServiceImplTest {

    RoverServiceImpl roverService;

    @Mock
    RoverRepository roverRepository;

    @Mock
    InputCache inputCache;

    @BeforeEach
    void initialise() {
        roverService = new RoverServiceImpl(roverRepository, inputCache);
    }

    @Test
    void should_getCorrectResult_when_inputIsCorrectandResultIsNotCachedOrStored() throws BadRequestException {
        // Given
        String input = "5 3\n1 1 E\nRFRFRFRF\n3 2 N\nFRRFLLFFRRFLL\n0 3 W\nLLFFFRFLFL";
        String expectedOutput = "1 1 E\n3 3 N LOST\n4 2 N\n";
        Mockito.when(inputCache.getValidOutput(any())).thenReturn(null);
        Mockito.when(roverRepository.getOutput(any())).thenReturn(null);
        commonCorrectGetResult(input, expectedOutput);
    }

    @Test
    void should_getCorrectResult_when_inputIsCorrectandResultIsStoredAndNotCached() throws BadRequestException {
        // Given
        String input = "5 3\n1 1 E\nRFRFRFRF\n3 2 N\nFRRFLLFFRRFLL\n0 3 W\nLLFFFRFLFL";
        String expectedOutput = "1 1 E\n3 3 N LOST\n4 2 N\n";
        Mockito.when(inputCache.getValidOutput(any())).thenReturn(null);
        Mockito.when(roverRepository.getOutput(any())).thenReturn(expectedOutput);
        Mockito.verify(inputCache, Mockito.never()).setOutput(any(), any(), any());
        commonCorrectGetResult(input, expectedOutput);
    }

    @Test
    void should_getCorrectResult_when_inputIsCorrectandResultIsStoredAndCached() throws BadRequestException {
        // Given
        String input = "5 3\n1 1 E\nRFRFRFRF\n3 2 N\nFRRFLLFFRRFLL\n0 3 W\nLLFFFRFLFL";
        String expectedOutput = "1 1 E\n3 3 N LOST\n4 2 N\n";
        Mockito.when(inputCache.getValidOutput(any())).thenReturn(expectedOutput);
        Mockito.verify(roverRepository, Mockito.never()).getOutput(any());
        commonCorrectGetResult(input, expectedOutput);
    }

    @Test
    void should_throwResponseStatusException_when_inputIsNotContainingAtLeast1Robot() {
        // Given
        String input = "5 5\n1 1 E";
        // When
        ResponseStatusException responseStatusException = Assert.assertThrows(ResponseStatusException.class, () -> roverService.getResult(input));
        // Then
        Assert.assertEquals(MISSING_INPUT_INFORMATION, responseStatusException.getReason());
    }

    void commonCorrectGetResult (String input, String expectedOutput) throws BadRequestException {
        // When
        String output = roverService.getResult(input);
        // Then
        Assert.assertEquals(expectedOutput, output);
    }
}
