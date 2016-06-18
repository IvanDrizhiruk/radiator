package ua.dp.ardas.radiator.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import ua.dp.ardas.radiator.jobs.buils.state.BuildStateInstance;

import java.util.ArrayList;
import java.util.List;


/**
 * Properties specific to JHipster.
 * <p>
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "radiator", ignoreUnknownFields = false)
public class RadiatorProperties {

    public final BuildState buildState = new BuildState();

    public BuildState getBuildState() {
        return buildState;
    }

    public static class BuildState {

        public String cron;
        public String brokenSound;
        public String errorMessage;
        public String emailFormat;
        public final List<BuildStateInstance> instances = new ArrayList<>();
        public Authorisation auth;

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public String getBrokenSound() {
            return brokenSound;
        }

        public void setBrokenSound(String brokenSound) {
            this.brokenSound = brokenSound;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getEmailFormat() {
            return emailFormat;
        }

        public void setEmailFormat(String emailFormat) {
            this.emailFormat = emailFormat;
        }

        public List<BuildStateInstance> getInstances() {
            return instances;
        }

        public Authorisation getAuth() {
            return auth;
        }

        public void setAuth(Authorisation auth) {
            this.auth = auth;
        }

        public static class Authorisation {
            public String username;
            public String password;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }
        }
    }
}
