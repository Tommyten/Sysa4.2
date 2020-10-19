import java.util.*;

public class GameOfLifeSimulator implements Runnable {

    private static final int MAX_GENERATIONS = 50;

    private Game game;
    private HashMap<Integer, Integer> generationAssignmentMap;
    private List<Integer> initalsThatProduceOscillation;

    public GameOfLifeSimulator(String binary) {
        game = new Game(binary);
        generationAssignmentMap = new HashMap<>(MAX_GENERATIONS);
        generationAssignmentMap.put(0, game.getAssignmentNumber());
        initalsThatProduceOscillation = new ArrayList<>();
    }

    @Override
    public void run() {
        for (int i = 1; i < MAX_GENERATIONS; i++) {
            game.generateNextGeneration();
            generationAssignmentMap.put(i, game.getAssignmentNumber());
        }

        HashMap<Integer, Integer> assignments = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry:
                generationAssignmentMap.entrySet()) {
            Integer test = assignments.put(entry.getValue(), entry.getKey());
            if(test == null);
            else if(test == entry.getKey()-1) return;
            else {
                log("Oscillating Object detected in Generations: " + test + " and " + entry.getKey());
                initalsThatProduceOscillation.add(Integer.parseInt(game.getInitial(), 2));
                return;
            }
        }
    }

    private void log(String logMessage) {
        System.out.println(Integer.parseInt(game.getInitial(), 2) + ": " + logMessage);
    }

    public static void main(String[] args) {
        int duplicates = 0;
        for (int i = 0; i < Math.pow(2, Game.GRID_SIZE * Game.GRID_SIZE); i++) {
            String binary = String
                    .format(
                            "%" + Game.GRID_SIZE * Game.GRID_SIZE + "s",
                            Integer.toBinaryString(i)
                    )
                    .replaceAll(" ", "0");

            Thread t = new Thread(new GameOfLifeSimulator(binary));
            t.start();
        }
    }
}
