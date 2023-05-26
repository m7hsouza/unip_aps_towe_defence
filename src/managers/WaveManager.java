package managers;

import java.util.ArrayList;
import java.util.Arrays;

import events.Wave;
import scenes.Playing;

public class WaveManager {

	public WaveManager(Playing playing) {
		this.playing = playing;
		createWaves();
	}

	private Playing playing;

	private ArrayList<Wave> waves = new ArrayList<>();

	private final int enemySpawnTickLimit = 60 * 1;
	private int enemySpawnTick = enemySpawnTickLimit;
	private int currentEnemyIndex, currentWaveIndex;

	private void createWaves() {
		ArrayList<Integer> enemies = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 2, 2));
		waves.add(new Wave(enemies));
	}

	public void update() {
		if (enemySpawnTick < enemySpawnTickLimit) enemySpawnTick++;
	}

	public boolean isTimeForNewEnemy() {
		return enemySpawnTick >= enemySpawnTickLimit;
	}

	public boolean isThereMoreEnemiesInWave() {
		return currentEnemyIndex < waves.get(currentWaveIndex).getEnemies().size();
	}

	public ArrayList<Wave> getWaves() {
		return this.waves;
	}

	public int getNextEnemy() {
		enemySpawnTick = 0;
		return waves.get(currentWaveIndex).getEnemy(currentEnemyIndex++);
	}


}
