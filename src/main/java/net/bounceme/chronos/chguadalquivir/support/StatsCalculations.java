package net.bounceme.chronos.chguadalquivir.support;

import java.util.List;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.chguadalquivir.model.Numerical;

@Component
public class StatsCalculations {
	
	/**
	 * @param executions
	 * @return
	 */
	public Double calculateAverage(List<Numerical> values) {
		Double average = 0.0;
		for (Numerical number : values) {
			average += number.getNumber();
		}

		return average / values.size();
	}
	
	/**
	 * @param values
	 * @param average
	 * @return
	 */
	public Double calculateDeviation(List<Numerical> values, Double average) {
		Double sum = 0.0;
		
		for (Numerical number : values) {
			sum += Math.pow(number.getNumber() - average, 2);
		}
		
		return Math.sqrt(sum / values.size());
	}
	
	/**
	 * @param deviation
	 * @return
	 */
	public Double calculateVariation(Double deviation) {
		return Math.pow(deviation, 2);
	}
}
