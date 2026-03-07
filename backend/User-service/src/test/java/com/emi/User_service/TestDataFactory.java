package com.emi.User_service;

import java.util.UUID;

import com.emi.User_service.requestDto.RequestReviewDto;
import com.emi.User_service.requestDto.UserRequestDto;

public class TestDataFactory {
    public static UserRequestDto validUserRequest() {
        return new UserRequestDto(
                "Himanshu Kushwaha",
                "https://cdn.library.com/users/profile123.png",
                "Passionate reader and tech enthusiast."
        );
    }
    
    public static RequestReviewDto validReviewRequest() {
    	return new RequestReviewDto(
    			UUID.fromString("e3a424d2-a524-4f65-bb94-e229f73e30fb"),
    			1,
    			"Very bad , i mean why u even made this shit");
    }


}
