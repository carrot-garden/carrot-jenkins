package com.carrotgarden.jenkins.tester;

import hudson.Extension;
import hudson.model.ManagementLink;
import hudson.model.RootAction;

import java.io.File;
import java.io.IOException;

import org.kohsuke.stapler.HttpRedirect;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * Creates the link on the "Manage Jenkins" page and handles all the web
 * requests.
 * 
 */
@Extension
public class TesterManager extends ManagementLink implements RootAction {

	public String getDisplayName() {
		return "Tester Manager";
	}

	/**
	 * Global side bar link.
	 */
	@Override
	public String getIconFileName() {
		return "plugin.png";
	}

	@Override
	public String getUrlName() {
		return "carrot-jenkins-tester-plugin";
	}

	/**
	 * @jelly property
	 */
	public static File getScriptDirectory() {
		return new File("./script-folder");
	}

	private boolean disableRemoteCatalog;

	public boolean disableRemoteCatalog() {
		return disableRemoteCatalog;
	}

	/**
	 * @jelly form
	 */
	public HttpResponse doSettingsAction(
			final StaplerRequest res, //
			final StaplerResponse rsp, //
			@QueryParameter("disableRemoteCatalog") final boolean disableRemoteCatalog, //
			@QueryParameter("allowRunScriptPermission") final boolean allowRunScriptPermission, //
			@QueryParameter("allowRunScriptEdit") final boolean allowRunScriptEdit //
	) throws IOException {

		this.disableRemoteCatalog = disableRemoteCatalog;

		System.err.println("XXX disableRemoteCatalog=" + disableRemoteCatalog);

		return new HttpRedirect("settings");

	}

}
