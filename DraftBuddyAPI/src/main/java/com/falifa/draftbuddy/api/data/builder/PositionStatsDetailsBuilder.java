package com.falifa.draftbuddy.api.data.builder;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.data.model.RawPlayerStats;
import com.falifa.draftbuddy.api.model.PositionStatsDetails;

@Component
public class PositionStatsDetailsBuilder {
	
	private static final Logger log = LoggerFactory.getLogger(PositionStatsDetailsBuilder.class);
	
	public PositionStatsDetails buildPositionStatsDetails(RawPlayerStats stats) {
		PositionStatsDetails details = new PositionStatsDetails();
		for (Entry<String, String> stat : stats.getStats().entrySet()) {
			APIStatMapping statField = APIStatMapping.mapValue(stat.getKey());
			if (statField != null) {
				setFieldWithValue(details, statField, stat.getValue());
			}
		}
		return details;
	}

	private void setFieldWithValue(PositionStatsDetails details, APIStatMapping statField, String statVal) {
		if (statVal != null && (!statVal.equals("null"))) {
			try {
				switch(statField) {
					case 	TOTAL_POINTS : details.setTotalPointsScored(Double.valueOf(statVal)); break;
					case PASS_ATT : details.setPassAttempts(Integer.valueOf(statVal)); break;
					case PASS_CMP : details.setPassCompletions(Integer.valueOf(statVal)); break;
					case PASS_YRD : details.setPassYards(Integer.valueOf(statVal)); break;
					case PASS_TD :  details.setPassTouchdowns(Integer.valueOf(statVal)); break; 
					case PASS_INT :  details.setInterceptions(Integer.valueOf(statVal)); break;
					case PASS_SCK :  details.setSacks(Integer.valueOf(statVal)); break;
					case RUSH_ATT :  details.setRushAttempts(Integer.valueOf(statVal)); break;
					case RUSH_YRD :  details.setRushYards(Integer.valueOf(statVal)); break;
					case RUSH_TD :  details.setRushTouchdowns(Integer.valueOf(statVal)); break;
					case REC_CMP :  details.setReceptions(Integer.valueOf(statVal)); break;
					case REC_YRD :  details.setReceivingYards(Integer.valueOf(statVal)); break;
					case REC_TD :  details.setReceivingTouchdowns(Integer.valueOf(statVal)); break;
					case XP_CMP :  details.setExtraPoints(Integer.valueOf(statVal)); break;
					case FG_0_20 :  details.setFg_0_20(Integer.valueOf(statVal)); break;
					case FG_20_30 :  details.setFg_20_30(Integer.valueOf(statVal)); break;
					case FG_30_40 :  details.setFg_30_40(Integer.valueOf(statVal)); break;
					case FG_40_50 :  details.setFg_40_50(Integer.valueOf(statVal)); break;
					case FG_50_PLUS :  details.setFg_50_plus(Integer.valueOf(statVal)); break;
				}
			} catch (NumberFormatException e) {
				log.error("ERROR ::NumberFormatException when populating player stats: " + statVal);
			}
		}
	}

}
