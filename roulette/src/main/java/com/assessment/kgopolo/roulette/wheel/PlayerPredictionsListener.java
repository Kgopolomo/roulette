package com.assessment.kgopolo.roulette.wheel;


import com.assessment.kgopolo.roulette.prediction.PlayerPrediction;
import com.assessment.kgopolo.roulette.prediction.PlayerPredictionsStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;

@Component
class PlayerPredictionsListener {
	@Autowired
	private PlayerPredictionsStorage playerPredictionsStorage;

	@Autowired
	private Iterator<PlayerPrediction> playerBetIterator;

	@PostConstruct
	void acceptBets() {
		new Thread(this::processBets).start();
	}

	private void processBets() {
		playerBetIterator.forEachRemaining(playerPredictionsStorage::addBet);
	}
}
