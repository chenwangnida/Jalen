/*
 * Copyright (c) 2014, Inria, University Lille 1.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Affero General Public License v3.0
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/agpl-3.0.html
 *
 * Author : Adel Noureddine
 */

package jalen.sensors.disk;

import jalen.Agent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;

public class DiskSensorProc implements DiskSensorsInterface {

	/**
	 * Process PID to monitor
	 */
	private int pid;

	/**
	 * Number of bytes read and written by PID to disk
	 */
	private Double readBytes, writeBytes;

	/**
	 * Path to the io file where disk data is stored
	 * Linux-systems only
	 */
	private String diskIOPath;

	/**
	 * Constructor
	 * @param pid Process PID to monitor
	 */
	public DiskSensorProc(int pid) {
		this.pid = pid;
		this.readBytes = 0.0;
		this.writeBytes = 0.0;
		this.diskIOPath = "/proc/" + pid + "/io";
	}

	@Override
	public Double[] getProcesDiskReadWriteBytes() {
		Double[] results = new Double[2];

		try {
			List<String> allLines = Files.readAllLines(Paths.get(this.diskIOPath), StandardCharsets.UTF_8);

			results[0] = Double.valueOf(allLines.get(4).split(" ")[1]) - this.readBytes;
			results[1] = Double.valueOf(allLines.get(5).split(" ")[1]) - Double.valueOf(allLines.get(6).split(" ")[1]) - this.writeBytes;

			this.readBytes = Double.valueOf(allLines.get(4).split(" ")[1]);
			this.writeBytes = Double.valueOf(allLines.get(5).split(" ")[1]) - Double.valueOf(allLines.get(6).split(" ")[1]);
		} catch (IOException e) {
			Agent.LOGGER.log(Level.WARNING, e.getMessage());
		}

		return results;
	}

}