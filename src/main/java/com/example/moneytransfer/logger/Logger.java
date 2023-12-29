package com.example.moneytransfer.logger;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
@Component
public class Logger {
    private final FileWriter writer;
    private Logger(){
        try {
            writer = new FileWriter("log.txt",true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void log(String msg) {
        try {
            writer.append("[")
                    .append(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
                    .append(":")
                    .append(LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss.nnn")))
                    .append("] ")
                    .append(msg)
                    .append("\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Log Error " + msg);
        }
    }

}
