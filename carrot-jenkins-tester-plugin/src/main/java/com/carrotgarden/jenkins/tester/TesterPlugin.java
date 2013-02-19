package com.carrotgarden.jenkins.tester;

import hudson.Extension;
import hudson.Plugin;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Hudson;

import java.util.logging.Logger;

@Extension
public class TesterPlugin extends Plugin implements Describable<TesterPlugin> {

	@Extension
	public static final class PluginDescriptor extends Descriptor<TesterPlugin> {
		@Override
		public String getDisplayName() {
			return "Tester Plugin";
		}
	}

	private final static Logger log = Logger.getLogger(TesterPlugin.class
			.getName());

	public static TesterPlugin get() {
		return Hudson.getInstance().getPlugin(TesterPlugin.class);
	}

	public PluginDescriptor getDescriptor() {
		return (PluginDescriptor) Hudson.getInstance().getDescriptorOrDie(
				getClass());
	}

	@Override
	public void start() throws Exception {
		super.start();
		load();
		log.info("Starting carrot-tester plugin.");
	}

	@Override
	public void stop() throws Exception {
		log.info("Stopping carrot-tester plugin.");
	}

}
