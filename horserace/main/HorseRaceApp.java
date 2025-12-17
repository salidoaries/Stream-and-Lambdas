package horserace.main;

import horserace.model.Horse;
import horserace.service.RaceService;
import java.util.List;

public class HorseRaceApp {
    public static void main(String[] args) {
        RaceService raceService = new RaceService();

        // Step 1: Register horses
        List<Horse> qualifiedHorses = raceService.registerHorses();

        // Step 2: Group them by age
        raceService.assignGroups(qualifiedHorses);

        // Step 3: Input race distance
        int distance = raceService.getRaceDistance();

        // Step 4: Start the race simulation
        raceService.startRace(qualifiedHorses, distance);
    }
}
