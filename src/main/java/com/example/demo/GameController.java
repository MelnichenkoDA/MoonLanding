package com.example.demo;

import com.example.demo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GameController {

    private GameRepository gameRepository;
    private Integer id;

    @Autowired
    GameController(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    @GetMapping("/")
    String describe(){
        return "Describe.html";
    }

    @GetMapping("/start")
    public @ResponseBody String[] start(){
        GameStats stats = gameRepository.save(new GameStats());
        this.id = stats.getId();

        String[] str = new String[7];
        str[0] = "ID : " + String.valueOf(stats.getId());
        str[1] = "Текущая высота : " + String.valueOf(stats.getHeight());
        str[2] = "Скорость : " + String.valueOf(stats.getSpeed());
        str[3] = "Запас топлива : " + String.valueOf(stats.getFuel());
        str[4] = "Расход топлива : " + String.valueOf(stats.getFuelConsumption());
        str[5] = "Текущий ход : " + String.valueOf(stats.getCurrentMove());
        str[6] = "Введите /move?fuel=x, чтобы сделать ход и использовать x топлива. Если ввести /move израсходуется 0.0 единиц топлива";

        return str;
    }

    @GetMapping("/move")
    public @ResponseBody String[] move(@RequestParam(name = "fuel", required = false) Double fuel){
        GameStats stats;

        if (id == null){

            stats = gameRepository.save(new GameStats());
            this.id = stats.getId();
        } else{
            stats = gameRepository.getOne(this.id);
        }

        if (stats.getFinished() == 0){
            if (fuel == null) fuel = 0.0;
            if (fuel > stats.getFuel()) return getStats(stats);
            calc(stats, fuel);
            gameRepository.save(stats);
        }

        if (stats.getFinished() != 0){
            String[] str = new String[7];
            if (stats.getFinished() == -1)
                str[0] = "Игра окончена, корабль разбился.";
            else
                str[0] = "Игра завершена, вы посадили корабль.";
            str[1] = "ID : " + String.valueOf(stats.getId());
            str[2] = "Текущая высота : " + String.valueOf(stats.getHeight());
            str[3] = "Скорость : " + String.valueOf(stats.getSpeed());
            str[4] = "Запас топлива : " + String.valueOf(stats.getFuel());
            str[5] = "Расход топлива : " + String.valueOf(stats.getFuelConsumption());
            str[6] = "Текущий ход : " + String.valueOf(stats.getCurrentMove());
            return str;
        }

        return getStats(stats);
    }

    @GetMapping("/load")
    public @ResponseBody  String[] load(@RequestParam(name = "id", required = false) Integer id){
        if (id == null)
            return new String[] {"Id не может быть пустым!"};

        try {
            GameStats stats = gameRepository.getOne(id);
            if (stats.getFinished() == 0){
                this.id = stats.getId();
                return new String[] {"Вы можете продолжить игру. Для этого перейдите по /move"};
            } else
                return new String[] {"Эта игра уже завершена, вы не можете ее продолжить."};
        } catch (Exception e){
            return new String[] {"Игры с таким id не существует. Загрузите другую или начните новую."};
        }
    }

    @GetMapping("/scoreboard")
    public @ResponseBody
    List<GameStats> scoreboard(){
        return gameRepository.findByFinishedOrderByFuel(1);
    }


    private void calc(GameStats stats, double fuel){
        stats.setSpeed(stats.getSpeed() - 1.6 * (fuel / 9.8 - 1));

        if (stats.getHeight() - stats.getSpeed() < 0){
            if (stats.getSpeed() < 5)
                stats.setFinished(1);
             else
                stats.setFinished(-1);
            stats.setHeight(0.0);
            stats.setSpeed(0.0);
        } else
            stats.setHeight(stats.getHeight() - stats.getSpeed());

        stats.setFuelConsumption(fuel);
        stats.setFuel(stats.getFuel() - fuel);
        stats.setCurrentMove(stats.getCurrentMove() + 1);
    }

    private String[] getStats(GameStats stats){
        String[] str = new String[6];
        str[0] = "ID : " + String.valueOf(stats.getId());
        str[1] = "Текущая высота : " + String.valueOf(stats.getHeight());
        str[2] = "Скорость : " + String.valueOf(stats.getSpeed());
        str[3] = "Запас топлива : " + String.valueOf(stats.getFuel());
        str[4] = "Расход топлива : " + String.valueOf(stats.getFuelConsumption());
        str[5] = "Текущий ход : " + String.valueOf(stats.getCurrentMove());
        return str;
    }
}
