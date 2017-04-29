/*
 * Copyright (c) 2014, Inria, University Lille 1.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Affero General Public License v3.0
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/agpl-3.0.html
 *
 * Author : Adel Noureddine
 */

package jalen;

import java.text.DecimalFormat;

public final class PowerModel {

	/**
	 * Private constructor
	 */
	private PowerModel() {}

	// Cycle duration in milliseconds
	private static Long cycleDuration = 0L;

	protected static Long getCycleDuration() {
		return PowerModel.cycleDuration;
	}
	
	/**
	 * Get CPU power for the current executing process in watt for the monitoring cycle
	 * @return CPU power of the current executing process in watt
	 */
	public static Double getProcessCPUPower() {
		return Agent.cpuFormula.getCPUPower();
	}

	/**
	 * Get disk power for the current executing process in watt for the monitoring cycle
	 * @return disk power of the current executing process in watt
	 */
	public static Double getProcessDiskPower() {
		if (OSValidator.isUnix())
			return Agent.diskFormula.getDiskPower();
		else
			return 0.0;
	}
	
	/**
	 * Calculate the last cycle duration (current time - last time cpu computation time)
	 */
	public static void computeCycleDuration() {
		// Calculate cycle duration
		PowerModel.cycleDuration = System.currentTimeMillis() - Agent.lastCPUComputationTime;
		Agent.lastCPUComputationTime = System.currentTimeMillis();
	}

	/**
	 * Two digit function to transform a double into a double with only two digits precision after .
	 */
	public static DecimalFormat twoDigit = new DecimalFormat("#,##0.00");

}
