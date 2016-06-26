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
    public final IntegrationTest integrationTest = new IntegrationTest();
    public final Kanbanflow kanbanflow = new Kanbanflow();

    public BuildState getBuildState() {
        return buildState;
    }

    public IntegrationTest getIntegrationTest() {
        return integrationTest;
    }

    public Kanbanflow getKanbanflow() {
        return kanbanflow;
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


    public static class IntegrationTest {

        public String cron;

        public String url;

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Kanbanflow {

        public String cron;

        public final List<BoardConfig> boardConfigs = new ArrayList<>();

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public List<BoardConfig> getBoardConfigs() {
            return boardConfigs;
        }

        public static class BoardConfig {
            public String boardName;
            public String url;
            public String token;
            public final List<Integer> columnNumbers = new ArrayList<>();

            public String getBoardName() {
                return boardName;
            }

            public void setBoardName(String boardName) {
                this.boardName = boardName;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public List<Integer> getColumnNumbers() {
                return columnNumbers;
            }
        }
    }
}
