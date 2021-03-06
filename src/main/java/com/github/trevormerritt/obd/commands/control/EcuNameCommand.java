/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.pires.obd.commands.control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.PersistentCommand;
import com.github.pires.obd.enums.AvailableCommandNames;

public class EcuNameCommand extends PersistentCommand{
	
	String ecuName = "";
	
	public EcuNameCommand() {
		super("09 0A");
	}
	
	public EcuNameCommand(ObdCommand other) {
		super(other);
	}

	@Override
	protected void performCalculations() {
		final String result = getResult();
		String workingData;
		
		if (result.contains(":")) {// CAN(ISO-15765) protocol.
			//0170:490A0145434D1:002D456E67696E2:65436F6E74726F3:6C000000000000
			workingData = result.replaceAll(".:", "").substring(9);// 9 is xxx490A01, xxx is bytes of information to

		} else {// ISO9141-2, KWP2000 Fast and KWP2000 5Kbps (ISO15031) protocols.
			workingData = result.replaceAll("490A0.", "");
		}

		ecuName = VinCommand.convertHexToString(workingData).replaceAll("[\u0000-\u001f]", "");

	}

	@Override
	public String getFormattedResult() {
		return String.valueOf(ecuName);
	}

	@Override
	public String getCalculatedResult() {
		return String.valueOf(ecuName);
	}

	@Override
	public String getName() {
		return AvailableCommandNames.ECU_NAME.getValue();
	}

	@Override
	protected void fillBuffer() {
		// do nothing
	}
	
	

}
