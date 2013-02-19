package com.carrotgarden.jenkins.tester;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;
import hudson.util.Secret;

import java.io.IOException;

import org.kohsuke.github.GitHub;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

/**
 * Credentials to access GitHub.
 */
public class TesterCredential extends AbstractDescribableImpl<TesterCredential> {
	public final String username;
	public final Secret password;

	@DataBoundConstructor
	public TesterCredential(final String username, final Secret password) {
		this.username = username;
		this.password = password;
	}

	public GitHub login() throws IOException {
		return GitHub.connect(username, null, password.getPlainText());
	}

	@Extension
	public static class TesterCredentialDescriptor extends Descriptor<TesterCredential> {

		@Override
		public String getDisplayName() {
			return ""; // unused
		}

		public FormValidation doValidate( //
				@QueryParameter final String username, //
				@QueryParameter final Secret password //
		) throws IOException {
			if (GitHub.connect(username, null, Secret.toString(password))
					.isCredentialValid()) {
				return FormValidation.ok("Verified");
			} else {
				return FormValidation.error("Failed to validate the account");
			}
		}

	}

}
