package com.assessment.kgopolo.roulette.prediction;


import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Component
class PlayerPredictionStorageImpl implements PlayerPredictionsStorage {
	private final AtomicReference<Set<PlayerPrediction>> playerBetsHolder = new AtomicReference<>(emptySet());

	@Override
	public void addBet(PlayerPrediction playerPrediction) {
		playerBetsHolder.updateAndGet(bets -> {
			bets.add(playerPrediction);
			return bets;
		});
	}

	@Override
	public Set<PlayerPrediction> grabCurrentGameBets() {
		return playerBetsHolder.getAndSet(emptySet());
	}

	private ConcurrentHashMap.KeySetView<PlayerPrediction, Boolean> emptySet() {
		return ConcurrentHashMap.newKeySet();
	}
}
