package com.carrotgarden.jenkins.tester;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;

import java.io.IOException;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Sample {@link Builder}.
 * 
 * <p>
 * When the user configures the project and enables this builder,
 * {@link TesterBuildStepDescriptor#newInstance(StaplerRequest)} is invoked and
 * a new {@link TesterBuilder} is created. The created instance is persisted to
 * the project configuration XML by using XStream, so this allows you to use
 * instance fields (like {@link #name}) to remember the configuration.
 * 
 * <p>
 * When a build is performed, the
 * {@link #perform(AbstractBuild, Launcher, BuildListener)} method will be
 * invoked.
 * 
 */
public class TesterBuilder extends Builder {

	private final String name;

	/**
	 * // Fields in config.jelly must match the parameter names in the //
	 * "DataBoundConstructor"
	 */
	@DataBoundConstructor
	public TesterBuilder(final String name) {
		this.name = name;
	}

	/**
	 * We'll use this from the <tt>config.jelly</tt>.
	 */
	public String getName() {
		return name;
	}

	@Override
	public boolean perform(final AbstractBuild build, final Launcher launcher,
			final BuildListener listener) {

		/**
		 * // This is where you 'build' the project. // Since this is a dummy,
		 * we just say 'hello world' and call that a // build.
		 */

		/**
		 * // This also shows how you can consult the global configuration of
		 * the // builder
		 */

		if (getDescriptor().getUseFrench())
			listener.getLogger().println("Bonjour, " + name + "!");
		else
			listener.getLogger().println("Hello, " + name + "!");
		return true;
	}

	/**
	 * // Overridden for better type safety. // If your plugin doesn't really
	 * define any property on Descriptor, // you don't have to do this.
	 */
	@Override
	public TesterBuildStepDescriptor getDescriptor() {
		return (TesterBuildStepDescriptor) super.getDescriptor();
	}

	/**
	 * Descriptor for {@link TesterBuilder}. Used as a singleton. The class is
	 * marked as public so that it can be accessed from views.
	 * 
	 * // This indicates to Jenkins that this is an implementation of an
	 * extension // point.
	 */
	@Extension
	public static final class TesterBuildStepDescriptor extends
			BuildStepDescriptor<Builder> {
		/**
		 * To persist global configuration information, simply store it in a
		 * field and call save().
		 * 
		 * <p>
		 * If you don't want fields to be persisted, use <tt>transient</tt>.
		 */
		private boolean useFrench;

		/**
		 * Performs on-the-fly validation of the form field 'name'.
		 * 
		 * @param value
		 *            This parameter receives the value that the user has typed.
		 * @return Indicates the outcome of the validation. This is sent to the
		 *         browser.
		 */
		public FormValidation doCheckName(//
				@QueryParameter final String value //
		) throws IOException, ServletException {
			if (value.length() == 0)
				return FormValidation.error("Please set a name");
			if (value.length() < 4)
				return FormValidation.warning("Isn't the name too short?");
			return FormValidation.ok();
		}

		@Override
		public boolean isApplicable(
				final Class<? extends AbstractProject> aClass) {
			// Indicates that this builder can be used with all kinds of project
			// types
			return true;
		}

		/**
		 * This human readable name is used in the configuration screen.
		 */
		@Override
		public String getDisplayName() {
			return "Carrot 3 : say hello world";
		}

		@Override
		public boolean configure(final StaplerRequest request,
				final JSONObject formData) throws FormException {

			/**
			 * // To persist global configuration information, // set that to
			 * properties and call save().
			 */
			useFrench = formData.getBoolean("useFrench");

			/**
			 * // Can also use req.bindJSON(this, formData); // (easier when
			 * there are many fields; need set* methods for this, // like
			 * setUseFrench)
			 */
			save();

			return super.configure(request, formData);

		}

		/**
		 * This method returns true if the global configuration says we should
		 * speak French.
		 * 
		 * The method name is bit awkward because global.jelly calls this method
		 * to determine the initial state of the checkbox by the naming
		 * convention.
		 */
		public boolean getUseFrench() {
			return useFrench;
		}

	}

}
