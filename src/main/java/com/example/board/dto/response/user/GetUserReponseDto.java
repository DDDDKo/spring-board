package com.example.board.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.board.dto.response.ResponseCode;
import com.example.board.dto.response.ResponseDto;
import com.example.board.dto.response.ResponseMessage;
import com.example.board.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetUserReponseDto extends ResponseDto{ //ResponseDto를 확장하고 나머지 더 보내줄것 생성
    private String email;
    private String nickname;
    private String profileImage;

    private GetUserReponseDto(UserEntity userEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.email = userEntity.getEmail();
        this.nickname =userEntity.getNickname();
        this.profileImage = userEntity.getProfileImageUrl();
    }

    public static ResponseEntity<GetUserReponseDto> success(UserEntity userEntity){
        GetUserReponseDto body = new GetUserReponseDto(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
