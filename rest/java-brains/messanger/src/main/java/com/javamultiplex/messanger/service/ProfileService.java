package com.javamultiplex.messanger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.javamultiplex.messanger.database.DatabaseClass;
import com.javamultiplex.messanger.model.Profile;

public class ProfileService {

	private static Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	
	public ProfileService() {

		profiles.put("agarwalr", new Profile(1, "agarwalr", "Rohit", "Agarwal"));
		profiles.put("agarwalg", new Profile(2, "agarwalg", "Raj", "Agarwal"));
	
	}

	public List<Profile> getAllProfiles() {

		return new ArrayList<>(profiles.values());
	}

	public Profile getProfile(String profileName){
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile){
		profile.setId(profiles.size()+1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile){
		if(profile.getProfileName().isEmpty()){
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeProfile(String profileName){
	
        return profiles.remove(profileName);
	}
	
}
