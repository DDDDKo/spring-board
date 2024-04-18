package com.example.board.service.implementations;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.board.dto.request.user.PatchNicknameRequestDto;
import com.example.board.dto.response.ResponseDto;
import com.example.board.dto.response.user.GetUserReponseDto;
import com.example.board.entity.UserEntity;
import com.example.board.repository.UserRepository;
import com.example.board.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplmentation implements UserService {
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetUserReponseDto> getUser(String email){

        try{
            // 1. user 테이블에서 email에 해당하는 user 조회 (존재하면 인스턴스를 가져옴 존재하지 않으면 null을 가져옴)
            // (email -> 조회 결과 인스턴스) 
            // SELECT * FROM user WHERE eamil= :email
            // findByEmail(eamil)
            UserEntity userEntity =  userRepository.findByEmail(email);

            // 2. 조회 결과에 따라 반환 결정

            // 2-1 null 이면 존재하지 않는 유저 응답처리
            if(userEntity == null) return ResponseDto.notExistUser();
            // 3. 조회 결과 데이터를 성공 응답
            return GetUserReponseDto.success(userEntity);

        }catch(Exception exception) {
            // 1-1 조회 처리 중 데이터베이스관련 예외가 발생하면 데이터베이스 에러 응답처리
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

    }

    @Override
    public ResponseEntity<ResponseDto> patchNickname (PatchNicknameRequestDto dto) {
        try{

            String nickname = dto.getNickname();
            boolean isExistedNickname = userRepository.existsByNickname(nickname);
            if(isExistedNickname) return ResponseDto.duplicateNickname();

            String email = dto.getEmail();
            UserEntity userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return ResponseDto.notExistUser();

            userEntity.setNickname(nickname);
            userRepository.save(userEntity);
            return ResponseDto.success();
            
        }catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

}
