package com.nypunya.lms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nypunya.lms.entity.User;
import com.nypunya.lms.entity.Role;
import com.nypunya.lms.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

class UserServiceTest {
    @Test void missingUserFailsWithControlledBusinessError(){
        UserRepository repo=mock(UserRepository.class);
        when(repo.findById(999L)).thenReturn(java.util.Optional.empty());
        UserService service=new UserService(repo);
        assertThrows(IllegalArgumentException.class, () -> service.get(999L));
    }
    @Test void roleFilterUsesRepositoryPagination(){
        UserRepository repo=mock(UserRepository.class);
        when(repo.findByRole(eq("STUDENT"), any(Pageable.class))).thenReturn(Page.empty());
        UserService service=new UserService(repo);
        assertTrue(service.list(Role.STUDENT, PageRequest.of(0,20)).isEmpty());
        verify(repo).findByRole(eq("STUDENT"), any(Pageable.class));
    }
}
