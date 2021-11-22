package RoverController;

import com.marsrover.rover.RoverControllerRestService;
import com.marsrover.rover.cache.InputCache;
import com.marsrover.rover.error.BadRequestException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class RoverControllerRestServiceTest {

    @Mock
    InputCache inputCache;

    RoverControllerRestService roverControllerRestService;

    @BeforeEach
    void initialise() {
        roverControllerRestService = new RoverControllerRestService(inputCache);
    }

    @Test
    public void should_executeLogic_when_responseIsNotCached() throws BadRequestException {
        // Given
        String input = "5 3\n1 1 E\nRFRFRFRF\n3 2 N\nFRRFLLFFRRFLL\n0 3 W\nLLFFFRFLFL";
        String expectedOutput = "1 1 E\n3 3 N LOST\n4 2 N\n";
        Mockito.when(inputCache.getValidOutput(Mockito.any())).thenReturn(null);
        // When
        ResponseEntity<String> output = roverControllerRestService.getCoordinates(input);
        // Then
        Assert.assertEquals(expectedOutput, output.getBody());
    }

    @Test
    public void should_getOutputFromCache_when_responseIsCached() throws BadRequestException {
        // Given
        String input = "5 3\n1 1 E\nRFRFRFRF\n3 2 N\nFRRFLLFFRRFLL\n0 3 W\nLLFFFRFLFL";
        String expectedOutput = "1 1 E\n3 3 N LOST\n4 2 N\n";
        Mockito.when(inputCache.getValidOutput(Mockito.any())).thenReturn(expectedOutput);
        Mockito.verify(inputCache, Mockito.never()).setOutput(Mockito.any(), Mockito.any(), Mockito.any());
        // When
        ResponseEntity<String> output = roverControllerRestService.getCoordinates(input);
        // Then
        Assert.assertEquals(expectedOutput, output.getBody());
    }

}
