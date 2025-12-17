package horserace.model;

import java.util.Random;

public class Horse {
    private String name;
    private String warcry;
    private int age;
    private String condition;
    private String group;
    private int distanceCovered;
    private boolean finished;
    private int speedCount;
    private static final Random rand = new Random(); // shared Random

    public Horse(String name, String warcry, int age, String condition) {
        this.name = name;
        this.warcry = warcry;
        this.age = age;
        this.condition = condition;
        this.distanceCovered = 0;
        this.finished = false;
        this.speedCount = 0;
    }

    // Simulate one run iteration
    public void run(int trackDistance) {
        if (finished) return;

        speedCount++;
        int speed = generateSpeed();
        distanceCovered += speed;
        int remaining = Math.max(0, trackDistance - distanceCovered);

        System.out.println(name + " ran " + speed + " remaining " + remaining);
        delay(200); // slight delay per horse output (no threads)

        if (distanceCovered >= trackDistance) {
            finished = true;
            System.out.println(name + " finished the race! " + warcry);
        }
    }

    // Speed logic based on group and speedCount
    private int generateSpeed() {
        int baseSpeed = rand.nextInt(10) + 1; // 1–10

        switch (group) {
            case "advanced":
                // starting from 3rd run, advanced horse gets 5–10 speed range
                if (speedCount >= 3) baseSpeed = rand.nextInt(6) + 5;
                break;
            case "intermediate":
                // starting from 5th run, speed increases by 10%
                if (speedCount >= 5)
                    baseSpeed += Math.round(baseSpeed * 0.10);
                break;
            // beginner stays same
        }
        return baseSpeed;
    }

    // Manual delay (no Thread.sleep)
    private void delay(int ms) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < ms) {
            // simple busy wait
        }
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getWarcry() { return warcry; }
    public int getAge() { return age; }
    public String getCondition() { return condition; }
    public boolean isFinished() { return finished; }
    public void setGroup(String group) { this.group = group; }
    public String getGroup() { return group; }
}
