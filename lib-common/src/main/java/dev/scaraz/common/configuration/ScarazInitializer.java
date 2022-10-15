package dev.scaraz.common.configuration;

import dev.scaraz.common.utils.AppConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

import java.util.*;

public class ScarazInitializer {

    private static final String SPRING_PROFILES_DEFAULT = "spring.profiles.default";

    private ScarazInitializer() {
    }

    private static final ScarazInitializer ins = new ScarazInitializer();

    private void ifHasBothProdAndDev(List<String> profiles) {
        if (profiles.contains(AppConstants.SPRING_PROFILE_DEVELOPMENT) && profiles.contains(AppConstants.SPRING_PROFILE_PRODUCTION))
            throw new IllegalStateException("You have miss configured your application! It should not run " +
                    "with both the 'dev' and 'prod' profiles at the same time.");
    }

    private void ifHasBothCloudAndDev(List<String> profiles) {
        if (profiles.contains(AppConstants.SPRING_PROFILE_DEVELOPMENT) && profiles.contains(AppConstants.SPRING_PROFILE_CLOUD))
            throw new IllegalStateException("You have miss configured your application! It should not " +
                    "run with both the 'dev' and 'cloud' profiles at the same time.");
    }

    public static void checkActiveProfile(Environment env) {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        ins.ifHasBothProdAndDev(profiles);
        ins.ifHasBothCloudAndDev(profiles);
    }

    public static void setDefaultProfile(SpringApplication app) {
        String runtime = System.getenv("PROFILE_ENV");
        HashMap<String, Object> props = new HashMap<>();

        props.put(SPRING_PROFILES_DEFAULT, Objects.requireNonNullElse(
                runtime, AppConstants.SPRING_PROFILE_DEVELOPMENT));

        app.setDefaultProperties(props);
    }
}
