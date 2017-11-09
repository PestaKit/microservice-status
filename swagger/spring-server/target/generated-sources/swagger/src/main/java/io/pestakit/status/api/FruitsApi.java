package io.pestakit.status.api;

import io.pestakit.status.api.model.Fruit;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-09T13:22:01.412+01:00")

@Api(value = "fruits", description = "the fruits API")
public interface FruitsApi {

    @ApiOperation(value = "", notes = "create a fruit", response = Object.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "created", response = Object.class) })
    @RequestMapping(value = "/fruits",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Object> createFruit(@ApiParam(value = "" ,required=true ) @RequestBody Fruit fruit);


    @ApiOperation(value = "", notes = "get the list of all fruits", response = Fruit.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "success", response = Fruit.class) })
    @RequestMapping(value = "/fruits",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Fruit>> getFruits();

}
