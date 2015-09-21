package org.apache.camel.component.salesforce.internal;

import org.apache.camel.component.salesforce.internal.dto.LoginToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

public class LoginTokenTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(SessionIntegrationTest.class);
	
	@Test
	public void testLoginTokenWithUnknownFields() throws Exception {
		
		String salesforceOAuthResponse = "{\n" + 
				"    \"access_token\": \"00XXXXXXXXXXXX!ARMAQKg_lg_hGaRElvizVFBQHoCpvX8tzwGnROQ0_MDPXSceMeZHtm3JHkPmMhlgK0Km3rpJkwxwHInd_8o022KsDy.p4O.X\",\n" + 
				"    \"is_readonly\": \"false\",\n" + 
				"    \"signature\": \"XXXXXXXXXX+MYU+JrOXPSbpHa2ihMpSvUqow1iTPh7Q=\",\n" + 
				"    \"instance_url\": \"https://xxxxxxxx--xxxxxxx.cs5.my.salesforce.com\",\n" + 
				"    \"id\": \"https://test.salesforce.com/id/00DO00000054tO8MAI/005O0000001cmmdIAA\",\n" + 
				"    \"token_type\": \"Bearer\",\n" + 
				"    \"issued_at\": \"1442798068621\",\n" + 
				"    \"an_unrecognised_field\": \"foo\"\n" + 
				"}";
		ObjectMapper mapper = new ObjectMapper();
		Exception e = null;
		LoginToken token = null;
		try {
			token = mapper.readValue(salesforceOAuthResponse, LoginToken.class);
		} catch (Exception ex) {
			e = ex;
		}
		
		//assert ObjectMapper deserialized the SF OAuth response and returned a valid token back
		assertNotNull("An invalid token was returned" , token);
		//assert No exception was thrown during the JSON deserialization process
		assertNull("Exception was thrown during JSON deserialisation" , e);
		//assert one of the token fields
		assertEquals("false", token.getIsReadOnly());
		
	}

}
