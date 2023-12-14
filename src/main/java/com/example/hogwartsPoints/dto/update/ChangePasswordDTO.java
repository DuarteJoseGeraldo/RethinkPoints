package com.example.hogwartsPoints.dto.update;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {
    @NonNull
    String currentPassword;
    @NonNull
    String newPassword;
    @NonNull
    String newPasswordConfirm;
}
