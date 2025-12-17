package horserace.service;

import horserace.model.Horse;
import horserace.util.InputValidator;
import java.util.*;
import java.util.stream.Collectors;

public class RaceService {
    private final Random rand = new Random();

    // 1. Register all horses
    public List<Horse> registerHorses() {
        System.out.println();
        int total = InputValidator.getValidInt("Insert number of horses: ");

        List<Horse> horses = new ArrayList<>();

        // Input and show group assignment after each horse
        for (int i = 1; i <= total; i++) {
            System.out.println("\nHorse " + i + " Information:");
            String name = InputValidator.getValidString("Name: ");
            String warcry = InputValidator.getValidString("Warcry: ");
            int age = InputValidator.getValidInt("Age: ");

            String condition = rand.nextBoolean() ? "Healthy" : "Unhealthy";
            Horse horse = new Horse(name, warcry, age, condition);
            horses.add(horse);
        }

        // Filter healthy horses
        List<Horse> qualified = horses.stream()
                .filter(h -> h.getCondition().equals("Healthy"))
                .collect(Collectors.toList());

        if (qualified.isEmpty()) {
            System.out.println("\nNo healthy horses available. Race canceled.");
            System.exit(0);
        }

        // Assign groups based on logic
        assignGroups(qualified);

        // Display all horses summary
        System.out.println("\nAll Horses:");
        horses.forEach(h -> System.out.println(
                h.getName() + " - (" + h.getAge() + " yrs) - " +
                        h.getCondition() + (h.getCondition().equals("Healthy") ? " - " + h.getGroup().toUpperCase() : "")
        ));

        System.out.println("\n" + qualified.size() + " Qualified Horses:");
        qualified.forEach(h -> System.out.println(
                h.getName() + " - " + h.getWarcry() + " (" + h.getAge() + " yrs)"
        ));

        return qualified;
    }

    // 2. Assign groups based on age logic
    public void assignGroups(List<Horse> horses) {
        Map<Integer, Long> ageFrequency = horses.stream()
                .collect(Collectors.groupingBy(Horse::getAge, Collectors.counting()));

        int mostCommonAge = ageFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();

        double avgAge = horses.stream()
                .mapToInt(Horse::getAge)
                .average()
                .orElse(0);

        horses.forEach(h -> {
            if (h.getAge() == mostCommonAge) h.setGroup("advanced");
            else if (h.getAge() > avgAge) h.setGroup("intermediate");
            else h.setGroup("beginner");
        });
    }

    // 3. Get race distance
    public int getRaceDistance() {
        return InputValidator.getValidInt("\nRace Distance: ");
    }

    // 4. Countdown using streams (no threads)
    public void startCountdown() {
        System.out.println();
        System.out.println("Race Starting...");

        List<String> countdown = List.of("3...", "2...", "1...", "GO!!!");

        countdown.forEach(s -> {
            System.out.println(s);
            delay(500); // 0.5s delay between each count
        });

        System.out.println();
    }

    // 5. Start race simulation
    public void startRace(List<Horse> horses, int distance) {
        startCountdown();

        List<Horse> finished = new ArrayList<>();

        while (finished.size() < horses.size()) {
            horses.stream()
                    .filter(h -> !h.isFinished())
                    .forEach(h -> {
                        h.run(distance);
                        if (h.isFinished() && !finished.contains(h)) {
                            finished.add(h);
                        }
                    });

            System.out.println();
        }
        delay(150); // small delay per horse for realism
        showResults(finished);
    }

    // 6. Show race results
    private void showResults(List<Horse> finished) {
        System.out.println("RACE FINISHED!");
        for (int i = 0; i < finished.size(); i++) {
            String result = (i == 0)
                    ? (i + 1) + " - " + finished.get(i).getName().toUpperCase() + " Winner!!!"
                    : (i + 1) + " - " + finished.get(i).getName();
            System.out.println(result);
        }
    }

    // --- Utility delay (no threads)
    private void delay(int ms) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < ms) {
            // busy-wait delay
        }
    }
}
