package com.marsrover.rover.controller;

import com.marsrover.rover.exception.BadRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coordinates")
@Api(
        tags = {"coordinates"},
        value = "coordinates"
)
public interface RoverApi {

    @PostMapping
    @ApiOperation(value = "Given an input retrieves several rover paths",
            tags = {"coordinates"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    ResponseEntity<String> getCoordinates(@RequestBody String input) throws BadRequestException;
}
