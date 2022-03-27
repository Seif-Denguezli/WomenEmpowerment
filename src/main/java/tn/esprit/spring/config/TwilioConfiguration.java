package tn.esprit.spring.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("twilio")
public class TwilioConfiguration {
	private String accountSid;
    private String authToken;
    private String trialNumber;

    public TwilioConfiguration() {

    }



	



	



	public String getAccountSid() {
		return "AC9e63e60f8d5b9ac2d7a87fef33b53ab6";
	}



	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}



	public String getAuthToken() {
		return "334fc4f57c9d84c6f168a06757811eac";
	}



	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}











	public String getTrialNumber() {
		return "+13514449201";
	}











	public void setTrialNumber(String trialNumber) {
		this.trialNumber = trialNumber;
	}











	












	

   
}
