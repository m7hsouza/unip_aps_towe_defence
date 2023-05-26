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

	private final Playing playing;

	private ArrayList<Wave> waves = new ArrayList<>();

	private final int enemySpawnTickLimit = 60 * 1;
	private int enemySpawnTick = enemySpawnTickLimit;
	private int currentEnemyIndex, currentWaveIndex;

	private boolean waveStartTime, waveTickTimeOver;
	private final int waveTickLimit = 60 * 15;
	private int waveTick;

	private void createWaves() {
		ArrayList<Integer> enemies = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 2, 2));
		waves.add(new Wave(enemies));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 1, 0, 0, 0, 0, 0, 2, 2, 2))));
	}

	public void update() {
		if (enemySpawnTick < enemySpawnTickLimit)
			enemySpawnTick++;

		if (waveStartTime) {
			if (++waveTick >= waveTickLimit) {
				waveTickTimeOver = true;
			}
		}
	}

	public void increaseWaveIndex() {
		currentWaveIndex++;
		waveTick = 0;
		waveTickTimeOver = false;
		waveStartTime = false;
	}

	public void resetEnemyIndex() {
		currentEnemyIndex = 0;
	}

	public boolean isTimeForNewEnemy() {
		return enemySpawnTick >= enemySpawnTickLimit;
	}

	public boolean isThereMoreEnemiesInWave() {
		return currentEnemyIndex < waves.get(currentWaveIndex).getEnemies().size();
	}

	public boolean isThereMoreWaves() {
		return currentWaveIndex < waves.size();
	}

	public boolean isWaveTimerOver() {
		return this.waveTickTimeOver;
	}

	public ArrayList<Wave> getWaves() {
		return this.waves;
	}

	public int getNextEnemy() {
		enemySpawnTick = 0;
		return waves.get(currentWaveIndex).getEnemy(currentEnemyIndex++);
	}

	public void setStartWaveTimer() {
		waveStartTime = true;
	}

}
