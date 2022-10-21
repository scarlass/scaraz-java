package dev.scaraz.gateway.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.scaraz.common.configuration.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RetrofitConfiguration {

    private final AppProperties appProperties;

    private final ObjectMapper objectMapper;

    @Bean
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(appProperties.getRetrofit().getLogLevel());
        return interceptor;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        AppProperties.Retrofit retrofitProp = appProperties.getRetrofit();
        return new OkHttpClient.Builder()
                .writeTimeout(retrofitProp.getWriteTimeout())
                .readTimeout(retrofitProp.getReadTimeout())
                .callTimeout(retrofitProp.getCallTimeout())
                .addInterceptor(loggingInterceptor())
                .build();
    }

    @Bean
    public Retrofit.Builder retrofitBuilder(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper));
    }

}
