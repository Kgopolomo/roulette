package com.assessment.kgopolo.roulette.prediction;


import com.assessment.kgopolo.roulette.player.Player;

public class PlayerPrediction {
	private final Player player;
	private final Prediction prediction;

	public PlayerPrediction(Player player, Prediction prediction) {
		this.player = player;
		this.prediction = prediction;
	}

	public Player getPlayer() {
		return player;
	}

	public Prediction getBet() {
		return prediction;
	}
}
