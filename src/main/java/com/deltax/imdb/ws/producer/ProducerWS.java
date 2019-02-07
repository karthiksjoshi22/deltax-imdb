package com.deltax.imdb.ws.producer;

import com.deltax.imdb.controller.AbstractController;
import com.deltax.imdb.dto.request.ProducerCreationBean;
import com.deltax.imdb.exception.DataException;
import com.deltax.imdb.services.producer.ProducerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/rest/producer")
public class ProducerWS extends AbstractController {

    @Autowired
    private ProducerService producerService;

    @ApiOperation(value = "Adds a new producer")
    @PostMapping(value = "/add",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addProducer(@Valid @RequestBody ProducerCreationBean producerCreationBean)
    {
        try
        {
            return buildResponse(producerService.addProducer(producerCreationBean),"Producer added successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation(value = "Fetches list of all producers")
    @GetMapping(value = "/fetch-all",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> fetchAllProducer()
    {
        try
        {
            return buildResponse(producerService.fetchAllProducer(),"List of all producers fetched successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation(value = "Updates producer information")
    @PutMapping(value = "/update/{pId}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> updateProducer(@Min(value = 1,message = "Producer id should be greater than or equal to 1")
                                            @PathVariable("pId")int pId,@Valid @RequestBody ProducerCreationBean producerCreationBean)
    {
        try
        {
            return buildResponse(producerService.updateProducer(pId,producerCreationBean),"Producer updated successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation("Soft deletes producer")
    @DeleteMapping(value = "/delete/{pId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> deleteProducer(@Min(value = 1,message = "Producer id should be greater than or equal to 1")
                                            @PathVariable("pId")int pId)
    {
        try
        {
            return buildResponse(producerService.deleteProducer(pId),"Producer deleted successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }
}
