package com.assessment.kgopolo.roulette.prediction;


import java.util.Set;

public interface PlayerPredictionsStorage {
	void addBet(PlayerPrediction playerPrediction);

	Set<PlayerPrediction> grabCurrentGameBets();
}
