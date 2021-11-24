package com.marsrover.rover.cache;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InputCacheTest {

    String input;
    String expectedOutput;
    private InputCache inputCache;

    @BeforeEach
    void initialise() {
        inputCache = new InputCache();
        input = "5 3\n1 1 E\nRFRFRFRF\n3 2 N\nFRRFLLFFRRFLL\n0 3 W\nLLFFFRFLFL";
        expectedOutput = "1 1 E\n3 3 N LOST\n4 2 N\n";
    }

    @Test
    void should_returnOutput_when_storedOutputIsValid() {
        // Given
        inputCache.setOutput(expectedOutput, 3600, input);
        // When
        String output = inputCache.getValidOutput(input);
        // Then
        Assert.assertNotNull(output);
    }

    @Test
    void should_returnNull_when_storedOutputIsNotValid() {
        // Given
        inputCache.setOutput(expectedOutput, -1, input);
        // Then
        String output = inputCache.getValidOutput(input);
        // When
        Assert.assertNull(output);
    }

    @Test
    void should_operateCorrect_when_moreThanOneValueStored() {
        // Given
        inputCache.setOutput(expectedOutput, 3600, "input1");
        inputCache.setOutput(expectedOutput, -1, "input2");
        // When
        String output1 = inputCache.getValidOutput("input1");
        String output2 = inputCache.getValidOutput("input2");
        // Then
        Assert.assertNotNull(output1);
        Assert.assertNull(output2);
    }
}
