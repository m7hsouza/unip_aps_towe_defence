package events;

import java.util.ArrayList;

public class Wave {
	private ArrayList<Integer> enemies;

	public Wave(ArrayList<Integer> enemies) {
		this.enemies = enemies;
	}

	public ArrayList<Integer> getEnemies() {
		return this.enemies;
	}

	public int getEnemy(int enemyIndex) {
		return this.enemies.get(enemyIndex);
	}
}
