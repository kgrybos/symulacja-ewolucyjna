package agh.ics.oop;

import agh.ics.oop.observers.ISimulationStatsObserver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class SimulationStatsCSVWriter implements ISimulationStatsObserver {
    private final PrintWriter printWriter;
    public SimulationStatsCSVWriter() throws IOException {
        String date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String filename = "Simulation-" + date + ".csv";
        Path filePath = Paths.get(filename);
        Files.createFile(filePath);
        File csvOutputFile = new File(filePath.toUri());
        printWriter = new PrintWriter(csvOutputFile);
        printWriter.println("Liczba zwierząt,Liczba traw,Liczba wolnych pól,Najpopularniejszy genotyp,Średnia energia,Średnia długość życia");
    }

    @Override
    public void updateSimulationStats(SimulationStats simulationStats) {
        printWriter.print(simulationStats.animalNumber());
        printWriter.print(",");
        printWriter.print(simulationStats.grassNumber());
        printWriter.print(",");
        printWriter.print(simulationStats.freeNumber());
        printWriter.print(",");
        printWriter.print(Arrays.stream(simulationStats
                .mostPopularGenotypes()
                .get(0))
                .map(MoveDirection::toString)
                .collect(Collectors.joining(" "))
        );
        printWriter.print(",");
        printWriter.print(simulationStats.averageEnergy());
        printWriter.print(",");
        printWriter.print(simulationStats.averageLifetime());
        printWriter.println();
    }

    public void close() {
        printWriter.close();
    }
}
