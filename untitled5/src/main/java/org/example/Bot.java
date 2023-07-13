package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class Bot extends TelegramLongPollingBot {

    private Map<Long, Integer> levelMap;
//    private Map<User, List<Integer>> users;

    private String checkButton;
    private List<InlineKeyboardButton> topRow = new ArrayList<>();
    private InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    private Integer level;
//    private User user;

//    private Integer requestTotalForUser = 0;
//
//    private Integer requestTotal = 0;
//    private String popularRequest;
//
//    private int uniqueTotal = 0;


//    private int maxChatId = 0;
//
//    private int maxChatIds = 0;
//    private List<Integer> countRequest;
//    private User mostActiveUser;
//    private List<Integer> sumOfEachRequest;
//    private List<User> uniqueUsers;

    public Bot() {
        this.levelMap = new HashMap<>();
//        this.users = new HashMap<>();
//        this.sumOfEachRequest = new ArrayList<>();
//        this.uniqueUsers = new ArrayList<>();


    }

    @Override
    public String getBotToken() {
        return "6051004836:AAGGw292DrLm8hO6vGNW4Vy3oueYkiibK2U";

    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId;
      //  User user = null;
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();

        } else {
            chatId = update.getMessage().getChatId();
        }
        this.level = this.levelMap.get(chatId);
//            user = update.getMessage().getFrom();
//        }
//
//        if (!uniqueUsers.contains(user)) {
//            uniqueUsers.add(user);
//            this.users.put(user, countRequest);
//            this.uniqueTotal = uniqueUsers.size();
//        }
//

//        this.countRequest = this.users.get(user);
//
//        if (countRequest == null) {
//            this.countRequest = new ArrayList<>();
//            for (int i = 0; i < 5; i++) {
//                countRequest.add(0);
//            }
//        }
//
//
//
//        if (this.sumOfEachRequest.size() < 5) {
//            for (int i = 0; i < 5; i++) {
//                sumOfEachRequest.add(0);
//            }
//        }


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        InlineKeyboardButton JokeButton = new InlineKeyboardButton();
        JokeButton.setText("Jokes");
        JokeButton.setCallbackData("J");

        InlineKeyboardButton countryButton = new InlineKeyboardButton();
        countryButton.setText("country");
        countryButton.setCallbackData("C");

        InlineKeyboardButton weatherButton = new InlineKeyboardButton();
        weatherButton.setText("weather");
        weatherButton.setCallbackData("W");

        InlineKeyboardButton quotesButton = new InlineKeyboardButton();
        quotesButton.setText("quotes");
        quotesButton.setCallbackData("Q");

        InlineKeyboardButton numbersButton = new InlineKeyboardButton();
        numbersButton.setText("numbers");
        numbersButton.setCallbackData("N");


        new Thread(() -> {
            if (level == null) {
//                this.requestTotal = 0;
//                for (int i = 0; i < uniqueUsers.size(); i++) {
//                    List<Integer> countRequest = this.users.get(uniqueUsers.get(i));
//                    Integer amountRequest = this.amountRequestsForEachUser(countRequest);
//                    this.requestTotal += amountRequest;
//                    System.out.println("sum of request" + " " + requestTotal);
//
//                    if (amountRequest > maxChatIds) {
//                        this.mostActiveUser = uniqueUsers.get(i);
//                        System.out.println("popular user" + " " + mostActiveUser);
//                    }
//                    this.requestTotalForUser = 0;
//                }


                sendMessage.setText("what do you want from me");
                topRow.clear();
                if (Window.booleans.get(0)) {
                    topRow.add(JokeButton);
                }
                if (Window.booleans.get(1)) {
                    topRow.add(countryButton);
                }
                if (Window.booleans.get(2)) {
                    topRow.add(weatherButton);
                }
                if (Window.booleans.get(3)) {
                    topRow.add(quotesButton);
                }
                if (Window.booleans.get(4)) {
                    topRow.add(numbersButton);
                }
                List<List<InlineKeyboardButton>> keyboard = List.of(this.topRow);
                inlineKeyboardMarkup.setKeyboard(keyboard);

                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                send(sendMessage);

                this.levelMap.put(chatId, 1);
                Utils.sleep();
            }
        }).start();

        new Thread(() -> {
            if (level == 1) {
                String cellBackData = update.getCallbackQuery().getData();
                if (cellBackData.equals("J")) {
                    HttpResponse<String> response = null;
                    try {
                        response = Unirest.get("https://v2.jokeapi.dev/joke/Any").asString();
                    } catch (UnirestException e) {
                        throw new RuntimeException(e);
                    }
                    ObjectMapper objectMapper = new ObjectMapper();
                    JokeModel jokeModel = null;
                    try {
                        jokeModel = objectMapper.readValue(response.getBody(), JokeModel.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    if (jokeModel.getDelivery() != null && jokeModel.getSetup() != null) {
                        sendMessage.setText(jokeModel.getSetup() + " " + jokeModel.getDelivery());
                    } else {
                        sendMessage.setText(jokeModel.getJoke());
                    }
//                    countRequest.set(0, countRequest.get(0) + 1);
//                    this.users.put(this.user, countRequest);
//                    System.out.println(" list of one user" + " " + countRequest);

                    send(sendMessage);
                    this.levelMap.put(chatId, null);

                } else if (cellBackData.equals("C")) {
                    this.checkButton = cellBackData;
                    sendMessage.setText("which country?");
//                    countRequest.set(1, countRequest.get(1) + 1);
//                    this.users.put(this.user, countRequest);
                    send(sendMessage);
                    this.levelMap.put(chatId, 2);
                } else if (cellBackData.equals("W")) {
                    this.checkButton = cellBackData;
                    sendMessage.setText("choose country or city");
//                    countRequest.set(2, countRequest.get(2) + 1);
//                    this.users.put(this.user, countRequest);
                    send(sendMessage);
                    this.levelMap.put(chatId, 2);

                } else if (cellBackData.equals("Q")) {
                    Random random = new Random();
                    int num = random.nextInt(1, 30);
                    HttpResponse<String> response = null;
                    try {
                        response = Unirest.get("https://dummyjson.com/quotes/" + num).asString();
                    } catch (UnirestException e) {
                        throw new RuntimeException(e);
                    }
                    ObjectMapper objectMapper = new ObjectMapper();
                    QuotesModel quotesModel = null;
                    try {
                        quotesModel = objectMapper.readValue(response.getBody(), QuotesModel.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    sendMessage.setText(quotesModel.getAuthor() + " " + "said:" + " " + quotesModel.getQuote());
//                    countRequest.set(3, countRequest.get(3) + 1);
//                    this.users.put(this.user, countRequest);
//                    System.out.println(" list of one user" + " " + countRequest);

                    this.levelMap.put(chatId, null);
                    send(sendMessage);

                } else if (cellBackData.equals("N")) {
                    this.checkButton = cellBackData;
                    sendMessage.setText("choose some number");
//                    countRequest.set(4, countRequest.get(4) + 1);
//                    this.users.put(this.user, countRequest);

                    Utils.sleep();
                    send(sendMessage);
                    this.levelMap.put(chatId, 2);
                }
            }
        }).start();

        new Thread(() -> {
            if (level == 2 && checkButton.equals("C")) {
                String countryName = update.getMessage().getText();
                HttpResponse<String> response = null;
                try {
                    response = Unirest.get("https://restcountries.com/v2/alpha/" + countryName).asString();
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                CountryModel countryModel = null;
                try {
                    countryModel = objectMapper.readValue(response.getBody(), CountryModel.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                sendMessage.setText("Capital: " + " " + countryModel.getCapital() + " Borders:" + " " + countryModel.getBorders());
                this.levelMap.put(chatId, null);
                send(sendMessage);

            } else if (level == 2 && checkButton.equals("W")) {
                String Name = update.getMessage().getText();
                HttpResponse<String> response = null;
                try {
                    response = Unirest.get("https://api.openweathermap.org/data/2.5/weather?q=" + Name + "&appid=e861c48b0f57a21b764c3ebd6518213b").asString();
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                WeatherModel weatherModel = null;
                try {
                    weatherModel = objectMapper.readValue(response.getBody(), WeatherModel.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                sendMessage.setText("temp:" + " " + weatherModel.getMain().get("temp") + " " + "feels like:" + " " + weatherModel.getMain().get("feels_like") + " " + " description:" + " " + weatherModel.getWeather().get(0).get("description"));
                this.levelMap.put(chatId, null);
                send(sendMessage);
            } else if (level == 2 && checkButton.equals("N")) {
                String number = update.getMessage().getText();
                String fact = null;
                try {
                    fact = Unirest.get("http://numbersapi.com/" + number).asString().getBody();
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
                sendMessage.setText(fact);
                this.levelMap.put(chatId, null);
                send(sendMessage);
            }
        }).start();


    }


    private void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

//    public Integer amountRequestsForEachUser(List<Integer> countRequest) {
//        for (int i = 0; i < Window.apiService.size(); i++) {
//            String request = Window.apiService.get(i);
//            this.requestTotalForUser += countRequest.get(i);
//            System.out.println(requestTotalForUser);
//            this.sumOfEachRequest.set(i, this.sumOfEachRequest.get(i) + this.countRequest.get(i));
//            System.out.println("list of request" + " " + sumOfEachRequest);
//            if (sumOfEachRequest.get(i) > maxChatId) {
//                maxChatId = sumOfEachRequest.get(i);
//                this.popularRequest = request;
//                System.out.println("popular request" + " " + popularRequest);
//            }
//        }
//
//        return requestTotalForUser;
//    }


    @Override
    public String getBotUsername() {
        return "mendysegal770Bot";
    }
}
