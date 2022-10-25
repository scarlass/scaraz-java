package dev.scaraz.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "scaraz.http",
        name = "enabled",
        havingValue = "true")
public class ScHttpConfiguration {

    private final ScHttpConfigurationProperties httpProperties;

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .writeTimeout(httpProperties.getWriteTimeout())
                .readTimeout(httpProperties.getReadTimeout())
                .callTimeout(httpProperties.getCallTimeout())
                .addInterceptor(loggingInterceptor())
                .build();
    }

    @Bean
    @ConditionalOnBean(ObjectMapper.class)
    public Retrofit.Builder retrofitBuilder(ObjectMapper om, OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(om));
    }

    private HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(httpProperties.getLogLevel());
        return interceptor;
    }

}
