package com.Project_Management.Service;

import com.Project_Management.Dto.RefreshTokenDto;
import com.Project_Management.Dto.UserDto;

public interface RefreshTokenService {
    public RefreshTokenDto createRefreshToken(String username);

    public RefreshTokenDto findByToken(String token);

    public RefreshTokenDto verifyToken(RefreshTokenDto refreshTokenDto);

    UserDto getUser(RefreshTokenDto tokenDto);
}
