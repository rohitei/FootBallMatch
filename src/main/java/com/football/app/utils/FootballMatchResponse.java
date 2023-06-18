package com.football.app.utils;

import java.util.List;

import com.football.app.dto.FootballMatch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootballMatchResponse {
	private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<FootballMatch> data;
}
