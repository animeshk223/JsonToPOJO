package utility;

import java.io.IOException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class Utils {
	
	public Response apiResponse;
	
	public void setApiResponseAsString(Response response) {
        apiResponse = response;
    }
	
	public String getApiResponseAsString() {
        return apiResponse.asString();
    }
	
	public <T> T responseToPojo(Class<T> type) throws Exception {
        try {
            return new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(getApiResponseAsString(), type);
        } catch (IOException ioex) {
            throw new Exception("Response Received did not match the expected Response Format POJO: " + type.getName() + ioex);
        }
    }
}
