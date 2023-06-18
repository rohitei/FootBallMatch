package com.football.app.utils;

import java.util.List;

import com.football.app.dto.FootballMatch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawnMatchResponse {
	private int drawnMatchesCount;
    private List<FootballMatch> drawnMatches;
}
