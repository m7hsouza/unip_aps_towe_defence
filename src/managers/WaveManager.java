package managers;

import java.util.ArrayList;
import java.util.Arrays;

import events.Wave;
import scenes.Playing;

public class WaveManager {

	private final Playing playing;

	private ArrayList<Wave> waves = new ArrayList<>();

	private final int enemySpawnTickLimit = 60 * 1;
	private int enemySpawnTick = enemySpawnTickLimit;

	private int currentEnemyIndex, currentWaveIndex;

	private final int waveTickLimit = 60 * 15;

	private int waveTick = 0;

	private boolean waveStartTimer, waveTickTimerOver;
	
	public WaveManager(Playing playing) {
		this.playing = playing;
		createWaves();
	}

	public void update() {
		if (enemySpawnTick < enemySpawnTickLimit)
			enemySpawnTick++;

		if (waveStartTimer) {
			waveTick++;
			if (waveTick >= waveTickLimit) {
				waveTickTimerOver = true;
			}
		}
	}
	
	public void increaseWave() {
		currentWaveIndex++;
		waveTickTimerOver = false;
		waveStartTimer = false;
	}

	public boolean isWaveTimerOver() {
		return waveTickTimerOver;
	}
	
	public void startWaveTimer() {
		waveStartTimer = true;
	}

	public int getNextEnemy() {
		enemySpawnTick = 0;
		return waves.get(currentWaveIndex).getEnemy(currentEnemyIndex++);
	}
	
	private void createWaves() {
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 0, 0, 0, 0, 0, 0, 0, 1))));
	}

	public ArrayList<Wave> getWaves() {
		return waves;
	}

	public boolean isTimeForNewEnemy() {
		return enemySpawnTick >= enemySpawnTickLimit;
	}

	public boolean isThereMoreEnemiesInWave() {
		return currentEnemyIndex < waves.get(currentWaveIndex).getEnemies().size();
	}
	
	public boolean isThereMoreWaves() {
		return currentWaveIndex + 1 < waves.size();
	}

	public void resetEnemyIndex() {
		currentEnemyIndex = 0;
	}

	public int getWaveIndex() {
		return currentWaveIndex;
	}

	public float getTimeLeft() {
		float ticksLeft = waveTickLimit - waveTick;
		return ticksLeft / 60.0f;
	}

	public boolean isWaveTimerStarted() {
		return waveStartTimer;
	}
}
