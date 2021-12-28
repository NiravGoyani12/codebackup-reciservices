package com.rcibanque.rcidirect.services.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class DAOUtils {

	private DAOUtils() {}


	private static final Map<Character, String> USER_TO_DATABASE_ESCAPES = new HashMap<>();

	static {
		USER_TO_DATABASE_ESCAPES.put('[', "[[]");
		USER_TO_DATABASE_ESCAPES.put(']', "[]]");
		USER_TO_DATABASE_ESCAPES.put('_', "[_]");
		USER_TO_DATABASE_ESCAPES.put('-', "[-]");
		USER_TO_DATABASE_ESCAPES.put('%', "[%]");
		USER_TO_DATABASE_ESCAPES.put('*', "%");
	}


	public static final String escapeDatabaseSpecialCharacters(String pString) {
		StringBuilder sb = new StringBuilder();

		if (StringUtils.isNotBlank(pString)) {
			for(int i=0; i<pString.length(); i++) {
				char character = pString.charAt(i);

				String escapedCharacter = new String(new char[]{character});
				for(Entry<Character, String> escape : USER_TO_DATABASE_ESCAPES.entrySet()) {
					if(character == escape.getKey().charValue()) {
						escapedCharacter = escape.getValue();
						break;
					}
				}
				sb.append(escapedCharacter);
			}
		}

		return sb.toString();
	}


}
