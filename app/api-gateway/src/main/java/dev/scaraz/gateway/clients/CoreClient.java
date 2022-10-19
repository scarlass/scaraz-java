package dev.scaraz.gateway.clients;

import dev.scaraz.common.dto.response.api.ApiEntryDTO;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.List;

public interface CoreClient {

    @GET("/internal/routes")
    Response<List<ApiEntryDTO>> getAllEntries(@Header("Authorization") String token);

}
