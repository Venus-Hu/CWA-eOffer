package apiPackage;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiRequestUtility extends APIUtility {

	final public String baseURL = "https://gateway-am.test.apiss.mcaas.fcs.gsa.gov/eoffer/test/offerapi/modservice/modFiles/";

	private RequestSpecification request;
	private Response response;
	private JSONObject requestParams;
	private JSONArray innerBodyArray;
	private JSONObject innerParam;

	/**
	 * this method will set a base for any api request
	 * 
	 * @throws IOException
	 */
	public void specifyBaseURI() throws IOException {

		RestAssured.baseURI = (baseURL);

		request = RestAssured.given();

		requestParams = new JSONObject();
		innerBodyArray = new JSONArray();
		innerParam = new JSONObject();

	}

	public void specifyBaseURIWithBasicAuth(String userName, String password) {

		RestAssured.baseURI = (baseURL);

		request = RestAssured.given().auth().basic(userName, password);

		requestParams = new JSONObject();
		innerBodyArray = new JSONArray();
		innerParam = new JSONObject();
	}

	/**
	 * this method will set request header with given key - value (e.g.
	 * "content-type", "application/json")
	 * 
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public void setRequestHeader(String key, String value) {
		request.headers(key, value);
	}

	/**
	 * this method will set cookie with given String name and String value
	 * 
	 * @param name
	 * @param value
	 */
	public void setRequestCookie(String name, String value) {
		request.cookies(name, value);
	}

	/**
	 * this method will set request body with given key - value as a string (e.g.
	 * "storeId", "1108")
	 * 
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void setRequestBody(String key, String value) {
		requestParams.put(key, value);
	}

	/**
	 * this method will set request body with long integer
	 * 
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void setRequestBody(String key, long value) {
		requestParams.put(key, value);
	}

	/**
	 * this method will set request body with key - value as an int (e.g.
	 * "quantity", 1)
	 * 
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void setRequestBody(String key, int value) {
		requestParams.put(key, value);
	}

	/**
	 * this method will set request body with double (e.g. "amount", 10.0)
	 * 
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void setRequestBody(String key, double value) {
		requestParams.put(key, value);
	}

	/**
	 * this method will set body with key and value as boolean (e.g "shipping":
	 * true)
	 * 
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void setRequestBody(String key, boolean value) {
		requestParams.put(key, value);
	}

	/**
	 * this method will take bulk entries as a String and put it in the request body
	 * 
	 * @param body
	 */
	public void setBulkRequestBody(String bodyString) {
		request.body(bodyString);
	}

	/**
	 * this method will send api request with given method name and request uri
	 * signature (e.g "POST", "/carts")
	 * 
	 * @param methodname
	 * @param endPoint
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void sendAPIRequest(String methodname, String endPoint) throws IOException {

		request.body(requestParams.toJSONString());
		request.given().log().everything();

		switch (methodname.toUpperCase()) {
		case "PATCH":
			response = request.patch(endPoint);
			break;
		case "POST":
			response = request.post(endPoint);
			break;
		case "PUT":
			response = request.put(endPoint);
			break;
		case "DELETE":
			response = request.delete(endPoint);
			break;
		default:
			response = request.get(endPoint);
			break;

		}

	}

	/**
	 * this method will send api request with given method name and request uri
	 * signature (e.g "POST", "/carts")
	 * 
	 * @param methodname
	 * @param endPoint
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Response sendAPIRequestWithResponse(String methodname, String endPoint) throws IOException {

		request.body(requestParams);
//		request.given().log().everything();

		switch (methodname.toUpperCase()) {
		case "PATCH":
			response = request.patch(endPoint);
			break;
		case "POST":
			response = request.post(endPoint);
			break;
		case "PUT":
			response = request.put(endPoint);
			break;
		case "DELETE":
			response = request.delete(endPoint);
			break;
		default:
			response = request.get(endPoint);
			break;

		}
		return response;

	}
	
	public void showAPIDetails() {
		request.given().log().everything();
	}

	/**
	 * this method will return status code int of the api call (e.g 200, 201....)
	 * 
	 * @throws IOException
	 */
	public int validateStatusCode(int code) throws IOException {

		int statusCode = response.getStatusCode();
		if (statusCode != code) {
			System.out.println("*****Response OnFailure***********\n" + convertBodyresponseToString());
			getPrintAllHeadersFromResponse();
			Assert.assertEquals(statusCode, code);
		}
		return statusCode;
	}

	/**
	 * this method will get json value by given json path as a String (e.g
	 * "stores[0].name")
	 * 
	 * @param jsonPath
	 * @return
	 * @throws IOException
	 */
	public String getBodyWithJsonPath(String jsonPath) {

		JsonPath jsonPathEvaluator = response.jsonPath();
		String jsonValue = jsonPathEvaluator.get(jsonPath).toString();
		return jsonValue;
	}

	/**
	 * this method will get json value as an int by given json path as a String (e.g
	 * "stores[0].pageNumber")
	 * 
	 * @param jsonPath
	 * @return
	 */
	public int getBodyIntWithJsonPath(String jsonPath) {

		JsonPath jsonPathEvaluator = response.jsonPath();
		int jsonValue = jsonPathEvaluator.get(jsonPath);
		return jsonValue;
	}

	/**
	 * this method will get json value as double by given json path as a String (e.g
	 * "stores[0].price")
	 * 
	 * @param jsonPath
	 * @return
	 */
	public double getBodyDoubleWithJsonPath(String jsonPath) {

		JsonPath jsonPathEvaluator = response.jsonPath();
		double jsonValue = jsonPathEvaluator.get(jsonPath);
		return jsonValue;
	}

	/**
	 * this method will get float value from the response e.g. 35.0f
	 * 
	 * @param jsonPath
	 * @return
	 */
	public float getBodyfloatWithJsonPath(String jsonPath) {

		JsonPath jsonPathEvaluator = response.jsonPath();
		float jsonValue = jsonPathEvaluator.get(jsonPath);
		return jsonValue;
	}

	/**
	 * this method will get json value as long by given json path as a String
	 * 
	 * @param jsonPath
	 * @return
	 */
	public long getBodyLongWithJsonPath(String jsonPath) {

		JsonPath jsonPathEvaluator = response.jsonPath();
		long jsonValue = jsonPathEvaluator.get(jsonPath);
		return jsonValue;
	}

	/**
	 * this method will return a List String of multiple values of the body (e.g
	 * ("stores[0,1,2].name")
	 * 
	 * @param jsonPath
	 * @return
	 */
	public List<String> getBodyListOfJsonPath(String jsonPath) {

		JsonPath jsonPathEvaluator = response.jsonPath();
		List<String> jsonValue = jsonPathEvaluator.get(jsonPath);
		return jsonValue;
	}

	/**
	 * this method will validate if text is present in the response body (e.g "Pick
	 * up in Sacramento")
	 * 
	 * @param text
	 * @throws IOException
	 */
	public void validateBodyContains(String text) throws IOException {
		String responseBody = response.getBody().asString();
		Assert.assertEquals("response body contains " + text, responseBody.toLowerCase().contains(text.toLowerCase()),
				true);
	}

	/**
	 * this method will validate expected value for given jsonPath in the body (e.g
	 * "stores[0].name","Sacramento (Arden)"
	 * 
	 * @param jsonPath
	 * @param expectedResult
	 * @throws IOException
	 */
	public void validateBodyValue(String jsonPath, String expectedResult) throws IOException {
		JsonPath jsonPathEvaluator = response.jsonPath();
		String value = jsonPathEvaluator.get(jsonPath);
		Assert.assertEquals(value, expectedResult);
	}

	/**
	 * this method will validate expected value for given json path in the body for
	 * int (e.g "stores[0].id", 123)
	 * 
	 * @param jsonPath
	 * @param expectedResult
	 * @throws IOException
	 */
	public void validateBodyValue(String jsonPath, int expectedResult) throws IOException {
		JsonPath jsonPathEvaluator = response.jsonPath();
		int value = jsonPathEvaluator.get(jsonPath);
		Assert.assertEquals(value, expectedResult);
	}

	/**
	 * this method will validate Double value from the body
	 * 
	 * @param jsonPath
	 * @param expectedResult
	 * @throws IOException
	 */
	public void validateBodyValue(String jsonPath, float expectedResult) throws IOException {
		JsonPath jsonPathEvaluator = response.jsonPath();
		float value = jsonPathEvaluator.get(jsonPath);
		Assert.assertEquals(value, expectedResult);
	}

	/**
	 * this method will validate double values e.g. price - 12.99
	 * 
	 * @param jsonPath
	 * @param expectedResult
	 * @throws IOException
	 */
	public void validateBodyValue(String jsonPath, double expectedResult) throws IOException {
		JsonPath jsonPathEvaluator = response.jsonPath();
		double value = jsonPathEvaluator.get(jsonPath);
		Assert.assertEquals(value, expectedResult);
	}

	/**
	 * this method will validate long integer in the response body (e.g. delivery
	 * slot id)
	 * 
	 * @param jsonPath
	 * @param expectedResult
	 * @throws IOException
	 */
	public void validateBodyValue(String jsonPath, long expectedResult) throws IOException {
		JsonPath jsonPathEvaluator = response.jsonPath();
		long value = jsonPathEvaluator.get(jsonPath);
		Assert.assertEquals(value, expectedResult);
	}

	/**
	 * * this method will validate long integer in the response body (e.g.
	 * true/false)
	 * 
	 * @param jsonPath
	 * @param expectedResult
	 * @throws IOException
	 */
	public void validateBodyValue(String jsonPath, boolean expectedResult) throws IOException {
		JsonPath jsonPathEvaluator = response.jsonPath();
		boolean value = jsonPathEvaluator.get(jsonPath);
		Assert.assertEquals(value, expectedResult);
	}

	/**
	 * this method will return headers (e.g "x-access-token")
	 * 
	 * @param keyName
	 * @return
	 */
	public String getHeaders(String keyName) {

		Headers allHeaders = response.headers();
		String headerValue = allHeaders.getValue(keyName);
		return headerValue;
	}

	/**
	 * This method will print all headers from the response
	 * 
	 * @return
	 */
	public Headers getPrintAllHeadersFromResponse() {
		Headers allHeaders = response.headers();
		for (Header header : allHeaders) {
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
		}
		return allHeaders;
	}

	/**
	 * this method will validate header value by given key name and expected value
	 * (e.g "x-cart-identifier", '12345')
	 * 
	 * @param headerKey
	 * @param expectedResult
	 * @throws IOException
	 */
	public void validateHeaderValue(String headerKey, String expectedResult) throws IOException {

		Headers allHeaders = response.headers();
		String headerValue = allHeaders.getValue(headerKey);
		Assert.assertEquals(headerValue, expectedResult);
	}

	/**
	 * this method will set nested node -- inner body
	 * 
	 * @param keyName
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void setInnerBody(String keyName, String value) {

		innerParam.put(keyName, value);
	}

	/**
	 * this method will add nested node to the request body with given key
	 * 
	 * @param keyName
	 */
	@SuppressWarnings("unchecked")
	public void addNestedNodesToTheBody(String keyName) {

		innerBodyArray.add(innerParam);
		requestParams.put(keyName, innerBodyArray);
	}

	/**
	 * this method will clear request body in case new body if needed for another
	 * request
	 * 
	 * @throws IOException
	 */
	public void clearRequestBody() throws IOException {
//		requestParams = new JSONObject();
//		request = RestAssured.given();
		specifyBaseURI();
	}

	/**
	 * this method will clear inner body
	 */
	public void clearInnerBody() {
		innerBodyArray = new JSONArray();
		innerParam = new JSONObject();
	}

	/**
	 * this method will return response as a String
	 * 
	 * @param omsresponse
	 * @return
	 */
	public String convertBodyresponseToString() {

		return response.asString();
	}

	/**
	 * queryParams - needs testing before use --- !
	 */
	public void setRequestQueryParam(String keyName, String valueName) {
		request.queryParam(keyName, valueName);
	}

	public String appendurl(String url) {

		int index = baseURL.indexOf(".com/");
		String newUrl = baseURL.substring(0, index + 5) + url;
		return newUrl;
	}

	public double getResponseTimeInSeconds() throws IOException {

		long responseTimeMil = response.getTime();
		double responseTimeSec = (responseTimeMil / 1000);
		return responseTimeSec;
	}
	
	/**
	 * this method will generate random number with given area code
	 * @return
	 * @throws IOException 
	 */
	public String generateRandomNumberForCCP(){

		Random rand = new Random();
		int num2 = rand.nextInt(900) + 1000;
		return num2+"";
	}

}
