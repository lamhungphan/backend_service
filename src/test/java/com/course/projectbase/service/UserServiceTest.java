package com.course.projectbase.service;

import com.course.projectbase.common.Gender;
import com.course.projectbase.common.UserStatus;
import com.course.projectbase.common.UserType;
import com.course.projectbase.controller.request.AddressRequest;
import com.course.projectbase.controller.request.UserCreationRequest;
import com.course.projectbase.controller.request.UserUpdateRequest;
import com.course.projectbase.controller.response.UserPageResponse;
import com.course.projectbase.controller.response.UserResponse;
import com.course.projectbase.exception.ResourceNotFoundException;
import com.course.projectbase.model.UserEntity;
import com.course.projectbase.repository.AddressRepository;
import com.course.projectbase.repository.UserRepository;
import com.course.projectbase.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    // Khái niệm STUBBING trong unit test:
    // khi tạo Mock cho 1 phương thức chỉ nên là Mock cho chính phương thức đó.
    // Nếu trong phương thức gọi các service khác -> khởi tạo các Mock tương ứng

    private @Mock UserRepository userRepository;
    private @Mock AddressRepository addressRepository;
    private @Mock PasswordEncoder passwordEncoder;
    private @Mock EmailService emailService;


    private UserService userService;
    private static UserEntity johnDoe;
    private static UserEntity janeDoe;

    @BeforeAll
    static void beforeAll() {
        johnDoe = new UserEntity();
        johnDoe.setId(1L);
        johnDoe.setFirstName("John");
        johnDoe.setLastName("Doe");
        johnDoe.setGender(Gender.MALE);
        johnDoe.setBirthday(new Date());
        johnDoe.setEmail("john@doe.com");
        johnDoe.setPhone("0898400532");
        johnDoe.setUsername("Emulator1");
        johnDoe.setPassword("password");
        johnDoe.setType(UserType.USER);
        johnDoe.setStatus(UserStatus.ACTIVE);

        janeDoe = new UserEntity();
        janeDoe = new UserEntity();
        janeDoe.setId(2L);
        janeDoe.setFirstName("Jane");
        janeDoe.setLastName("Doe");
        janeDoe.setGender(Gender.FEMALE);
        janeDoe.setBirthday(new Date());
        janeDoe.setEmail("jane@doe.com");
        janeDoe.setPhone("0123456789");
        janeDoe.setUsername("Emulator2");
        janeDoe.setPassword("password");
        janeDoe.setType(UserType.USER);
        janeDoe.setStatus(UserStatus.ACTIVE);
    }

    @BeforeEach
    void setUp() {
        // Khởi tạo bước triển khai là UserService
        userService = new UserServiceImpl(userRepository, addressRepository, passwordEncoder, emailService);

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testGetListUsers_Success() {
        // Giả lập phương thức
        Page<UserEntity> userPage = new PageImpl<>(Arrays.asList(johnDoe, janeDoe));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        // Gọi phương thức cần test
        UserPageResponse result = userService.findAll(null, null, 0, 10);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testSearchUsers_Success() {
        Page<UserEntity> userPage = new PageImpl<>(Arrays.asList(johnDoe, janeDoe));
        when(userRepository.searchByKeyword(any(), any(Pageable.class))).thenReturn(userPage);

        UserPageResponse result = userService.findAll("John", null, 0, 10);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements()); // Nếu expected = 1 sẽ lỗi, Tại sao?
    }

    @Test
    void testGetListUsers_Empty() {
        Page<UserEntity> userPage = new PageImpl<>(List.of());
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        UserPageResponse result = userService.findAll(null, null, 0, 10);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(johnDoe));

        UserResponse result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetUserById_Failure() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> userService.findById(2L));
        assertNotNull("User not found", exception.getMessage());
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail("johnDoe@gmail.com")).thenReturn(johnDoe);

        UserResponse result = userService.findByEmail("johnDoe@gmail.com");

        Assertions.assertNotNull(result);
        assertEquals("johnDoe@gmail.com", result.getEmail());
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername("johnDoe")).thenReturn(johnDoe);

        // Gọi phương thức cần kiểm tra
        UserResponse result = userService.findByUsername("johnDoe");

        Assertions.assertNotNull(result);
        assertEquals("johnDoe", result.getUsername());
    }

    @Test
    void testSave_Success() {
        // Giả lập hành vi của UserRepository
        when(userRepository.save(any(UserEntity.class))).thenReturn(johnDoe);

        UserCreationRequest userCreationRequest = new UserCreationRequest();
        userCreationRequest.setFirstName("Tay");
        userCreationRequest.setLastName("Java");
        userCreationRequest.setGender(Gender.MALE);
        userCreationRequest.setBirthday(new Date());
        userCreationRequest.setEmail("quoctay87@gmail.com");
        userCreationRequest.setPhone("0975118228");
        userCreationRequest.setUsername("tayjava");

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setApartmentNumber("ApartmentNumber");
        addressRequest.setFloor("Floor");
        addressRequest.setBuilding("Building");
        addressRequest.setStreetNumber("StreetNumber");
        addressRequest.setStreet("Street");
        addressRequest.setCity("City");
        addressRequest.setCountry("Country");
        addressRequest.setAddressType(1);
        userCreationRequest.setAddresses(List.of(addressRequest));

        // Gọi phương thức cần kiểm tra
        long result = userService.save(userCreationRequest);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(1L, result);
    }

    @Test
    void testUpdate_Success() {
        Long userId = 2L;

        UserEntity updatedUser = new UserEntity();
        updatedUser.setId(userId);
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Smith");
        updatedUser.setGender(Gender.FEMALE);
        updatedUser.setBirthday(new Date());
        updatedUser.setEmail("janesmith@gmail.com");
        updatedUser.setPhone("0123456789");
        updatedUser.setUsername("janesmith");
        updatedUser.setType(UserType.USER);
        updatedUser.setStatus(UserStatus.ACTIVE);

        // Giả lập hành vi của UserRepository
        when(userRepository.findById(userId)).thenReturn(Optional.of(johnDoe));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);

        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setId(userId);
        updateRequest.setFirstName("Jane");
        updateRequest.setLastName("Smith");
        updateRequest.setGender(Gender.MALE);
        updateRequest.setBirthday(new Date());
        updateRequest.setEmail("janesmith@gmail.com");
        updateRequest.setPhone("0123456789");
        updateRequest.setUsername("janesmith");

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setApartmentNumber("ApartmentNumber");
        addressRequest.setFloor("Floor");
        addressRequest.setBuilding("Building");
        addressRequest.setStreetNumber("StreetNumber");
        addressRequest.setStreet("Street");
        addressRequest.setCity("City");
        addressRequest.setCountry("Country");
        addressRequest.setAddressType(1);
        updateRequest.setAddresses(List.of(addressRequest));

        // Gọi phương thức cần kiểm tra
        userService.update(updateRequest);

        UserResponse result = userService.findById(userId);

        assertEquals("janesmith", result.getUsername());
        assertEquals("janesmith@gmail.com", result.getEmail());
    }

    @Test
    void testDeleteUser_Success() {
        // Chuẩn bị dữ liệu
        Long userId = 1L;

        // Giả lập hành vi repository
        when(userRepository.findById(userId)).thenReturn(Optional.of(janeDoe));

        // Gọi phương thức cần kiểm tra
        userService.delete(userId);

        // Kiểm tra kết quả
        assertEquals(UserStatus.INACTIVE, janeDoe.getStatus());
        verify(userRepository, times(1)).save(janeDoe);
    }

    @Test
    void testUserNotFound_ThrowsException() {
        // Chuẩn bị dữ liệu
        Long userId = 1L;

        // Giả lập hành vi repository
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Gọi phương thức và kiểm tra ngoại lệ
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userService.delete(userId));

        // Kiểm tra nội dung ngoại lệ
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}
