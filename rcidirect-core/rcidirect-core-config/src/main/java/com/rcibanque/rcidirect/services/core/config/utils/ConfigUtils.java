package com.rcibanque.rcidirect.services.core.config.utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rcibanque.rcidirect.services.core.config.domain.Market;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;

/**
 * Access to <i>configuration files</i><br/>
 * <ul>
 * <li>The <i>external configuration directory</i> path (production) is provided as a VM argument (<b>rcidirect.external.config.dir</b>)<br/></li>
 * <li>The <i>market</i> (uk, be, etc.) is provided as a VM argument (<b>rcidirect.market</b>)<br/></li>
 * </ul>
 */
public final class ConfigUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);


	private ConfigUtils() {}


	private static final String KEY_EXTERNAL_CONFIG_DIRECTORY = "rcidirect.external.config.dir";

	private static final String KEY_MARKET = "rcidirect.market";

	private static Market MARKET = null;


	/**
	 * Returns the <i>configuration file</i> whose name is provided.<br/>
	 *
	 * @param pFileName File name
	 * @return The file, or null if it does not exist or if neither <i>market</i> nor <i>external configuration directory</i> properties are undefined.
	 */
	public static final URI getConfigurationFile(String pFileName) {

		//   - default module classpath files are there for documentation purpose only (empty values)
		//   - at runtime, external folder files are used
		//   - for development or testing purpose (building), market-specific classpath folder files are used

		URI configurationFile = null;

		String externalConfigurationDirectoryPath = getExternalConfigurationDirectoryPath();

		if(StringUtils.isNotEmpty(externalConfigurationDirectoryPath)) {

			File configurationFileFile = new File(externalConfigurationDirectoryPath);
			if(StringUtils.isNotEmpty(pFileName)) {
				configurationFileFile = new File(configurationFileFile, pFileName);
			}

			if(! configurationFileFile.exists()) {
				LOGGER.error("External configuration file does not exist ({})", configurationFileFile.getAbsolutePath());
			}
			else {
				configurationFile = configurationFileFile.toURI();
			}
		}
		else {

			String configurationFilePath = "/configuration/" + getMarket().name() + "/";
			if(StringUtils.isNotEmpty(pFileName)) {
				configurationFilePath += pFileName;
			}
			URL configurationFileURL = ConfigUtils.class.getResource(configurationFilePath);
			if(configurationFileURL == null) {
				LOGGER.error("Market-specific configuration file does not exist ({})", configurationFilePath);
			}
			else {
				try {
					configurationFile = configurationFileURL.toURI();
				}
				catch (URISyntaxException e) {
					LOGGER.warn("Invalid market-specific configuration file ({})", configurationFilePath);
				}
			}
		}

		return configurationFile;
	}


	/**
	 * @return The <i>market</i> property value
	 * @see #isMarket(String...)
	 */
	public static final Market getMarket() {

		if(MARKET == null) {

			String marketValue = System.getProperty(KEY_MARKET);
			if(StringUtils.isNotEmpty(marketValue)) {

				for(Market testedMarket : Market.values()) {
					if(testedMarket.name().equalsIgnoreCase(marketValue)) {
						MARKET = testedMarket;
						break;
					}
				}
			}

			if(MARKET == null) {
				throw new IllegalStateException("Invalid market: " + marketValue);
			}
		}

		return MARKET;
	}

	/**
	 * @param pMarkets Possible markets
	 * @return True if the current market is one of the possible markets
	 */
	public static boolean isMarket(Market...pMarkets) {
		boolean res = false;
		Market market = getMarket();
		for(Market testedMarket : IteratorUtils.nullableIterable(pMarkets)) {
			if(market.equals(testedMarket)) {
				res = true;
				break;
			}
		}
		return res;
	}


	/**
	 * @return The <i>external configuration directory</i> property value
	 */
	private static String getExternalConfigurationDirectoryPath() {

		return System.getProperty(KEY_EXTERNAL_CONFIG_DIRECTORY);
	}

}

