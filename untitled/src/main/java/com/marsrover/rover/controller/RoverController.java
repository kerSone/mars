package com.marsrover.rover.controller;

import com.marsrover.rover.error.BadRequestException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coordinates")
public interface RoverController {

    @GetMapping(path = "/{input}")
    @ApiOperation(value = "Given an input retrieves several rover paths",
    tags = {"coordinates"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    ResponseEntity<String> getCoordinates(@PathVariable("input") String input) throws BadRequestException;
}
