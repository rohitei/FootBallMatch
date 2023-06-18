package com.football.app.controller;


import com.football.app.dto.AuthRequest;
import com.football.app.dto.FootballMatch;
import com.football.app.entity.UserInfo;
import com.football.app.service.JwtService;
import com.football.app.service.UserInfoService;
import com.football.app.utils.DrawnMatchResponse;
import com.football.app.utils.FootballMatchResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private UserInfoService service;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
	private RestTemplate restTemplate;

    
    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserInfo> getAllTheProducts() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public UserInfo getProductById(@PathVariable int id) {
        return service.getProduct(id);
    }


    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
    
   // http://localhost:8080/user/matches/draws?year=2011
    @GetMapping("/matches/draws")
    public ResponseEntity<DrawnMatchResponse> getDrawnMatch(@RequestParam("year") int year) {
        String apiUrl = "https://jsonmock.hackerrank.com/api/football_matches";
        String queryParams = String.format("?year=%d&page=1", year);
        String url = apiUrl + queryParams;

        FootballMatchResponse response = restTemplate.getForObject(url, FootballMatchResponse.class);
        if (response != null) {
            List<FootballMatch> drawnMatches = getDrawnMatches(response.getData());
            int drawnMatchesCount = drawnMatches.size();

            DrawnMatchResponse drawnMatchesResponse = new DrawnMatchResponse(drawnMatchesCount, drawnMatches);
            return ResponseEntity.ok(drawnMatchesResponse);
        }

        return ResponseEntity.ok(new DrawnMatchResponse(0, new ArrayList<>()));
    }

    private List<FootballMatch> getDrawnMatches(List<FootballMatch> matches) {
        List<FootballMatch> drawnMatches = new ArrayList<>();

        for (FootballMatch match : matches) {
            if (match.getTeam1goals() == match.getTeam2goals()) {
                drawnMatches.add(match);
            }
        }

        return drawnMatches;
    }

}
