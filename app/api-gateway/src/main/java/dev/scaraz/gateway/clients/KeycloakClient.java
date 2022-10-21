package dev.scaraz.gateway.clients;

import com.fasterxml.jackson.databind.node.ObjectNode;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.Observer;

public interface KeycloakClient {
    @FormUrlEncoded
    @POST("protocol/openid-connect/token")
    Observable<Response<ObjectNode>> accessToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret);
}
