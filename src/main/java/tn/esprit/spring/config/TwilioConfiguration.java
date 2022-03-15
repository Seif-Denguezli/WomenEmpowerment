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
		return "AC2c4063cee0d989aba25164d1f13fc2c2";
	}



	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}



	public String getAuthToken() {
		return "08ef089685403ff1885f0b27a099b71e";
	}



	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}











	public String getTrialNumber() {
		return trialNumber;
	}











	public void setTrialNumber(String trialNumber) {
		this.trialNumber = trialNumber;
	}











	












	

   
}
