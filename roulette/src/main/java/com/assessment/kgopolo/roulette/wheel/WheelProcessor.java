package com.assessment.kgopolo.roulette.wheel;


import com.assessment.kgopolo.roulette.prediction.PlayerPrediction;
import com.assessment.kgopolo.roulette.prediction.PlayerPredictionsStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.Set;

@Component
class WheelProcessor {
	private static final PrimitiveIterator.OfInt WINNING_NUMBERS = new Random().ints(0, 36).iterator();
	private static final String BALL_LANDING_TIME_PLACEHOLDER = "${ball.landing.time}";

	@Autowired
	private PlayerPredictionsStorage playerPredictionsStorage;

	@Autowired
	private PredictionResultsProcessor predictionResultsProcessor;

	@Autowired
	private WheelResultsPublisher wheelResultsPublisher;

	@Scheduled(fixedRateString = BALL_LANDING_TIME_PLACEHOLDER, initialDelayString = BALL_LANDING_TIME_PLACEHOLDER)
	void landBall() {
		int winningNumber = nextWinningNumber();
		Set<PlayerPrediction> playerPredictions = playerPredictionsStorage.grabCurrentGameBets();

		predictionResultsProcessor.processResults(winningNumber, playerPredictions);
		wheelResultsPublisher.publishCurrentGameResults(winningNumber, playerPredictions);
		wheelResultsPublisher.publishTotalResults();
	}

	private int nextWinningNumber() {
		return WINNING_NUMBERS.nextInt();
	}

}
