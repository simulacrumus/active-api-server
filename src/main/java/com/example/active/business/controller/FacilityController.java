package com.example.active.business.controller;

import com.example.active.business.domain.FacilityDTO;
import com.example.active.business.service.FacilityService;
import com.example.active.data.entity.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/facilities")
public class FacilityController {

    private final String DEFAULT_FACILITY_SORT_OPTION = "title";
    @Autowired
    private FacilityService facilityService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityDTO> getFacilities(
            @RequestParam(name = "key") String apiKey,
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "lng") Double lng,
            @RequestParam(name = "lat") Double lat,
            @RequestParam(name = "sort", defaultValue = DEFAULT_FACILITY_SORT_OPTION) String sort,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return facilityService.findAll(query, sort, lat, lng);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Optional<FacilityDTO> getFacilityByKey(
            @PathVariable("id") String key,
            @RequestParam(name = "key") String apiKey,
            @RequestParam(name = "lng") Double lng,
            @RequestParam(name = "lat") Double lat,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return facilityService.findByKey(key, lat, lng);
    }

    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void saveFacility(
            @RequestParam(name = "key") String apiKey,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Facility facility
    ){
        facilityService.save(facility);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFacility(
            @PathVariable("id") Long id,
            @RequestParam(name = "key") String apiKey,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        facilityService.deleteFacility(id);
    }
}