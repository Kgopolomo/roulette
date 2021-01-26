package com.assessment.kgopolo.roulette.prediction;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Optional;

@Component
class PlayerPredictionsFromConsoleIteratorImpl implements Iterator<PlayerPrediction>, DisposableBean {
	private final PlayerPredictionFactory playerPredictionFactory;

	private final BufferedReader betsBufferedReader;

	@Autowired
	public PlayerPredictionsFromConsoleIteratorImpl(PlayerPredictionFactory playerPredictionFactory) {
		this(new BufferedReader(new InputStreamReader(System.in)), playerPredictionFactory);
	}

	private PlayerPredictionsFromConsoleIteratorImpl(BufferedReader bufferedReader, PlayerPredictionFactory playerPredictionFactory) {
		this.betsBufferedReader = bufferedReader;
		this.playerPredictionFactory = playerPredictionFactory;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public PlayerPrediction next() {
		try {
			String input = betsBufferedReader.readLine();
			Optional<PlayerPrediction> playerBet = playerPredictionFactory.createPlayerBet(input);
			return playerBet.orElseGet(this::next);
		} catch (IOException | NumberFormatException e) {
			return next();
		}
	}

	@Override
	public void destroy() throws Exception {
		betsBufferedReader.close();
	}
}
