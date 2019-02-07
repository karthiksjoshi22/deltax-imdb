package com.deltax.imdb.ws.producer;

import com.deltax.imdb.constants.GeneralConstants;
import com.deltax.imdb.controller.AbstractController;
import com.deltax.imdb.dto.request.ActorCreationBean;
import com.deltax.imdb.exception.DataException;
import com.deltax.imdb.services.actor.ActorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/rest/actor")
public class ActorWS extends AbstractController {

    @Autowired
    private ActorService actorService;

    @ApiOperation("Adds a new actor")
    @PostMapping(value = "/add",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addActor(@Valid @RequestBody ActorCreationBean actorCreationBean)
    {
        try
        {
            return buildResponse(actorService.addActor(actorCreationBean),"Actor added successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation("Fetches list of all actors")
    @GetMapping(value = "/fetch-all/{page}/{size}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> fetchAllActors(@Min(value = 0,message = GeneralConstants.PAGE_NUMBER)
                                            @PathVariable("page")int page,@Min(value = 1,message = GeneralConstants.PAGE_SIZE)@PathVariable("size")int size)
    {
        try
        {
            return buildResponse(actorService.fetchAllActors(page, size),"List of all actors fetched successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation("Updates actor information")
    @PutMapping(value = "/update/{actorId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> updateActor(@Min(value = 1,message = GeneralConstants.ACTOR_ID)
                                         @PathVariable("actorId")int actorId,@Valid @RequestBody ActorCreationBean actorCreationBean)
    {
        try
        {
            return buildResponse(actorService.updateActor(actorId,actorCreationBean),"Actor updated successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation("Soft deletes actor by setting delete flag to true")
    @DeleteMapping(value = "/delete/{actorId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> deleteActor(@Min(value = 1,message = GeneralConstants.ACTOR_ID)
                                        @ApiParam("Actor id which needs to be deleted") @PathVariable("actorId")int actorId)
    {
        try
        {
            return buildResponse(actorService.deleteActor(actorId),"Actor deleted successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation("Fetches list of all actors for drop-down list")
    @GetMapping(value = "/fetch-drop-down",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> fetchDropDown()
    {
        try
        {
            return buildResponse(actorService.fetchAllActorDropDown(),"Actor fetched successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation(value = "Fetches actor information by id")
    @GetMapping(value = "/fetch-by-id/{aId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> fetchById(@Min(value = 1,message = GeneralConstants.ACTOR_ID)
                                       @PathVariable("aId")int aId)
    {
        try
        {
            return buildResponse(actorService.fetchById(aId),"Actor data fetched successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }
}
