package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("name", "Tom");
        return "home";
    }

    @PostMapping("/home")
    public String setNameHome(Model model,
                              @RequestParam(name = "NameInput", required = false, defaultValue = "TestWorld") String name1) {
        model.addAttribute("name1", name1);
        return "home";
    }

    @GetMapping("/calc")
    public String calc() {
        return "calc";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam("number1") double number1,
                            @RequestParam("operation") String operation,
                            @RequestParam("number2") double number2,
                            Model model) {
        double result = switch (operation) {
            case "+" -> number1 + number2;
            case "-" -> number1 - number2;
            case "*" -> number1 * number2;
            case "/" -> number1 / number2;
            case "**" -> Math.pow(number1, number2);
            default -> throw new IllegalArgumentException("Недопустимая операция: " + operation);
        };
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/valute")
    public String valute() {
        return "valuta";
    }

    @GetMapping("/convert")
    public String convert(@RequestParam("fromCurrency") String fromCurrency,
                          @RequestParam("toCurrency") String toCurrency,
                          @RequestParam("amount") double amount,
                          Model model) {
        double conversionRate = getConversionRate(fromCurrency, toCurrency);
        double convertedAmount = Math.round(amount * conversionRate * 100.0) / 100.0;

        model.addAttribute("convertedAmount", convertedAmount);
        model.addAttribute("fromCurrency", fromCurrency);
        model.addAttribute("toCurrency", toCurrency);
        return "valut_result";
    }

    private double getConversionRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals("USD") && toCurrency.equals("EUR")) {
            return 0.90486;
        } else if (fromCurrency.equals("EUR") && toCurrency.equals("USD")) {
            return 1.11;
        } else if (fromCurrency.equals("GBP") && toCurrency.equals("USD")) {
            return 1.31;
        } else if (fromCurrency.equals("USD") && toCurrency.equals("GBP")) {
            return 0.76192;
        } else if (fromCurrency.equals("JPY") && toCurrency.equals("USD")) {
            return 0.006856;
        } else if (fromCurrency.equals("USD") && toCurrency.equals("JPY")) {
            return 146.26;
        } else if (fromCurrency.equals("RUB") && toCurrency.equals("USD")) {
            return 0.011111;
        } else if (fromCurrency.equals("USD") && toCurrency.equals("RUB")) {
            return 90;
        }
        return 1.0;
    }
}
