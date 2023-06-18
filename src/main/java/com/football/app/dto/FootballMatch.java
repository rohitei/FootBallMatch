package com.football.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FootballMatch {
	private String competition;
    private int year;
    private String team1;
    private String team2;
    private int team1goals;
    private int team2goals;
}
