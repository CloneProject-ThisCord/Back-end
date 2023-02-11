package com.hanghae.thiscord_clone.domain;

public class ProfileImages {

	private static final String[] IMAGES = {
		"https://discord99-buket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%B5%E1%84%87%E1%85%A9%E1%86%AB+%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B51.png",
		"https://discord99-buket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%B5%E1%84%87%E1%85%A9%E1%86%AB+%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B52.png",
		"https://discord99-buket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%B5%E1%84%87%E1%85%A9%E1%86%AB+%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B53.png",
		"https://discord99-buket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%B5%E1%84%87%E1%85%A9%E1%86%AB+%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B54.png",
		"https://discord99-buket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%B5%E1%84%87%E1%85%A9%E1%86%AB+%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B55.png"
	};

	public static String getProfileImage(int num) {
		if (num == 0 | num == 5) {
			return IMAGES[0];
		} else if (num == 1 || num == 6) {
			return IMAGES[1];
		} else if (num ==2 || num == 7) {
			return IMAGES[2];
		} else if (num == 3 || num == 8) {
			return IMAGES[3];
		} else {
			return IMAGES[4];
		}
	}
}
