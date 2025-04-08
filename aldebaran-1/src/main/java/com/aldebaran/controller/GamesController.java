package com.aldebaran.controller;

import com.aldebaran.model.Categories;
import com.aldebaran.model.Game;
import com.aldebaran.model.TopGamesResult;
import com.aldebaran.service.GamesService;
import com.aldebaran.util.MapBuilder;
import com.aldebaran.util.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class GamesController {
    @Autowired
    GamesService gamesService;
    public static final Integer LIMIT_POPULAR = 50;
    public static final Integer LIMIT_NEW = 50;
    public static final Integer LIMIT_BETS = 5;
    public static final Integer LIMIT_WINS = 5;
    public static final Integer LIMIT_ROUNDS = 5;
    public static final Integer LIMIT_OVERESTIMATES = 100;


    @PermitAll
    @GetMapping({"/games"})
    public Object getGames(HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        MapBuilder<String, Object> mbResult = new MapBuilder();
        List<Game> gamesAll = this.gamesService.filterGameList(this.gamesService.getGameList(), UserUtil.getUserId(request), null);

        List<TopGamesResult> topGamesRounds = this.gamesService.topGamesRounds("1");


        List<Categories> categoriesOrder = this.gamesService.categoriesOrder();
        List<String> carouselCategories = this.gamesService.getCategoriesFiltered(gamesAll, categoriesOrder,
                UserUtil.getUserId(request), "CAROUSEL");
        List<String> listCategories = this.gamesService.getCategoriesFiltered(gamesAll, categoriesOrder,
                UserUtil.getUserId(request), "LIST");

        mbResult.add("gamesAll", gamesAll);
        mbResult.add("gamesPopular", this.gamesService.topGames(gamesAll, topGamesRounds, LIMIT_POPULAR));
        mbResult.add("gamesNew", this.gamesService.gamesLastLimit(gamesAll, LIMIT_NEW));
        mbResult.add("carouselCategories", carouselCategories);
        mbResult.add("listCategories", listCategories);
        mbResult.add("allListCategories", this.gamesService.getCategoriesFromList(gamesAll));

        return mbResult.map();
    }


    @PermitAll
    @GetMapping({"/games-new"})
    public Object getGameNews(HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        MapBuilder<String, Object> mbResult = new MapBuilder();
        List<Game> gamesAll = this.gamesService.filterGameList(this.gamesService.getGameList(), UserUtil.getUserId(request), null);

        mbResult.add("gamesNew", this.gamesService.gamesLastLimit(gamesAll, LIMIT_NEW));
        return mbResult.map();
    }


    @PermitAll
    @GetMapping({"/gameCategories"})
    public Object getGameCategories(HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        MapBuilder<String, Object> mbResult = new MapBuilder();
        List<Game> gamesAll = this.gamesService.filterGameList(this.gamesService.getGameList(), UserUtil.getUserId(request), null);

        mbResult.add("categories", this.gamesService.getCategoriesFromList(gamesAll));

        return mbResult.map();
    }


    @PermitAll
    @GetMapping({"/topGames"})
    public Object topGames(HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        MapBuilder<String, Object> mbResult = new MapBuilder();
        List<Game> gamesAll = this.gamesService.filterGameList(this.gamesService.getGameList(), UserUtil.getUserId(request), null);

        List<TopGamesResult> topGamesRounds = new ArrayList<>();
        List<TopGamesResult> topGamesWins = new ArrayList<>();

        mbResult.add("topGamesRounds", this.gamesService.topGames(gamesAll, topGamesRounds, LIMIT_BETS));
        mbResult.add("gamesWins", this.gamesService.topGames(gamesAll, topGamesWins, LIMIT_WINS));

        return mbResult.map();
    }


    @PostMapping({"/tournaments/list"})
    public Object tournamentsList(@RequestParam(name = "start_date") String start_date, @RequestParam(name = "end_date") String end_date, @RequestParam(name = "subpath") String subpath) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return this.gamesService.tournamentsList(start_date, end_date, subpath);
    }


    @PostMapping({"/tournament/get"})
    public Object tournamentGet(@RequestParam(name = "subpath") String subpath) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return this.gamesService.tournamentGet(subpath);
    }
}


