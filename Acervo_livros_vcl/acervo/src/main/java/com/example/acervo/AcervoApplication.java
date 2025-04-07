package com.example.acervo;

import com.example.acervo.aplicacao.ConsoleApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AcervoApplication implements CommandLineRunner {

    @Autowired
    ConsoleApp consoleApp;

    public static void main(String[] args) {
        SpringApplication.run(AcervoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        consoleApp.iniciar();
    }

}
