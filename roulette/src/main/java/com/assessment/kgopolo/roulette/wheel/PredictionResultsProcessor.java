package com.assessment.kgopolo.roulette.wheel;


import com.assessment.kgopolo.roulette.prediction.PlayerPrediction;
import com.assessment.kgopolo.roulette.prediction.Prediction;
import com.assessment.kgopolo.roulette.player.Player;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Component
class PredictionResultsProcessor {
	void processResults(int winningNumber, Set<PlayerPrediction> playerPredictions) {
		playerPredictions.forEach(playerBet -> {
			Player player = playerBet.getPlayer();
			Prediction prediction = playerBet.getBet();

			Optional<BigDecimal> winnings = prediction.getWinnings(winningNumber);
			updateBetStatus(prediction, winnings);
			updatePlayerStatistics(player, prediction, winnings);
		});
	}

	private void updateBetStatus(Prediction prediction, Optional<BigDecimal> winnings) {
		prediction.getWinnings().set(Optional.of(winnings.orElse(BigDecimal.ZERO)));
		prediction.getOutcome().set(Optional.of(winnings.map(amount -> Prediction.Outcome.WIN).orElse(Prediction.Outcome.LOSE)));
	}

	private void updatePlayerStatistics(Player player, Prediction prediction, Optional<BigDecimal> winnings) {
		player.getTotalWin().updateAndGet(curWin -> curWin.add(winnings.orElse(BigDecimal.ZERO)));
		player.getTotalBet().updateAndGet(curTotal -> curTotal.add(prediction.getBetAmount()));
	}
}
