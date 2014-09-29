package Shoutbox;

import java.util.Date;

public class UserInfo {
	private String userId[] = new String[3];
	private String userToken[] = new String[3];
	private Date userLiveDate[] = new Date[3];

	// UserInfo(String id[],String token[]){
	UserInfo(String id[], String token[], Date liveDate[]) {
		for (int i = 0; i < userId.length; i++) {
			this.userId[i]=new String();
			this.userId[i] = id[i];
		}
		// this.userId=id;
		for (int i = 0; i < userToken.length; i++){
			this.userToken[i]=new String();
			this.userToken[i] = token[i];}
		// this.userToken=token;
		for (int i = 0; i < userLiveDate.length; i++){
			
			this.userLiveDate[i] = liveDate[i];
			}
		// this.userLiveDate=liveDate;
	}

	public String getID(int index) {
		return (this.userId[index]);
	}

	public String getToken(int index) {
		return (this.userToken[index]);
	}

	public Date getLiveDate(int index) {
		return (this.userLiveDate[index]);
	}

	public void setLiveDate(int index, Date liveDate) {
		this.userLiveDate[index] = liveDate;
		
	}

}
