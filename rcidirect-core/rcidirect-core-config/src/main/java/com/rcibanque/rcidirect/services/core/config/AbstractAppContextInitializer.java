package com.rcibanque.rcidirect.services.core.config;

import java.io.IOException;
import java.net.URI;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import com.rcibanque.rcidirect.services.core.config.utils.ConfigUtils;


public abstract class AbstractAppContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {


	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAppContextInitializer.class);


	@Override
	public final void initialize(ConfigurableApplicationContext pAppContext) {
		LOGGER.info("Application context initialization");

		ConfigurableEnvironment environment = pAppContext.getEnvironment();

		LOGGER.info("Configuration directory: {}", ConfigUtils.getConfigurationFile(null));
		LOGGER.info("Market: {}", ConfigUtils.getMarket());

		// ----------------------------
		// Logging
		// ----------------------------
		URI log4jConfigurationFile = ConfigUtils.getConfigurationFile(getLog4jConfigurationFileName());
		if(log4jConfigurationFile != null) {

			try {
				LOGGER.info("Registering log4j configuration file ({})", log4jConfigurationFile);

				PropertyConfigurator.configure(log4jConfigurationFile.toURL());
			}
			catch (IOException ex) {
				LOGGER.error("Invalid log4j configuration file", ex);
			}
		}

		// ----------------------------
		// General configuration
		// ----------------------------
		for(String generalConfigurationFileName : getGeneralConfigurationFileNames()) {

			URI configurationFile = ConfigUtils.getConfigurationFile(generalConfigurationFileName);
			if(configurationFile != null) {

				try {
					LOGGER.info("Registering configuration file ({})", configurationFile);

					// Highest priority for external configuration file
					environment.getPropertySources().addFirst(new ResourcePropertySource(configurationFile.toString()));
				}
				catch (IOException ex) {
					LOGGER.error("Invalid configuration file", ex);
				}
			}
		}


		// ----------------------------
		// Additional configuration
		// ----------------------------
		initializeMore(pAppContext);

		LOGGER.info("Application context initialization - done");
	}


	protected abstract String[] getGeneralConfigurationFileNames();

	protected abstract String getLog4jConfigurationFileName();

	protected abstract void initializeMore(ConfigurableApplicationContext pAppContext);


}