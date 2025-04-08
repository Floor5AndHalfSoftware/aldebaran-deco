package com.aldebaran.service;

import com.aldebaran.model.Categories;
import com.aldebaran.model.Game;
import com.aldebaran.model.GameParams;
import com.aldebaran.model.TopGamesResult;
import com.aldebaran.util.MapBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Transactional
public class GamesService {
    @Autowired
    com.aldebaran.service.HttpService http;
    @Autowired
    com.aldebaran.service.MtpuService mtpu;
    @Autowired
    com.aldebaran.service.CryptoService cryptoService;

    @Cacheable({"gameCategoriesCache"})
    public List<String> getCategoriesFromList(List<Game> games) {
        return (List<String>) games.stream().map(Game::getCategory).map(c -> c.split(",")).flatMap(Arrays::stream).distinct().sorted()
                .collect(Collectors.toList());
    }


    public List<String> getCategoriesOrderedFromList(List<Categories> categoriesOrder, List<Game> games) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        List<String> categories = (List<String>) games.stream().filter(c -> !c.getCategory().toLowerCase().contains("minigame"))
                .map(Game::getCategory).map(c -> c.split(",")).flatMap(Arrays::stream).distinct().sorted()
                .collect(Collectors.toList());

        List<String> categoriesOrdered = new ArrayList<>();
        if (categoriesOrder != null) {
            for (Categories item : categoriesOrder) {
                String category = item.getCategory();
                if (categories.contains(category)) {
                    categoriesOrdered.add(category);
                }
            }
        }
        for (String c : categories) {
            if (!categoriesOrdered.contains(c)) {
                categoriesOrdered.add(c);
            }
        }

        return categoriesOrdered;
    }

    public List<Game> gamesLastDays(List<Game> games) {
        Comparator<Game> comparator = Comparator.comparing(Game::getTs).reversed();
        return (List<Game>) games.stream().sorted(comparator).filter(Game::isNew).collect(Collectors.toList());
    }

    @Cacheable({"gameListCache"})
    public List<Game> gamesLastLimit(List<Game> games, int limit) {
        if (games.size() <= limit) {
            return new ArrayList<>(games);
        }

        PriorityQueue<Game> minHeap = new PriorityQueue<>(Comparator.comparing(Game::getTs));

        for (Game game : games) {
            minHeap.offer(game);
            if (minHeap.size() > limit) {
                minHeap.poll();
            }
        }

        List<Game> result = new ArrayList<>(minHeap);
        result.sort(Comparator.comparing(Game::getTs).reversed());

        return result;
    }


    public List<Game> filterGameList(List<Game> games, String userid, String category) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {

        Stream<Game> stream = games.parallelStream()
                .filter(game -> (game.getGameParams().getGscode() != null))
                .filter(game -> isEnableOnlyFor(game.getGameParams(), userid));


        if (category != null) {

            stream = stream.filter(game -> isCategory(game, category));
        }


        return stream.collect(Collectors.toList());
    }


    @Cacheable({"gameListCache"})
    public List<Game> getGameList() throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        MapBuilder<String, String> mb = new MapBuilder();


        String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSession() + this.cryptoService.getSecretSharedSession());

        mb.add("skin", this.http.getSkin());

        mb.add("hash", hash);

        JsonNode root = mapper.readTree(this.http.newRequestGet("games/list", this.http.getUrlReportsFrontend(), mb.map()));


        JsonNode games = root.get("games");

        return (List<Game>) mapper.readValue(games.toString(), (TypeReference) new Object());
    }


    private boolean isCategory(Game game, String category) {

        return (category == null || game.getCategory().toLowerCase().contains(category.toLowerCase()));
    }

    private boolean isEnableOnlyFor(GameParams gameParams, String userid) {

        return (gameParams.getEnableOnlyFor() == null || (
                gameParams.getEnableOnlyFor() != null && gameParams.getEnableOnlyFor().contains(userid + ",")));
    }


    @Cacheable({"gameListCache"})
    public List<Game> topGames(List<Game> games, List<TopGamesResult> topGames, int limit) throws JsonMappingException, JsonProcessingException {

        Set<String> topGameIds = (Set<String>) topGames.stream()
                .map(gameTop -> String.valueOf(gameTop.getId()))
                .collect(Collectors.toSet());


        int i = 1;

        List<Game> gamesBets = new ArrayList<>();


        for (Game game : games) {

            Integer orderPopular = safeParseInt(game.getGameParams().getOrderPopular());


            if (orderPopular != null) {

                game.setValue(orderPopular);

            } else if (topGameIds.contains(String.valueOf(game.getGameID()))) {

                game.setValue(i);

                i++;
            } else {
                continue;
            }


            gamesBets.add(game);
        }


        return (List<Game>) gamesBets.stream()
                .sorted(Comparator.comparing(Game::getValue))
                .limit(limit)
                .collect(Collectors.toList());
    }


    private Integer safeParseInt(String value) {
        try {

            return (value != null) ? Integer.parseInt(value) : null;

        } catch (NumberFormatException e) {

            return null;
        }
    }


    @Cacheable({"topGamesRoundsCache"})
    public List<TopGamesResult> topGamesRounds(String statsType) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {

        List<TopGamesResult> result = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        MapBuilder<String, String> mb = new MapBuilder();


        String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSession() + this.cryptoService.getSecretSharedSession());

        mb.add("statstype", statsType);

        mb.add("hash", hash);


        JsonNode root = mapper.readTree(this.http.newRequestGet("bia/topGamesRecord", this.http.getUrlGamesBackend(), mb.map()));

        JsonNode topGames = root.get("topgamesrecord");


        if (topGames != null) {

            for (JsonNode item : (Iterable<JsonNode>) topGames) {

                TopGamesResult game = (TopGamesResult) mapper.readValue(item.toString(), TopGamesResult.class);

                result.add(game);
            }
        }

        return result;
    }


    @Cacheable({"topGamesWinCache"})
    public List<TopGamesResult> topGamesWin(String statsType) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {

        List<TopGamesResult> result = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        MapBuilder<String, String> mb = new MapBuilder();


        String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSession() + this.cryptoService.getSecretSharedSession());

        mb.add("statstype", statsType);

        mb.add("hash", hash);


        JsonNode root = mapper.readTree(this.http.newRequestGet("bia/topGamesWin", this.http.getUrlGamesBackend(), mb.map()));

        JsonNode topGames = root.get("topgameswin");


        if (topGames != null) {

            for (JsonNode item : (Iterable<JsonNode>) topGames) {

                TopGamesResult game = (TopGamesResult) mapper.readValue(item.toString(), TopGamesResult.class);

                result.add(game);
            }
        }

        return result;
    }


    public JsonNode tournamentsList(String start_date, String end_date, String subpath) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        MapBuilder<String, String> mb = new MapBuilder();


        String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSession() + this.cryptoService.getSecretSharedSession() + subpath + start_date);

        mb.add("subpath", subpath);

        mb.add("start_date", start_date);

        mb.add("end_date", end_date);

        mb.add("hash", hash);

        return mapper.readTree(this.http.newRequestGet("tournaments/list", this.http.getUrlReportsFrontend(), mb.map()));
    }


    public JsonNode tournamentGet(String subpath) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        MapBuilder<String, String> mb = new MapBuilder();


        String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSession() + this.cryptoService.getSecretSharedSession() + this.cryptoService.getSecretSharedNonce());

        mb.add("nonce", this.cryptoService.getSecretSharedNonce());

        mb.add("subpath", subpath);

        mb.add("hash", hash);

        return mapper.readTree(this.http.newRequestGet("tournament/get", this.http.getUrlGamesFrontend(), mb.map()));
    }


    @Cacheable({"gameCategoriesCache"})
    public List<Categories> categoriesOrder() throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {

        List<Categories> result = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        MapBuilder<String, String> mb = new MapBuilder();


        String hash = this.cryptoService.digest(this.cryptoService.getSecretSharedSession() + this.cryptoService.getSecretSharedSession());

        mb.add("nonce", this.cryptoService.getSecretSharedNonce());

        mb.add("hash", hash);

        JsonNode root = mapper.readTree(this.http.newRequestGet("categoriesOrder", this.http.getUrlGamesFrontend(), mb.map()));

        JsonNode categories = root.get("categories");

        if (categories != null) {

            for (JsonNode item : (Iterable<JsonNode>) categories) {

                Categories category = (Categories) mapper.readValue(item.toString(), Categories.class);

                result.add(category);
            }
        }

        return result;
    }


    public List<String> getCategoriesFiltered(List<Game> gamesAll, List<Categories> categoriesOrder, String userId, String type) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException {

        List<Categories> enabledCategories = new ArrayList<>();

        List<Categories> disabledCategories = new ArrayList<>();

        List<Categories> enabledCategoriesFiltered = new ArrayList<>();

        List<Categories> disabledCategoriesFiltered = new ArrayList<>();


        for (Categories c : categoriesOrder) {

            if (!c.getType().equals(type)) {
                continue;
            }


            boolean isForAllUsers = (c.getUsers_id() == null || c.getUsers_id().isBlank());

            boolean isForThisUser = (userId != null && !isForAllUsers &&
                    Arrays.<String>asList(c.getUsers_id().split(",")).contains(userId));


            if (c.isEnabled()) {

                if (isForAllUsers) {

                    enabledCategories.add(c);
                    continue;

                }
                if (isForThisUser)
                    enabledCategoriesFiltered.add(c);
                continue;
            }

            if (isForAllUsers) {

                disabledCategories.add(c);
                continue;

            }
            if (isForThisUser) {

                disabledCategoriesFiltered.add(c);
            }
        }


        List<Categories> finalEnabledCategories = !enabledCategoriesFiltered.isEmpty() ? enabledCategoriesFiltered :
                enabledCategories;

        List<Categories> finalDisabledCategories = !disabledCategoriesFiltered.isEmpty() ? disabledCategoriesFiltered :
                disabledCategories;


        List<String> finalCategories = getCategoriesOrderedFromList(finalEnabledCategories, gamesAll);


        finalCategories.removeAll((Collection) finalDisabledCategories.stream().map(Categories::getCategory).collect(Collectors.toSet()));


        return finalCategories;
    }
}


