package com.tekraj.schoolmanagement;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.tekraj.schoolmanagement.entity.User;
import com.tekraj.schoolmanagement.repository.UserRepository;
import com.tekraj.schoolmanagement.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
class UserServiceTest {

    @Mock
    private UserRepository userRepository; 

    @InjectMocks
    private UserService userService; 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  
    }

    @Test
    void testSoftDelete_UserExists() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user)); 

        // Act
        userService.softDelete(userId);

        // Assert
        verify(userRepository, times(1)).findById(userId); 
        verify(userRepository, times(1)).save(user); 
        assertNotNull(user.getDeletedAt(), "Deleted at should not be null"); 
        assertEquals(LocalDate.now(), user.getDeletedAt(), "Deleted at should be today's date");
    }

    @Test
    void testSoftDelete_UserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty()); 

        // Act
        userService.softDelete(userId);

        // Assert
        verify(userRepository, times(1)).findById(userId);  
        verify(userRepository, never()).save(any(User.class));  
    }

    @Test
    void testSaveUser() {
        // Arrange
        User user = new User();
        user.setId(1L);

        // Act
        userService.saveUser(user);

        // Assert
        verify(userRepository, times(1)).save(user);  
    }
}
